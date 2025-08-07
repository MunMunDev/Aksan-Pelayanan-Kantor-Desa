package com.abcd.aksan_aplikasipelayanankantordesa.data.repository

import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun postUploadBerkas(
        post: RequestBody,
        idDokumen: RequestBody,
        idBerkas: RequestBody,
        noKtp: RequestBody,
        dokumen: RequestBody,
        file: MultipartBody.Part
    ): ResponseModel{
        val data = api.postUploadDokumen(post, idDokumen, idBerkas, noKtp, dokumen, file)
        return data
    }
}