package net.dirox.c4p.common

import java.util.*

class Constants {

}

enum class HttpCode(val code: Int) {
    OK(200),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404)
}

object Api {
    const val USER_GITHUB_LIST = "/users"
    const val USER_GITHUB_ID = "/users/{username}"
    const val REFRESH_TOKEN = "token/refresh"
    const val LOGIN = "login"
    const val SOCIAL_LOGIN = "social-login"
    const val USERS = "public/users"
    const val FUNDRAISERS = "public/fundraisers"
    const val DEALS = "public/deals"
    const val DEAL_DETAIL = "public/deals/{id}"
    const val CHECK_CLAIMABLE = "public/me/deals/claimable"
    const val CHECK_REPORT_FUNDRAISER = "public/me/reported-fundraisers"
    const val CLAIM_DEAL = "public/deals/{id}/claim"
    const val PATCH_LOCATION_COUPON = "public/coupons/{id}"
    const val PATCH_REDEEM = "public/coupons/{id}/redeem"
    const val GET_COUPON_DETAIL = "public/coupons/{code}"
    const val BUSINESSES = "/api/public/businesses"
    const val CREATE_FUNDRAISERS = "public/fundraisers"
    const val MY_FUNDRAISERS = "public/me/fundraisers"
    const val MY_FAVORITE_BUSINESS = "public/me/favorite/business"
    const val MY_FAVORITE_FUNDRAISERS = "public/me/favorite/fundraiser"
    const val FUNDRAISERS_STATUS = "public/fundraisers/{id}/status"
    const val FILE_URLS = "public/files/urls"
    const val ME = "public/me"
    const val UPDATE_EMAIL = "public/me/email"
    const val UPDATE_PHONE = "public/me/phone-number"
    const val MY_POINTS = "public/me/points"
    const val CREATE_ACCOUNT = "public/users"
    const val CHECK_USER_CODE = "public/users/reference/{code}"
    const val ACTIVATE_USER = "public/users/activate/{code}"
    const val CHANGE_PASSWORD = "public/me/change-password"
    const val FILES = "public/files"
    const val EXPORT_TAX_RECEIPT = "public/me/donations/tax-receipt"
    const val EXPORT_FUNDRAISER_PARTICIPANTS = "public/me/fundraisers/{id}/participants/export"
    const val EXPORT_FUNDRAISER_DONORS = "public/me/fundraisers/{id}/donors/export"
    const val CATEGORY = "public/categories"
    const val FUNDRAISER_DETAIL = "public/fundraisers/{id}"
    const val FUNDRAISER_DETAIL_COMMENT = "public/fundraisers/{id}/comments"
    const val FUNDRAISER_CAMPAIGN_MANAGER = "public/fundraisers/{id}/managers"
    const val FUNDRAISER_DETAIL_LOG = "public/fundraisers/{id}/logs"
    const val FUNDRAISER_DETAIL_DONOR = "public/fundraisers/{id}/donors"
    const val FUNDRAISER_DETAIL_DONATION = "public/fundraisers/{id}/donations"
    const val FUNDRAISER_DETAIL_PARTICIPANT = "public/fundraisers/{id}/participants"
    const val FUNDRAISER_DETAIL_JOIN = "public/fundraisers/{id}/participate"
    const val CHANGE_STATUS_PARTICIPANTS = "public/fundraisers/participants"
    const val FUNDRAISER_REMOVE_CAMPAIGN_MANAGER = "/api/public/fundraisers/{id}/manager/{mid}/remove"
    const val FUNDRAISER_COMMENT = "public/fundraisers/comments"
    const val FUNDRAISER_LOG = "public/logs"
    const val FUNDRAISER_REPORT = "public/fundraisers/report"
    const val FAVORITES_CHECK = "public/favorites/check/{type}"
    const val FAVORITES = "public/favorites"
    const val FORGOT_PASSWORD_EMAIL = "public/resetting/request"
    const val FORGOT_PASSWORD_PHONE = "public/resetting/request"
    const val FORGOT_PASSWORD_VERIFY_OTP = "resetting/otp-token-exchange"
    const val CREATE_ACCOUNT_VERIFY_OTP = "public/me/verify-phone-number"
    const val UPDATE_PHONE_VERIFY_OTP_OLD_PHONE = "public/change-phone-number/confirm"
    const val UPDATE_PHONE_VERIFY_OTP_NEW_PHONE = "public/change-phone-number/verify"
    const val CREATE_ACCOUNT_RESEND_OTP = "public/me/resend-phone-number-verification"
    const val CREATE_ACCOUNT_RESEND_EMAIL = "public/me/resend-email-verification"
    const val FORGOT_PASSWORD_NEW_PASS = "public/me/set-password"
    const val FCM_DEVICE_ID = "public/device-token"
    const val SETTINGS = "public/settings"
    const val CONSTANTS = "public/constants"
    const val DONATE_PAYMENT_METHODS = "public/payment-methods"
    const val PAYMENT_METHODS_MINE = "public/me/payment-methods"
    const val DONATION = "public/donations"
    const val MY_DONATION = "public/me/donations"
    const val MY_NOTIFICATIONS = "public/me/notifications"
    const val NOTIFICATIONS = "public/notifications/{id}"
    const val READ_NOTIFICATIONS = "public/notifications/{id}/read"
    const val FUNDRAISER_STATISTIC = "public/fundraisers/statistic"
    const val COUPONS = "public/coupons"
    const val MY_COUPONS = "public/me/coupons"
    const val POINTS = "public/me/points"
    const val POST_SHARE_FUNDRAISER = "public/fundraisers/{id}/shares"
    const val UNREAD_NOTIFICATIONS_NUMBER = "public/me/notifications/unread"
    const val STRIPE_CONNECT = "public/stripe/connect"
    const val API_VERSION = "version"
    const val FUNDRAISER_STATE = "public/fundraisers/{id}/state"
    const val STRIPE_KEYS = "public/stripe/keys"
}

