package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Authentification.BaseResponse
import com.example.myapplication.Authentification.LoginResponse
import com.example.myapplication.Authentification.LoginViewModel
import com.example.myapplication.Authentification.SessionManager
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val token = SessionManager.getToken(this)

        if (!token.isNullOrBlank()) {
            navigateToHome()
        }

        viewModel.loginResult.observe(this){
            when(it){
                is BaseResponse.Loading ->{
                    showLoading()
                }
                is BaseResponse.Success ->{
                    stopLoading()
                    processLogin(it.body)
                }
                is BaseResponse.Error ->{
                    stopLoading()
                    processError(it.message)
                }else ->{
                stopLoading()

            }
            }
        }

        val button = findViewById<Button>(R.id.btnConnexion)
        button.setOnClickListener {
            doLogin()
        }
    }
    private fun navigateToHome() {
        val intent = Intent(this, PagePrincipale::class.java)
        startActivity(intent)
    }
    fun doLogin() {
        val courriel = findViewById<EditText>(R.id.edtCourriel).text.toString()
        val motDePasse = findViewById<EditText>(R.id.edtMotDePasse).text.toString()
        viewModel.loginUser(courriel= courriel, motDePasse = motDePasse)

    }
    fun showLoading() {
        val prgbar = findViewById<ProgressBar>(R.id.prgbar)
        prgbar.visibility = View.VISIBLE
    }
    fun stopLoading() {
        val prgbar = findViewById<ProgressBar>(R.id.prgbar)
        prgbar.visibility = View.GONE
    }
    fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.message)
        if (!data?.body?.token.isNullOrEmpty()) {
            data?.body?.token?.let { SessionManager.saveAuthToken(this, it) }
            navigateToHome()
        }
    }
    fun processError(msg: String?) {
        val messageErreur ="Veuillez vérifier votre courriel et votre mot de passe";

        showToast("Error:" + messageErreur)
    }
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
