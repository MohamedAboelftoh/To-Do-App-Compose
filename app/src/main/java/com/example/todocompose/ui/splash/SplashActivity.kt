package com.example.todocompose.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todocompose.R
import com.example.todocompose.ui.home.MainActivity
import com.example.todocompose.ui.splash.ui.theme.ToDoComposeTheme
import com.example.todocompose.ui.theme.primaryColor
import com.example.todocompose.ui.theme.screensBackground

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(screensBackground),
                    verticalArrangement = Arrangement.Center ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(.35f),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo image"
                    )
                    Image(
                        modifier = Modifier.fillMaxSize(.3f),
                        painter = painterResource(id = R.drawable.signature),
                        contentDescription = "signature image"
                    )
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                } , 2000)
            }
        }
    }
}