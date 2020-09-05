package com.saitow.data.repository

import com.saitow.data.api.ApiHelper
import javax.inject.Inject

/**
 * Created by Mostafa Anter on 9/5/20.
 */
class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    fun searchForBankData(routingCode: String) = apiHelper.searchForBankData(routingCode)

    fun validateBIC(bicToValidate: String) = apiHelper.validateBIC(bicToValidate)

    fun validateIBAN(iban: String) = apiHelper.validateIBAN(iban)

    fun validatePostCode(countryCode: String, postCode: String) = apiHelper.validatePostCode(countryCode, postCode)
}