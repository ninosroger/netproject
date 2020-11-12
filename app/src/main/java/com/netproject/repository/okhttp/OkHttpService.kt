package com.netproject.repository.okhttp

import com.netproject.NetApplication
import com.netproject.repository.api.ApiService
import com.netproject.repository.common.NetConstants
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class OkHttpService {
    companion object {
        fun getService(): ApiService = Holder.retrofit.create(ApiService::class.java)
        fun getOtherService(): ApiService = OtherHolder.retrofit.create(ApiService::class.java)
    }

    private object Holder {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetConstants.BASE_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private object OtherHolder {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetConstants.OTHER_URL)
            .client(
                OkHttpClient()
                    .newBuilder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(invoke {
                        var originalResponse = it.proceed(it.request())
                        if (originalResponse.headers("set-cookie").isNotEmpty()) {
                            var cookies = originalResponse.headers("set-cookie")
                            var cookieBuffer = StringBuffer()
                            for (str in cookies) {
                                cookieBuffer.append(str).append(";")
                            }
                            NetApplication.cookies = cookieBuffer.toString()
                                .substring(0, cookieBuffer.toString().length - 1)
                        }
                        return@invoke originalResponse
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
}