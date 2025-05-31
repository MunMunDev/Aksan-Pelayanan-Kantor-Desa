package com.abcd.aksan_aplikasipelayanankantordesa.ui.fragment.user.home

import androidx.lifecycle.ViewModel
import com.abcd.aksan_aplikasipelayanankantordesa.data.database.api.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ApiService
): ViewModel() {

}