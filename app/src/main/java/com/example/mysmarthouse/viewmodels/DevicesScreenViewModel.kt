package com.example.mysmarthouse.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.dao.SettingDao
import com.example.mysmarthouse.models.Device
import com.example.mysmarthouse.network.endpoints.DeviceApi
import com.example.mysmarthouse.network.endpoints.TokenApi
import com.example.mysmarthouse.repository.DeviceRepository
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DevicesScreenViewModel(
    private val database: HouseDatabase
): ViewModel() {
    var loading by mutableStateOf(true)
        private set
    var devices by mutableStateOf<List<Device>>(emptyList())
        private set

    fun loadDevicesList() {
        viewModelScope.launch {
            devices = DeviceRepository(database).getItems()
            loading = false
        }
    }
}

class DevicesScreenViewModelFactory(
    private val database: HouseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create( modelClass: Class<T> ): T {
        if( modelClass.isAssignableFrom( DevicesScreenViewModel::class.java ) ) {
            @Suppress( "UNCHECKED_CAST" )
            return DevicesScreenViewModel( database ) as T
        }
        throw IllegalArgumentException( "Unknown ViewModel Class" )
    }
}