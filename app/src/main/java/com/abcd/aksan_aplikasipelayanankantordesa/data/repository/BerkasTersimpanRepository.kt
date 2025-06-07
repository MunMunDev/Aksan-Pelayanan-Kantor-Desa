package com.abcd.aksan_aplikasipelayanankantordesa.data.repository

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BerkasTersimpanRepository @Inject constructor(
    private val api: ApiService
) {
    // Surat Pengantar
    // Ket 0 = proses, ket 1 = finish
    suspend fun getKeteranganNikah(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganNikah("", id_user, ket)
    }
    suspend fun getKeteranganLahir(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganLahir("", id_user, ket)
    }
    suspend fun getKeteranganUsaha(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganUsaha("", id_user, ket)
    }
    suspend fun getKeteranganTidakMampu(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganTidakMampu("", id_user, ket)
    }
    suspend fun getKeteranganAkteKematian(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganAkteKematian("", id_user, ket)
    }
    suspend fun getKeteranganPindah(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganPindah("", id_user, ket)
    }
    suspend fun getKeteranganIzinKeramaian(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganIzinKeramaian("", id_user, ket)
    }
    suspend fun getKeteranganDomisili(id_user: String, ket: Int): ArrayList<BerkasModel>{
        return api.getKeteranganDomisili("", id_user, ket)
    }
}