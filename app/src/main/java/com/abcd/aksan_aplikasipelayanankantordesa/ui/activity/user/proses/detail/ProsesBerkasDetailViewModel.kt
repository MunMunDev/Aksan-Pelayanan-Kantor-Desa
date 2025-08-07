package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.proses.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.ProsesBerkasDetailRepository
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProsesBerkasDetailViewModel @Inject constructor(
    private val repository: ProsesBerkasDetailRepository
): ViewModel(){
    private val _prosesBerkasDetail = MutableLiveData<UIState<ArrayList<DokumenModel>>>()
    val getProsesBerkasDetail : LiveData<UIState<ArrayList<DokumenModel>>> = _prosesBerkasDetail

    private val _uploadDokumen = MutableLiveData<UIState<ResponseModel>>()
    val getUploadDokumen : LiveData<UIState<ResponseModel>> = _uploadDokumen

    fun fetchProsesBerkasDetail(idBerkas: Int){
        viewModelScope.launch {
            _prosesBerkasDetail.postValue(UIState.Loading)
            delay(1_000)
            try {
                _prosesBerkasDetail.postValue(UIState.Success(repository.getAllProsesBerkasDetail(idBerkas)))
            } catch (ex: Exception){
                _prosesBerkasDetail.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postUploadDokumen(
        post: RequestBody,
        idDokumen: RequestBody,
        idBerkas: RequestBody,
        noKtp: RequestBody,
        dokumen: RequestBody,
        file: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _uploadDokumen.postValue(UIState.Loading)
            delay(1_000)
            try {
                _uploadDokumen.postValue(
                    UIState.Success(
                        repository.postUploadBerkas(post, idDokumen, idBerkas, noKtp, dokumen, file)
                    )
                )
            } catch (ex: Exception){
                _uploadDokumen.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }
}