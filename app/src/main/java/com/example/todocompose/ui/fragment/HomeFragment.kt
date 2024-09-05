package com.example.todocompose.ui.fragment

import android.content.Intent
import android.os.Build
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todocompose.R
import com.example.domain.entity.Task
import com.example.todocompose.ui.datails.DetailsActivity
import com.example.todocompose.ui.datails.OnTaskEditedListener
import com.example.todocompose.ui.datails.onTaskEditedListener
import com.example.todocompose.ui.theme.primaryColor
import com.example.todocompose.ui.theme.tarnsParent
import com.example.todocompose.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeFragment(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val formatter = DateTimeFormatter.ofPattern("d-MM-yyyy")
    val currentTime = LocalDateTime.now().format(formatter)
    var date by remember { mutableStateOf(currentTime)
    }
    homeViewModel.getTasksByDate(currentTime)
    Column {
        AndroidView(
            factory = { CalendarView(it) },
            update = {
                it.setOnDateChangeListener { calendarView, year, month, day ->
                    val monthStr = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                    date = "$day-$monthStr-$year"
                    homeViewModel.getTasksByDate(date)
                }
            }
        )
        LazyColumn {
            items(
                items = homeViewModel.tasks.value,
                key = { name -> name }
            ) { task ->
                val dismissState = rememberDismissState(
                    initialValue = DismissValue.Default,
                    positionalThreshold = { swipeActivationFloat -> swipeActivationFloat / 3 }
                )
                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> tarnsParent
                                DismissValue.DismissedToEnd -> Color.Green
                                DismissValue.DismissedToStart -> Color.Red
                            }, label = ""
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                IconButton(onClick = { scope.launch { dismissState.reset() } }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                                }
                                if (dismissState.targetValue == DismissValue.DismissedToStart)
                                    IconButton(onClick = {
                                        homeViewModel.deleteTask(task)
                                        homeViewModel.getTasksByDate(date)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                                    }
                            }
                        }
                    },
                    dismissContent = {
                        TaskCard(task =task )
                    }
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        onTaskAddedListener = object : OnTaskAddedListener {
            override fun onTaskAdded(task: Task) {
                homeViewModel.getTasksByDate(date)
            }
        }
    }
    onTaskEditedListener= object :OnTaskEditedListener{
        override fun onTaskEdited() {
            homeViewModel.getTasksByDate(date)
        }
    }
}

@Composable
fun TaskCard(
    viewModel: HomeViewModel = hiltViewModel() ,
    task : Task ) {
    val context = LocalContext.current
    val isDone = remember {
        mutableStateOf(task.isDone)
    }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .background(Color.White)
                .clickable {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("task", task)
                    context.startActivity(intent)
                },
            horizontalArrangement = Arrangement.SpaceAround ,
            verticalAlignment = Alignment.CenterVertically ,

        ) {
            Column {
                Text(
                    modifier = Modifier.padding(vertical =5.dp),
                    text = task.title!! ,
                    fontSize = 20.sp ,
                    style = TextStyle(color = if(!isDone.value)primaryColor else Color.Green)
                )
                Row (
                    modifier = Modifier.padding(vertical =5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                   Icon(painter = painterResource(id = R.drawable.clock_icon), contentDescription =null)
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = task.time!! ,
                        fontSize = 17.sp ,
                        style = TextStyle(color = Color.Black) ,
                    )
                }
            }
            Button(
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    isDone.value = true
                    viewModel.updateTask(Task(id = task.id , title = task.title , time = task.time , date = task.date , isDone = true))
                } ,
                colors = if(isDone.value) buttonColors(Color.Green) else buttonColors(primaryColor)
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
}
