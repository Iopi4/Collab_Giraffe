package com.example.giraffenew

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
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

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                enableKioskMode()
            } else {
                Toast.makeText(this, "Overlay permission required for kiosk mode", Toast.LENGTH_LONG).show()
            }
        }
    }

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

                // Включаем kiosk режим после рендера UI
                LaunchedEffect(Unit) {
                    enableKioskMode()
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableKioskMode()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            enableKioskMode()
        }
    }

    private fun enableKioskMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                overlayPermissionLauncher.launch(intent)
                return
            }
        }

        // Включаем immersive mode
        window.decorView.systemUiVisibility = (
                android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        or android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }

    // Блокируем кнопку назад
    //override fun onBackPressed() {
        // Не вызываем super.onBackPressed() чтобы заблокировать
        // или можно показать диалог подтверждения выхода
        // super.onBackPressed()
    //}



}