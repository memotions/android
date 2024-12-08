package com.memtionsandroid.memotions.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_DATE_TIME // Atau sesuaikan dengan format yang digunakan
    return LocalDateTime.parse(this, formatter)
}

fun String.toNickname(): String {
    val words = this.split(" ").take(2)
    return when {
        words[0].length > 10 -> words[0]
        words.size > 1 && words[1].length > 10 -> words[1]
        else -> words.joinToString(" ")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatMonthYear(): String {
    if (this.length == 4) return this
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id", "ID"))

    val adjustedInput = "$this 01"

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd")
    val localDate = LocalDate.parse(adjustedInput, dateFormatter)
    return localDate.format(outputFormatter)
}



@RequiresApi(Build.VERSION_CODES.O)
fun String.formatDateTime(): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME // ISO_DATE_TIME mendukung zona waktu
    val outputFormatter = DateTimeFormatter.ofPattern("HH.mm • dd MMMM yyyy")

    return try {
        val dateTime = LocalDateTime.parse(this, inputFormatter)
        val now = LocalDateTime.now()
        val duration = java.time.Duration.between(dateTime, now)
        when {
            duration.toMinutes() < 1 -> "Baru saja"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} menit lalu"
            duration.toHours() < 24 -> "${duration.toHours()} jam lalu"
            else -> dateTime.format(outputFormatter) // Format default jika lebih dari sehari
        }
    } catch (e: Exception) {
        "Format waktu tidak valid"
    }
}
