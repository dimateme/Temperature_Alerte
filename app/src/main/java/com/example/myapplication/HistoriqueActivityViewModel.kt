/**
 * Classe qui permet de faire le lien entre le model et la vue
 * @property recyclerListData
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

class HistoriqueActivityViewModel: ViewModel() {
    lateinit var recyclerListData: MutableLiveData<ListeTemperature>

    init {
        recyclerListData = MutableLiveData()
    }
    //Fonction qui permet d'obtenir les températures
    fun obtenirTemperatureObservable(): MutableLiveData<ListeTemperature> {
        return recyclerListData
    }
    //Fonction qui permet d'obtenir la liste des températures
    fun obtenirListeTemperature(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.abtenirListeTemperature()
        call.enqueue(object : Callback<ListeTemperature>{
            override fun onFailure(call: retrofit2.Call<ListeTemperature>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ListeTemperature>, response: Response<ListeTemperature>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())

                }else{
                    recyclerListData.postValue(null)
                }
            }
        })

    }
}