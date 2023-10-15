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
import com.example.mysmarthouse.repository.TokenRepository
import com.example.mysmarthouse.utils.Helper
import kotlinx.coroutines.launch

class SettingScreenViewModel(
    private val database: HouseDatabase
):ViewModel() {
    var tokenUpdatedAt: String? by mutableStateOf(null)
    var refreshingToken by mutableStateOf(false)
        private set

    fun refreshToken() {
        refreshingToken = true
        viewModelScope.launch {
            TokenRepository(database.dao).reloadToken()
            refreshingToken = false
            getTokenUpdatedAt()
        }
    }

    fun getTokenUpdatedAt() {
        viewModelScope.launch {
            var dao = database.dao
            val setting = dao.find("access_token")
            tokenUpdatedAt = setting.lastUpdate
        }
    }
}

class SettingScreenViewModelFactory(
    private val database: HouseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create( modelClass: Class<T> ): T {
        if( modelClass.isAssignableFrom( SettingScreenViewModel::class.java ) ) {
            @Suppress( "UNCHECKED_CAST" )
            return SettingScreenViewModel( database ) as T
        }
        throw IllegalArgumentException( "Unknown ViewModel Class" )
    }
}