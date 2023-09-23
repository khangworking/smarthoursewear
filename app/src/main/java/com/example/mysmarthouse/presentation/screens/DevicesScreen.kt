package com.example.mysmarthouse.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import coil.compose.AsyncImage
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.viewmodels.DevicesScreenViewModel
import com.example.mysmarthouse.viewmodels.DevicesScreenViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun DevicesScreen(navController: NavController, myDb: HouseDatabase) {
    val viewModel = viewModel<DevicesScreenViewModel>(
        factory = DevicesScreenViewModelFactory(myDb)
    )

    LaunchedEffect(Unit) {
        viewModel.loadDevicesList()
    }

    if (viewModel.loading) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        val scalingLazyColumnState = rememberScalingLazyListState()
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
            for (device in viewModel.devices) {
                item {
                    Chip(
                        onClick = {
                            navController.navigate("devices/${device.tuyaId}/status")
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White,
                            iconColor = Color.White
                        ),
                        icon = {
                               AsyncImage(
                                   model = "https://images.tuyacn.com/${device.icon}",
                                   contentDescription = null,
                                   contentScale = ContentScale.Crop,
                                   modifier = Modifier.size(ChipDefaults.LargeIconSize)
                                       .wrapContentSize(align = Alignment.Center)
                                       .clip(CircleShape)
                               )
                        },
                        label = {
                            Text(text = device.name)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}