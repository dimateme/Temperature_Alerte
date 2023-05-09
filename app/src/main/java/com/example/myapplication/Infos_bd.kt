package com.example.myapplication


import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_infos_bd.*
import java.lang.Float.parseFloat
import java.util.*


class Infos_bd : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    lateinit var btnTraduction: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale() //Charger la langue enregistrée dans les préférences partagées
        setContentView(R.layout.activity_infos_bd)
        val retourPagePrincipale = supportActionBar
        retourPagePrincipale?.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        val id_temperatureSeuil = intent.getStringExtra("id_temperature")
        val seuilMax = intent.getStringExtra("seuilMax")
        val seuilMin = intent.getStringExtra("seuilMin")

        val edtSeuilMax = findViewById<EditText>(R.id.edtSeuilMax)
        val edtSeuilMin = findViewById<EditText>(R.id.edtSeuilMin)
        edtSeuilMax.setText(seuilMax)
        edtSeuilMin.setText(seuilMin)
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)
        val btnModifier = findViewById<TextView>(R.id.btnModifier)
        btnModifier.setOnClickListener {

            if(id_temperatureSeuil != null){
                modifierTemperatureSeuil(id_temperatureSeuil.toString())

            }else{
                Toast.makeText(this, "La valeur seuil n'exite pas", Toast.LENGTH_SHORT).show()
            }
        }

        btnTraduction = findViewById(R.id.btnChangerLangue)
        btnTraduction.setOnClickListener{
            changerDeLangue()
        }
    }
    /*fonction qui permet de changer de langue*/
    private fun changerDeLangue(){
        val listeLangue = arrayOf("Français","Anglais","Espagnol","Allemand")
        val mBuilder =AlertDialog.Builder(this@Infos_bd)
        mBuilder.setTitle("Choisissez une langue")
        mBuilder.setSingleChoiceItems(listeLangue,-1){dialog, which ->
            if(which == 0){
                setLocale("fr")
                recreate()
            }else if(which == 1){
                setLocale("en")
                recreate()
            }else if(which == 2){
                setLocale("es")
                recreate()
            }else if(which == 3){
                setLocale("de")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
    //Fonction qui permet de changer la langue de l'application

    private fun setLocale(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)


        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang",Lang)
        editor.apply()
    }
    //Fonction qui permet de charger la langue enregistrée dans les préférences partagées
    private fun loadLocale(){
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang","")
        setLocale(language.toString())
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