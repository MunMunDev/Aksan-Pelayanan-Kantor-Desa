package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BeritaModel
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _keranjangBelanja = MutableLiveData<UIState<List<BeritaModel>>>()

    fun fetchBerita(){
        viewModelScope.launch(Dispatchers.IO) {
            _keranjangBelanja.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getBerita("")
                _keranjangBelanja.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _keranjangBelanja.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun getBerita(): LiveData<UIState<List<BeritaModel>>> = _keranjangBelanja
}