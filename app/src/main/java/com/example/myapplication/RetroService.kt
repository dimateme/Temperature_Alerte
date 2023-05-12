/**
 * Nom du programme : Alerte Temperature
 * Version : 1.0
 * Nom du fichier : RetroService.kt
 * Auteur : Jean Paul Bai
 * Date création : 2023-03-25
 * Description : Interface qui permet de faire les requêtes vers le serveur
 * qui permettent d'obtenir la liste des températures, d'ajouter une température
 * et de modifier les seuils
 *
 */
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
    //la fonction permet d'obtenir la liste des températures
    @GET("historique")
    fun abtenirListeTemperature(): Call<ListeTemperature>

    @GET("historique{id_temperature}")
    fun obtenirTemperature(@Path("id_temperature") searchFloat: Float): Call<ListeTemperature>
    //la fonction permet d'ajouter une température envoyée par le senseur via le broker mqtt dans la base de données
    @POST("historique")
    @Headers("Content-Type: application/json")
    fun ajouterTemperature(@Body params: Temperature): Call<TemperatureResponse>

    //la fonction qui permet d'obtenir la liste des seuils
    @GET("seuil")
    fun abtenirSeuil(): Call<ListeTemperaturSeuil>
    @PUT("seuil/{id}")
    @Headers("Content-Type: application/json")
    fun modifierSeuil(@Path("id") id: String, @Body params: TemperatureSeuil): Call<TemperatureSeuilResponse>

}