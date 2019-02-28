package com.mvc.topay.and.topay_android.common

class Constant {

    object SP {
        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
        const val TEMP_TOKEN = "temp_token"
        const val TEMP_EMAIL = "temp_email"
        const val USER_ID = "userId"
        const val USER_EMAIL = "email"
        const val USER_RESETPASSWORD_TYPE = "user_resetpassword_type"
        const val RESETPASSWORD_LOGIN = 1
        const val RESETPASSWORD_PAY = 2
    }

    object LANGUAGE {
        //APP语言
        const val DEFAULT_LANGUAGE = "zh_CN"
        const val CHINESE = "zh_CN"
        const val ENGLISH = "en"
        const val DEFAULT_ENGLUSH = "default_english"
        //设置接口国际化
        const val DEFAULT_ACCEPT_LANGUAGE = "default_accept_language"
        const val ACCEPT_CHINESE = "zh-cn"
        const val ACCEPT_ENGLISH = "en-US"
    }
}
