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

class SignUpActivity : AppCompatActivity() {
    lateinit var edtSignUpEmail: EditText
    lateinit var edtSignUpUsername: EditText
    lateinit var edtSignUpPassword: EditText
    lateinit var edtSignUpConfirmPassword: EditText

    lateinit var btnSignUp: Button
    lateinit var btnSignUpLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        edtSignUpEmail = findViewById(R.id.edtSignupEmail)
        edtSignUpUsername = findViewById(R.id.edtSignupUsername)
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword)
        edtSignUpConfirmPassword = findViewById(R.id.edtSignUpConfirmPassword)

        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin)

        btnSignUp.setOnClickListener {
            if (edtSignUpPassword.text.toString() == edtSignUpConfirmPassword.text.toString()) {
//                todo:registration process
                val signUpUrl: String =
                    "http://192.168.42.198//OnlineStoreApp/join_new_user.php?email=" + edtSignUpEmail.text.toString() +
                            "&username=" + edtSignUpUsername.text.toString() + "&pass=" + edtSignUpPassword.text.toString()
                val requestQ: RequestQueue = Volley.newRequestQueue(this@SignUpActivity)

                val stringRequest: StringRequest = StringRequest(Request.Method.GET, signUpUrl,
                    { response ->
                        if (response.equals("a user with the same email address already exists")) {
                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("COULD NOT SIGNUP")
                                .setIcon(R.drawable.info)
                                .setMessage("User already exists!").create().show()
                        } else {
                            //keeping track of the user who signed up
                            Person.email = edtSignUpEmail.text.toString()

                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Info").setIcon(R.drawable.info)
                                .setMessage(response).create()
                                .show()
                            val homeIntent = Intent(this@SignUpActivity, HomeScreen::class.java)
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

            } else {
//                alert
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Password mismatch!")
                    .setMessage("Password and confirm password should match").create().show()
            }
        }

        btnSignUpLogin.setOnClickListener {
            finish()
        }
    }
}