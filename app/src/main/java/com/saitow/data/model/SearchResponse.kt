package com.saitow.data.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("data")
    val `data`: Data = Data()
)