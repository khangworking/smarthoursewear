package com.example.mysmarthouse.repository

import android.util.Log
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.models.Device
import com.example.mysmarthouse.network.endpoints.DeviceApi
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi

class DeviceRepository(private val database: HouseDatabase) {
    suspend fun getItems(): List<Device> {
        val dao = database.deviceDao
        val devices = dao.getDevices()
        if (devices.count() <= 0) {
            fetchAndSaveDevices()
        }
        val deviceDao = database.deviceDao
        return deviceDao.getDevices()
    }

    suspend fun getStatuses(deviceId: String) {
        val time = Helper.getTime()
        var token = token()
        val sign = Helper.sign(
            clientId = Constants.CLIENT_ID,
            secret = Constants.CLIENT_SECRET,
            t = time.toString(),
            accessToken = token,
            nonce = null,
            stringToSign = Helper.stringToSign(signUrl = "/v1.0/devices/$deviceId/status")
        )
        val results = apiClient().getDeviceStatus(
            sign = sign,
            t = time,
            accessToken = token,
            deviceId = deviceId
        )
        Log.d(Helper.logTagName(), results.body().toString())
    }

    private suspend fun fetchAndSaveDevices() {
        val deviceDao = database.deviceDao
        val time = Helper.getTime()
        val token = token()
        val sign = Helper.sign(
            clientId = Constants.CLIENT_ID,
            secret = Constants.CLIENT_SECRET,
            t = time.toString(),
            accessToken = token,
            nonce = null,
            stringToSign = Helper.stringToSign(signUrl = "/v1.0/users/${Constants.USER_UID}/devices")
        )
        val results = apiClient().getDevicesByUser(sign = sign, t = time, accessToken = token)
        if (results.isSuccessful) {
            val devicesResponse = results.body()!!.result
            for (device in devicesResponse!!) {
                var record = deviceDao.findByTuyaId(device.id)
                if (record == null) {
                    record = Device(
                        tuyaId = device.id,
                        name = device.name,
                        icon = device.icon,
                        category = device.category
                    )
                }
                deviceDao.upsertDevice(record)
            }
        } else {
            Log.d(Helper.logTagName(), "Cannot fetch devices")
        }
    }

    private suspend fun apiClient(): DeviceApi {
        return TuyaCloudApi.getInstace().create(DeviceApi::class.java)
    }

    private suspend fun token():String {
        val dao = database.dao
        val setting = dao.find(Constants.SettingKeys.ACCESS_TOKEN)
        return setting.value!!
    }
}