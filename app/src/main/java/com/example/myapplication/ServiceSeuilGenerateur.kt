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