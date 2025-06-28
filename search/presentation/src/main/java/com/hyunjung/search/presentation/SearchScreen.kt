package com.hyunjung.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.ClockIcon
import com.hyunjung.core.presentation.designsystem.CloseIcon
import com.hyunjung.core.presentation.designsystem.SearchIcon
import com.hyunjung.search.presentation.component.SearchToolbar

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    // todo : viewmodel 사용 해서 만들면 됩니다.
}

@Composable
fun SearchContent(
    searchQuery: String,
    recentSearchQueries: List<String>,
    suggestions: List<String>,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CherrydanColors.White)
    ) {
        SearchToolbar(
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            onBackClick = onBackClick,
            modifier = modifier
        )
        if (searchQuery.isEmpty()) {
            RecentSearch(recentSearchQueries = recentSearchQueries)
        } else {
            SuggestedSearchList(
                searchQuery = searchQuery,
                suggestions = suggestions,
                onSuggestionClick = { query ->
                    onSearchQueryChanged(query)
                    onSearchTriggered(query)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
private fun RecentSearch(
    recentSearchQueries: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(1f)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "최근 검색",
                    style = CherrydanTypography.Main5_B,
                    color = CherrydanColors.Black
                )
                Text(
                    text = "전체 삭제",
                    style = CherrydanTypography.Main5_R,
                    color = CherrydanColors.Gray4
                )
            }
        }
        items(recentSearchQueries) { query ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ClockIcon,
                    contentDescription = null,
                    tint = CherrydanColors.Gray4,
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = query,
                    style = CherrydanTypography.Main5_R,
                    color = CherrydanColors.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = CloseIcon,
                    contentDescription = null,
                    tint = CherrydanColors.Black,
                )
            }
        }
    }
}

@Composable
private fun SuggestedSearchList(
    searchQuery: String,
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(suggestions) { suggestion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { onSuggestionClick(suggestion) }),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = SearchIcon,
                    contentDescription = null,
                    tint = CherrydanColors.Gray4,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = highlightQuery(
                        text = suggestion,
                        query = searchQuery,
                        highlightColor = CherrydanColors.MainPink2,
                    ),
                    style = CherrydanTypography.Main5_R,
                    color = CherrydanColors.Black,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Empty Query")
@Composable
private fun SearchScreenPreview() {
    val recentSearchQueries = listOf(
        "강남 맛집",
        "효소 체험",
        "맛있는 피자 분당점",
    )
    CherrydanTheme {
        SearchContent(
            searchQuery = "",
            recentSearchQueries = recentSearchQueries,
            suggestions = emptyList(),
            onSearchQueryChanged = {},
            onSearchTriggered = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With Query")
@Composable
private fun SearchScreenWithQueryPreview() {
    val recentSearchQueries = emptyList<String>()
    val suggestions = listOf(
        "강남 맛집",
        "강남역 카페",
        "강남 데이트 코스",
        "강남신세계"
    )

    CherrydanTheme {
        SearchContent(
            searchQuery = "강남",
            recentSearchQueries = recentSearchQueries,
            suggestions = suggestions,
            onSearchQueryChanged = {},
            onSearchTriggered = {},
            onBackClick = {}
        )
    }
}

@Composable
fun highlightQuery(text: String, query: String, highlightColor: Color): AnnotatedString {
    if (query.isBlank()) return AnnotatedString(text)

    val startIndex = text.indexOf(query, ignoreCase = true)
    if (startIndex == -1) return AnnotatedString(text)

    val endIndex = startIndex + query.length
    return buildAnnotatedString {
        append(text.substring(0, startIndex))
        withStyle(SpanStyle(color = highlightColor)) {
            append(text.substring(startIndex, endIndex))
        }
        append(text.substring(endIndex))
    }
}