package com.abcd.aksan_aplikasipelayanankantordesa.data.repository.layanan

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LayananRepositoryImp @Inject constructor(
    private val api: ApiService
): LayananRepository {
    // Keterangan Nikah
    override suspend fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, aktaKelahiran: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganNikah(
            post, idUser, ktp, kk, suratPengantarRtRw, aktaKelahiran, pasFoto
        )
    }

    // Keterangan Lahir
    override suspend fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpOrangTua: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganLahirDariBidan: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganLahir(
            post, idUser, ktpOrangTua, kk, keteranganLahirDariBidan
        )
    }

    // Keterangan Usaha
    override suspend fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, pasFoto: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganUsaha(
            post, idUser, ktp, kk, suratPengantarRtRw, pasFoto, buktiKepemilikanUsaha
        )
    }

    // Keterangan TidakMampu
    override suspend fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, keteranganPenghasilan: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganTidakMampu(
            post, idUser, ktp, kk, suratPengantarRtRw, keteranganPenghasilan, pasFoto
        )
    }

    // Keterangan AkteKematian
    override suspend fun postKeteranganAkteKematian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganKematian: MultipartBody.Part, keteranganRtRw: MultipartBody.Part, fotoAlmarhum: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganAkteKematian(
            post, idUser, ktp, kk, keteranganKematian, keteranganRtRw, fotoAlmarhum
        )
    }

    // Keterangan Pindah
    override suspend fun postKeteranganPindah(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganPindah(
            post, idUser, ktp, kk, keteranganPindahDariTempatAsal, pasFoto
        )
    }

    // Keterangan IzinKeramaian
    override suspend fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,
        kk: MultipartBody.Part, suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ): ResponseModel {
        return api.postKeteranganIzinKeramaian(
            post, idUser, ktp, kk, suratPengantarRtRw, rencanaKegiatan
        )
    }

    // Keterangan Domisili
    override suspend fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ): ResponseModel {
        return api.postKeteranganDomisili(
            post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanTempatTinggal, pasFoto
        )
    }

}