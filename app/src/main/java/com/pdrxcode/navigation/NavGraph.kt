package com.pdrxcode.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pdrxcode.ui.screens.HomeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Explorer : Screen("explorer")
    data object Settings : Screen("settings")
    data object Extensions : Screen("extensions")
    data object Debug : Screen("debug")
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        // Outras telas serão adicionadas nas etapas seguintes
    }
}