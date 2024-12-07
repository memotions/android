package com.memtionsandroid.memotions.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)



fun String.formatDateTime(): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME  // Menggunakan ISO_DATE_TIME untuk mendukung zona waktu
    val outputFormatter = DateTimeFormatter.ofPattern("HH.mm • dd-MMMM-yyyy")

    // Parse input string to LocalDateTime
    val dateTime = LocalDateTime.parse(this, inputFormatter)

    // Format LocalDateTime to desired output format
    return dateTime.format(outputFormatter)
}
