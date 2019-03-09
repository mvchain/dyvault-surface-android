package com.mvc.topay.and.topay_android.common

class Constant {

    object SP {
        const val TOKEN = "token"
        const val SET_RATE = "set_rate"
        const val BALANCE = "balance"
        const val RATE_LIST = "rate_list"  //All exchange rate lists
        const val DEFAULT_SYMBOL = "default_symbol" //Default exchange rate unit
        const val DEFAULT_RATE = "default_rate"//Default exchange rate
        const val ASSETS_LIST = "assets_list" //Default exchange rate unit
        const val REFRESH_TOKEN = "refresh_token"   //Long token for refreshing short tokens
        const val TEMP_TOKEN = "temp_token"     //Temporary token
        const val TEMP_EMAIL = "temp_email"//Temporary email
        const val USER_ID = "userId"
        const  val CURRENCY_LIST = "currency_list" //save all currency
        const val USER_EMAIL = "email"
        const val USER_SALT = "salt"
        const val USER_INFO = "user_info"
        const val OLD_TIME = "old_time"
        const val READ_MSG = "read_msg"
        const val USER_RESETPASSWORD_TYPE = "user_resetpassword_type"   //Temporarily save the type of password change
        const val RESETPASSWORD_LOGIN = 1   //change login password
        const val RESETPASSWORD_PAY = 2//change pay password
    }

    object LANGUAGE {
        //APP Language
        const val DEFAULT_LANGUAGE = "zh_CN"
        const val CHINESE = "zh_CN"
        const val ENGLISH = "en"
        const val DEFAULT_ENGLUSH = "default_english"
        //Setting interface internationalization
        const val DEFAULT_ACCEPT_LANGUAGE = "default_accept_language"
        const val ACCEPT_CHINESE = "zh-cn"
        const val ACCEPT_ENGLISH = "en-US"
    }
}
