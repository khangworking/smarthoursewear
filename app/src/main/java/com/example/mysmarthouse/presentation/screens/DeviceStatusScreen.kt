package com.example.mysmarthouse.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.network.models.DeviceStatus
import com.example.mysmarthouse.utils.Helper
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

    if (viewModel.loading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
        }
    } else if (viewModel.statuses.isEmpty()) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "No record")
        }
    } else {
        val scalingLazyColumnState = rememberScalingLazyListState()
        Log.d(Helper.logTagName(), viewModel.statuses.toString())
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                top = 50.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 40.dp
            ),
            state = scalingLazyColumnState
        ) {
            for (status in viewModel.statuses) {
                item {
                    StatusChip(
                        item = status,
                        deviceTuyaId = deviceTuyaId,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(item: DeviceStatus, deviceTuyaId: String, viewModel: DeviceStatusScreenViewModel) {
    if (item.value is Boolean) {
        var currentValue by remember { mutableStateOf(item.value) }
        ToggleChip(
            checked = currentValue,
            onCheckedChange = {
                currentValue = it
                viewModel.changeDeviceStatus(
                    deviceTuyaId = deviceTuyaId,
                    key = item.code,
                    value = currentValue
                )
            },
            label = {
                Text(text = item.code)
            },
            toggleControl = {
                if (viewModel.loading) {
                    CircularProgressIndicator()
                } else {
                    Image(
                        imageVector = ToggleChipDefaults.switchIcon(currentValue),
                        contentDescription = null
                    )
                }

            },
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        Chip(
            onClick = { /*TODO*/ },
            colors = ChipDefaults.chipColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White,
                iconColor = Color.White
            ),
            label = {
                Text(text = item.code)
            },
            secondaryLabel = {
                Text(text = item.value.toString())
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

}