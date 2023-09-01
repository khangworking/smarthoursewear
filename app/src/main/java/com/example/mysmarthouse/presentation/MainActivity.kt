/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.mysmarthouse.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.mysmarthouse.dao.HouseDatabase
import com.example.mysmarthouse.presentation.theme.MySmartHouseTheme

class MainActivity : ComponentActivity() {
    lateinit var myDb: HouseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myDb = HouseDatabase.getInstance(this)
        setContent {
            WearApp(myDb)
        }
    }
}

@Composable
fun WearApp(myDb: HouseDatabase) {
    MySmartHouseTheme {
        Navigation(myDb)
    }
}