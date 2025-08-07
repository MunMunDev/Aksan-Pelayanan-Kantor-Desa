package com.abcd.aksan_aplikasipelayanankantordesa.data.database.api

import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DesaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
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
    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getUser(
        @Query("get_user") getUser: String,
        @Query("no_ktp") no_ktp: String,
        @Query("password") password: String
    ): UserModel

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getDesa(
        @Query("get_desa") get_desa: String,
    ): ArrayList<DesaModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getBerita(
        @Query("get_berita") get_berita: String,
    ): ArrayList<BeritaModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getAllProsesBerkas(
        @Query("get_all_proses_berkas") get_all_proses_berkas: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getAllProsesBerkasDetail(
        @Query("get_proses_berkas_detail") get_proses_berkas_detail: String,
        @Query("id_berkas") id_berkas: Int,
    ): ArrayList<DokumenModel>

    // Surat Pengantar Keterangan
    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganNikah(
        @Query("getKeteranganNikah") getKeteranganNikah: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganLahir(
        @Query("getKeteranganLahir") getKeteranganLahir: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganUsaha(
        @Query("getKeteranganUsaha") getKeteranganUsaha: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganTidakMampu(
        @Query("getKeteranganTidakMampu") getKeteranganTidakMampu: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganAkteKematian(
        @Query("getKeteranganAkteKematian") getKeteranganAkteKematian: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganPindah(
        @Query("getKeteranganPindah") getKeteranganPindah: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganIzinKeramaian(
        @Query("getKeteranganIzinKeramaian") getKeteranganIzinKeramaian: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    @GET("pelayanan-kantor-desa/api/get.php")
    suspend fun getKeteranganDomisili(
        @Query("getKeteranganDomisili") getKeteranganDomisili: String,
        @Query("id_user") id_user: String,
        @Query("ket") ket: Int,
    ): ArrayList<BerkasModel>

    //Register
    @FormUrlEncoded
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postRegister(
        @Field("register_user") register_user: String,
        @Field("id_desa") id_desa: Int,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("no_ktp") no_ktp: String,
        @Field("no_kk") no_kk: String,
        @Field("tempat_lahir") tempat_lahir: String,
        @Field("tanggal_lahir") tanggal_lahir: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("password") password: String,
    ): ResponseModel

    @FormUrlEncoded
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postUpdateDataDiri(
        @Field("post_update_data_diri") post_update_data_diri:String,
        @Field("id_user") id_user: Int,
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("nomor_hp") nomor_hp: String,
        @Field("no_ktp") no_ktp: String,
        @Field("no_kk") no_kk: String,
        @Field("tempat_lahir") tempat_lahir: String,
        @Field("tanggal_lahir") tanggal_lahir: String,
        @Field("jenis_kelamin") jenis_kelamin: String,
        @Field("password") password: String,
        @Field("no_ktp_lama") no_ktp_lama: String,
    ): ResponseModel


    // Layanan
    // Keterangan Nikah
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganNikah(
        @Part("post_keterangan_nikah") post_keterangan_nikah: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part akta_kelahiran: MultipartBody.Part,
        @Part pas_foto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Lahir
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganLahir(
        @Part("post_keterangan_lahir") post_keterangan_lahir: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp_orang_tua: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part keterangan_lahir_dari_bidan: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Usaha
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganUsaha(
        @Part("post_keterangan_usaha") post_keterangan_usaha: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part bukti_kepemelikan_usaha: MultipartBody.Part,
        @Part pas_foto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan TidakMampu
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganTidakMampu(
        @Part("post_keterangan_tidak_mampu") post_keterangan_tidak_mampu: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part keterangan_penghasilan: MultipartBody.Part,
        @Part pas_foto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Nikah
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganAkteKematian(
        @Part("post_keterangan_akte_kematian") post_keterangan_akte_kematian: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part("keterangan_kematian") keterangan_kematian: MultipartBody.Part,
        @Part("foto_almarhum") foto_almarhum: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Pindah
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganPindah(
        @Part("post_keterangan_pindah") post_keterangan_pindah: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part keterangan_pindah_dari_tempat_asal: MultipartBody.Part,
        @Part pas_foto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan IzinKeramaian
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganIzinKeramaian(
        @Part("post_keterangan_izin_keramaian") post_keterangan_izin_keramaian: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part("rencana_kegiatan") rencana_kegiatan: RequestBody, //Text
    ): ResponseModel

    // Keterangan Domisili
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postKeteranganDomisili(
        @Part("post_keterangan_domisili") post_keterangan_domisili: RequestBody,
        @Part("id_user") id_user: RequestBody,
        @Part ktp: MultipartBody.Part,
        @Part kk: MultipartBody.Part,
        @Part surat_pengantar_rt_rw: MultipartBody.Part,
        @Part bukti_kepemilikan_tempat_tinggal: MultipartBody.Part,
        @Part pas_foto: MultipartBody.Part,
    ): ResponseModel

    // UploadDokumen
    @Multipart
    @POST("pelayanan-kantor-desa/api/post.php")
    suspend fun postUploadDokumen(
        @Part("post_upload_dokumen") post_upload_dokumen: RequestBody,
        @Part("id_dokumen") id_dokumen: RequestBody,
        @Part("id_berkas") id_berkas: RequestBody,
        @Part("no_ktp") no_ktp: RequestBody,
        @Part("dokumen") dokumen: RequestBody,
        @Part file: MultipartBody.Part,
    ): ResponseModel

}