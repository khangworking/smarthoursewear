package com.example.mysmarthouse.api

import com.example.mysmarthouse.models.tuya.Result
import com.example.mysmarthouse.models.tuya.Token
import com.example.mysmarthouse.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TokenApi {
    @GET("/v1.0/token?grant_type=1")
    suspend fun getToken(
        @Header(Constants.TuyaHeaderKeys.CLIENT_ID) clientId: String = Constants.CLIENT_ID,
        @Header(Constants.TuyaHeaderKeys.SIGN) sign: String,
        @Header(Constants.TuyaHeaderKeys.TIME) t: Long,
        @Header(Constants.TuyaHeaderKeys.SIGN_METHOD) signMethod: String = Constants.SIGN_METHOD
    ): Response<Result<Token>>
}