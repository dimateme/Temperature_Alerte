/**
 * ServiceSeuilGenerateur.kt
 * @Auteur : Paul Bai
 * @Date : 18/03/2023
 * @Update : 12/05/2023
 * @Description : Permet de se connecter au serveur pour récupérer les données des températures seuils
 * @Version : 1.0
 */
package com.example.myapplication

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceSeuilGenerateur {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://51.79.50.149:3002/api/temperature/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> creerService(service: Class<T>): T {
        return retrofit.create(service)
    }

}