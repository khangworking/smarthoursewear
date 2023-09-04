package com.example.mysmarthouse.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysmarthouse.network.endpoints.TokenApi
import com.example.mysmarthouse.dao.SettingDao
import com.example.mysmarthouse.models.Setting
import com.example.mysmarthouse.repository.TokenRepository
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
        viewModelScope.launch {
            TokenRepository(dao).reloadToken()
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