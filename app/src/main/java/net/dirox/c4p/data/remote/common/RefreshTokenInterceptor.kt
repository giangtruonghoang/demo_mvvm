package net.dirox.c4p.data.remote.common

import android.util.Log
import net.dirox.c4p.data.prefs.UserPrefs
import okhttp3.*
import com.google.gson.Gson
import net.dirox.c4p.BuildConfig
import net.dirox.c4p.data.request.RefreshTokenRequest
import okhttp3.RequestBody
import net.dirox.c4p.C4PApp
import net.dirox.c4p.common.Api
import net.dirox.c4p.common.HttpCode
import net.dirox.c4p.data.response.RefreshTokenResponse
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


class RefreshTokenInterceptor(private val connectionSpec: ConnectionSpec) : Interceptor {

    private var needRefreshToken = AtomicBoolean(false)

    private val noCheckUnAuthorizedList = mutableListOf<String>(
        Api.LOGIN
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.newBuilder()

        val token = UserPrefs.getAccessToken()
        setAuthHeader(builder, token)

        request = builder.build()
        val response = chain.proceed(request)

        if (response.code() == HttpCode.UNAUTHORIZED.code && !isNonAuthorizedUrl(request.url())) {

            needRefreshToken.set(true)

            synchronized(this) {

                if (!needRefreshToken.get()) {
                    setAuthHeader(builder, UserPrefs.getAccessToken())
                    request = builder.build()
                    response.close()
                    return chain.proceed(request)
                }

                val res = refreshToken()
                needRefreshToken.set(false)

                if (res.accessToken != null && res.refreshToken != null) {
                    // Refresh token success
                    C4PApp.instance.onAccessTokenRefreshed()
                    UserPrefs.saveAccessToken(res.accessToken)
                    UserPrefs.saveRefreshToken(res.refreshToken)
                    setAuthHeader(builder, res.accessToken)
                    request = builder.build()
                    response.close()
                    return chain.proceed(request)
                } else {
                    C4PApp.instance.onRefreshTokenFailed()
                    response.close()
                    Thread.sleep(24 * 60 * 60 * 1000)

                    return response
                }
            }

        } else {
            return response
        }
    }

    private fun refreshToken(): RefreshTokenResponse {

        if (UserPrefs.getRefreshToken() == null) {
            return RefreshTokenResponse(null, null)
        }

        val refreshToken = UserPrefs.getRefreshToken()!!

        val gson = Gson()
        val contentType = MediaType.get("application/json; charset=utf-8")
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        if (BuildConfig.API_HOST.startsWith("https")) {
            clientBuilder.connectionSpecs(Collections.singletonList(connectionSpec))
        }
        val client = clientBuilder.build()
        val body = RequestBody.create(contentType, gson.toJson(RefreshTokenRequest(refreshToken)))
        val request = Request.Builder()
            .url(BuildConfig.API_HOST + Api.REFRESH_TOKEN)
            .post(body)
            .build()
        val response = client.newCall(request).execute()

        return try {
            val data = gson.fromJson(response.body()?.string(), RefreshTokenResponse::class.java)
            Log.i("OkHttp", "--> Refreshed token successfully: ${data.accessToken} - refreshToken: ${data.refreshToken}")
            data
        } catch(e: Exception) {
            Log.i("OkHttp", "--> Refreshed token failed: ${response.code()}")
            RefreshTokenResponse(null, null)
        }
    }

    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        if (token != null && !builder.build().url().toString().contains(Api.API_VERSION)) builder.header("Authorization", String.format("Bearer %s", token))
    }

    private fun isNonAuthorizedUrl(url: HttpUrl): Boolean {
        return noCheckUnAuthorizedList.map { BuildConfig.API_HOST + it }.contains(url.toString())
    }
}