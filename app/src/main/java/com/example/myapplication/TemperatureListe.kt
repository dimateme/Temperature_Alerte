/**
 *  @Auteur : Jean Paul Bai
 *  @Email  :paul@gmail.com
 *
 *  @Fichier   : TemperatureListe.kt
 *  @Type   : Class
 *  @Note   : Cette classe permet de définir les données de la liste des températures
 *  @Date   : 18/03/2023
 *  @Update : 12/05/2023
 *  @Description : Cette classe permet de définir les données de la liste des températures, la température et la date qui sont ajoutées dans la base de données
 *  @See    : ListeTemperature.kt
 *  @Version : 1.0
 */

package com.example.myapplication


data class ListeTemperature (
    val body: List<Temperature>
)
data class Temperature (

    val temperature: Float?,
    val date: String?

)
/**/
data class TemperatureResponse (
    val status : Float?,
    val message : String?,
    val body : Temperature?
)