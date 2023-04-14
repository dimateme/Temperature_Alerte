package com.example.myapplication

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetroService {

    @POST("historique")
    @Headers("Content-Type: application/json")
    fun ajouterTemperature(@Body params: Temperature): Call<TemperatureResponse>

    @GET("seuil")
    fun abtenirSeuil(): Call<ListeTemperaturSeuil>
    @PUT("seuil/{id}")
    @Headers("Content-Type: application/json")
    fun modifierSeuil(@Path("id") id: String, @Body params: TemperatureSeuil): Call<TemperatureSeuilResponse>

//    @PUT("seuil/{id}")
//    @Headers("Content-Type: application/json")
//    fun modifierSeuil2(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
}