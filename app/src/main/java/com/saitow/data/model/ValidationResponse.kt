package com.saitow.data.model


import com.google.gson.annotations.SerializedName

data class ValidationResponse(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("data")
    val `data`: ValidationData = ValidationData()
)