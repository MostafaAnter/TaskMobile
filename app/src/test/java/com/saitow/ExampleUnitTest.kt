package com.saitow

import com.saitow.utils.InputValidation
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun checkRoutingCodeValidation() {
        val isValid = InputValidation.isValidBankRoutingCode("54050110")
        assertEquals(true, isValid)
    }

    @Test
    fun checkBICCodeValidation() {
        val isValid = InputValidation.isValidBIC("MALADE51DKH")
        assertEquals(true, isValid)
    }

    @Test
    fun checkIBANCodeValidation() {
        val isValid = InputValidation.isValidIBAN("DE91 5465 1240 0001 6089 75")
        val isValid2 = InputValidation.isValidIBAN("DE91546512400001608975")
        assertEquals(true, isValid)
        assertEquals(false, isValid2)
    }


}