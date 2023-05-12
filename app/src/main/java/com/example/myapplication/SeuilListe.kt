/**
 *  @Auteur : Jean Paul Bai
 *  @Email  :paul@gmail.com
 *
 *  @Fichier   : SeuilListe.kt
 *  @Type   : Class
 *  @Note   : Cette classe permet de définir les données des températures seuils
 *  @Date   : 18/03/2023
 *  @Update : 12/05/2023
 *  @Description : Cette classe permet de définir les données des températures seuils pour lesquelles un message d'alerte sera envoyé
 *  @Version : 1.0
 */

package com.example.myapplication

data class SeuilListe(
    val body: List<Temperature>
)

data class TemperatureSeuil (

    val SeuilMax: Float?,
    val SeuilMin: Float?

)
data class TemperatureSeuilResponse (
    val status : Float?,
    val message : String?,
    val body : TemperatureSeuil?
)