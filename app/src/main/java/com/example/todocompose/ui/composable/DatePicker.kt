package com.example.todocompose.ui.composable

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun openDatePicker(
    context: Context ,
    onDateSetListener : DatePickerDialog.OnDateSetListener ,
    date : LocalDate
) {
    Locale.setDefault(Locale.ENGLISH)
    val datePickerDialog = DatePickerDialog(
        context ,
        onDateSetListener ,
        date.year ,
        date.monthValue ,
        date.dayOfMonth
    )
    datePickerDialog.show()
}
fun openDateTime(
    context: Context ,
    date : String
) {
    val mCalendar = Calendar.getInstance()
    val startYear = mCalendar.get(Calendar.YEAR)
    val startMonth = mCalendar.get(Calendar.MONTH)
    val startDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    var time = ""
    DatePickerDialog(context, {_,year,month,day->
            TimePickerDialog(context, {_,hour,minute->
                       val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year , month , day , hour, minute)
                val monthStr : String
                if((month+1).toString().length == 1){
                    monthStr = "0${month + 1}"
                }
                else{
                    monthStr = month.toString()
                }
                time = "$day - $monthStr - $year - $hour:$minute"

                },
                mHour , mMinute , false).show()
    },
        startYear , startMonth , startDay).show()
}