enum class SocialProvider(val providerName: String) {
    FACEBOOK("facebook"),
    GOOGLE("google")
}

object UploadFolder {
    val FUNDRAISER = "fundraiser"
    val USER_PROFILE = "user_profile"
    val COMMENT = "comment"
    val LOG = "log"
}

object DateTimeConstants {
    val defaultDateTimeFormat = "dd/MM/yyyy"
    val dateTimeFormatUS = "dd/MM/yyyy"
    val enUSPosixLocale = Locale.US
    val apiDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
}

object StringPattern {
    val PATTERN_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}"
}

object StringLinkPattern {
    val PATTERN_YOUTUBE = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+"
}

object LocalNotification {
    const val RefreshTokenFailed = "RefreshTokenFailed"
    const val OnRefCodeReceived = "OnRefCodeReceived"
}

object RemoteNotification {
    val NOTIFICATION_ID_PARAM = "notification_id"
}

object Limit {
    const val UPLOAD_FILE_SIZE = 8 * 1024 * 1024
}

object ReadMoreOptionConstant {
    const val MAX_CHARACTER = 100
    const val MAX_LINE = 3
}

object KeyResendEmail {
    const val NEW_ACCOUNT = "new_account"
    const val VERIFY_OLD_EMAIL = "change_email"
    const val VERIFY_NEW_EMAIL = "verify_new_email"
}

object KeyResendPhone {
    const val NEW_ACCOUNT = "new_account"
    const val VERIFY_OLD_PHONE = "change_phone_number"
    const val VERIFY_NEW_PHONE = "verify_new_phone_number"
}

object Gender {
    const val MALE = "MALE"
    const val FEMALE = "FEMALE"
    const val OTHER = "OTHER"
}

enum class ShareAction {
    COPY,
    COPY_CODE,
    FACEBOOK,
    TWITTER,
    EMAIL,
    TEXT
}
enum class ShareType {
    FUNDRAISER,
    REFER_FRIEND
}