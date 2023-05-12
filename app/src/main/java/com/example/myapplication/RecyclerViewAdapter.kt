/**
 * Nom du programme : Alerte Temperature
 * Version : 1.0
 * Nom du fichier : ReclerViewAdapter.kt
 * Auteur : Jean Paul Bai
 * Date création : 2023-02-25
 * Description : il permet de fournir l'accèsc aux données à afficher dans le RecyclerView
 * */

package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var listeTemperature = mutableListOf<Temperature>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.liste_temperature, parent, false)
        return MyViewHolder(inflater)
    }
    override fun getItemCount(): Int {
        return listeTemperature.size
    }
    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.bind(listeTemperature[position])
    }
    class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val temperature = item.findViewById<TextView>(R.id.txtTemperature)
        val date = item.findViewById<TextView>(R.id.txtDate)
        fun bind(data: Temperature) {
            temperature.text = data.temperature.toString()
            date.text = data.date
        }
    }
}