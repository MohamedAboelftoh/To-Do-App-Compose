package com.example.todocompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ButtonScreen(val route : String , val  title : String ,val  icon : ImageVector) {
    data object Home : ButtonScreen("Home" , "Home" , Icons.Default.List)
    data object Setting : ButtonScreen("Setting" , "Setting" , Icons.Default.Settings)
}

val buttonNavItems = listOf(
    ButtonScreen.Home ,
    ButtonScreen.Setting
)