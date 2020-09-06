package com.saitow.utils

/**
 * Created by Mostafa Anter on 9/6/20.
 */
class InputValidation {
    companion object{
        fun isValidBankRoutingCode(code : String): Boolean{
            val regex = Regex(pattern = """\d{8}""")
            return code.matches(regex)
        }

        fun isValidBIC(code : String): Boolean{
            val regex = Regex(pattern = """[A-Z]{6}[0-9A-Z]{2}([0-9A-Z]{3})""")
            return code.matches(regex)
        }

        fun isValidIBAN(code : String): Boolean{
            val regex = Regex(pattern = """[A-Z]{2}\d{2} \d{4} \d{4} \d{4} \d{4} [\d]{0,2}""")
            return code.matches(regex)
        }
    }
}