package com.example.mysmarthouse.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.models.Scene
import com.example.mysmarthouse.repository.SceneRepository
import kotlinx.coroutines.launch

class SceneScreenViewModel(
    private val database: HouseDatabase
): ViewModel() {
    var loading by mutableStateOf(true)
        private set

    var scenes by mutableStateOf<List<Scene>>(emptyList())
        private set

    var resyncing by mutableStateOf(false)
        private set

    fun loadScenes() {
        viewModelScope.launch {
            scenes = SceneRepository(database).loadScenes()
            loading = false
        }
    }

    fun resync() {
        resyncing = true
        viewModelScope.launch {
            scenes = SceneRepository(database).upsertScenes()
            resyncing = false
        }
    }

    fun execScene(sceneId: String) {
        viewModelScope.launch {
            SceneRepository(database).execScene(sceneId)
        }
    }
}

class SceneScreenViewModelFactory(
    private val database: HouseDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create( modelClass: Class<T> ): T {
        if( modelClass.isAssignableFrom( SceneScreenViewModel::class.java ) ) {
            @Suppress( "UNCHECKED_CAST" )
            return SceneScreenViewModel( database ) as T
        }
        throw IllegalArgumentException( "Unknown ViewModel Class" )
    }
}