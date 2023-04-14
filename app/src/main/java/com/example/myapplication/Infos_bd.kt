package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_infos_bd.*


class Infos_bd : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infos_bd)

        var intent = intent
        val id_temperatureSeuil = intent.getStringExtra("id_temperature")
        val seuilMax = intent.getStringExtra("seuilMax")
        val seuilMin = intent.getStringExtra("seuilMin")

        val edtSeuilMax = findViewById<EditText>(R.id.edtSeuilMax)
        val edtSeuilMin = findViewById<EditText>(R.id.edtSeuilMin)
        edtSeuilMax.setText(seuilMax)
        edtSeuilMin.setText(seuilMin)

        val btnModifier = findViewById<TextView>(R.id.btnModifier)
        btnModifier.setOnClickListener {

            if(id_temperatureSeuil != null){
                modifierTemperatureSeuil(id_temperatureSeuil.toString())
                Toast.makeText(this, "Modification effectuée", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Modification non effectuée", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun modifierTemperatureSeuil(id:String){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val temperatureSeuil = TemperatureSeuil(edtSeuilMax.text.toString().toFloat(),edtSeuilMin.text.toString().toFloat())
        viewModel.updateTemperatureSeuil(id,temperatureSeuil)
        println( viewModel.updateTemperatureSeuil(id,temperatureSeuil))

    }



}