package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_REGISTER_CODE = 1902
        const val GET_LOGGED_IN_OWNER_ID = "get logged in user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val underlineText = SpannableString("Create Account")
        underlineText.setSpan(UnderlineSpan(), 0, underlineText.length, 0)

        createAccount.setText(underlineText, TextView.BufferType.SPANNABLE)
        createAccount.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivityForResult(intent, REQUEST_REGISTER_CODE)
        }

        login.setOnClickListener {
            loginFlipper.showNext()

            //Backendless
            Backendless.UserService.login(userNameLogin.text.toString(), passwordLogin.text.toString(), object : AsyncCallback<BackendlessUser> {
                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(this@LoginActivity, fault?.message, Toast.LENGTH_SHORT).show()
                    loginFlipper.showPrevious()
                }

                override fun handleResponse(response: BackendlessUser?) {
                    loginFlipper.showPrevious()

                    //start new activity and finish current
                    val intent = Intent(this@LoginActivity, RestaurantDisplayActivity::class.java).apply {
                        putExtra(GET_LOGGED_IN_OWNER_ID, response?.objectId)
                    }
                    startActivity(Intent(this@LoginActivity, RestaurantDisplayActivity::class.java))
                    finish()
                }

            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_REGISTER_CODE -> userNameLogin.setText(data?.getStringExtra(GET_USERNAME))
        }
    }
}
