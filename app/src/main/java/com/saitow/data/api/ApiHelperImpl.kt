package com.saitow.data.api

import com.saitow.data.model.SearchResponse
import com.saitow.data.model.ValidationResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Mostafa Anter on 9/5/20.
 */
class ApiHelperImpl @Inject constructor(private val apiHelper: ApiHelper): ApiHelper {
    override fun searchForBankData(routingCode: String): Single<SearchResponse> = apiHelper.searchForBankData(routingCode)

    override fun validateBIC(bicToValidate: String): Single<ValidationResponse> = apiHelper.validateBIC(bicToValidate)

    override fun validateIBAN(iban: String): Single<ValidationResponse> = apiHelper.validateIBAN(iban)

    override fun validatePostCode(
        countryCode: String,
        postCode: String): Single<ValidationResponse> = apiHelper.validatePostCode(countryCode, postCode)
}