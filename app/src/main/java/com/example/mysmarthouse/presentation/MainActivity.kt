/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.mysmarthouse.presentation

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.Text
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.presentation.theme.MySmartHouseTheme
import com.example.mysmarthouse.worker.RefreshTokenWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    lateinit var myDb: HouseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myDb = HouseDatabase.getInstance(this)
        val refreshRequest = PeriodicWorkRequestBuilder<RefreshTokenWorker>(30, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(refreshRequest)
        setContent {
            if (isOnline()) {
                WearApp(myDb)
            } else {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Internet please!!")
                }
            }

        }
    }

    fun isOnline(): Boolean {
        val connMgr = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}

@Composable
fun WearApp(myDb: HouseDatabase) {
    MySmartHouseTheme {
        Navigation(myDb)
    }
}