package com.example.todocompose.ui.composable

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.todocompose.ui.navigation.ButtonScreen
import com.example.todocompose.ui.theme.primaryColor

@Composable
fun AppButtonNavigation(navHostController: NavHostController , buttonNavItems : List<ButtonScreen>) {
    BottomNavigation(backgroundColor = Color.White) {
        val selectedItem = remember {
            mutableIntStateOf(0)
        }
        buttonNavItems.forEachIndexed  {index, item ->
            BottomNavigationItem(
                selected = selectedItem.intValue == index,
                onClick = {
                    when(item.route){
                        "Home"->{
                            navHostController.navigate(ButtonScreen.Home.route)
                            selectedItem.intValue = index
                        }
                        "Setting"->{
                            navHostController.navigate(ButtonScreen.Setting.route)
                            selectedItem.intValue = index

                        }
                    }
                },
                selectedContentColor = primaryColor,
                unselectedContentColor = Color.Black,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (selectedItem.intValue == index) primaryColor else Color.Black
                    ) } ,
            )
        }
    }

}