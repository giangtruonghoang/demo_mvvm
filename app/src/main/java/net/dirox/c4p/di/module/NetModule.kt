package net.dirox.c4p.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import net.dirox.c4p.BuildConfig
import net.dirox.c4p.common.Api
import net.dirox.c4p.data.local.GlobalStorage
import net.dirox.c4p.data.service.ApiService
import net.dirox.c4p.data.remote.common.RefreshTokenInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val netModule = module {

    factory(named("http-logger")) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return@factory loggingInterceptor
    }

    factory {
        val cacheSize = 10 * 1024 * 1024L
        return@factory Cache(androidContext().applicationContext.cacheDir, cacheSize)
    }

    factory(named("refresh-token")) {
        RefreshTokenInterceptor(get())
    }

    factory(named("common")) {
        Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
                .header("User-Agent", "Android")
                .header("Content-Type", "application/json")
                .header("Accept", "application/vnd.github+json")

            chain.proceed(builder.build())
        }
    }

    factory(named("support-caching")) {
        Interceptor { chain ->
            val request = chain.request()
            val urlBuilder = request.url().newBuilder()
            if (GlobalStorage.apiVersion != null&& !request.url().toString().contains(Api.API_VERSION)) {
                urlBuilder.addQueryParameter("version", GlobalStorage.apiVersion)
            }
            val newRequest = request.newBuilder().url(urlBuilder.build())
            chain.proceed(newRequest.build())
        }
    }

    factory {
        val client = OkHttpClient.Builder()
        client.cache(get())
        client.addInterceptor(get(named("http-logger")))
        client.addInterceptor(get(named("common")))
        client.addInterceptor(get(named("refresh-token")))
        client.addInterceptor(get(named("support-caching")))
        if (BuildConfig.API_HOST.startsWith("https")) {
            client.connectionSpecs(Collections.singletonList<ConnectionSpec>(get()))
        }
        return@factory client.build()
    }

    factory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return@factory gsonBuilder.create()
    }

    factory {
        ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.API_HOST)
            .client(get())
            .build()
    }

    single {
        val retrofit: Retrofit = get()
        return@single retrofit.create(ApiService::class.java)
    }
}