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
    init {
        val dao = database.dao
    }
    var loading by mutableStateOf(true)
        private set
    var devices by mutableStateOf<List<Device>>(emptyList())
        private set

    fun loadDevicesList() {
        Log.d(Helper.logTagName(), "loadDevicesList")

        viewModelScope.launch {
            val dao = database.deviceDao
            val devices = dao.getDevices()
            if (devices.count() > 0) {
                setDevices()
            } else {
                fetchAndSaveDevices()
            }
            loading = false
        }
    }

    suspend fun fetchAndSaveDevices() {
        val deviceApi = TuyaCloudApi.getInstace().create(DeviceApi::class.java)
        val dao = database.dao
        val deviceDao = database.deviceDao
        val setting = dao.find("access_token")
        val time = Helper.getTime()
        val sign = Helper.sign(
            clientId = Constants.CLIENT_ID,
            secret = Constants.CLIENT_SECRET,
            t = time.toString(),
            accessToken = setting.value!!,
            nonce = null,
            stringToSign = Helper.stringToSign(signUrl = "/v1.0/users/${Constants.USER_UID}/devices")
        )
        val results = deviceApi.getDevicesByUser(sign = sign, t = time, accessToken = setting.value!!)
        if (results.isSuccessful) {
            val devicesResponse = results.body()!!.result
            for (device in devicesResponse!!) {
                var record = deviceDao.findByTuyaId(device.id)
                if (record == null) {
                    record = Device(
                        tuyaId = device.id,
                        name = device.name,
                        icon = device.icon
                    )
                }
                deviceDao.upsertDevice(record)
            }
        } else {
            Log.d(Helper.logTagName(), "Cannot fetch devices")
        }
    }

    suspend fun setDevices() {
        val deviceDao = database.deviceDao
        devices = deviceDao.getDevices()
        Log.d(Helper.logTagName(), devices.count().toString())
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