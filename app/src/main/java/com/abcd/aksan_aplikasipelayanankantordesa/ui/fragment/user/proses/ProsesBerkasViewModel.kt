package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.proses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.BerkasModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.BerkasTersimpanRepository
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.ProsesBerkasRepository
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProsesBerkasViewModel @Inject constructor(
    private val repository: ProsesBerkasRepository
): ViewModel() {

    private val _prosesBerkas = MutableLiveData<UIState<ArrayList<BerkasModel>>>()
    val getProsesBerkas : LiveData<UIState<ArrayList<BerkasModel>>> = _prosesBerkas

    fun fetchProsesBerkas(idUser: Int){
        viewModelScope.launch {
            _prosesBerkas.postValue(UIState.Loading)
            delay(1_000)
            try {
                _prosesBerkas.postValue(UIState.Success(repository.getAllProsesBerkas(idUser)))
            } catch (ex: Exception){
                _prosesBerkas.postValue(UIState.Failure(ex.message.toString()))
            }
        }
    }

}