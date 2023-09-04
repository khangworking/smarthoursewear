package com.example.mysmarthouse.repository

import android.util.Log
import com.example.mysmarthouse.dao.SettingDao
import com.example.mysmarthouse.models.Setting
import com.example.mysmarthouse.network.endpoints.TokenApi
import com.example.mysmarthouse.network.models.Result
import com.example.mysmarthouse.network.models.Token
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi
import retrofit2.Response

class TokenRepository(private val dao: SettingDao) {
    suspend fun requestToken(): Response<Result<Token>> {
        val tokenApi = TuyaCloudApi.getInstace().create(TokenApi::class.java)
        val time = Helper.getTime()
        val sign = Helper.sign(
            clientId = Constants.CLIENT_ID,
            secret = Constants.CLIENT_SECRET,
            t = time.toString(),
            accessToken = null,
            nonce = null,
            stringToSign = Helper.stringToSign(signUrl = Constants.Endpoints.GET_ACCESS_TOKEN)
        )
        return tokenApi.getToken(sign = sign, t = time)
    }

    suspend fun upsertSettingRecord(key: String, value: String) {
        var setting = dao.find(key)
        if (setting != null) {
            setting.value = value
            dao.upsertSetting(setting)
        } else {
            setting = Setting(
                key = Constants.SettingKeys.ACCESS_TOKEN,
                value = value
            )
            dao.upsertSetting(setting)
        }
    }

    suspend fun reloadToken() {
        val response = requestToken()
        if (!response.isSuccessful) {
            return
        }

        val responseBody = response.body()!!
        if (responseBody.result == null) {
            Log.d(Helper.logTagName(), responseBody.msg!!)
            return
        }

        val tokenResult = responseBody.result!!
        upsertSettingRecord(
            key = Constants.SettingKeys.ACCESS_TOKEN,
            value = tokenResult.accessToken
        )
        val millis = System.currentTimeMillis() + tokenResult.expireTime.times(1000)
        upsertSettingRecord(
            key = Constants.SettingKeys.EXPIRE_TIME,
            value = millis.toString()
        )
    }
}