package com.pdrxcode.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdrxcode.ui.screens.DebugScreen
import com.pdrxcode.ui.screens.ExplorerScreen
import com.pdrxcode.ui.screens.ExtensionsScreen
import com.pdrxcode.ui.screens.HomeScreen
import com.pdrxcode.ui.screens.SettingsScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Explorer : Screen("explorer")
    data object Settings : Screen("settings")
    data object Extensions : Screen("extensions")
    data object Debug : Screen("debug")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Explorer.route) { ExplorerScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
        composable(Screen.Extensions.route) { ExtensionsScreen() }
        composable(Screen.Debug.route) { DebugScreen() }
    }
}