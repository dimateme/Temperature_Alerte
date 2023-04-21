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
    fun obtenirTemperatureObservable(): MutableLiveData<ListeTemperature> {
        return recyclerListData
    }
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

    fun rechercherTemperature(searchFloat: Float){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.rechercherTemperature(searchFloat)
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