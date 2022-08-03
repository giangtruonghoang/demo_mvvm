package net.dirox.c4p.data.response

import com.google.gson.annotations.SerializedName


data class BaseError(
    @SerializedName("messages")
    val messages: List<String>?
)



