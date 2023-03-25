package com.example.myapplication

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.mqtt.MqttClientHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    //    lateinit var seuilAdapteur: SeuilAdapteur
    lateinit var viewModel: MainActivityViewModel
    var temperatureObtenue:Float = 0.0f
    lateinit var afficherValeurTemperature: TextView
    lateinit var editTextDate: EditText
    lateinit var recyclerListData: MutableLiveData<ListeTemperaturSeuil>
    var seuilMax: Float = 0.0f
    var seuilMin: Float = 0.0f
    var str:String = "0"
    var valeur: Float=0.0f
    var temperaturePrecedente:Float = 0.0f
    private val mqttClient by lazy {
        MqttClientHelper(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        setMqttCallBack()
//        obtenirTemperaturesSeuils()

        val button = findViewById<Button>(R.id.refreshButton)
        button.setOnClickListener {
            println("temperatureObtenue: $temperatureObtenue")
            obtenirTemperaturesSeuils()
            if(temperatureObtenue >0.0f){
                if(valeur!=0.0f && seuilMax!=0.0f){
                    if(valeur >= seuilMax){
                        Toast.makeText(this, "Temperature est haute", Toast.LENGTH_LONG).show()
                    }else if(valeur < seuilMin || valeur <seuilMax){
                        Toast.makeText(this, "Temperature est basse", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this, "Affichage de la température en attente", Toast.LENGTH_LONG).show()
            }
        }




        afficherValeurTemperature = findViewById(R.id.temperatureDhtt22Value)

        if(mqttClient.isConnected()) {
            mqttClient.subscribe("esp32/temperature")
//            obtenirTemperaturesSeuils()
        }else{
            Timer("SettingUp", false).schedule(1000) {
                mqttClient.subscribe("esp32/temperature")
//                obtenirTemperaturesSeuils()

            }

        }

    }


    private fun setMqttCallBack() {
        fun initRecyclerView(){
            viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
            viewModel.getCreateNewTemperatureObserver().observe(this, Observer<TemperatureResponse?> {
            })
            viewModel.getTemperatureListeSeuil()
        }

        mqttClient.setCallback(object : MqttCallbackExtended {
            override fun connectionLost(throwable: Throwable) {
                val snackbarMsg = "Connection to host lost:\n'$SOLACE_MQTT_HOST'"
                Log.w("Debug", snackbarMsg)
                Snackbar.make(findViewById(android.R.id.content), snackbarMsg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun connectComplete(b: Boolean, s: String) {
                val snackbarMsg = "Connected to host:\n'$SOLACE_MQTT_HOST'."
                Log.w("Debug", snackbarMsg)
                Snackbar.make(findViewById(android.R.id.content), snackbarMsg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                initRecyclerView()
                val calendar: Calendar = Calendar.getInstance()
                val currentDateTime: String = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH)+1}-${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
                Log.w("Debug", "Message received from host '$SOLACE_MQTT_HOST': $mqttMessage")
                str = mqttMessage.toString()
                temperatureObtenue = str.toFloat()
                afficherValeurTemperature.text = str
                valeur = str.toFloat()
                //Methode qui permetd d'ajouter la temperature dans la base de donnée
                if (valeur != temperaturePrecedente){
                    val temperature = Temperature(valeur.toFloat(),currentDateTime)
                    viewModel.createNewTemperature(temperature)
                }
                temperaturePrecedente = valeur
                editTextDate.setText(temperature.toString())
                temperatureObtenue = afficherValeurTemperature.text.toString().toFloat()

            }


            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
                Log.w("Debug", "Message published to host '$SOLACE_MQTT_HOST'")
            }

        })
    }
    fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
    }
    fun obtenirTemperaturesSeuils(){
        recyclerListData = MutableLiveData()
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.abtenirSeuil()
        call.enqueue(object : Callback<ListeTemperaturSeuil>{
            override fun onFailure(call: Call<ListeTemperaturSeuil>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ListeTemperaturSeuil>, response: Response<ListeTemperaturSeuil>) {
                val valeurMax: String
                val valeurMin: String
                if(response.isSuccessful){
                    val mesValeurs= response.body()

                    seuilMax = mesValeurs?.body?.get(0)?.SeuilMax.toString().toFloat()
                    seuilMin = mesValeurs?.body?.get(1)?.SeuilMin.toString().toFloat()
                    println("seuilMax: $seuilMax")
                }else{
                    recyclerListData.postValue(null)
                }
            }


        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Gérer les clics sur les éléments de la barre d'action ici. La barre d'action
        // gère automatiquement les clics sur le bouton Home/Up, aussi longtemps
        // que vous spécifiez une activité parente dans AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        mqttClient.destroy()
        super.onDestroy()

    }



}
