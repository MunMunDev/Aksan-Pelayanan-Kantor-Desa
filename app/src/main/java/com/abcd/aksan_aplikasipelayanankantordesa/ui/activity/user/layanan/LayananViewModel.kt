package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.layanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.ResponseModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.layanan.LayananRepositoryValue
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class LayananViewModel @Inject constructor(
    private val repository: LayananRepositoryValue
): ViewModel() {
    private val _response = MutableLiveData<UIState<ResponseModel>>()
    val getResponse : LiveData<UIState<ResponseModel>> = _response

    fun postKeteranganNikah(
        post: RequestBody, idUser: RequestBody, ktpSuami: MultipartBody.Part, ktpIstri: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, aktaKelahiran: MultipartBody.Part, pasFoto: MultipartBody.Part,
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganNikah(
                    post, idUser, ktpSuami, ktpIstri, kk, suratPengantarRtRw, aktaKelahiran, pasFoto
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganLahir(
        post: RequestBody, idUser: RequestBody, ktpSuami: MultipartBody.Part, ktpIstri: MultipartBody.Part,
        kk: MultipartBody.Part, keteranganLahirDariBidan: MultipartBody.Part,
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganLahir(
                    post, idUser, ktpSuami, ktpIstri, kk, keteranganLahirDariBidan
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganUsaha(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanUsaha: MultipartBody.Part,
        pasFoto: MultipartBody.Part
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganUsaha(
                    post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanUsaha, pasFoto
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganTidakMampu(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, keteranganPenghasilan: MultipartBody.Part,
        pasFoto: MultipartBody.Part, rencanaKegiatan: RequestBody
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganTidakMampu(
                    post, idUser, ktp, kk, suratPengantarRtRw, keteranganPenghasilan, pasFoto, rencanaKegiatan
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganAkteKematian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        keteranganRtRw: MultipartBody.Part, keteranganKematian: MultipartBody.Part,
        fotoAlmarhum: MultipartBody.Part, tanggalKematian: RequestBody,
        sebabKematian: RequestBody, yangMenerankanKematian: RequestBody,
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganAkteKematian(
                    post, idUser, ktp, kk, keteranganRtRw, keteranganKematian, fotoAlmarhum,
                    tanggalKematian, sebabKematian, yangMenerankanKematian
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganPindah(
        post: RequestBody,
        idUser: RequestBody,
        ktp: MultipartBody.Part,
        kk: MultipartBody.Part,
        keteranganPindahDariTempatAsal: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
        pindahKe: RequestBody,
        alasanPindah: RequestBody
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganPindah(
                    post, idUser, ktp, kk, keteranganPindahDariTempatAsal, pasFoto,
                    pindahKe, alasanPindah
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganIzinKeramaian(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part,kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, rencanaKegiatan: RequestBody
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganIzinKeramaian(
                    post, idUser, ktp, kk, suratPengantarRtRw, rencanaKegiatan
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun postKeteranganDomisili(
        post: RequestBody, idUser: RequestBody, ktp: MultipartBody.Part, kk: MultipartBody.Part,
        suratPengantarRtRw: MultipartBody.Part, buktiKepemilikanTempatTinggal: MultipartBody.Part,
        pasFoto: MultipartBody.Part,
    ){
        viewModelScope.launch {
            _response.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = repository.postKeteranganDomisili(
                    post, idUser, ktp, kk, suratPengantarRtRw, buktiKepemilikanTempatTinggal, pasFoto
                )
                _response.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _response.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }


}