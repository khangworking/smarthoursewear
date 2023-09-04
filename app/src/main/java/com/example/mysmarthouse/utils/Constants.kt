package com.example.mysmarthouse.utils

object Constants {
    const val BASE_URL = "https://openapi.tuyaus.com"
    const val CLIENT_ID = "ed5ddmaac3hedphgsget"
    const val CLIENT_SECRET = "c1629857510f4a00b9c783a63765f426"
    const val SIGN_METHOD = "HMAC-SHA256"
    const val USER_UID = "az1667534243992o8eqK"

    object TuyaHeaderKeys {
        const val CLIENT_ID = "client_id"
        const val SIGN = "sign"
        const val TIME = "t"
        const val SIGN_METHOD = "sign_method"
        const val ACCESS_TOKEN = "access_token"
    }

    object Endpoints {
        const val GET_ACCESS_TOKEN = "/v1.0/token?grant_type=1"
    }

    object SettingKeys {
        const val ACCESS_TOKEN = "access_token"
        const val EXPIRE_TIME = "expire_time"
    }
}