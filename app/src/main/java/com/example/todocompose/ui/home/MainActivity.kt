package com.example.todocompose.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todocompose.ui.composable.AppButtonNavigation
import com.example.todocompose.ui.fragment.ButtonSheet
import com.example.todocompose.ui.fragment.HomeFragment
import com.example.todocompose.ui.fragment.SettingsFragment
import com.example.todocompose.ui.navigation.ButtonScreen
import com.example.todocompose.ui.navigation.buttonNavItems
import com.example.todocompose.ui.theme.ToDoComposeTheme
import com.example.todocompose.ui.theme.primaryColor
import com.example.todocompose.ui.theme.screensBackground
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoComposeTheme {
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()
                val buttonSheetState = androidx.compose.material
                    .rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                ModalBottomSheetLayout(
                    sheetShape = RoundedCornerShape(topStart = 10.dp , topEnd = 10.dp),
                    sheetContent = {
                                   ButtonSheet(this@MainActivity , buttonSheetState)
                } ,
                    sheetState = buttonSheetState
                    ) {
                    Scaffold(
                        containerColor = screensBackground,
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(text = "To Do List",
                                    style = TextStyle(
                                        color = Color.White ,
                                        fontSize = 20.sp
                                    )
                                )},
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = primaryColor ,
                                )
                            )
                        },
                        bottomBar = {
                            AppButtonNavigation(navController , buttonNavItems)
                        } ,
                        floatingActionButton = {
                            FloatingActionButton(
                                shape = RoundedCornerShape(50) ,
                                onClick = { coroutineScope.launch { buttonSheetState.show() } },
                                modifier = Modifier.
                                border(width = 2.dp , color = Color.White , shape = RoundedCornerShape(50.dp)),
                                containerColor = primaryColor ,
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null ,
                                    tint = Color.White
                                )
                            }
                        } ,
                        floatingActionButtonPosition = FabPosition.Center,
                    ) {
                            NavHost(
                                modifier = Modifier.padding(it),
                                navController = navController ,
                                startDestination = ButtonScreen.Home.route ,
                            ){
                                composable(ButtonScreen.Home.route){
                                    HomeFragment()
                                }
                                composable(ButtonScreen.Setting.route){
                                    SettingsFragment()
                                }
                            }
                    }
                }
            }
        }
    }
}
