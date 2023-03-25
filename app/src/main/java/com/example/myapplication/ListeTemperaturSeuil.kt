package com.example.myapplication

data class ListeTemperaturSeuil(val body: List<Seuil>)
data class Seuil(val SeuilMax: Float?, val SeuilMin: Float?)
data class SeuilResponse (
    val status : Float?,
    val message : String?,
    val body : Seuil?
)