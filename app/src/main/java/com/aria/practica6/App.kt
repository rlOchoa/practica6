package com.aria.practica6

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aria.practica6.ui.screen.*
import com.aria.practica6.ui.theme.AppColorTheme
import com.aria.practica6.ui.theme.Practica6Theme
import com.aria.practica6.viewmodel.ThemeViewModel

@Composable
fun Practica6App() {
    val navController = rememberNavController()
    val themeViewModel: ThemeViewModel = viewModel()
    val currentTheme by themeViewModel.colorTheme.collectAsState()

    Practica6Theme(colorTheme = currentTheme) {
        AppNavigation(navController, themeViewModel)
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToCamera = { navController.navigate("camera") },
                onNavigateToMicrophone = { navController.navigate("microphone") }
            )
        }

        composable("camera") {
            CameraScreen()
        }

        composable("microphone") {
            MicrophoneScreen()
        }

        composable("settings") {
            SettingsScreen(
                currentTheme = themeViewModel.colorTheme.collectAsState().value,
                onThemeSelected = { themeViewModel.setTheme(it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}