package net.dirox.c4p.data.response

import com.google.gson.annotations.SerializedName

class RefreshTokenResponse(@SerializedName("token") val accessToken: String?,
                           @SerializedName("refresh_token") val refreshToken: String?): BaseResponse()