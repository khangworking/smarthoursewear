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

    suspend fun fetchAndSaveDevices() {
        val deviceApi = TuyaCloudApi.getInstace().create(DeviceApi::class.java)
        val dao = database.dao
        val deviceDao = database.deviceDao
        val setting = dao.find(Constants.SettingKeys.ACCESS_TOKEN)
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
}