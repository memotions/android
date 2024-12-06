package com.memtionsandroid.memotions.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class ValidationErrorResponse(

    @field:SerializedName("data")
    val data: Any?,

    @field:SerializedName("errors")
    val errors: List<ValidationErrorsItem>,

    @field:SerializedName("status")
    val status: String
)

data class ValidationErrorDetails(

    @field:SerializedName("expected")
    val expected: String?,

    @field:SerializedName("received")
    val received: String?,

    @field:SerializedName("validation")
    val validation: String?
)

data class ValidationErrorsItem(

    @field:SerializedName("path")
    val path: List<String>,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("details")
    val details: ValidationErrorDetails?,

    @field:SerializedName("message")
    val message: String
)
