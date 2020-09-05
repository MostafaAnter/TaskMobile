package com.saitow.data.api

import com.saitow.data.model.SearchResponse
import com.saitow.data.model.ValidationResponse
import io.reactivex.Single

/**
 * Created by Mostafa Anter on 9/5/20.
 */
interface ApiHelper {
    fun searchForBankData(routingCode: String): Single<SearchResponse>

    fun validateBIC(bicToValidate: String): Single<ValidationResponse>

    fun validateIBAN(iban: String): Single<ValidationResponse>

    fun validatePostCode(countryCode: String, postCode: String): Single<ValidationResponse>
}