package com.mvc.topay.and.topay_android.common

object HttpUrl {
    /**
     * Send verification code (do not enter the email address, directly take the current user registration email)
     */
    const val USER_SEND_EMAIL = "/user/email"


    /**
     * User registration, you need to bring the previous information and token together for verification.
     */
    const val USER_REGISTER = "/user/register"

    /**
     * Verify registration information, return a one-time token, need to be passed in subsequent registration
     */
    const val USER_VERIFICATION = "/user"

}
