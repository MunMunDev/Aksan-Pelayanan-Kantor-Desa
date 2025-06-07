package com.abcd.aksan_aplikasipelayanankantordesa.data.database.api

import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.UserModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("no_ktp") no_ktp: String,
        @Query("password") password: String
    ): UserModel

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getBerita(
        @Query("get_berita") get_berita: String,
    ): ArrayList<BeritaModel>

    // Surat Pengantar Keterangan
    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganNikah(
        @Query("getKeteranganNikah") getKeteranganNikah: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganLahir(
        @Query("getKeteranganLahir") getKeteranganLahir: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganUsaha(
        @Query("getKeteranganUsaha") getKeteranganUsaha: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganTidakMampu(
        @Query("getKeteranganTidakMampu") getKeteranganTidakMampu: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganAkteKematian(
        @Query("getKeteranganAkteKematian") getKeteranganAkteKematian: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganPindah(
        @Query("getKeteranganPindah") getKeteranganPindah: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganIzinKeramaian(
        @Query("getKeteranganIzinKeramaian") getKeteranganIzinKeramaian: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-kelurahan/api/get.php")
    suspend fun getKeteranganDomisili(
        @Query("getKeteranganDomisili") getKeteranganDomisili: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

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