package com.pdrxcode.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pdrxcode.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    navController: NavController,
    currentRoute: String?,
    drawerState: androidx.compose.material3.DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Cabeçalho do Drawer (simples)
                    Text(
                        text = "pdrX Code",
                        modifier = Modifier.padding(16.dp)
                    )
                    HorizontalDivider()

                    // Item Explorer
                    NavigationDrawerItem(
                        label = { Text("Explorer") },
                        selected = currentRoute == Screen.Explorer.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Explorer.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Folder, contentDescription = null) }
                    )

                    // Item Configurações
                    NavigationDrawerItem(
                        label = { Text("Configurações") },
                        selected = currentRoute == Screen.Settings.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Settings.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) }
                    )

                    // Item Extensões
                    NavigationDrawerItem(
                        label = { Text("Extensões") },
                        selected = currentRoute == Screen.Extensions.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Extensions.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Extension, contentDescription = null) }
                    )

                    // Item Debug
                    NavigationDrawerItem(
                        label = { Text("Debug") },
                        selected = currentRoute == Screen.Debug.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.Debug.route) {
                                popUpTo(Screen.Home.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.BugReport, contentDescription = null) }
                    )
                }
            }
        },
        content = content
    )
}