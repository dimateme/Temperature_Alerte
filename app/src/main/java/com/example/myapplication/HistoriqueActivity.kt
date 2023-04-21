package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Authentification.SessionManager
import kotlinx.android.synthetic.main.activity_historique.*

class HistoriqueActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: HistoriqueActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        val afficher = supportActionBar
        afficher?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
        initViewModel()

    }

    private fun initRecyclerView() {
       recyclerView.apply {
           layoutManager = LinearLayoutManager(this@HistoriqueActivity)
           val decoration = DividerItemDecoration( this@HistoriqueActivity, DividerItemDecoration.VERTICAL)
           addItemDecoration(decoration)
           recyclerViewAdapter = RecyclerViewAdapter()
           adapter =recyclerViewAdapter
       }
    }
    fun initViewModel(){
        viewModel = ViewModelProvider(this).get(HistoriqueActivityViewModel::class.java)
        viewModel.obtenirTemperatureObservable().observe(this, Observer {
            if(it == null){
                Toast.makeText(this@HistoriqueActivity, "Pas de resultat trouvé....", Toast.LENGTH_SHORT).show()
            }else{
                recyclerViewAdapter.listeTemperature = it.body.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.obtenirListeTemperature()

    }

}