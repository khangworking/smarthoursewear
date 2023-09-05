package com.example.mysmarthouse.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.models.Device
import com.example.mysmarthouse.network.models.DeviceStatus
import com.example.mysmarthouse.repository.DeviceRepository
import kotlinx.coroutines.launch

class DeviceStatusScreenViewModel(
    private val database: HouseDatabase,
    private val deviceId: String
): ViewModel() {
    var loading by mutableStateOf(true)
        private set
    var statuses by mutableStateOf<List<DeviceStatus>>(emptyList())
        private set

    fun loadStatuses() {
        viewModelScope.launch {
            statuses = DeviceRepository(database = database).getStatuses(deviceId = deviceId)
            loading = false
        }
    }

    fun changeDeviceStatus(deviceTuyaId: String, key: String, value: Boolean) {
        viewModelScope.launch {
            DeviceRepository(database = database).sendCommand(
                deviceTuyaId = deviceTuyaId,
                key = key,
                value = value
            )
        }
    }
}

class DeviceStatusScreenViewModelFactory(
    private val database: HouseDatabase,
    private val deviceId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create( modelClass: Class<T> ): T {
        if( modelClass.isAssignableFrom( DeviceStatusScreenViewModel::class.java ) ) {
            @Suppress( "UNCHECKED_CAST" )
            return DeviceStatusScreenViewModel( database, deviceId ) as T
        }
        throw IllegalArgumentException( "Unknown ViewModel Class" )
    }
}