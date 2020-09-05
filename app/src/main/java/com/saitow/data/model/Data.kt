package com.saitow.data.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("pageCount")
    val pageCount: Int = 0,
    @SerializedName("bics")
    val bics: List<Bic> = listOf(),
    @SerializedName("location")
    val location: String = "",
    @SerializedName("bankName")
    val bankName: String = "",
    @SerializedName("countryCode")
    val countryCode: String = "",
    @SerializedName("blz")
    val blz: String = "",
    @SerializedName("bic")
    val bic: String = ""
)