package com.example.mysmarthouse.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mysmarthouse.dao.SettingDao
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
        GlobalScope.launch{
            Log.d(TAG, "Process token")
            val setting = dao.find("Token")
            Log.d(TAG, setting.value!!)
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