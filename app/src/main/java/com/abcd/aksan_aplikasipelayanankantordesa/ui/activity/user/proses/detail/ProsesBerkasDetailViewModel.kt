package com.abcd.aksan_aplikasipelayanankantordesa.ui.activity.user.proses.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.aksan_aplikasipelayanankantordesa.data.model.DokumenModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.repository.ProsesBerkasDetailRepository
import com.abcd.aksan_aplikasipelayanankantordesa.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProsesBerkasDetailViewModel @Inject constructor(
    private val repository: ProsesBerkasDetailRepository
): ViewModel(){
    private val _prosesBerkasDetail = MutableLiveData<UIState<ArrayList<DokumenModel>>>()
    val getProsesBerkasDetail : LiveData<UIState<ArrayList<DokumenModel>>> = _prosesBerkasDetail

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
}