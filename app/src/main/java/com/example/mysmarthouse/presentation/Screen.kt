package com.example.mysmarthouse.presentation

sealed class Screen(val route: String) {
    object TopScreen: Screen("top_screen")
    object DevicesScreen: Screen("devices_screen")
    object ScenesScreen: Screen("scenes_screen")
    object DeviceStatusScreen: Screen("devices/{deviceId}/status")
}
