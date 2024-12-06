package com.memtionsandroid.memotions.utils

sealed class DataResult<out R> private constructor() {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val error: Event<String>) : DataResult<Nothing>()
    data object Loading : DataResult<Nothing>()
    data object Idle : DataResult<Nothing>()
}