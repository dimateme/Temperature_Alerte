package com.example.myapplication.Authentification

sealed class BaseResponse<out T> {
    data class Success<out T>(val body: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing? = null) : BaseResponse<Nothing>()
    data class Error(val message: String?) : BaseResponse<Nothing>()
}

