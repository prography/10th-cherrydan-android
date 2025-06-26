package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.NavCategorySelectedIcon
import com.hyunjung.core.presentation.designsystem.NavCategoryUnselectedIcon
import com.hyunjung.core.presentation.designsystem.NavHomeSelectedIcon
import com.hyunjung.core.presentation.designsystem.NavHomeUnselectedIcon
import com.hyunjung.core.presentation.designsystem.NavMyCampaignsSelectedIcon
import com.hyunjung.core.presentation.designsystem.NavMyCampaignsUnselectedIcon
import com.hyunjung.core.presentation.designsystem.NavMyPageSelectedIcon
import com.hyunjung.core.presentation.designsystem.NavMyPageUnselectedIcon
import com.hyunjung.core.presentation.designsystem.NavNoticeSelectedIcon
import com.hyunjung.core.presentation.designsystem.NavNoticeUnselectedIcon
import com.hyunjung.core.presentation.designsystem.util.topBorder

/**
 * Bottom Navigation Item 데이터 클래스
 *
 * @param title 네비게이션 아이템의 제목
 * @param unselectedIcon 선택되지 않았을 때의 아이콘
 * @param selectedIcon 선택되었을 때의 아이콘
 */
data class BottomNavItem(
    val title: String,
    val unselectedIcon: @Composable () -> ImageVector,
    val selectedIcon: @Composable () -> ImageVector
)

/**
 * CherryDan 앱의 Bottom Navigation 컴포넌트
 *
 * @param items 네비게이션 아이템 리스트
 * @param selectedIndex 현재 선택된 아이템의 인덱스 (0부터 시작)
 * @param onItemSelected 아이템이 선택되었을 때 호출되는 콜백 함수
 * @param modifier 추가적인 Modifier
 */
@Composable
fun CherrydanBottomNavigation(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .topBorder(
                color = CherrydanColors.PointBeige,
                height = 2f
            ),
        color = CherrydanColors.White,
        tonalElevation = 12.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                CherrydanBottomNavItem(
                    item = item,
                    isSelected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 개별 Bottom Navigation Item 컴포넌트
 *
 * @param item 네비게이션 아이템 데이터
 * @param isSelected 현재 선택된 상태인지 여부
 * @param onClick 클릭 시 호출되는 콜백 함수
 * @param modifier 추가적인 Modifier
 */
@Composable
private fun CherrydanBottomNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedColor = if (isSelected) {
        CherrydanColors.MainPink3
    } else {
        CherrydanColors.MainPink1
    }

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(top = 12.dp, bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.selectedIcon() else item.unselectedIcon(),
                tint = Color.Unspecified,
                contentDescription = item.title,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.title,
            style = CherrydanTypography.Main6_R,
            color = animatedColor,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CherrydanBottomNavigationPreview() {
    val sampleItems = listOf(
        BottomNavItem(
            title = "카테고리",
            unselectedIcon = { NavCategoryUnselectedIcon },
            selectedIcon = { NavCategorySelectedIcon }
        ),
        BottomNavItem(
            title = "체리단 소식",
            unselectedIcon = { NavNoticeUnselectedIcon },
            selectedIcon = { NavNoticeSelectedIcon }
        ),
        BottomNavItem(
            title = "홈",
            unselectedIcon = { NavHomeUnselectedIcon },
            selectedIcon = { NavHomeSelectedIcon }
        ),
        BottomNavItem(
            title = "내 체험단",
            unselectedIcon = { NavMyCampaignsUnselectedIcon },
            selectedIcon = { NavMyCampaignsSelectedIcon }
        ),
        BottomNavItem(
            title = "마이페이지",
            unselectedIcon = { NavMyPageUnselectedIcon },
            selectedIcon = { NavMyPageSelectedIcon }
        )
    )

    CherrydanTheme {
        CherrydanBottomNavigation(
            items = sampleItems,
            selectedIndex = 2, // 홈이 선택된 상태
            onItemSelected = { /* Preview에서는 동작 안함 */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CherrydanBottomNavigationUsageExample() {
    CherrydanTheme {
        Column {
            // 메인 콘텐츠 영역 (예시)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("메인 콘텐츠 영역")
            }

            // Bottom Navigation
            CherrydanBottomNavigation(
                items = listOf(
                    BottomNavItem(
                        title = "카테고리",
                        unselectedIcon = { NavCategoryUnselectedIcon },
                        selectedIcon = { NavCategorySelectedIcon }
                    ),
                    BottomNavItem(
                        title = "체리단 소식",
                        unselectedIcon = { NavNoticeUnselectedIcon },
                        selectedIcon = { NavNoticeSelectedIcon }
                    ),
                    BottomNavItem(
                        title = "홈",
                        unselectedIcon = { NavHomeUnselectedIcon },
                        selectedIcon = { NavHomeSelectedIcon }
                    ),
                    BottomNavItem(
                        title = "내 체험단",
                        unselectedIcon = { NavMyCampaignsUnselectedIcon },
                        selectedIcon = { NavMyCampaignsSelectedIcon }
                    ),
                    BottomNavItem(
                        title = "마이페이지",
                        unselectedIcon = { NavMyPageUnselectedIcon },
                        selectedIcon = { NavMyPageSelectedIcon }
                    )
                ),
                selectedIndex = 2,
                onItemSelected = { index ->
                    // 실제 앱에서는 여기에 네비게이션 로직이 들어감
                    println("Selected tab: $index")
                }
            )
        }
    }
}