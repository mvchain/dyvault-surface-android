package com.mvc.topay.and.topay_android.common

object HttpUrl {
    /**
     * Send verification code (do not enter the email address, directly take the current user registration email)
     */
    const val USER_SEND_EMAIL = "user/email/logout"


    /**
     * Send verification code (do not enter the email address, directly take the current user registration email)
     */
    const val USER_EMAIL = "user/email"
    /**
     * User registration, you need to bring the previous information and token together for verification.
     */
    const val USER_REGISTER = "user/register"

    /**
     * Verify registration information, return a one-time token, need to be passed in subsequent registration
     */
    const val USER_VERIFICATION = "user"

    /**
     * refresh token
     */
    const val USER_REFRESH = "user/refresh"


    /**
     * user login
     */
    const val USER_LOGIN = "user/login"

    /**
     * Verify the mailbox before changing the password
     */
    const val USER_RESET = "user/reset"

    /**
     * forget password
     */
    const val USER_FORGET = "user/forget"

    /**
     * Get the exchange rate and refresh every 12 hours.
     */
    const val EXCHANGE_RATE = "token/exchange/rate"

    /**
     * currency list
     */
    const val CURRENCY_LIST = "token"

    /**
     * Get the balance, suggest caching. Currency name icon and other information
     */
    const val ASSETS_LIST = "asset"
    /**
     * User information acquisition
     */
    const val USER_INFO = "user/info"

}
