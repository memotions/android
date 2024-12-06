package com.memtionsandroid.memotions.data.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.memtionsandroid.memotions.data.local.entity.EmotionAnalysis

class ListConverter {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromEmotionAnalysisList(value: List<EmotionAnalysis>?): String? {
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toEmotionAnalysisList(value: String?): List<EmotionAnalysis>? {
        return value?.let {
            val type = object : TypeToken<List<EmotionAnalysis>>() {}.type
            Gson().fromJson(it, type)
        }
    }
}