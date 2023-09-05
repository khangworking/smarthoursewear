package com.example.mysmarthouse.repository

import android.util.Log
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.network.endpoints.SceneApi
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi

class SceneRepository(private val database: HouseDatabase) {
    suspend fun getScenes() {
        val time = Helper.getTime()
        var token = TokenRepository(database.dao).getToken()
        val sign = Helper.sign(
            clientId = Constants.CLIENT_ID,
            secret = Constants.CLIENT_SECRET,
            t = time.toString(),
            accessToken = token,
            nonce = null,
            stringToSign = Helper.stringToSign(signUrl = "/v1.1/homes/${Constants.HOME_ID}/scenes")
        )
        val response = apiClient().getScenes(
            sign = sign,
            t = time,
            accessToken = token
        )
        Log.d(Helper.logTagName(), response.body().toString())
    }

    private fun apiClient(): SceneApi {
        return TuyaCloudApi.getInstace().create(SceneApi::class.java)
    }
}