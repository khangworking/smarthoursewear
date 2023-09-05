package com.example.mysmarthouse.network.endpoints

import com.example.mysmarthouse.network.models.Result
import com.example.mysmarthouse.network.models.Scene
import com.example.mysmarthouse.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SceneApi {
    @GET("/v1.1/homes/{home_id}/scenes")
    suspend fun getScenes(
        @Header(Constants.TuyaHeaderKeys.CLIENT_ID) clientId: String = Constants.CLIENT_ID,
        @Header(Constants.TuyaHeaderKeys.SIGN) sign: String,
        @Header(Constants.TuyaHeaderKeys.TIME) t: Long,
        @Header(Constants.TuyaHeaderKeys.SIGN_METHOD) signMethod: String = Constants.SIGN_METHOD,
        @Header(Constants.TuyaHeaderKeys.ACCESS_TOKEN) accessToken: String,
        @Path("home_id") homeId: String = Constants.HOME_ID
    ) : Response<Result<List<Scene>>>

    @POST("/v1.0/homes/{home_id}/scenes/{scene_id}/trigger")
    suspend fun triggerScene(
        @Header(Constants.TuyaHeaderKeys.CLIENT_ID) clientId: String = Constants.CLIENT_ID,
        @Header(Constants.TuyaHeaderKeys.SIGN) sign: String,
        @Header(Constants.TuyaHeaderKeys.TIME) t: Long,
        @Header(Constants.TuyaHeaderKeys.SIGN_METHOD) signMethod: String = Constants.SIGN_METHOD,
        @Header(Constants.TuyaHeaderKeys.ACCESS_TOKEN) accessToken: String,
        @Path("home_id") homeId: String = Constants.HOME_ID,
        @Path("scene_id") sceneId: String
    ) : Response<Result<Boolean>>
}