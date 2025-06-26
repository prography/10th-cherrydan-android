package com.hyunjung.cherrydan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.home.presentation.navigation.HomeNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CherrydanTheme {
                HomeNavigation()
            }
        }
    }
}

@Preview
@Composable
private fun MainActivityPreview() {
    CherrydanTheme {
        HomeNavigation()
    }
}