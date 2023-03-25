package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivityViewModel :ViewModel() {
    lateinit var createNewTemperatureLiveData: MutableLiveData<TemperatureResponse?>
    lateinit var recyclerSeuilListData: MutableLiveData<ListeTemperaturSeuil>
    init {
        createNewTemperatureLiveData = MutableLiveData()
        recyclerSeuilListData = MutableLiveData()
    }

    fun getTemperatureSeuilObservrable() : MutableLiveData<ListeTemperaturSeuil>{
        return recyclerSeuilListData
    }
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
    fun getCreateNewTemperatureObserver(): MutableLiveData<TemperatureResponse?>{
        return createNewTemperatureLiveData
    }
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

}