package com.wisilica.retrfit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor: Interceptor {
    private var mContext: Context? = null


    constructor(context: Context?) {
        mContext = context
    }


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (!isConnected()) {
            throw NoConnectivityException()
            // Throwing our custom exception 'NoConnectivityException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


    fun isConnected(): Boolean {
        try {
            if (mContext != null) {
                val connMgr = mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connMgr.activeNetworkInfo
                return if (networkInfo != null && networkInfo.isConnected) {
                    true
                } else {
                    false
                }

            } else {
                return false
            }
        } catch (e: Exception) {
            return false
        }
    }

}