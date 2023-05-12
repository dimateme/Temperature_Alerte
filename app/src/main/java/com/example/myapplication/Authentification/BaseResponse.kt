/**
 * BaseResponse.kt
 * Contient la classe scellée BaseResponse
 * Cette classe est utilisée pour gérer la réponse de l'API
 * Il peut s'agir d'un succès, d'un chargement ou d'une erreur.
 * @param T le type de réponse
 * Author: JEAN PAUL bai
 * Version: 1
 * Date: 2023-03-03
 */
package com.example.myapplication.Authentification

sealed class BaseResponse<out T> {
    data class Success<out T>(val body: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing? = null) : BaseResponse<Nothing>()
    data class Error(val message: String?) : BaseResponse<Nothing>()
}

