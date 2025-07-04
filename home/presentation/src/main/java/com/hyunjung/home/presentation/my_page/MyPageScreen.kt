package com.hyunjung.home.presentation.my_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.ArrowRightIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.NotificationIcon
import com.hyunjung.core.presentation.designsystem.SearchIcon
import com.hyunjung.core.presentation.designsystem.component.CherrydanTopAppBar
import com.hyunjung.core.presentation.designsystem.component.TopBarIconButton
import com.hyunjung.core.presentation.ui.R

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        containerColor = CherrydanColors.White,
        contentColor = CherrydanColors.Black,
        topBar = {
            CherrydanTopAppBar(
                title = stringResource(id = R.string.my_page_title),
                navigationIcon = null,
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 20.dp)
                        .background(CherrydanColors.PointBeige, shape = RoundedCornerShape(4.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 11.dp, vertical = 44.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.my_page_greeting),
                            style = CherrydanTypography.Title4,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ArrowRightIcon,
                            contentDescription = null,
                        )
                    }
                }
            }
            item {
                Text(
                    text = "고객 센터",
                    style = CherrydanTypography.Main5_B,
                    color = CherrydanColors.MainPink2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            item {
                MyPageItemText(
                    text = "공지 사항"
                )
            }
            item {
                MyPageItemText(
                    text = "1:1 문의"
                )
            }
            item {
                MyPageItemText(
                    text = "자주 묻는 질문"
                )
            }
            item {
                MyPageItemText(
                    text = "이용 가이드"
                )
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.padding(16.dp),
                    color = CherrydanColors.Gray2,
                    thickness = 1.dp
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.my_page_service_setting),
                    style = CherrydanTypography.Main5_B,
                    color = CherrydanColors.MainPink2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            item {
                MyPageItemText(
                    text = stringResource(id = R.string.my_page_notification_setting)
                )
            }
            item {
                MyPageItemText(
                    text = stringResource(id = R.string.my_page_version_info)
                )
            }
            item {
                MyPageItemText(
                    text = stringResource(id = R.string.my_page_privacy_policy)
                )
            }
            item {
                MyPageItemText(
                    text = stringResource(id = R.string.my_page_terms_and_conditions)
                )
            }
            item {
                MyPageItemText(
                    text = stringResource(id = R.string.my_page_management_policy)
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { /* TODO: Handle logout */ },
                        modifier = Modifier
                            .padding(end = 8.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 9.dp),
                        colors = ButtonColors(
                            containerColor = CherrydanColors.Gray2,
                            contentColor = CherrydanColors.Gray5,
                            disabledContainerColor = CherrydanColors.Gray2,
                            disabledContentColor = CherrydanColors.Gray5
                        ),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.my_page_withdrawal),
                            style = CherrydanTypography.Main5_R,
                            color = CherrydanColors.Gray5
                        )
                    }
                    Button(
                        onClick = { /* TODO: Handle logout */ },
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 9.dp),
                        colors = ButtonColors(
                            containerColor = CherrydanColors.Gray2,
                            contentColor = CherrydanColors.Gray5,
                            disabledContainerColor = CherrydanColors.Gray2,
                            disabledContentColor = CherrydanColors.Gray5
                        ),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.my_page_logout),
                            style = CherrydanTypography.Main5_R,
                            color = CherrydanColors.Gray5
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MyPageItemText(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = CherrydanTypography.Main2_R,
            color = CherrydanColors.Black
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    CherrydanTheme {
        MyPageScreen()
    }
}