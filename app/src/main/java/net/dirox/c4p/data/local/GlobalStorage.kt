package net.dirox.c4p.data.local

import java.util.*


object GlobalStorage {
    var listCountries: Map<String, String> = mapOf()

    var apiVersion: String? = null

    var isAcceptPermissionLocation : Boolean = false

    var currentUserLocation: AppLocation = AppLocation(null, null, Date().time)


    var cacheKeysForBusinesses: MutableMap<String, String> = mutableMapOf()
    var cacheKeysForDeals: MutableMap<String, String> = mutableMapOf()

    var resettingUsername: String? = null
    var resettingEmail: String? = null
}

data class AppLocation(
    var latitude: Double?,
    var longitude: Double?,
    var createdAt: Long
) {
    override fun toString(): String {
        return "$latitude;$longitude;$createdAt"
    }

    companion object {
        fun fromString(s: String?): AppLocation? {
            val s = s ?: return null
            val parts = s.split(";")
            if (parts.size == 3) {
                if (parts[0] == "null" && parts[1] == "null")
                    return null
                val latitude = parts[0].toDouble()
                val longitude = parts[1].toDouble()
                val createdAt = parts[2].toLong()
                return AppLocation(latitude, longitude, createdAt)
            }
            return null
        }
    }
}