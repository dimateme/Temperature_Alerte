package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    @GET("historique")
    fun abtenirListeTemperature(): Call<ListeTemperature>
    @GET("historique")
    fun rechercherTemperature(@Query("temperature") searchFloat: Float): Call<ListeTemperature>

    @GET("historique{id_temperature}")
    fun obtenirTemperature(@Path("id_temperature") searchFloat: Float): Call<ListeTemperature>

    @POST("historique")
    @Headers("Content-Type: application/json")
    fun ajouterTemperature(@Body params: Temperature): Call<TemperatureResponse>

    @GET("seuil")
    fun abtenirSeuil(): Call<ListeTemperaturSeuil>
    @PUT("seuil/{id}")
    @Headers("Content-Type: application/json")
    fun modifierSeuil(@Path("id") id: String, @Body params: TemperatureSeuil): Call<TemperatureSeuilResponse>

}