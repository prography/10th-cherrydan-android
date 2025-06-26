package com.hyunjung.home.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
import com.hyunjung.core.presentation.designsystem.component.BottomNavItem
import com.hyunjung.core.presentation.designsystem.component.CherrydanBottomNavigation
import com.hyunjung.core.presentation.ui.R
import com.hyunjung.home.presentation.category.CategoryScreen
import com.hyunjung.home.presentation.home.HomeScreen
import com.hyunjung.home.presentation.my_campaigns.MyCampaignsScreen
import com.hyunjung.home.presentation.my_page.MyPageScreen
import com.hyunjung.home.presentation.notice.NoticeScreen

object HomeNavRoute {
    const val CATEGORY = "category"
    const val NOTICE = "notice"
    const val HOME = "home"
    const val MY_CAMPAIGNS = "my_campaigns"
    const val MY_PAGE = "my_page"
}

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedIndex = when (currentRoute) {
        HomeNavRoute.CATEGORY -> 0
        HomeNavRoute.NOTICE -> 1
        HomeNavRoute.HOME -> 2
        HomeNavRoute.MY_CAMPAIGNS -> 3
        HomeNavRoute.MY_PAGE -> 4
        else -> 2
    }

    val bottomNavItems = listOf(
        BottomNavItem(
            title = stringResource(R.string.home_nav_category),
            unselectedIcon = { NavCategoryUnselectedIcon },
            selectedIcon = { NavCategorySelectedIcon }
        ),
        BottomNavItem(
            title = stringResource(R.string.home_nav_notice),
            unselectedIcon = { NavNoticeUnselectedIcon },
            selectedIcon = { NavNoticeSelectedIcon }
        ),
        BottomNavItem(
            title = stringResource(R.string.home_nav_home),
            unselectedIcon = { NavHomeUnselectedIcon },
            selectedIcon = { NavHomeSelectedIcon }
        ),
        BottomNavItem(
            title = stringResource(R.string.home_nav_my_campaigns),
            unselectedIcon = { NavMyCampaignsUnselectedIcon },
            selectedIcon = { NavMyCampaignsSelectedIcon }
        ),
        BottomNavItem(
            title = stringResource(R.string.home_nav_my_page),
            unselectedIcon = { NavMyPageUnselectedIcon },
            selectedIcon = { NavMyPageSelectedIcon }
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        bottomBar = {
            CherrydanBottomNavigation(
                items = bottomNavItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    handleTabNavigation(
                        navController = navController,
                        currentRoute = currentRoute,
                        targetIndex = index
                    )
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeNavRoute.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(HomeNavRoute.CATEGORY) { CategoryScreen() }
            composable(HomeNavRoute.NOTICE) { NoticeScreen() }
            composable(HomeNavRoute.HOME) { HomeScreen() }
            composable(HomeNavRoute.MY_CAMPAIGNS) { MyCampaignsScreen() }
            composable(HomeNavRoute.MY_PAGE) { MyPageScreen() }
        }
    }
}

private fun handleTabNavigation(
    navController: NavController,
    currentRoute: String?,
    targetIndex: Int
) {
    val targetRoute = when (targetIndex) {
        0 -> HomeNavRoute.CATEGORY
        1 -> HomeNavRoute.NOTICE
        2 -> HomeNavRoute.HOME
        3 -> HomeNavRoute.MY_CAMPAIGNS
        4 -> HomeNavRoute.MY_PAGE
        else -> HomeNavRoute.HOME
    }

    // 이미 같은 탭이면 아무것도 하지 않음 (또는 스크롤을 맨 위로)
    if (currentRoute == targetRoute) {
        // TODO: 스크롤을 맨 위로 이동하는 로직 추가 가능
        return
    }

    navController.navigate(targetRoute) {
        // 백스택에서 시작 화면까지 모든 화면 제거하고 상태 저장
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        // 동일한 destination 중복 방지
        launchSingleTop = true
        // 상태 복원
        restoreState = true
    }
}