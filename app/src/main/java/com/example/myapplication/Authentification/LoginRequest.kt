package com.example.myapplication.Authentification

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("Courriel")
    var Courriel: String,
    @SerializedName("MotDePasse")
    var MotDePasse: String
)
