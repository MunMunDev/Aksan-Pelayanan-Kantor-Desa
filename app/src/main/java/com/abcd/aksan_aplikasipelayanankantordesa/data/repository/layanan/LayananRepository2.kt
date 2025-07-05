package com.abcd.aksan_aplikasipelayanankantordesa.data.repository.layanan

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LayananRepository2 @Inject constructor(
    private val api: ApiService
) {

    // Keterangan Nikah
    suspend fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, akteKelahiran: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganNikah(
            post, idUser, ktp, kk, suratPengantarRtRw, akteKelahiran, pasFoto
        )
        return data
    }

    // Keterangan Lahir
    suspend fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpOrangTua: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganLahirDariBidan: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganLahir(
            post, idUser, ktpOrangTua, kk, keteranganLahirDariBidan
        )
        return data
    }

    // Keterangan Usaha
    suspend fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganUsaha(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanUsaha, pasFoto
        )
        return data
    }

    // Keterangan TidakMampu
    suspend fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, keteranganPenghasilan: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganTidakMampu(
            post, idUser, ktp, kk, suratPengantarRtRw, keteranganPenghasilan, pasFoto
        )
        return data
    }

    // Keterangan AkteKematian
    suspend fun postKeteranganAkteKematian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganRtRw: MultipartBody.Part, keteranganKematian: MultipartBody.Part, fotoAlmarhum: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganAkteKematian(
            post, idUser, ktp, kk, keteranganRtRw, keteranganKematian, fotoAlmarhum
        )
        return data
    }

    // Keterangan Pindah
    suspend fun postKeteranganPindah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganPindah(
            post, idUser, ktp, kk, keteranganPindahDariTempatAsal, pasFoto
        )
        return data
    }

    // Keterangan IzinKeramaian
    suspend fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,
        kk: MultipartBody.Part, suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ): ResponseModel {
        val data = api.postKeteranganIzinKeramaian(
            post, idUser, ktp, kk, suratPengantarRtRw, rencanaKegiatan
        )
        return data
    }

    // Keterangan Domisili
    suspend fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        val data = api.postKeteranganDomisili(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanTempatTinggal, pasFoto
        )
        return data
    }

}