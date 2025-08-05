package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DesaModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private var api: ApiService
): ViewModel(){
    private var _postRegister = MutableLiveData<UIState<ResponseModel>>()
    fun getRegister(): LiveData<UIState<ResponseModel>> = _postRegister

    private var _desa = MutableLiveData<UIState<ArrayList<DesaModel>>>()
    fun getDesa(): LiveData<UIState<ArrayList<DesaModel>>> = _desa

    fun postRegister(
        idDesa:Int, nama: String, alamat: String, nomorHp: String,
        noKtp: String, noKK: String, tempatLahir: String,
        tanggalLahir: String, jenisKelamin: String, password: String,
    ) {
        viewModelScope.launch {
            _postRegister.postValue(UIState.Loading)
            delay(300)
            try {
                val postRegister = api.postRegister(
                    "", idDesa, nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
                    tanggalLahir, jenisKelamin, password
                )
                _postRegister.postValue(UIState.Success(postRegister))
            } catch (ex: Exception) {
                _postRegister.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun fetchDesa() {
        viewModelScope.launch {
            _desa.postValue(UIState.Loading)
            delay(300)
            try {
                val data = api.getDesa("")
                _desa.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _desa.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

}