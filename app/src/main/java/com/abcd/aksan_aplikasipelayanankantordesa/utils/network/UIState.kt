package com.abcd.aksan_aplikasipelayanankantordesa.utils.network

sealed class UIState<out R> {
    object Loading: UIState<Nothing>()
    class Failure(val message: String): UIState<Nothing>()
    class Success<out T>(val data: T): UIState<T>()
}