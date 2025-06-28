package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFold
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import com.hyunjung.auth.presentation.LocalCherrydanContentColor
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.component.CherrydanScrollableTabRowDefaults.tabIndicatorOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CherrydanScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = CherrydanScrollableTabRowDefaults.ContainerColor,
    contentColor: Color = CherrydanScrollableTabRowDefaults.ContentColor,
    edgePadding: Dp = CherrydanScrollableTabRowDefaults.EdgePadding,
    tabSpacing: Dp = CherrydanScrollableTabRowDefaults.TabRowSpacing,
    indicator: @Composable (tabPositions: List<CherrydanTabPosition>) -> Unit =
        @Composable { tabPositions ->
            CherrydanScrollableTabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
    tabs: @Composable () -> Unit
) {
    ScrollableTabRowWithSubComposeImpl(
        selectedTabIndex = selectedTabIndex,
        indicator = indicator,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        edgePadding = edgePadding,
        tabs = tabs,
        tabSpacing = tabSpacing,
        scrollState = rememberScrollState()
    )
}

@Composable
private fun ScrollableTabRowWithSubComposeImpl(
    selectedTabIndex: Int,
    indicator: @Composable (tabPositions: List<CherrydanTabPosition>) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    edgePadding: Dp,
    tabs: @Composable () -> Unit,
    tabSpacing: Dp,
    scrollState: ScrollState,
) {
    Surface(modifier = modifier, color = containerColor, contentColor = contentColor) {
        val coroutineScope = rememberCoroutineScope()
        val scrollableTabData = remember(scrollState, coroutineScope) {
            ScrollableTabData(scrollState = scrollState, coroutineScope = coroutineScope)
        }
        SubcomposeLayout(
            Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.CenterStart)
                .horizontalScroll(scrollState)
                .selectableGroup()
                .clipToBounds()
        ) { constraints ->
            val padding = edgePadding.roundToPx()
            val spacing = tabSpacing.roundToPx()

            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)

            val layoutHeight =
                tabMeasurables.fastFold(initial = 0) { curr, measurable ->
                    maxOf(curr, measurable.maxIntrinsicHeight(Constraints.Infinity))
                }

            val tabConstraints =
                constraints.copy(
                    minHeight = layoutHeight,
                    maxHeight = layoutHeight,
                )

            val tabPlaceables = mutableListOf<Placeable>()
            val tabContentWidths = mutableListOf<Dp>()

            tabMeasurables.fastForEach {
                val placeable = it.measure(tabConstraints)
                var contentWidth =
                    minOf(it.maxIntrinsicWidth(placeable.height), placeable.width).toDp()
                contentWidth -= HorizontalTextPadding * 2
                tabPlaceables.add(placeable)
                tabContentWidths.add(contentWidth)
            }

            val layoutWidth = padding * 2 +
                    tabPlaceables.sumOf { it.width } +
                    spacing * (tabPlaceables.size - 1)

            layout(layoutWidth, layoutHeight) {
                val tabPositions = mutableListOf<CherrydanTabPosition>()
                var left = padding
                tabPlaceables.fastForEachIndexed { index, placeable ->
                    placeable.placeRelative(left, 0)
                    tabPositions.add(
                        CherrydanTabPosition(
                            left = left.toDp(),
                            width = placeable.width.toDp(),
                            contentWidth = tabContentWidths[index]
                        )
                    )
                    left += placeable.width + spacing
                }

                subcompose(TabSlots.Indicator) { indicator(tabPositions) }
                    .fastForEach {
                        it.measure(Constraints.fixed(layoutWidth, layoutHeight)).placeRelative(0, 0)
                    }

                scrollableTabData.onLaidOut(
                    density = this@SubcomposeLayout,
                    edgeOffset = padding,
                    tabPositions = tabPositions,
                    selectedTab = selectedTabIndex
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CherrydanTabsPreview() {
    val tabs = listOf("전체", "지역", "제품", "기자단", "SNS 플랫폼", "체험단 플랫폼")
    val selectedIndex = 0
    CherrydanTheme {
        CherrydanScrollableTabRow(selectedTabIndex = selectedIndex) {
            tabs.forEachIndexed { index, label ->
                val selected = index == selectedIndex
                CherrydanTab(
                    selected = selected,
                    onClick = {}
                ) {
                    Text(
                        text = label,
                        color = LocalCherrydanContentColor.current,
                        style = if (selected) CherrydanTypography.Main4_B else CherrydanTypography.Main4_R
                    )
                }
            }
        }
    }
}

object CherrydanScrollableTabRowDefaults {
    val TabRowSpacing: Dp = 24.dp
    val EdgePadding: Dp = 16.dp
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

    fun Modifier.tabIndicatorOffset(currentTabPosition: CherrydanTabPosition): Modifier =
        composed(
            inspectorInfo = debugInspectorInfo {
                name = "tabIndicatorOffset"
                value = currentTabPosition
            }
        ) {
            val currentTabWidth by animateDpAsState(
                targetValue = currentTabPosition.width,
                animationSpec = TabRowIndicatorSpec
            )
            val indicatorOffset by animateDpAsState(
                targetValue = currentTabPosition.left,
                animationSpec = TabRowIndicatorSpec
            )
            fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .offset { IntOffset(indicatorOffset.roundToPx(), 0) }
                .width(currentTabWidth)
        }
}

private enum class TabSlots {
    Tabs,
    Indicator
}

@Immutable
class CherrydanTabPosition internal constructor(val left: Dp, val width: Dp, val contentWidth: Dp) {

    val right: Dp
        get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CherrydanTabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false
        if (contentWidth != other.contentWidth) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + contentWidth.hashCode()
        return result
    }

    override fun toString(): String {
        return "CherrydanTabPosition(left=$left, right=$right, width=$width, contentWidth=$contentWidth)"
    }
}

private class ScrollableTabData(
    private val scrollState: ScrollState,
    private val coroutineScope: CoroutineScope
) {
    private var selectedTab: Int? = null

    fun onLaidOut(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<CherrydanTabPosition>,
        selectedTab: Int
    ) {
        if (this.selectedTab != selectedTab) {
            this.selectedTab = selectedTab
            tabPositions.getOrNull(selectedTab)?.let {
                val calculatedOffset = it.calculateTabOffset(density, edgeOffset, tabPositions)
                if (scrollState.value != calculatedOffset) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(
                            calculatedOffset,
                            ScrollableTabRowScrollSpec,
                        )
                    }
                }
            }
        }
    }

    private fun CherrydanTabPosition.calculateTabOffset(
        density: Density,
        edgeOffset: Int,
        tabPositions: List<CherrydanTabPosition>
    ): Int =
        with(density) {
            val totalTabRowWidth = tabPositions.last().right.roundToPx() + edgeOffset
            val visibleWidth = totalTabRowWidth - scrollState.maxValue
            val tabOffset = left.roundToPx()
            val scrollerCenter = visibleWidth / 2
            val tabWidth = width.roundToPx()
            val centeredTabOffset = tabOffset - (scrollerCenter - tabWidth / 2)
            val availableSpace = (totalTabRowWidth - visibleWidth).coerceAtLeast(0)
            return centeredTabOffset.coerceIn(0, availableSpace)
        }
}

private val TabRowIndicatorSpec: AnimationSpec<Dp> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)

private val ScrollableTabRowScrollSpec: AnimationSpec<Float> =
    tween(durationMillis = 250, easing = FastOutSlowInEasing)
private val HorizontalTextPadding: Dp = 16.dp