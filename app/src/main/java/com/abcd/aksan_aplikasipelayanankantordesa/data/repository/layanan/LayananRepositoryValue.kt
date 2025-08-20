package com.abcd.aksan_aplikasipelayanankantordesa.data.repository.layanan

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LayananRepositoryValue @Inject constructor(
    private val api: ApiService
) {
    // Keterangan Nikah
    suspend fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktpSuami: MultipartBody.Part, ktpIstri: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, aktaKelahiran: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganNikah(
            post, idUser, ktpSuami, ktpIstri, kk, suratPengantarRtRw, aktaKelahiran, pasFoto
        )
    }

    // Keterangan Lahir
    suspend fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpSuami: MultipartBody.Part, ktpIstri: MultipartBody.Part,
        kk: MultipartBody.Part, keteranganLahirDariBidan: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganLahir(
            post, idUser, ktpSuami, ktpIstri, kk, keteranganLahirDariBidan
        )
    }

    // Keterangan Usaha
    suspend fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, pasFoto: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganUsaha(
            post, idUser, ktp, kk, suratPengantarRtRw, pasFoto, buktiKepemilikanUsaha
        )
    }

    // Keterangan TidakMampu
    suspend fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, keteranganPenghasilan: MultipartBody.Part,
        pasFoto: MultipartBody.Part, rencanaKegiatan: RequestBody

    ): ResponseModel {
        return api.postKeteranganTidakMampu(
            post, idUser, ktp, kk, suratPengantarRtRw, keteranganPenghasilan, pasFoto, rencanaKegiatan
        )
    }

    // Keterangan AkteKematian
    suspend fun postKeteranganAkteKematian(
        post: RequestBody,
        idUser: RequestBody,
        ktp: MultipartBody.Part,
        kk: MultipartBody.Part,
        keteranganKematian: MultipartBody.Part,
        keteranganRtRw: MultipartBody.Part,
        fotoAlmarhum: MultipartBody.Part,
        tanggalKematian: RequestBody,
        sebabKematian: RequestBody,
        yangMenerankanKematian: RequestBody,
    ): ResponseModel {
        return api.postKeteranganAkteKematian(
            post, idUser, ktp, kk, keteranganKematian, keteranganRtRw, fotoAlmarhum,
            tanggalKematian, sebabKematian, yangMenerankanKematian
        )
    }

    // Keterangan Pindah
    suspend fun postKeteranganPindah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganPindah(
            post, idUser, ktp, kk, keteranganPindahDariTempatAsal, pasFoto
        )
    }

    // Keterangan IzinKeramaian
    suspend fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,
        kk: MultipartBody.Part, suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ): ResponseModel {
        return api.postKeteranganIzinKeramaian(
            post, idUser, ktp, kk, suratPengantarRtRw, rencanaKegiatan
        )
    }

    // Keterangan Domisili
    suspend fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganDomisili(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanTempatTinggal, pasFoto
        )
    }

}