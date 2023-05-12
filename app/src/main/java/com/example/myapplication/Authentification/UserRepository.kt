/**
 * Class UserRepository
 * Cette classe est utilisée pour établir la connexion entre la UserApi et la LoginRequest.
 * @param loginRequest
 * @return Response<LoginResponse>
 * Author: JEAN PAUL bai
 * Version: 1
 * Date: 2023-03-03
 */
package com.example.myapplication.Authentification

import retrofit2.Response

class UserRepository {
    suspend fun loginUser(loginRequest:LoginRequest): Response<LoginResponse>? {
        return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}