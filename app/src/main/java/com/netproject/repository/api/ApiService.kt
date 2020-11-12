package com.netproject.repository.api

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("api.html")
    fun getYzm(@Field("bs") bs: String, @Field("sjh") sjh: String): Observable<JsonObject>

    @FormUrlEncoded
    @POST("api.html")
    fun getP(
        @Field("bs") bs: String,
        @Field("sjh") sjh: String,
        @Field("yzm") yzm: String
    ): Observable<JsonObject>

    @FormUrlEncoded
    @POST("api.html")
    fun login(
        @Field("bs") bs: String,
        @Field("sjh") sjh: String,
        @Field("ck") ck: String,
        @Field("fhz") fhz: String
    ): Observable<JsonObject>

    @FormUrlEncoded
    @POST("mobileService/radomLogin.htm")
    fun radomLogin(
        @Field("isRemberPwd") isRemberPwd: String,
        @Field("loginStyle") loginStyle: String,
        @Field("deviceId") deviceId: String,
        @Field("password") password: String,
        @Field("netWay") netWay: String,
        @Field("mobile") mobile: String,
        @Field("yw_code") yw_code: String,
        @Field("timestamp") timestamp: String,
        @Field("appId") appId: String,
        @Field("keyVersion") keyVersion: String,
        @Field("deviceBrand") deviceBrand: String,
        @Field("pip") pip: String,
        @Field("provinceChanel") provinceChanel: String,
        @Field("version") version: String,
        @Field("deviceModel") deviceModel: String,
        @Field("deviceOS") deviceOS: String,
        @Field("deviceCode") deviceCode: String
    ): Observable<JsonObject>
}
