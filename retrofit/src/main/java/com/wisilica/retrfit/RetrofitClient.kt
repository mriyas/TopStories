package com.wisilica.retrfit

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


class RetrofitClient {

    companion object {

        var instance : Retrofit? = null
        var mContext: Context? = null
        var mUrl: String? = null
        fun getRetrofitClient(context: Context, baseUrl: String):Retrofit?{
            if(mContext==null) {
                mContext = context
                return getRetrofitClient(baseUrl)
            }else{
                return instance as Retrofit;

            }
        }

        fun getRetrofitClient(baseUrl: String): Retrofit? {
            if(null != instance && mContext == null&&baseUrl.equals(mUrl))
                return instance as Retrofit;

            mUrl=baseUrl
            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(interceptor)
            }
            //builder.authenticator(AuthTokenAuthenticator())
            //var client: OkHttpClient = builder.build()

            var client: OkHttpClient = getUnsafeOkHttpClient().build()

            if(null != mContext) {
                builder.addInterceptor(NetworkConnectionInterceptor(mContext))
            }


            instance = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(mUrl)
                .build()
            return instance

        }

        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {

            try {

                // Create a trust manager that does not validate certificate chains

                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                    @Throws(CertificateException::class)

                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {

                    }



                    @Throws(CertificateException::class)

                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {

                    }



                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {

                        return arrayOf()

                    }

                })



                // Install the all-trusting trust manager

                val sslContext = SSLContext.getInstance("SSL")

                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager

                val sslSocketFactory = sslContext.socketFactory



                val builder = OkHttpClient().newBuilder()
                builder.readTimeout(60, TimeUnit.SECONDS)
                builder.connectTimeout(60, TimeUnit.SECONDS)

                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    builder.addInterceptor(interceptor)
                }
                if(null != mContext) {
                    builder.addInterceptor(NetworkConnectionInterceptor(mContext))
                }
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)

                builder.hostnameVerifier { _, _ -> true }

                //builder.authenticator(AuthTokenAuthenticator())

                return builder

            } catch (e: Exception) {

                throw RuntimeException(e)

            }

        }

//        fun cancelAll() {
//
//            if (client != null && client!!.dispatcher() != null) {
//                client!!.dispatcher().cancelAll()
//            }
//        }
    }
}