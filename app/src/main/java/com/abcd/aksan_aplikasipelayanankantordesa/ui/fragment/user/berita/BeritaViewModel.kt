package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.berita

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
class BeritaViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {
    private var _berita = MutableLiveData<UIState<ArrayList<BeritaModel>>>()

    fun fetchBerita(){
        viewModelScope.launch(Dispatchers.IO) {
            _berita.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getBerita("")
                _berita.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _berita.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun getBerita(): LiveData<UIState<ArrayList<BeritaModel>>> = _berita
}