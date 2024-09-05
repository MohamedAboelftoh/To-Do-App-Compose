package com.example.todocompose.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todocompose.R
import com.example.domain.entity.Task
import com.example.todocompose.ui.viewmodels.ButtonSheetViewModel
import com.example.todocompose.ui.theme.primaryColor
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonSheet(
    context: Context,
    buttonSheetState: ModalBottomSheetState,
    buttonSheetViewModel: ButtonSheetViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val title = remember { mutableStateOf("") }
    var dateTime by remember {
        mutableStateOf("")
    }
    var time by remember {
        mutableStateOf("")
    }
    var date by remember {
        mutableStateOf("")
    }
    var isError by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.35f)
            .padding(horizontal = 10.dp, vertical = 10.dp) ,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = stringResource(id = R.string.add_new_task) ,
            fontSize = 19.sp ,
            style = TextStyle(color = Color.Black),
            textAlign = TextAlign.Center
        )
        TextField(
            modifier = Modifier.padding(start = 15.dp , top = 15.dp),
            value = title.value ,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (isError)
                    Icon(Icons.Filled.Info,"error", tint = MaterialTheme.colorScheme.error)
            },
            onValueChange ={
            title.value = it
        },
            enabled = true,
            readOnly = false,
            label = { Text(text = stringResource(id = R.string.enter_your_task))}
            )
        Button(
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp),
            onClick = {
                val mCalendar = Calendar.getInstance()
                val startYear = mCalendar.get(Calendar.YEAR)
                val startMonth = mCalendar.get(Calendar.MONTH)
                val startDay = mCalendar.get(Calendar.DAY_OF_MONTH)
                val mHour = mCalendar[Calendar.HOUR_OF_DAY]
                val mMinute = mCalendar[Calendar.MINUTE]

                DatePickerDialog(context, { _, year, month, day ->
                    TimePickerDialog(context, { _, hour, minute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(year, month, day, hour, minute)
                        val amPm: String = if (hour >= 12) {
                            "PM"
                        } else {
                            "AM"
                        }
                        val hourIn12Format = if (hour > 12) hour - 12 else if (hour == 0) 12 else hour
                        val monthStr = if ((month + 1).toString().length == 1) {
                            "0${month + 1}"
                        } else {
                            (month + 1).toString()
                        }

                        val minuteStr = if (minute < 10) "0$minute" else minute.toString()

                        dateTime = "$day-$monthStr-$year-$hourIn12Format:$minuteStr $amPm"
                        time = "$hourIn12Format:$minuteStr $amPm"
                        date = "$day-$monthStr-$year"

                    }, mHour, mMinute, false).show()
                }, startYear, startMonth, startDay).show()

            },
            colors = ButtonDefaults.buttonColors(primaryColor)

        ) {
            Text(
                text = stringResource(id = R.string.select_time) ,
                fontSize = 18.sp ,
                style = TextStyle(color = Color.White)
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = dateTime ,
            fontSize = 17.sp ,
            style = TextStyle(color = Color.Gray) ,
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            onClick = {
                if(title.value.isBlank() || dateTime.isBlank()){
                    isError = true
                    return@Button
                }
                buttonSheetViewModel.insertTask(Task(title = title.value , time = time , date = date))
                coroutineScope.launch {
                    buttonSheetState.hide()
                }
                onTaskAddedListener?.onTaskAdded(Task(title = title.value , time = time , date = date))
            } ,
            colors = ButtonDefaults.buttonColors(primaryColor)
        ) {
            Text(
                text = stringResource(id = R.string.add) ,
                fontSize = 16.sp ,
                style = TextStyle(color = Color.White) ,
            )

        }

    }
}


interface OnTaskAddedListener{
    fun onTaskAdded(task:Task)
}
var onTaskAddedListener: OnTaskAddedListener?= null