package com.example.mysmarthouse.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthouse.network.endpoints.TokenApi
import com.example.mysmarthouse.dao.SettingDao
import com.example.mysmarthouse.models.Setting
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val dao: SettingDao
):ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    fun refreshToken() {
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

        GlobalScope.launch(Dispatchers.IO) {
            val results = tokenApi.getToken(sign = sign, t = time)

            var setting = dao.find(Constants.SettingKeys.ACCESS_TOKEN)
            if (setting != null) {
                setting.value = results.body()!!.result?.accessToken
                dao.upsertSetting(setting)
            } else {
                val setting = Setting(key = Constants.SettingKeys.ACCESS_TOKEN, value= results.body()!!.result?.accessToken)
                dao.upsertSetting(setting)
            }

            setting = dao.find(Constants.SettingKeys.EXPIRE_TIME)
            val millis = System.currentTimeMillis() + (results.body()!!.result?.expireTime?.times(
                1000
            ) ?: 0)
            if (setting != null) {
                setting.value = millis.toString()
                dao.upsertSetting(setting)
            } else {
                setting = Setting(key = Constants.SettingKeys.EXPIRE_TIME, value= millis.toString())
                dao.upsertSetting(setting)
            }
        }
    }
}

class WelcomeViewModelFactory(
    private val dao: SettingDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create( modelClass: Class<T> ): T {
        if( modelClass.isAssignableFrom( WelcomeViewModel::class.java ) ) {
            @Suppress( "UNCHECKED_CAST" )
            return WelcomeViewModel( dao ) as T
        }
        throw IllegalArgumentException( "Unknown ViewModel Class" )
    }
}