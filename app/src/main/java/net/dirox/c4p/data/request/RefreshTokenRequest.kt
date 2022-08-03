package net.dirox.c4p.data.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(@SerializedName("refresh_token") val refreshToken: String)