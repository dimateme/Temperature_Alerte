package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_infos_bd.*
import java.lang.Float.parseFloat


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

            }else{
                Toast.makeText(this, "La valeur seuil n'exite pas", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*Cette fonction permet de modifier les températures seuils*/
    fun modifierTemperatureSeuil(id:String){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        var estFloat: Boolean=true
        var valeurMax:Float = 0.0f
        var valeurMin:Float = 0.0f
        try {
             valeurMax= parseFloat(edtSeuilMax.text.toString())
             valeurMin= parseFloat(edtSeuilMin.text.toString())
            estFloat = true
        }catch (e: NumberFormatException){
            estFloat = false
        }
        if(estFloat){
            val temperatureSeuil = TemperatureSeuil(valeurMax,valeurMin)
            viewModel.updateTemperatureSeuil(id,temperatureSeuil)
            Toast.makeText(this, "La modification a été avec succès", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Veuillez entrer des valeurs numériques", Toast.LENGTH_SHORT).show()
        }


    }



}