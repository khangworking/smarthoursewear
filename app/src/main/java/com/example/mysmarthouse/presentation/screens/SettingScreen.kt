package com.example.mysmarthouse.presentation.screens

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Refresh
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Alert
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.rememberScalingLazyListState
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.viewmodels.SceneScreenViewModel
import com.example.mysmarthouse.viewmodels.SceneScreenViewModelFactory
import com.example.mysmarthouse.viewmodels.SettingScreenViewModel
import com.example.mysmarthouse.viewmodels.SettingScreenViewModelFactory

@Composable
fun SettingScreen(navController: NavController, myDb: HouseDatabase) {
    val viewModel = viewModel<SettingScreenViewModel>(
        factory = SettingScreenViewModelFactory(myDb)
    )
    Box {
        val scalingLazyListState = rememberScalingLazyListState()
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
            state = scalingLazyListState
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Settings")
                }
            }
            item {
                TokenChip(
                    onClick = {
                        viewModel.refreshToken()
                    },
                    viewModel
                )
            }
        }
    }
}

@Composable
fun TokenChip(onClick: () -> Unit, viewModel: SettingScreenViewModel) {
    LaunchedEffect(Unit) {
        viewModel.getTokenUpdatedAt()
    }

    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = Color.DarkGray,
            contentColor = Color.White,
            iconColor = Color.White
        ),
        label = {
            Text(text = "API token")
        },
        modifier = Modifier.fillMaxWidth(),
        icon = {
            if (viewModel.refreshingToken) {
                CircularProgressIndicator()
            } else {
                Icon(imageVector = Icons.Rounded.Key, contentDescription = null)
            }
        },
        secondaryLabel = {
            viewModel.tokenUpdatedAt?.let {
                Text(
                    text = "Last Update ${Helper.millisecondsToDateTime(viewModel.tokenUpdatedAt!!.toLong(), "dd/MM/yy HH:mm")}", fontSize = 10.sp
                )
            }
        }
    )
}
