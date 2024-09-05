package com.example.todocompose.ui.datails

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.entity.Task
import com.example.todocompose.R
import com.example.todocompose.ui.fragment.onTaskAddedListener
import com.example.todocompose.ui.theme.ToDoComposeTheme
import com.example.todocompose.ui.theme.primaryColor
import com.example.todocompose.ui.theme.screensBackground
import com.example.todocompose.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
@AndroidEntryPoint
class DetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val task = intent.getParcelableExtra<Task>("task")
        setContent {
            ToDoComposeTheme {
                Scaffold (
                    containerColor = screensBackground ,
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "To Do",
                                    style = TextStyle(color = Color.White , fontSize = 20.sp)
                            )},
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = primaryColor ,
                            )
                        )
                    }
                ){
                    DetailsScreen(task = task!! , modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(vertical = 30.dp, horizontal = 20.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(Color.White))

                    }

                }
            }
        }
    }
@Composable
fun DetailsScreen(
    task: Task ,
    modifier: Modifier ,
    homeViewModel : HomeViewModel = hiltViewModel()
) {
    val title = remember { mutableStateOf(task.title) }
    var dateTime by remember {
        mutableStateOf("${task.date}-${task.time}")
    }
    var time by remember {
        mutableStateOf(task.time)
    }
    var date by remember {
        mutableStateOf(task.date)
    }
    val context = LocalContext.current as Activity
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            text = stringResource(id = R.string.edit_task),
            style = TextStyle(color = Color.Black, fontSize = 20.sp),
            textAlign = TextAlign.Center
        )
        TextField(
            modifier = Modifier.padding(start = 15.dp, top = 15.dp),
            value = title.value!!,
            onValueChange = {
                title.value = it
            },
            enabled = true,
            readOnly = false,
            label = { Text(text = stringResource(id = R.string.edit_your_task)) }
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
                        val hourIn12Format =
                            if (hour > 12) hour - 12 else if (hour == 0) 12 else hour
                        val monthStr = if ((month + 1).toString().length == 1) {
                            "0${month + 1}"
                        } else {
                            (month + 1).toString()
                        }

                        val minuteStr =
                            if (minute < 10) "0$minute" else minute.toString()

                        dateTime =
                            "$day-$monthStr-$year-$hourIn12Format:$minuteStr $amPm"
                        time = "$hourIn12Format:$minuteStr $amPm"
                        date = "$day-$monthStr-$year"

                    }, mHour, mMinute, false).show()
                }, startYear, startMonth, startDay).show()

            },
            colors = ButtonDefaults.buttonColors(primaryColor)

        ) {
            Text(
                text = stringResource(id = R.string.select_time),
                fontSize = 18.sp,
                style = TextStyle(color = Color.White)
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = dateTime,
            fontSize = 17.sp,
            style = TextStyle(color = Color.Gray),
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp),
            onClick = {
                    homeViewModel.updateTask(
                    Task(
                        id = task.id,
                        title = title.value,
                        time = time,
                        date = date
                    )
                )
                onTaskEditedListener?.onTaskEdited()
                context.finish()
            },
            colors = ButtonDefaults.buttonColors(primaryColor)
        ) {
            Text(
                text = stringResource(id = R.string.save_changes),
                fontSize = 20.sp,
                style = TextStyle(color = Color.White),
            )
        }
    }
}
interface OnTaskEditedListener{
    fun onTaskEdited()
}
var onTaskEditedListener : OnTaskEditedListener ?= null
