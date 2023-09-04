package com.example.mysmarthouse.presentation

import androidx.compose.runtime.Composable
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.presentation.screens.DevicesScreen
import com.example.mysmarthouse.presentation.screens.ScenesScreen
import com.example.mysmarthouse.presentation.screens.TopScreen

@Composable
fun Navigation(myDb: HouseDatabase) {
    val navController = rememberSwipeDismissableNavController()
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = Screen.TopScreen.route
    ) {
        composable(route = Screen.TopScreen.route) {
            TopScreen(navController = navController, myDb)
        }
        composable(route = Screen.DevicesScreen.route) {
            DevicesScreen(navController = navController, myDb)
        }
        composable(route = Screen.ScenesScreen.route) {
            ScenesScreen(navController = navController)
        }
    }
}