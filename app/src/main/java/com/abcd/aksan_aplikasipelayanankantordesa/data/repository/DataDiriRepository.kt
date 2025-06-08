package com.abcd.aksan_aplikasipelayanankantordesa.data.repository

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataDiriRepository @Inject constructor(
    private val api: ApiService
){
    suspend fun postDataDiri(
        idUser:Int, namaLengkap: String, alamat: String, nomorHp: String,
        noKtp: String, noKK: String, tempatLahir: String, tanggalLahir: String,
        jenisKelamin: String, password: String, noKtpLama: String
    ): ResponseModel{
        val data = api.postUpdateDataDiri(
            "", idUser, namaLengkap, alamat, nomorHp, noKtp, noKK,
            tempatLahir, tanggalLahir, jenisKelamin, password, noKtpLama
        )
        return data
    }
}