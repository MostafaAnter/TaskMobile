package com.saitow.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Data(
    @Json(name = "page")
    val page: Int = 0,
    @Json(name = "pageCount")
    val pageCount: Int = 0,
    @Json(name = "bics")
    val bics: List<Bic> = listOf(),
    @Json(name = "location")
    val location: String = "",
    @Json(name = "bankName")
    val bankName: String = "",
    @Json(name = "countryCode")
    val countryCode: String = "",
    @Json(name = "blz")
    val blz: String = "",
    @Json(name = "bic")
    val bic: String = ""
)