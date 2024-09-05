package com.example.todocompose.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todocompose.R
import com.example.todocompose.ui.theme.primaryColor
import java.util.Locale

@Composable
fun SettingsFragment() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 30.dp, top = 40.dp)){
        Text(
            text = stringResource(id = R.string.language),
            style = TextStyle(fontSize = 20.sp , color = Color.Black),
        )
        LanguageSpinner(listOf(stringResource(id = R.string.english)  , stringResource(id = R.string.arabic) ))
        Spacer(modifier = Modifier.padding(20.dp))
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun LanguageSpinner(list : List<String>) {
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(list[0]) }
    val context = LocalContext.current
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 30.dp)
            .border(width = 2.dp, color = primaryColor)

    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .height(40.dp)
                .clickable {
                    expanded.value = !expanded.value
                })
        {
            Text(text = currentValue.value)
            Icon(imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
            DropdownMenu(expanded = expanded.value, onDismissRequest = {
                expanded.value = false
            }) {
                list.forEach {
                    DropdownMenuItem(text = { Text(text = it) }, onClick = {
                        currentValue.value = it
                        expanded.value = false
                        updateLanguage(it , context)
                        //Toast.makeText(context , it , Toast.LENGTH_LONG).show()
                    })
                }

            }
        }

    }
}
fun updateLanguage(language: String, context: Context) {
    if(language == "English" || language == "الانجليزية" ){
        val locale = Locale("en")
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        if (context is Activity) {
            context.recreate()
        }
    }else if(language == "Arabic" || language == "العربية"){
        val locale = Locale("ar")
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        if (context is Activity) {
            context.recreate()
        }
    }
}