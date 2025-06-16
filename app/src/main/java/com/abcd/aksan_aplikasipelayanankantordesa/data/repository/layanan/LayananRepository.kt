package com.abcd.aksan_aplikasipelayanankantordesa.data.repository.layanan

import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface LayananRepository{

    // Keterangan Nikah
    suspend fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, aktaKelahiran: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Lahir
    suspend fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpOrangTua: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganLahirDariBidan: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Usaha
    suspend fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
        pasFoto: MultipartBody.Part
    ): ResponseModel

    // Keterangan TidakMampu
    suspend fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, keteranganPenghasilan: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan AkteKematian
    suspend fun postKeteranganAkteKematian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganRtRw: MultipartBody.Part, keteranganKematian: MultipartBody.Part,
        fotoAlmarhum: MultipartBody.Part,
    ): ResponseModel

    // Keterangan Pindah
    suspend fun postKeteranganPindah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel

    // Keterangan IzinKeramaian
    suspend fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ): ResponseModel

    // Keterangan Domisili
    suspend fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
    ): ResponseModel

}