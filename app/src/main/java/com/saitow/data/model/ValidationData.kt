package com.saitow.data.model


import com.google.gson.annotations.SerializedName

data class ValidationData(
    @SerializedName("bic", alternate= ["iban", "postCode"])
    val text: String = ""
)