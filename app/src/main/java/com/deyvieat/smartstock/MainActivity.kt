package com.deyvieat.smartstock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.deyvieat.smartstock.core.navigation.NavigationWrapper
import com.deyvieat.smartstock.core.ui.theme.SmartStockTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartStockTheme {
                NavigationWrapper()
            }
        }
    }
}
