package com.example.mysmarthouse.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.mysmarthouse.R
import com.example.mysmarthouse.presentation.Screen

@Composable
fun TopScreen(navController: NavController) {
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
        item {
            Greeting()
        }
        item {
            OptionsList(navController)
        }
    }
}

@Composable
fun Greeting() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.welcome_back)
    )
}

@Composable
fun OptionsList(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DevicesChip(navController)
        ScenesChip(navController)
    }
}

@Composable
fun DevicesChip(navController: NavController) {
    Chip(
        onClick = { navController.navigate(Screen.DevicesScreen.route) },
        colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            iconColor = Color.White,
            contentColor = Color.White
        ),
        label = {
            Text(text = "Devices")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        icon = {
            Icon(
                Icons.Rounded.Apps,
                contentDescription = null
            )
        }
    )
}

@Composable
fun ScenesChip(navController: NavController) {
    Chip(
        onClick = { navController.navigate(Screen.ScenesScreen.route) },
        colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colors.secondaryVariant,
            iconColor = Color.White,
            contentColor = Color.White
        ),
        label = {
            Text(text = "Scenes")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp),
        icon = {
            Icon(Icons.Rounded.Check, null)
        }
    )
}