package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
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

    fun postRegister(
        nama: String, alamat: String, nomorHp: String,
        noKtp: String, noKK: String, tempatLahir: String,
        tanggalLahir: String, jenisKelamin: String, password: String,
    ) {
        viewModelScope.launch {
            _postRegister.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postRegister = api.postRegister(
                    "", nama, alamat, nomorHp, noKtp, noKK, tempatLahir,
                    tanggalLahir, jenisKelamin, password
                )
                _postRegister.postValue(UIState.Success(postRegister))
            } catch (ex: Exception) {
                _postRegister.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun getRegister(): LiveData<UIState<ResponseModel>> = _postRegister
}