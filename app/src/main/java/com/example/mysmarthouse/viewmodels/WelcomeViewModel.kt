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

val TAG = "MyAppLog"

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
            Log.d(TAG, "Process token")
            val results = tokenApi.getToken(sign = sign, t = time)
            if (results != null) {
                Log.d(TAG, results.body().toString())
                val result = results.body()!!.result
                Log.d(TAG, result.toString())
            }
            val setting = dao.find("access_token")
            if (setting != null) {
                Log.d(TAG, setting.value!!)
                setting.value = results.body()!!.result?.accessToken
                dao.upsertSetting(setting)
            } else {
                val setting = Setting(key = "access_token", value= results.body()!!.result?.accessToken)
                dao.upsertSetting(setting)
                Log.d(TAG, "Not found setting object")
            }

        }
        Log.d(TAG, "refreshToken")
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