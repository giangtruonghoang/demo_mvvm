package net.dirox.c4p.data.response

import com.google.gson.annotations.SerializedName

abstract class BaseResponse {
    @SerializedName("code")
    val code: Int? = null
    @SerializedName("status")
    val status: String? = null
    @SerializedName("message")
    val message: String? = null
    @SerializedName("error_code")
    val error_code: String? = null
    @SerializedName("error")
    val error: BaseError? = null
}