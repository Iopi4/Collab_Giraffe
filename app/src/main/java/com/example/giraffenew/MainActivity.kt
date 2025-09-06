package com.example.giraffenew

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.navigate.SampleAppNavGraph
import com.example.giraffenew.services.ModbusBackgroundService
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var registerDataSource: RegisterStatusDataSource
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Явный запуск сервиса
        val serviceIntent = Intent(this, ModbusBackgroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        enableEdgeToEdge()
        setContent {
            GIRAFFEnewTheme {
                SampleAppNavGraph()
                //AccidentWarningLogScreen()
            }
        }
    }
}