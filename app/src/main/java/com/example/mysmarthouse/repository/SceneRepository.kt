package com.example.mysmarthouse.repository

import android.util.Log
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.dao.SceneDao
import com.example.mysmarthouse.network.endpoints.SceneApi
import com.example.mysmarthouse.network.models.Result
import com.example.mysmarthouse.network.models.Scene
import com.example.mysmarthouse.utils.Constants
import com.example.mysmarthouse.utils.Helper
import com.example.mysmarthouse.utils.TuyaCloudApi
import retrofit2.Response

class SceneRepository(private val database: HouseDatabase) {

    suspend fun loadScenes(): List<com.example.mysmarthouse.models.Scene> {
        val sceneDao = database.sceneDao
        val scenes = sceneDao.getAllScenes()
        if (scenes.count() <= 0) {
            fetchAndSaveRecords()
        }
        return sceneDao.getAllScenes()
    }

    suspend fun fetchScenes(): Response<Result<List<Scene>>> {
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
        return apiClient().getScenes(
            sign = sign,
            t = time,
            accessToken = token
        )
    }

    suspend fun fetchAndSaveRecords() {
        val response = fetchScenes()

        if (response.isSuccessful) {
            val sceneDao = database.sceneDao
            val scenes = response.body()!!.result!!
            for (scene in scenes) {
                saveScene(sceneDao, scene)
            }
        } else {
            Log.d(Helper.logTagName(), "Fail to load")
        }
    }

    private fun apiClient(): SceneApi {
        return TuyaCloudApi.getInstace().create(SceneApi::class.java)
    }

    suspend fun saveScene(sceneDao: SceneDao, scene: Scene) {
        var record = sceneDao.findByTuyaId(scene.id)
        if (record == null) {
            record = com.example.mysmarthouse.models.Scene(
                tuyaId = scene.id,
                name = scene.name
            )
        }
        sceneDao.upsertScene(record)
    }
}