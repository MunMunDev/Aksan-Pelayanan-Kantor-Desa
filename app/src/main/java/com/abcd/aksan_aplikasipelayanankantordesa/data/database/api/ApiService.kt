package com.abcd.aksan_aplikasipelayanankantordesa.data.database.api

import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getAllUser(
        @Query("all_user") allUser: String
    ): List<UserModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("no_ktp") no_ktp: String,
        @Query("password") password: String
    ): UserModel

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getBerita(
        @Query("get_berita") get_berita: String,
    ): List<BeritaModel>


    // POST
    @FormUrlEncoded
    @POST("pelayanan-kantor-kelurahan/api/post.php")
    suspend fun addUser(
        @Field("add_user") addUser:String,
        @Field("nama") nama:String,
        @Field("alamat") alamat:String,
        @Field("nomor_hp") nomorHp:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("sebagai") sebagai:String
    ): ResponseModel

    //Register
    @Multipart
    @POST("pelayanan-kantor-kelurahan/api/post.php")
    suspend fun postRegister(
        @Part("register_user") register_user: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("nomor_hp") nomor_hp: RequestBody,
        @Part("no_ktp") no_ktp: RequestBody,
        @Part("no_kk") no_kk: RequestBody,
        @Part("tempat_lahir") tempat_lahir: RequestBody,
        @Part("tanggal_lahir") tanggal_lahir: RequestBody,
        @Part("jenis_kelamin") jenis_kelamin: RequestBody,
        @Part("password") password: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part fotoDiri: MultipartBody.Part,
    ): ResponseModel

}