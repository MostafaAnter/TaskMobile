package com.saitow.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidationResponse(
    @Json(name = "code")
    val code: String = "",
    @Json(name = "data")
    val `data`: ValidationData = ValidationData()
)