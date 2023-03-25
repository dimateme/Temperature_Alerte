package com.example.myapplication.Authentification
import com.google.gson.annotations.SerializedName
data class LoginResponse(
    @SerializedName("status")
    var status: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("body")
    var body: Body
){
    data class Body(
        @SerializedName("Courriel")
        var Courriel: String,
        @SerializedName("MotDePasse")
        var MotDePasse: String,
        @SerializedName("Token")
        var token: String

    )
}
