package com.saitow.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Bic(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "countryId")
    val countryId: String = "",
    @Json(name = "bankName")
    val bankName: String = "",
    @Json(name = "location")
    val location: String = "",
    @Json(name = "blz")
    val blz: String = "",
    @Json(name = "bicCode")
    val bicCode: String = "",
    @Json(name = "bicCodeOther")
    val bicCodeOther: List<String> = listOf(),
    @Json(name = "countryCode")
    val countryCode: String = ""
)