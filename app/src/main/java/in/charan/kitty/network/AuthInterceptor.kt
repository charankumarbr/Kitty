package `in`.charan.kitty.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by Charan on March 27, 2021
 */
class AuthInterceptor: Interceptor {

    private val PRAGMA = "Pragma"

    private val CACHE_CONTROL = "Cache-Control"

    private var cacheControl: CacheControl? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("x-api-key", "")
            .build()
        val response = chain.proceed(request)

        return response.newBuilder()
            .removeHeader(PRAGMA)
            .removeHeader(CACHE_CONTROL)
            .addHeader(CACHE_CONTROL, getCacheControl().toString())
            .build()
    }

    private fun getCacheControl(): CacheControl {
        if (cacheControl == null) {
            cacheControl = CacheControl.Builder()
                .maxAge(12, TimeUnit.HOURS)
                .build()
        }
        return cacheControl!!
    }
}