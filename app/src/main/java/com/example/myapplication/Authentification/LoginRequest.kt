/**
 * BaseResponse.kt
 * Author: JEAN PAUL bai
 * Version: 1
 * Date: 2023-03-03
 */
package com.example.myapplication.Authentification

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("Courriel")
    var Courriel: String,
    @SerializedName("MotDePasse")
    var MotDePasse: String
)
