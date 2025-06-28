package com.hyunjung.core.presentation.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hyunjung.auth.presentation.LocalCherrydanContentColor
import com.hyunjung.core.presentation.designsystem.BackIcon
import com.hyunjung.core.presentation.designsystem.CherrydanColors
import com.hyunjung.core.presentation.designsystem.CherrydanTypography

@Composable
fun CherrydanTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    colors: CherrydanTopAppBarColors = CherrydanTopAppBarDefaults.topAppBarColors(),
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    paddingValues: PaddingValues = PaddingValues(
        horizontal = CherrydanTopAppBarDefaults.HorizontalPadding,
        vertical = CherrydanTopAppBarDefaults.VerticalPadding,
    ),
    centeredTitle: Boolean = false,
) {
    TopAppBarBase(
        title = { Text(text = title, style = CherrydanTypography.Title1) },
        actions = actions,
        colors = colors,
        centeredTitle = centeredTitle,
        paddingValues = paddingValues,
        navigationIcon = navigationIcon,
        modifier = modifier,
    )
}

@Composable
private fun TopAppBarBase(
    title: @Composable (() -> Unit),
    colors: CherrydanTopAppBarColors,
    navigationIcon: @Composable (() -> Unit)?,
    actions: @Composable (RowScope.() -> Unit),
    paddingValues: PaddingValues,
    centeredTitle: Boolean,
    modifier: Modifier,
) {
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = CherrydanTopAppBarDefaults.ActionsSpacing,
            ),
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.containerColor)
            .padding(paddingValues),
    ) {
        if (navigationIcon != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                CompositionLocalProvider(
                    LocalCherrydanContentColor provides colors.navigationIconColor,
                    content = navigationIcon
                )
            }
        }

        Box(
            modifier = Modifier
                .align(if (centeredTitle) Alignment.Center else Alignment.CenterStart)
                .padding(
                    start = if (!centeredTitle && navigationIcon != null) {
                        CherrydanTopAppBarDefaults.NavigationButtonSize
                    } else 0.dp
                )
        ) {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = 1f,
                ),
                LocalCherrydanContentColor provides colors.titleColor,
                content = title
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            CompositionLocalProvider(
                LocalCherrydanContentColor provides colors.actionIconColor,
                content = actionsRow
            )
        }
    }
}

@Composable
fun TopBarIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    Box(
        modifier = modifier
            .size(CherrydanTopAppBarDefaults.ActionIconSize)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TopBarIconButton(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String,
) {
    Box(
        modifier = modifier
            .size(CherrydanTopAppBarDefaults.ActionIconSize)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CherrydanTopAppBarNoNavigationPreview() {
    Box {
        CherrydanTopAppBar(
            title = "Title",
            actions = {
                TopBarIconButton(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    onClick = {}
                )
            }
        )
    }
}

@Preview(showBackground = true, name = "TopAppBar With Navigation")
@Composable
private fun CherrydanTopAppBarPreview() {
    Box {
        CherrydanTopAppBar(
            title = "Title",
            navigationIcon = {
                TopBarIconButton(
                    imageVector = BackIcon,
                    contentDescription = "Back",
                    onClick = {}
                )
            },
            actions = {
                TopBarIconButton(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    onClick = {}
                )
            }
        )
    }
}

@Preview(showBackground = true, name = "CenteredTopAppBar")
@Composable
private fun CherrydanTopAppBarCenteredPreview() {
    Box {
        CherrydanTopAppBar(
            title = "Centered Title",
            centeredTitle = true,
            navigationIcon = {
                TopBarIconButton(
                    imageVector = BackIcon,
                    contentDescription = "Back",
                    onClick = {}
                )
            },
            actions = {
                TopBarIconButton(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    onClick = {}
                )
            }
        )
    }
}


object CherrydanTopAppBarDefaults {

    val HorizontalPadding: Dp = 16.dp
    val VerticalPadding: Dp = 12.dp
    val ActionIconSize: Dp = 40.dp
    val ActionButtonSize: Dp = 40.dp
    val NavigationButtonSize: Dp = 40.dp
    val ActionsSpacing: Dp = 4.dp

    val TitleContentColor: Color = CherrydanColors.Black
    val ContainerColor: Color = CherrydanColors.White
    val ActionIconColor: Color = CherrydanColors.Black.copy(alpha = 0.8f)
    val NavigationIconColor: Color = CherrydanColors.Black.copy(alpha = 0.8f)

    @Composable
    fun topAppBarColors(
        containerColor: Color = ContainerColor,
        titleContentColor: Color = TitleContentColor,
        actionIconContentColor: Color = ActionIconColor,
        navigationIconContentColor: Color = NavigationIconColor
    ): CherrydanTopAppBarColors = CherrydanTopAppBarColors(
        containerColor = containerColor,
        titleColor = titleContentColor,
        actionIconColor = actionIconContentColor,
        navigationIconColor = navigationIconContentColor,
    )
}