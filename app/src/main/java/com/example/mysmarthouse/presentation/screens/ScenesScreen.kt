package com.example.mysmarthouse.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.viewmodels.SceneScreenViewModel
import com.example.mysmarthouse.viewmodels.SceneScreenViewModelFactory

@Composable
fun ScenesScreen(navController: NavController, database: HouseDatabase) {
    val viewModel = viewModel<SceneScreenViewModel>(
        factory = SceneScreenViewModelFactory(database)
    )

    LaunchedEffect(Unit) {
        viewModel.loadScenes()
    }

    if (viewModel.loading) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
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
                top = 40.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 40.dp
            ),
            state = scalingLazyColumnState
        ) {
            for (scene in viewModel.scenes) {
                item {
                    Chip(
                        onClick = { viewModel.execScene(scene.tuyaId) },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = Color.DarkGray,
                            contentColor = Color.White,
                            iconColor = Color.White
                        ),
                        label = {
                            Text(text = scene.name)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}