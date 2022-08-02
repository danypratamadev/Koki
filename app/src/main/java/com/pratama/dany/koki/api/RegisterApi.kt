package com.pratama.dany.koki.api

import com.pratama.dany.koki.api.model.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded

interface RegisterApi {

    @GET("login")
    fun loginApps(
        @Header("e") email: String,
        @Header("p") pass: String,
        @Header("r") role: Int,
        @Header("key") key: String
    ): Call<LoginResult>

    @GET("masterkategori")
    fun getKategori(
        @Header("key") key: String
    ): Call<KategoriResult>

    @GET("menucabang")
    fun getMenuCabang(
        @Header("key") key: String,
        @Header("c") cabang: Int,
        @Header("k") kategori: Int
    ): Call<MenuResult>

    @GET("member")
    fun getMember(
        @Header("key") key: String,
        @Header("m") member: String
    ): Call<MemberResult>

    @FormUrlEncoded
    @POST("transaksi")
    fun insertTransaksi(
        @Header("key") key: String,
        @Field("id") id: String,
        @Field("cabang") cabang: Int,
        @Field("user") user: String,
        @Field("member") member: String,
        @Field("meja") meja: Int,
        @Field("item") item: Int,
        @Field("dmember") dmember: Int,
        @Field("dpromosi") dpromosi: Int,
        @Field("total") total: Int,
        @Field("m[]") idMenu: ArrayList<Int>,
        @Field("j[]") jumlah: ArrayList<Int>,
        @Field("n[]") note: ArrayList<String>
    ): Call<TransaksiResult>

    @GET("transaksi")
    fun getTransaksi(
        @Header("key") key: String,
        @Header("c") cabang: Int
    ): Call<TransaksiResult>

    @GET("detailtransaksi")
    fun getDetailTransaksi(
        @Header("key") key: String,
        @Header("t") transaksi: String
    ): Call<DetailResult>

    @GET("dashtoday")
    fun getDashToday(
        @Header("key") key: String,
        @Header("c") cabang: Int
    ): Call<DashResult>

    @GET("order")
    fun getOrder(
        @Header("key") key: String,
        @Header("c") cabang: Int
    ): Call<OrderResult>

    @GET("detailorder")
    fun getDetailOrder(
        @Header("key") key: String,
        @Header("t") transaksi: String
    ): Call<DetailResult>

    @GET("orderpay")
    fun payOrder(
        @Header("key") key: String,
        @Header("t") order: String,
        @Header("u") user: String
    ): Call<TransaksiResult>

    @FormUrlEncoded
    @PUT("menucabang")
    fun sendUpdateMenu(
        @Header("key") key: String,
        @Field("menu") namaMenu: String,
        @Field("harga") hrgMenu: Int,
        @Field("status") status: Int,
        @Field("id") idMenu: Int
    ): Call<PublicResult>

    @GET("cook")
    fun getDataMasak(
        @Header("key") key: String,
        @Header("c") cabang: Int
    ): Call<MasakResult>

    @GET("koki")
    fun getKoki(
        @Header("key") key: String,
        @Header("c") cabang: Int
    ): Call<KokiResult>

    @FormUrlEncoded
    @PUT("cook")
    fun updateDataMasak(
        @Header("key") key: String,
        @Field("id") id: Int,
        @Field("s") status: Int,
        @Field("pemasak") pemasak: String
    ): Call<PublicResult>

    @FormUrlEncoded
    @PUT("cook")
    fun updateDataMasak(
        @Header("key") key: String,
        @Field("id") id: Int,
        @Field("s") status: Int
    ): Call<PublicResult>

}