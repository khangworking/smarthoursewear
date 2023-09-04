package com.example.mysmarthouse.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.viewmodels.DeviceStatusScreenViewModel
import com.example.mysmarthouse.viewmodels.DeviceStatusScreenViewModelFactory

@Composable
fun DeviceStatusScreen(navController: NavController, database: HouseDatabase, deviceTuyaId: String) {
    val viewModel = viewModel<DeviceStatusScreenViewModel>(
        factory = DeviceStatusScreenViewModelFactory(database, deviceTuyaId)
    )

    LaunchedEffect(Unit) {
        viewModel.loadStatuses()
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = deviceTuyaId)
    }
}