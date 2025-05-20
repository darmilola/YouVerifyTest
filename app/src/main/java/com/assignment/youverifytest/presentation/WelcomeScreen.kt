package com.assignment.youverifytest.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.assignment.craftsilicontest.component.AsyncImageComponent
import com.assignment.craftsilicontest.component.ImageComponent
import com.assignment.youverifytest.R
import com.assignment.youverifytest.di.initKoin
import com.assignment.youverifytest.ui.theme.YouVerifyTestTheme
import kotlinx.coroutines.delay

class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YouVerifyTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    initKoin()
                    LaunchedEffect(key1 = true) {
                        delay(3000L)
                        startActivity(Intent(this@WelcomeScreen, MainActivity::class.java))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(color = Color.Yellow)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(color = Color.Yellow),
                            contentAlignment = Alignment.Center
                        ) {

                            val columnModifier = Modifier
                                .fillMaxSize()
                            Column(
                                modifier = columnModifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {

                                ImageComponent(
                                    imageModifier = Modifier
                                        .size(200.dp),
                                    imageRes = R.drawable.splashscreen_logo,
                                    colorFilter = ColorFilter.tint(color = Color.Black)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}