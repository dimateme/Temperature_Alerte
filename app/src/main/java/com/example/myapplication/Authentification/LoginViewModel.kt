package com.example.myapplication.Authentification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application) {

    val userRepo= UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()


    fun loginUser(courriel: String, motDePasse: String){
        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    Courriel = courriel,
                    MotDePasse = motDePasse
                )
                val response = userRepo.loginUser(loginRequest=loginRequest)
                if(response?.code() == 200){
                    loginResult.value = BaseResponse.Success(response.body())
                }else{
                    loginResult.value = BaseResponse.Error(response?.message())
                }
            } catch (e: Exception) {
                loginResult.value = BaseResponse.Error(e.message)
            }
        }
    }
}