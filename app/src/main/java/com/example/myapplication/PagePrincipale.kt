package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Authentification.SessionManager
import com.example.myapplication.mqtt.MqttClientHelper
import com.google.android.material.snackbar.Snackbar

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import kotlinx.android.synthetic.main.activity_main.*

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_historique.*

import kotlinx.android.synthetic.main.activity_page_principale.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.*
import kotlin.concurrent.schedule

class PagePrincipale : AppCompatActivity() {
    lateinit var viewModel: MainActivityViewModel
    var temperatureObtenue:Float = 0.0f
    lateinit var afficherValeurTemperature: TextView /*champ qui permet d'afficher la température du senseur*/
    lateinit var editTextDate: EditText
    lateinit var recyclerListData: MutableLiveData<ListeTemperaturSeuil>
    var seuilMax: Float = 0.0f
    var seuilMin: Float = 0.0f
    var str:String = "0"
    var etat: Boolean = false
    var valeur: Float=0.0f
    var temperaturePrecedente:Float = 0.0f
    var id_temperature: String = "0"
    var etatDeLaTemperature: Int = 0
    private val mqttClient by lazy {
        MqttClientHelper(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page_principale)

        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)
        setMqttCallBack() // fonction qui permet la connexion au broker mqtt

        obtenirTemperaturesSeuils()
        val etatTemperature = findViewById<Button>(R.id.btnEtatTemperature)
        etatTemperature.setOnClickListener {

            if(temperatureObtenue >= seuilMax){
                etatDeLaTemperature = 1;
                // si la température est supérieur au seuil max, on envoie un message au eps32 pour allumer la led rouge
                mqttClient.publish("etat_temp", etatDeLaTemperature.toString())
            }else if(temperatureObtenue <= seuilMin){
                etatDeLaTemperature = 2;
                // si la température est inférieur au seuil min, on envoie un message au eps32 pour allumer la led rouge ne s'allume pas ou s'éteint
                mqttClient.publish("etat_temp", etatDeLaTemperature.toString())
            }else{
                // si la température est inférieur au seuil min, on envoie un message au eps32 pour allumer la led rouge ne s'allume pas ou s'éteint
                etatDeLaTemperature = 0;
            }
            println("id valeur "+id_temperature)
            modifierTemperatureSeuil(id_temperature)
        }


        afficherValeurTemperature = findViewById(R.id.temperatureDhtt22Value)

        if(mqttClient.isConnected()) {

            mqttClient.subscribe("esp32/temperature")
        }else{
            Timer("SettingUp", false).schedule(1000) {
                mqttClient.subscribe("esp32/temperature")

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
                val currentDateTime: String = "${calendar.get(Calendar.YEAR)}-${calendar.get(
                    Calendar.MONTH)+1}-${calendar.get(Calendar.DAY_OF_MONTH)} ${calendar.get(
                    Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${calendar.get(Calendar.SECOND)}"
                Log.w("Debug", "Message received from host '$SOLACE_MQTT_HOST': $mqttMessage")
                str = mqttMessage.toString()
                temperatureObtenue = str.toFloat()
                afficherValeurTemperature.text = str
                valeur = str.toFloat()
                //Methode qui permetd d'ajouter la temperature dans la base de donnée si elle est différente de la précédente
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
        Toast.makeText(this@PagePrincipale, message, Toast.LENGTH_LONG).show()
    }
    /*Fonction qui permet d'obtenir les températures seuils*/
    fun obtenirTemperaturesSeuils(){
        recyclerListData = MutableLiveData()
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.abtenirSeuil()
        call.enqueue(object : Callback<ListeTemperaturSeuil> {

            override fun onResponse(call: Call<ListeTemperaturSeuil>, response: Response<ListeTemperaturSeuil>) {
                if(response.isSuccessful && response.body() != null){
                    val mesValeurs= response.body()
                    seuilMax = mesValeurs?.body?.get(0)?.SeuilMax.toString().toFloat()
                    seuilMin = mesValeurs?.body?.get(0)?.SeuilMin.toString().toFloat()
                    id_temperature = mesValeurs?.body?.get(0)?.id.toString()

                }else{
                    recyclerListData.postValue(null)
                }
            }
            override fun onFailure(call: Call<ListeTemperaturSeuil>, t: Throwable) {
                recyclerListData.postValue(null)
            }


        })
    }
    /*fonction qui permet les temperatures seuils*/
    fun modifierTemperatureSeuil(id:String){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        val temperatureSeuil = TemperatureSeuil(2.5F,2.5F)
        viewModel.updateTemperatureSeuil(id,temperatureSeuil)
        println( viewModel.updateTemperatureSeuil(id,temperatureSeuil))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //Gonflez le menu; cela ajoute des éléments à la barre d'action si elle est présente
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Gérer les clics sur les éléments de la barre d'action ici. La barre d'action
        // gère automatiquement les clics sur le bouton Home/Up, aussi longtemps
        // que vous spécifiez une activité parente dans AndroidManifest.xml.
        return  when (item.itemId) {
            R.id.action_seDeconnecter -> {
                SessionManager.clearData(this)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                return true
            }
            R.id.action_infos_bd -> {

                if(id_temperature !=null){
                    val intent = Intent(this@PagePrincipale, Infos_bd::class.java)
                    intent.putExtra("id_temperature",id_temperature)
                    intent.putExtra("seuilMax",seuilMax.toString())
                    intent.putExtra("seuilMin",seuilMin.toString())
                    startActivity(intent)
                }

                return true
            }
            R.id.action_historique -> {
                val intent = Intent(this@PagePrincipale, HistoriqueActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        mqttClient.destroy()
        super.onDestroy()

    }

}