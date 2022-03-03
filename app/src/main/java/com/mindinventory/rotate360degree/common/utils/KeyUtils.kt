package com.mindinventory.rotate360degree.common.utils

object KeyUtils {
    const val TOKEN_PREFIX = "Bearer "
    const val UN_AUTHORIZATION_LOGOUT = "unAuthorizationLogout"
    const val AUTHORIZATION = "Authorization"
    const val ACCEPT_LANGUAGE = "Accept-Language"
    const val ACCEPT_LANGUAGE_EN = "en-US"
    const val SPLASH_TIMEOUT = 3000L

    const val HTTP_SUCCESS = 1
    const val HTTP_FAILURE = 2
    const val HTTP_AUTH_ERROR = 3

    // login keys
    const val FACEBOOK_SIGN_IN = "facebook"
    const val GOOGLE_SIGN_IN = "google"
    const val SOCIAL_LOGIN_REQUEST = 101

    // cms
    const val TERMS_OF_USE = 1101
    const val PRIVACY_POLICY = 1102
    const val ABOUT_US = 1103

    //WorkManager
    const val REMINDER = "reminder"
}
