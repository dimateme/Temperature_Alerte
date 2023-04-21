package com.example.myapplication


data class ListeTemperature (
    val body: List<Temperature>
)
data class Temperature (

    val temperature: Float?,
    val date: String?
//    val id: String?

)
/**/
data class TemperatureResponse (
    val status : Float?,
    val message : String?,
    val body : Temperature?
)