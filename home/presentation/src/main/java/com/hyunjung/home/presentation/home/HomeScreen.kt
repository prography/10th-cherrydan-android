package com.hyunjung.home.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyunjung.auth.presentation.LocalCherrydanContentColor
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.NotificationIcon
import com.hyunjung.core.presentation.designsystem.SearchIcon
import com.hyunjung.core.presentation.designsystem.component.CampaignItemContent
import com.hyunjung.core.presentation.designsystem.component.CampaignItemData
import com.hyunjung.core.presentation.designsystem.component.CherrydanScrollableTabRow
import com.hyunjung.core.presentation.designsystem.component.CherrydanScrollableTabRowDefaults
import com.hyunjung.core.presentation.designsystem.component.CherrydanScrollableTabRowDefaults.tabIndicatorOffset
import com.hyunjung.core.presentation.designsystem.component.CherrydanTab
import com.hyunjung.core.presentation.designsystem.component.CherrydanTabPosition
import com.hyunjung.core.presentation.designsystem.component.CherrydanTopAppBar
import com.hyunjung.core.presentation.designsystem.component.TopBarIconButton
import com.hyunjung.core.presentation.ui.R

@Composable
fun HomeScreenRoot(modifier: Modifier = Modifier) {
    HomeScreen(modifier = modifier)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: String = "FakeViewModel" // todo : 실제 ViewModel을 사용하세요.
) {
    HomeContent(modifier = modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
) {
    // todo : 실제 데이터는 ViewModel을 통해 가져오는 것이 좋습니다.
    val items = (1..29).map {
        CampaignItemData(
            id = it,
            label = "강남맛집",
            endDate = 6,
            title = "[와모야] 맛있는 효소 릴스 체험 $it",
            reward = "효소 1박스",
            totalApplyCount = 23,
            currentApplyCount = 12,
            type = "유튜브"
        )
    }

    // todo : viewmodel을 사용하여 상태를 관리하는 것이 좋습니다.
    var selectedCategory by remember { mutableStateOf(CategoryTab.All) }
    var selectedSort by remember { mutableStateOf(SortTab.Popular) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CherrydanTopAppBar(
                title = stringResource(id = R.string.splash_title),
                actions = {
                    TopBarIconButton(
                        imageVector = NotificationIcon,
                        contentDescription = "알림",
                        onClick = {}
                    )
                    TopBarIconButton(
                        imageVector = SearchIcon,
                        contentDescription = "검색",
                        onClick = {}
                    )
                }
            )
        },
        containerColor = CherrydanColors.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(104.dp)
                        .padding(16.dp)
                        .background(
                            color = CherrydanColors.MainPink2,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🔥 배너입니다",
                        style = CherrydanTypography.Title4,
                        color = CherrydanColors.PointBeige
                    )
                }
            }
            stickyHeader {
                Column(
                    modifier = Modifier.background(color = CherrydanColors.White)
                ) {
                    HomeFilter(
                        tabs = CategoryTab.entries.map { it.label },
                        selectedTabIndex = CategoryTab.entries.indexOf(selectedCategory),
                        selectedContentStyle = CherrydanTypography.Main3_B,
                        unSelectedContentedStyle = CherrydanTypography.Main3_R,
                        onTabSelected = { selectedCategory = CategoryTab.entries[it] },
                        edgePadding = 20.dp
                    )
                    Spacer(Modifier.height(16.dp))
                    HomeFilter(
                        tabs = SortTab.entries.map { it.label },
                        selectedTabIndex = SortTab.entries.indexOf(selectedSort),
                        onTabSelected = { selectedSort = SortTab.entries[it] },
                        indicator = null
                    )
                }
            }

            twoColumnItemsIndexed(items) { index, item ->
                CampaignItemContent(
                    item = item,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun HomeFilter(
    tabs: List<String>,
    selectedTabIndex: Int,
    selectedContentStyle: TextStyle = CherrydanTypography.Main4_B.copy(
        fontSize = 14.sp
    ),
    unSelectedContentedStyle: TextStyle = CherrydanTypography.Main4_R,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    indicator: (@Composable (tabPositions: List<CherrydanTabPosition>) -> Unit)? =
        @Composable { tabPositions ->
            CherrydanScrollableTabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
    edgePadding: Dp = 16.dp
) {
    Box(modifier = modifier) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
            color = CherrydanColors.PointBeige
        )
        CherrydanScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = CherrydanColors.MainPink3,
            indicator = indicator ?: {},
            edgePadding = edgePadding,
        ) {
            tabs.forEachIndexed { index, label ->
                val selected = index == selectedTabIndex
                CherrydanTab(
                    selected = selected,
                    onClick = { if (!selected) onTabSelected(index) },
                ) {
                    Text(
                        text = label,
                        color = LocalCherrydanContentColor.current,
                        style = if (selected) selectedContentStyle else unSelectedContentedStyle,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    CherrydanTheme {
        HomeContent()
    }
}

private inline fun <T> LazyListScope.twoColumnItemsIndexed(
    items: List<T>,
    noinline key: ((index: Int, item: T) -> Any)? = null,
    crossinline contentType: (index: Int, item: T) -> Any? = { _, _ -> null },
    crossinline itemContent: @Composable LazyItemScope.(index: Int, item: T) -> Unit
) {
    val rowCount = (items.size + 1) / 2

    items(
        count = rowCount,
        key = if (key != null) {
            { rowIndex -> key(rowIndex * 2, items[rowIndex * 2]) }
        } else null,
        contentType = { rowIndex -> contentType(rowIndex * 2, items[rowIndex * 2]) }
    ) { rowIndex ->
        val leftIndex = rowIndex * 2
        val rightIndex = leftIndex + 1
        val leftItem = items[leftIndex]
        val rightItem = items.getOrNull(rightIndex)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(Modifier.weight(1f)) {
                itemContent(leftIndex, leftItem)
            }
            Box(Modifier.weight(1f)) {
                if (rightItem != null) {
                    itemContent(rightIndex, rightItem)
                }
            }
        }
    }
}