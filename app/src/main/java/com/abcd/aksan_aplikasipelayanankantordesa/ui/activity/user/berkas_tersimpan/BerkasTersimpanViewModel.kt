package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.berkas_tersimpan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.BerkasTersimpanRepository
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BerkasTersimpanViewModel @Inject constructor(
    private val repository: BerkasTersimpanRepository
) :ViewModel() {
    private val _keteranganNikah = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganNikah : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganNikah

    private val _keteranganLahir = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganLahir : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganLahir

    private val _keteranganUsaha = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganUsaha : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganUsaha

    private val _keteranganTidakMampu = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganTidakMampu : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganTidakMampu

    private val _keteranganAkteKematian = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganAkteKematian : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganAkteKematian

    private val _keteranganPindah = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganPindah : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganPindah

    private val _keteranganIzinKeramaian = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganIzinKeramaian : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganIzinKeramaian

    private val _keteranganDomisili = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getKeteranganDomisili : LiveData<UIState<ArrayList<BerkasModel>>> = _keteranganDomisili

    fun fetchKeteranganNikah(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganNikah.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganNikah.postValue(UIState.Success(repository.getKeteranganNikah(idUser, ket)))
            } catch (ex: Exception){
                _keteranganNikah.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganLahir(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganLahir.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganLahir.postValue(UIState.Success(repository.getKeteranganLahir(idUser, ket)))
            } catch (ex: Exception){
                _keteranganLahir.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganUsaha(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganUsaha.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganUsaha.postValue(UIState.Success(repository.getKeteranganUsaha(idUser, ket)))
            } catch (ex: Exception){
                _keteranganUsaha.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganTidakMampu(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganTidakMampu.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganTidakMampu.postValue(UIState.Success(repository.getKeteranganTidakMampu(idUser, ket)))
            } catch (ex: Exception){
                _keteranganTidakMampu.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganAkteKematian(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganAkteKematian.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganAkteKematian.postValue(UIState.Success(repository.getKeteranganAkteKematian(idUser, ket)))
            } catch (ex: Exception){
                _keteranganAkteKematian.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganPindah(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganPindah.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganPindah.postValue(UIState.Success(repository.getKeteranganPindah(idUser, ket)))
            } catch (ex: Exception){
                _keteranganPindah.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganIzinKeramaian(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganIzinKeramaian.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganIzinKeramaian.postValue(UIState.Success(repository.getKeteranganIzinKeramaian(idUser, ket)))
            } catch (ex: Exception){
                _keteranganIzinKeramaian.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

    fun fetchKeteranganDomisili(idUser: String, ket: Int){
        viewModelScope.launch {
            _keteranganDomisili.postValue(UIState.Loading)
            delay(1_000)
            try {
                _keteranganDomisili.postValue(UIState.Success(repository.getKeteranganDomisili(idUser, ket)))
            } catch (ex: Exception){
                _keteranganDomisili.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }


}