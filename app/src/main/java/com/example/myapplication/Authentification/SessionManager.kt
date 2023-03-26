package com.example.myapplication.Authentification

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.R

object SessionManager {
    const val USER_TOKEN = "user_token"
    fun saveAuthToken(context: Context, token: String) {
        saveString(context, USER_TOKEN, token)
    }
    fun saveString(context: Context, key: String, value: String) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()

    }
    fun getToken(context: Context): String? {
        return getString(context, USER_TOKEN)
    }

    fun getString(context: Context, key: String): String? {
        val prefs: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
        return prefs.getString(this.USER_TOKEN, null)
    }

    fun clearData(context: Context){
        val editor = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

}