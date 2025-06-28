package com.hyunjung.core.presentation.designsystem.component

import com.hyunjung.core.presentation.designsystem.component.CherrydanFixedTabRowDefaults.fixedTabIndicatorOffset
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.hyunjung.auth.presentation.LocalCherrydanContentColor
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun CherrydanFixedTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = CherrydanFixedTabRowDefaults.ContainerColor,
    contentColor: Color = CherrydanFixedTabRowDefaults.ContentColor,
    indicator: @Composable (tabPositions: List<CherrydanTabPosition>) -> Unit =
        @Composable { tabPositions ->
            CherrydanFixedTabRowDefaults.Indicator(
                Modifier.fixedTabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
    tabs: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        contentColor = contentColor
    ) {
        SubcomposeLayout(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val tabMeasurables = subcompose(FixedTabSlots.Tabs, tabs)
            val tabCount = tabMeasurables.size
            val tabWidth = if (tabCount > 0) constraints.maxWidth / tabCount else 0

            val tabConstraints = constraints.copy(
                minWidth = tabWidth,
                maxWidth = tabWidth
            )

            val tabPlaceables = tabMeasurables.map { measurable ->
                measurable.measure(tabConstraints)
            }

            val layoutHeight = tabPlaceables.maxOfOrNull { it.height } ?: 0
            val layoutWidth = constraints.maxWidth

            layout(layoutWidth, layoutHeight) {
                val tabPositions = mutableListOf<CherrydanTabPosition>()

                tabPlaceables.fastForEachIndexed { index, placeable ->
                    val left = index * tabWidth
                    placeable.placeRelative(left, 0)

                    tabPositions.add(
                        CherrydanTabPosition(
                            left = left.toDp(),
                            width = tabWidth.toDp(),
                            contentWidth = (tabWidth - HorizontalTextPadding.roundToPx() * 2).toDp()
                        )
                    )
                }

                subcompose(FixedTabSlots.Indicator) {
                    indicator(tabPositions)
                }.fastForEach { measurable ->
                    val indicatorPlaceable = measurable.measure(
                        Constraints.fixed(layoutWidth, layoutHeight)
                    )
                    indicatorPlaceable.placeRelative(0, 0)
                }
            }
        }
    }
}

private enum class FixedTabSlots {
    Tabs,
    Indicator
}

@Preview(showBackground = true)
@Composable
private fun CherrydanFixedTabsPreview() {
    val tabs = listOf("활동", "맞춤형")
    val selectedIndex = 0

    CherrydanTheme {
        CherrydanFixedTabRow(selectedTabIndex = selectedIndex) {
            tabs.forEachIndexed { index, label ->
                val selected = index == selectedIndex
                CherrydanTab(
                    selected = selected,
                    onClick = {}
                ) {
                    Text(
                        text = label,
                        color = LocalCherrydanContentColor.current,
                        style = if (selected) CherrydanTypography.Main3_B else CherrydanTypography.Main3_R
                    )
                }
            }
        }
    }
}

object CherrydanFixedTabRowDefaults {
    val ContainerColor: Color = Color.Transparent
    val ContentColor: Color = CherrydanColors.MainPink3

    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = 2.dp,
        color: Color = ContentColor
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .height(height)
                .background(color = color)
        )
    }

    fun Modifier.fixedTabIndicatorOffset(currentTabPosition: CherrydanTabPosition): Modifier =
        composed(
            inspectorInfo = debugInspectorInfo {
                name = "fixedTabIndicatorOffset"
                value = currentTabPosition
            }
        ) {
            val currentTabWidth by animateDpAsState(
                targetValue = currentTabPosition.width,
                animationSpec = FixedTabRowIndicatorSpec,
                label = "indicator-width"
            )
            val indicatorOffset by animateDpAsState(
                targetValue = currentTabPosition.left,
                animationSpec = FixedTabRowIndicatorSpec,
                label = "indicator-offset"
            )
            fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset { IntOffset(indicatorOffset.roundToPx(), 0) }
                .width(currentTabWidth)
        }
}

private val FixedTabRowIndicatorSpec: AnimationSpec<Dp> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)

private val HorizontalTextPadding: Dp = 16.dp