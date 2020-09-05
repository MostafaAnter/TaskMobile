package com.saitow.data.api

import com.saitow.data.model.SearchResponse
import com.saitow.data.model.ValidationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Mostafa Anter on 9/4/20.
 */
interface ApiService {
    // Search for Bank Data
    @GET("/searchBic")
    fun searchForBankData(@Query("blz") routingCode: String): Single<SearchResponse>

    // Validate BIC
    @GET("/validateBic")
    fun validateBIC(@Query("bic") bicToValidate: String): Single<ValidationResponse>

    // Validate IBAN
    @GET("/validateIban")
    fun validateIBAN(@Query("iban") iban: String): Single<ValidationResponse>


    // Search for Bank Data
    @GET("/validatePostCode")
    fun validatePostCode(@Query("countryCode") countryCode: String, @Query("postCode") postCode: String): Single<ValidationResponse>
}