package com.abcd.aksan_aplikasipelayanankantordesa.data.repository

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProsesBerkasRepository @Inject constructor(
    private val api: ApiService
){
    suspend fun getAllProsesBerkas(
        idUser:Int
    ): ArrayList<BerkasModel>{
        val data = api.getAllProsesBerkas("", idUser)
        return data
    }
}