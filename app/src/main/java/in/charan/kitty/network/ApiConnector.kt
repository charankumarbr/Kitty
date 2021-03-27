package `in`.charan.kitty.network

import `in`.charan.kitty.BuildConfig
import `in`.charan.kitty.KittyApp
import `in`.charan.kitty.network.api.BreedApi
import `in`.charan.kitty.util.AppUtil
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Charan on March 27, 2021
 */
object ApiConnector {

    private const val VERSION = "v1"
    private const val BASE_URL = "https://api.thecatapi.com/$VERSION/"

    private lateinit var retrofit: Retrofit
    private lateinit var breedApi: BreedApi

    fun getBreedApi(): BreedApi {
        if (!this::retrofit.isInitialized) {
            breedApi = getRetrofit().create(BreedApi::class.java)
        }
        return breedApi
    }

    private fun getRetrofit(): Retrofit {
        if (!this::retrofit.isInitialized) {
            val objectMapper = ObjectMapper()
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            objectMapper.registerModule(KotlinModule())
            retrofit = Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .build()
        }
        return retrofit
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(getLoggingInterceptor())
            .addNetworkInterceptor(AuthInterceptor())
            .callTimeout(20, TimeUnit.SECONDS)
            .cache(getCache())
            .build()
    }

    private fun getCache(): Cache {
        val httpCacheDirectory = File(KittyApp.appContext.cacheDir, "http-cache")
        //-- 10 MB cache --//
        val cacheDirSize = AppUtil.CACHE_SIZE_IN_MB * 1024 * 1024
        return Cache(httpCacheDirectory, cacheDirSize.toLong())
    }

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

}