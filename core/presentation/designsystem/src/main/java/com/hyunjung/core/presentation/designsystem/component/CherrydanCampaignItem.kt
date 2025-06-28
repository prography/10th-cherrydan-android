package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherryUnselectedIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.R

fun LazyGridScope.CampaignItem(
    item: CampaignItemData,
    modifier: Modifier = Modifier,
) {
    item(key = item.id) {
        CampaignItemContent(
            item = item,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CampaignItemContent(
    item: CampaignItemData,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        CampaignCard(
            label = item.label,
        )
        Text(
            text = "${item.endDate}일 남음",
            style = CherrydanTypography.Main5_R,
            color = CherrydanColors.MainPink3,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = item.title,
            style = CherrydanTypography.Main5_R,
            color = CherrydanColors.Black,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = item.reward,
            style = CherrydanTypography.Main5_R,
            color = CherrydanColors.Black,
        )

        Text(
            text = buildAnnotatedString {
                append("신청 ${item.totalApplyCount}/")
                withStyle(style = SpanStyle(color = CherrydanColors.Gray4)) {
                    append("${item.currentApplyCount}명")
                }
            },
            style = CherrydanTypography.Main5_R,
            color = CherrydanColors.Black,
        )

        // todo : type에 따라 icon 및 텍스트 enum class 로 생성 해서 교체 필요
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.img_google),
                contentDescription = null,
                tint = Unspecified,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = item.type,
                style = CherrydanTypography.Main5_R,
                color = CherrydanColors.Black,
            )
        }
    }
}

// todo : image url 받고, coil로 처리
@Composable
fun CampaignCard(
    label: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Image(
            painter = painterResource(R.drawable.img_campaign_sample),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = label,
            color = CherrydanColors.PointBlue,
            style = CherrydanTypography.Main6_R,
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
                .background(color = CherrydanColors.Gray5)
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .align(Alignment.TopStart)
        )

        // todo : selected / unselected icon 지정 및 크기 조정 하기
        Icon(
            imageVector = CherryUnselectedIcon,
            modifier = Modifier
                .padding(8.dp)
                .size(28.dp)
                .align(Alignment.BottomEnd)
                .clickable {},
            tint = CherrydanColors.White,
            contentDescription = null
        )
    }
}

// todo: data 싹 고쳐야함
data class CampaignItemData(
    val id: Int,
    val label: String,
    val endDate: Int,
    val title: String,
    val reward: String,
    val totalApplyCount: Int,
    val currentApplyCount: Int,
    val type: String,
)

@Preview
@Composable
private fun CampaignItemPreview() {
    val items = listOf(
        CampaignItemData(
            id = 1,
            label = "강남맛집",
            endDate = 6,
            title = "[와모야] 맛있는 효소 릴스 체험",
            reward = "효소 1박스",
            totalApplyCount = 23,
            currentApplyCount = 12,
            type = "유튜브"
        ),
        CampaignItemData(
            id = 2,
            label = "강남맛집",
            endDate = 6,
            title = "[와모야] 맛있는 효소 릴스 체험",
            reward = "효소 1박스",
            totalApplyCount = 23,
            currentApplyCount = 12,
            type = "유튜브"
        ),
        CampaignItemData(
            id = 3,
            label = "강남맛집",
            endDate = 6,
            title = "[와모야] 맛있는 효소 릴스 체험",
            reward = "효소 1박스",
            totalApplyCount = 23,
            currentApplyCount = 12,
            type = "유튜브"
        )
    )
    CherrydanTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(28.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            ) {
            items.forEach { CampaignItem(it) }
        }
    }
}