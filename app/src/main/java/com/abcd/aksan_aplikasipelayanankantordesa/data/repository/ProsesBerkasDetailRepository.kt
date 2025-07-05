package com.abcd.aksan_aplikasipelayanankantordesa.data.repository

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProsesBerkasDetailRepository @Inject constructor(
    private val api: ApiService
){
    suspend fun getAllProsesBerkasDetail(
        idBerkas:Int
    ): ArrayList<DokumenModel>{
        val data = api.getAllProsesBerkasDetail("", idBerkas)
        return data
    }
}