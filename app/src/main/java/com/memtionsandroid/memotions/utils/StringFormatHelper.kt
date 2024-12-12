package com.memtionsandroid.memotions.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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

fun String.formatMonthYear(): String {
    if (this.length == 4) return this
    val outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("id", "ID"))

    val adjustedInput = "$this 01"

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd")
    val localDate = LocalDate.parse(adjustedInput, dateFormatter)
    return localDate.format(outputFormatter)
}

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


fun Int.toRomanNumerals(): String {
    val romanNumerals = listOf(
        Pair(1_000, "M"), Pair(900, "CM"), Pair(500, "D"), Pair(400, "CD"),
        Pair(100, "C"), Pair(90, "XC"), Pair(50, "L"), Pair(40, "XL"),
        Pair(10, "X"), Pair(9, "IX"), Pair(5, "V"), Pair(4, "IV"),
        Pair(1, "I")
    )

    var number = this
    val result = StringBuilder()

    for ((value, numeral) in romanNumerals) {
        while (number >= value) {
            result.append(numeral)
            number -= value
        }
    }

    return result.toString()
}



fun Long?.toformatDateFromMillis(default: String, locale: Locale = Locale.getDefault()): String {
    return if (this != null) {
        val formatter = SimpleDateFormat("dd MMM yyyy", locale)
        formatter.format(Date(this))
    } else {
        default
    }
}

fun Long.toLocalDate(zoneId: ZoneId = ZoneId.systemDefault()): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .toLocalDate()
}

