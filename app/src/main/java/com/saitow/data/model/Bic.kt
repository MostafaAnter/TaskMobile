package com.saitow.data.model


import com.google.gson.annotations.SerializedName

data class Bic(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("countryId")
    val countryId: String = "",
    @SerializedName("bankName")
    val bankName: String = "",
    @SerializedName("location")
    val location: String = "",
    @SerializedName("blz")
    val blz: String = "",
    @SerializedName("bicCode")
    val bicCode: String = "",
    @SerializedName("bicCodeOther")
    val bicCodeOther: List<Any> = listOf(),
    @SerializedName("countryCode")
    val countryCode: String = ""
)