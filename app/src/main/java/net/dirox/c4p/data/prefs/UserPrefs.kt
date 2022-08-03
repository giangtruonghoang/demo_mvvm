package net.dirox.c4p.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import net.dirox.c4p.C4PApp
import net.dirox.c4p.data.local.AppLocation

object UserPrefs  {

    private val sharedPreferences: SharedPreferences by lazy {
        C4PApp.instance.getSharedPreferences("", Context.MODE_PRIVATE)
    }

    private const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
    fun saveAccessToken(value: String?) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN_KEY, value)
        }
    }
    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }

    private const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
    fun saveRefreshToken(value: String?) {
        sharedPreferences.edit {
            putString(REFRESH_TOKEN_KEY, value)
        }
    }
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY,null)
    }

    fun isUserLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    private const val LAST_LOGGED_EMAIL_KEY = "LAST_LOGGED_EMAIL"
    fun saveLastLoggedEmail(email: String) {
        sharedPreferences.edit {
            putString(LAST_LOGGED_EMAIL_KEY, email)
        }
    }
    fun getLastLoggedEmail(): String? {
        return sharedPreferences.getString(LAST_LOGGED_EMAIL_KEY,null)
    }

    private const val CREATED_USERNAME_KEY = "CREATED_USERNAME"
    fun saveCreatedUserName(email: String?) {
        sharedPreferences.edit {
            putString(CREATED_USERNAME_KEY, email)
        }
    }

    fun getCreatedUserName(): String? {
        return sharedPreferences.getString(CREATED_USERNAME_KEY,null)
    }

    fun clear() {
        sharedPreferences.edit {
            remove(ACCESS_TOKEN_KEY)
            remove(REFRESH_TOKEN_KEY)
        }
    }

    fun saveShareFundraiserId(userId : String,  fundraiserId : String, value: Boolean) {
        sharedPreferences.edit {
            putBoolean(userId + fundraiserId, value)
        }
    }

    fun getShareFundraiserId(userId : String,  fundraiserId : String) : Boolean {
        return sharedPreferences.getBoolean(userId + fundraiserId,false)
    }

    fun saveLocation(location: AppLocation) {
        sharedPreferences.edit {
            putString("LOCATION", location.toString())
        }
    }

    fun getLocation() : AppLocation? {
        return AppLocation.fromString(sharedPreferences.getString("LOCATION", null))
    }

    fun saveJoinedStatus(userId: String, fundraiserId: String) {
        sharedPreferences.edit {
            putBoolean("JOINED_${userId}_${fundraiserId}", true)
        }
    }

    fun getJoinedStatus(userId: String, fundraiserId: String): Boolean {
        return sharedPreferences.getBoolean("JOINED_${userId}_${fundraiserId}", false)
    }

    fun saveReportedStatus(userId: String, fundraiserId: String) {
        sharedPreferences.edit {
            putBoolean("REPORTED_${userId}_${fundraiserId}", true)
        }
    }

    fun getReportedStatus(userId: String, fundraiserId: String): Boolean {
        return sharedPreferences.getBoolean("REPORTED_${userId}_${fundraiserId}", false)
    }

    private const val CREATE_ACCOUNT_REFCODE = "CREATE_ACCOUNT_REFCODE"
    fun saveCreateAccountRefCode(value: String?) {
        sharedPreferences.edit {
            putString(CREATE_ACCOUNT_REFCODE, value)
        }
    }
    fun getCreateAccountRefCode(): String? {
        return sharedPreferences.getString(CREATE_ACCOUNT_REFCODE, null)
    }
}