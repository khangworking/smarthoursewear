package com.example.mysmarthouse.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState

@Composable
fun ScenesScreen(navController: NavController) {
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
        items(10) { index ->
            Chip(
                onClick = { /*TODO*/ },
                colors = ChipDefaults.chipColors(
                    backgroundColor = Color.DarkGray,
                    contentColor = Color.White,
                    iconColor = Color.White
                ),
                label = {
                    Text(text = "Scene $index")
                }
            )
        }
    }
}