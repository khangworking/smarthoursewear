package com.example.mysmarthouse.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.repository.TokenRepository
import com.example.mysmarthouse.utils.Helper

class RefreshTokenWorker(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(appContext, workerParameters) {
    override suspend fun doWork(): Result {
        var db = HouseDatabase.getInstance(applicationContext)
        TokenRepository(db.dao).reloadToken()
        return Result.success()
    }
}