/**
 * Classe de données qui capture les informations sur les utilisateurs connectés, récupérées à partir du LoginRepository.
 * Auteur: JEAN PAUL bai
 * Version: 1
 * Date: 2023-03-03
 */
package com.example.myapplication.Authentification
import com.google.gson.annotations.SerializedName
data class LoginResponse(
    @SerializedName("status")
    var status: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("body")
    var `body`: Body

){
    data class Body(
        @SerializedName("token")
        var token: String

    )
}
