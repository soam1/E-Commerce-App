package com.akashsoam.onlinestoreapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var btnMainActivitySignUp: Button
    lateinit var btnMainActivityLogin: Button

    lateinit var edtLoginEmail: EditText
    lateinit var edtLoginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMainActivitySignUp = findViewById(R.id.btnLoginSignUp)
        btnMainActivityLogin = findViewById(R.id.btnLogin)
        edtLoginEmail = findViewById(R.id.edtLoginEmail)
        edtLoginPassword = findViewById(R.id.edtLoginPassword)

        btnMainActivityLogin.setOnClickListener {
            val signUpUrl: String =
                "http://192.168.42.198/OnlineStoreApp/login_app_user.php?email=" + edtLoginEmail.text.toString() + "&pass=" + edtLoginPassword.text.toString()

            val requestQ: RequestQueue = Volley.newRequestQueue(this@MainActivity)

            val stringRequest = StringRequest(
                Request.Method.GET, signUpUrl,
                { response ->
                    if (response.equals("The user does not exist")) {
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("COULD NOT Login")
                            .setIcon(R.drawable.info)
                            .setMessage("$response!").create().show()
                        val homeIntent = Intent(this@MainActivity, MainActivity::class.java)
                        startActivity(homeIntent)
                    } else {
                        //keeping track of user who logged in
                        Person.email = edtLoginEmail.text.toString()

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Info").setIcon(R.drawable.info)
                            .setMessage(response).create()
                            .show()
                        val homeIntent = Intent(this@MainActivity, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }
                },
                { error ->
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Error!")
                        .setMessage("Request could not be completed" + error.message.toString())
                        .create()
                        .show()
                }
            )
            requestQ.add(stringRequest)
        }


        btnMainActivitySignUp.setOnClickListener {
            val signUpIntent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }


    }
}