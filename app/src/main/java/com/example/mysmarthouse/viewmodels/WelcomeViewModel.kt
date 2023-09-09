package com.example.mysmarthouse.viewmodels

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
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
    var loading by mutableStateOf(false)
        private set

    fun refreshToken() {
        loading = true
        viewModelScope.launch {
            TokenRepository(dao).reloadToken()
            loading = false
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