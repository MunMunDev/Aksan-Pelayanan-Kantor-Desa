package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.akun.data_diri

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.DataDiriRepository
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataDiriViewModel @Inject constructor(
    private val repository : DataDiriRepository
): ViewModel(){
    private val _updateDataDiri = MutableLiveData<UIState<ResponseModel>>()
    val getResponseUpdateDataDiri : LiveData<UIState<ResponseModel>> = _updateDataDiri

    fun postUpdateDataDiri(
        idUser:Int, namaLengkap: String, alamat: String, nomorHp: String,
        noKtp: String, noKK: String, tempatLahir: String, tanggalLahir: String,
        jenisKelamin: String, password: String, noKtpLama: String
    ){
        viewModelScope.launch {
            _updateDataDiri.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postDataDiri(
                    idUser, namaLengkap, alamat, nomorHp, noKtp, noKK,
                    tempatLahir, tanggalLahir, jenisKelamin, password, noKtpLama
                )
                _updateDataDiri.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateDataDiri.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }
}