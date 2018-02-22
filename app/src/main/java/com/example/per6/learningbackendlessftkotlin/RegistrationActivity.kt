package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_registration.*

const val GET_USERNAME = "get username"

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerButton.setOnClickListener {

            if (passwordRegister.text.toString() != confirmPasswordRegister.text.toString()) {
                Toast.makeText(this@RegistrationActivity, "Password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passwordRegister.text.isNotEmpty()) {

                val backendlessUser = BackendlessUser()
                with(backendlessUser) {
                    email = emailRegister.text.toString()
                    password = passwordRegister.text.toString()
                    setProperties(firstNameRegister.text.toString(), lastNameRegister.text.toString(), userNameRegister.text.toString())
                }

                Backendless.UserService.register(backendlessUser, object : AsyncCallback<BackendlessUser> {
                    override fun handleFault(fault: BackendlessFault?) {
                        Toast.makeText(this@RegistrationActivity, fault?.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun handleResponse(response: BackendlessUser?) {
                        Toast.makeText(this@RegistrationActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent().apply {
                            putExtra(GET_USERNAME, backendlessUser.getProperty("username") as String)
                        }
                        setResult(LoginActivity.REQUEST_REGISTER_CODE, intent)
                        finish()
                    }

                })
            } else {
                Toast.makeText(this@RegistrationActivity, "Invalid arguments", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun BackendlessUser.setProperties(firstname : String, lastname : String, username : String) {
        setProperty("firstname", firstname)
        setProperty("lastname", lastname)
        setProperty("username", username)
    }
}
