package com.example.giraffenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.giraffenew.accident_and_warning_log.AccidentWarningLogScreen
import com.example.giraffenew.accident_and_warning_log.data.remoute.FakeRegisterStatusDataSource
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.navigate.SampleAppNavGraph
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var registerDataSource: RegisterStatusDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Тестовое включение регистра через 3 секунды
        lifecycleScope.launch {
            delay(3000)
            (registerDataSource as FakeRegisterStatusDataSource).updateStatus("REG-001", true)

            // Тестовое выключение через еще 5 секунд
            delay(5000)
            (registerDataSource as FakeRegisterStatusDataSource).updateStatus("REG-001", false)
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