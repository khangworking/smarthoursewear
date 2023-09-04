package com.example.mysmarthouse.network.endpoints

import com.example.mysmarthouse.network.models.Device
import com.example.mysmarthouse.network.models.Result
import com.example.mysmarthouse.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DeviceApi {
    @GET("/v1.0/users/{uid}/devices")
    suspend fun getDevicesByUser(
        @Header(Constants.TuyaHeaderKeys.CLIENT_ID) clientId: String = Constants.CLIENT_ID,
        @Header(Constants.TuyaHeaderKeys.SIGN) sign: String,
        @Header(Constants.TuyaHeaderKeys.TIME) t: Long,
        @Header(Constants.TuyaHeaderKeys.SIGN_METHOD) signMethod: String = Constants.SIGN_METHOD,
        @Header(Constants.TuyaHeaderKeys.ACCESS_TOKEN) accessToken: String,
        @Path("uid") uid: String = Constants.USER_UID
    ): Response<Result<List<Device>>>
}