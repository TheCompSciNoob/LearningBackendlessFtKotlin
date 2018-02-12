package com.example.per6.learningbackendlessftkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val underlineText = SpannableString("Create Account")
        underlineText.setSpan(UnderlineSpan(), 0, underlineText.length, 0)

        createAccount.setText(underlineText, TextView.BufferType.SPANNABLE)
        createAccount.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            loginSwitcher.showNext()

            Handler().postDelayed({
                loginSwitcher.showNext()
            }, 1000)
        }
    }
}
