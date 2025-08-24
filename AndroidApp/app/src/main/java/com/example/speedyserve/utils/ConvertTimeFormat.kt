package com.example.speedyserve.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
internal fun convertTimeFormat(time : String) : String{
    val inputFormat = DateTimeFormatter.ofPattern("HH:mm")
    val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")
    val timeFormat = LocalTime.parse(time,inputFormat)
    return timeFormat.format(outputFormat)
}