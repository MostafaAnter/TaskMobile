package com.saitow.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidationData(
    @Json(name = "bic")
    val bic: String = "",
    @Json(name = "iban")
    val iban: String = "",
    @Json(name = "postCode")
    val postCode: String = ""
)