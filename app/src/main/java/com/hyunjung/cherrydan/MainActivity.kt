package com.hyunjung.cherrydan

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyunjung.cherrydan.navigation.AppNavigation
import com.hyunjung.core.presentation.designsystem.CherrydanTheme
import com.hyunjung.feature.auth.login.LogInScreenRoot
import com.hyunjung.feature.auth.splash.SplashScreen
import com.hyunjung.feature.home.navigation.HomeNavigation
import timber.log.Timber
import java.security.MessageDigest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CherrydanTheme {
                var isLoggedIn by remember { mutableStateOf(false) }

                if (isLoggedIn) {
                    HomeNavigation()
                } else {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "splash"
                    ) {
                        composable("splash") {
                            SplashScreen(
                                onSplashFinished = {
                                    // TODO: 실제로는 토큰 확인 후 결정
                                    // if (hasValidToken) { isLoggedIn = true } else { navigate to login }
                                    navController.navigate("login") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("login") {
                            LogInScreenRoot(
                                onKakaoLogInClick = { },
                                onNaverLogInClick = { },
                                onGoogleLogInClick = { },
                                onLoginSuccess = { isLoggedIn = true }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("MainActivity onResume called")
    }
}

@Preview
@Composable
private fun MainActivityPreview() {
    CherrydanTheme {
        AppNavigation(
            navController = rememberNavController()
        )
    }
}