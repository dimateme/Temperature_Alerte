/**
 * Classe qui permet de faire le lien entre le model et la vue
 * @property createNewTemperatureLiveData
 * @property recyclerSeuilListData
 * @property updateTemperatureSeuil
 * Author : Jean Paul Bai
 * Date : 2023-03-25
 * Version : 1
 */
package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivityViewModel :ViewModel() {
    lateinit var createNewTemperatureLiveData: MutableLiveData<TemperatureResponse?>
    lateinit var recyclerSeuilListData: MutableLiveData<ListeTemperaturSeuil>
    lateinit var updateTemperatureSeuil: MutableLiveData<TemperatureSeuilResponse?>
    init {
        createNewTemperatureLiveData = MutableLiveData()
        recyclerSeuilListData = MutableLiveData()
        updateTemperatureSeuil = MutableLiveData()
    }

    //Fonction qui permet d'obtenir les températures
    fun getTemperatureSeuilObservrable() : MutableLiveData<ListeTemperaturSeuil>{
        return recyclerSeuilListData
    }
    //Fonction qui permet d'obtenir les températures
    fun getTemperatureListeSeuil(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.abtenirSeuil()
        call.enqueue(object : Callback<ListeTemperaturSeuil>{
            override fun onFailure(call: Call<ListeTemperaturSeuil>, t: Throwable) {
                recyclerSeuilListData.postValue(null)
            }

            override fun onResponse(call: Call<ListeTemperaturSeuil>, response: Response<ListeTemperaturSeuil>) {
                if(response.isSuccessful){
                    recyclerSeuilListData.postValue(response.body())

                }else{
                    recyclerSeuilListData.postValue(null)
                }
            }
        })
    }
    //Fonction qui permet d'obtenir les températures
    fun getCreateNewTemperatureObserver(): MutableLiveData<TemperatureResponse?>{
        return createNewTemperatureLiveData
    }
    fun getUpdateTemperatureObserver(): MutableLiveData<TemperatureSeuilResponse?> {
        return updateTemperatureSeuil
    }
    //Fonction qui permet d'ajouter une nouvelle température
    fun createNewTemperature(temperature: Temperature){
        val retroService = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroService.ajouterTemperature(temperature)
        call.enqueue((object :Callback<TemperatureResponse>{
            override fun onFailure(call: Call<TemperatureResponse>, t: Throwable) {
                createNewTemperatureLiveData.postValue(null)
            }

            override fun onResponse(call: Call<TemperatureResponse>, response: Response<TemperatureResponse>) {
                if(response.isSuccessful){
                    createNewTemperatureLiveData.postValue(response.body())
                }else{
                    createNewTemperatureLiveData.postValue(null)
                }
            }
        }))
    }

    //Fonction qui permet de modifier les températures seuils
    fun updateTemperatureSeuil(id:String,temperatureSeuil: TemperatureSeuil){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.modifierSeuil(id, temperatureSeuil)
        call.enqueue((object :Callback<TemperatureSeuilResponse>{
            override fun onFailure(call: Call<TemperatureSeuilResponse>, t: Throwable) {
                updateTemperatureSeuil.postValue(null)
            }

            override fun onResponse(call: Call<TemperatureSeuilResponse>, response: Response<TemperatureSeuilResponse>) {
                if(response.isSuccessful){
                    updateTemperatureSeuil.postValue(response.body())
                }else{
                    updateTemperatureSeuil.postValue(null)
                }
            }
        }))
    }


}