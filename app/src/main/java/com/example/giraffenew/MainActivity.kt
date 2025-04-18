package com.example.giraffenew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.giraffenew.navigate.SampleAppNavGraph
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GIRAFFEnewTheme {SampleAppNavGraph()}
            }


        }
    }