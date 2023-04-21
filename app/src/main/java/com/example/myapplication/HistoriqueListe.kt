package com.example.myapplication

data class HistoriqueListe(val body: List<Historique>)
data class Historique(val id:String, val temperature: Float?, val date: String?, val seuil: String?)
