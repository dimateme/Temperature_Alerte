//package com.example.myapplication
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.reculer_rowliste.view.*
//
//class SeuilAdapteur:RecyclerView.Adapter<SeuilAdapteur.ViewHolderAdapteur>() {
//
//    var ListeSeuil = mutableListOf<Seuil>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeuilAdapteur.ViewHolderAdapteur {
//        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.reculer_rowliste, parent, false)
//        return ViewHolderAdapteur(inflater)
//    }
//    override fun getItemCount(): Int {
//        return ListeSeuil.size
//    }
//
//
//    override fun onBindViewHolder(holder: SeuilAdapteur.ViewHolderAdapteur, position: Int) {
//        holder.bind(ListeSeuil[position])
//    }
//
//
//
//
//    class ViewHolderAdapteur(view: View) : RecyclerView.ViewHolder(view) {
//        val textSeuiMax = view.textSeuilMax
//        val textSeuilMin = view.textSeuilMin
//        fun bind(data: Seuil) {
//            textSeuiMax.text = data.SeuilMax.toString()
//            textSeuilMin.text = data.SeuilMin.toString()
//        }
//    }
//
//}