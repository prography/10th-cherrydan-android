package com.hyunjung.search.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyunjung.core.presentation.designsystem.BackIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.core.presentation.designsystem.CherrydanTypography
import com.hyunjung.core.presentation.designsystem.SearchCancelIcon

@Composable
fun SearchToolbar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = BackIcon,
                contentDescription = null,
            )
        }
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
        )
        Text(
            text = "닫기",
            style = CherrydanTypography.Main5_R,
            color = CherrydanColors.Black,
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp)
                .clickable(onClick = onBackClick)
        )
    }
}

@Composable
private fun RowScope.SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    CherrydanSearchTextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = CherrydanColors.Gray1,
            unfocusedContainerColor = CherrydanColors.Gray1,
        ),
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = SearchCancelIcon,
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = "검색어를 입력해 주세요.",
                style = CherrydanTypography.Main5_R,
                color = CherrydanColors.Gray4,
            )
        },
        onValueChange = {
            if ("\n" !in it) onSearchQueryChanged(it)
        },
        modifier = Modifier
            .weight(1f)
            .height(40.dp)
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    if (searchQuery.isBlank()) return@onKeyEvent false
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(4.dp),
        value = searchQuery,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (searchQuery.isBlank()) return@KeyboardActions
                onSearchExplicitlyTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
@Preview(showBackground = true)
fun SearchToolbarPreview() {
    CherrydanTheme {
        val searchQuery = remember { mutableStateOf("") }

        SearchToolbar(
            searchQuery = searchQuery.value,
            onSearchQueryChanged = { searchQuery.value = it },
            onSearchTriggered = {},
            onBackClick = {}
        )
    }
}