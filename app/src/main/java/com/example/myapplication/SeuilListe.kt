package com.example.myapplication

data class SeuilListe(
    val body: List<Temperature>
)

data class TemperatureSeuil (

    val SeuilMax: Float?,
    val SeuilMin: Float?
//    val id: String?

)
/**/
data class TemperatureSeuilResponse (
    val status : Float?,
    val message : String?,
    val body : TemperatureSeuil?
)