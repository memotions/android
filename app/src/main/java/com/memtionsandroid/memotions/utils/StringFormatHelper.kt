package com.memtionsandroid.memotions.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)

fun String.toLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_DATE_TIME // Atau sesuaikan dengan format yang digunakan
    return LocalDateTime.parse(this, formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatDateTime(): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME // ISO_DATE_TIME mendukung zona waktu
    val outputFormatter = DateTimeFormatter.ofPattern("HH.mm • dd MMMM yyyy")

    return try {
        // Parse input string to LocalDateTime
        val dateTime = LocalDateTime.parse(this, inputFormatter)

        // Hitung waktu sekarang
        val now = LocalDateTime.now()

        // Hitung selisih waktu
        val duration = java.time.Duration.between(dateTime, now)

        // Tampilkan waktu relatif jika masih hari ini
        when {
            duration.toMinutes() < 1 -> "Baru saja"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} menit lalu"
            duration.toHours() < 24 -> "${duration.toHours()} jam lalu"
            else -> dateTime.format(outputFormatter) // Format default jika lebih dari sehari
        }
    } catch (e: Exception) {
        "Format waktu tidak valid" // Tangani error jika parsing gagal
    }
}
