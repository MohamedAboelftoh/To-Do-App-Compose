package com.example.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id : Int ?= null,
    val title : String?= null,
    var isDone : Boolean = false,
    val time : String?= null ,
    val date : String?= null
): Parcelable
