package com.example.loginactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ValidationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get username and password from MainActivity
        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        // Hardcoded credentials
        val correctUsername = "jungkook"
        val correctPassword = "09011997"

        // Validate credentials
        if (username == correctUsername && password == correctPassword) {

            // Successful login
            val resultIntent = Intent()
            resultIntent.putExtra("success", true)
            resultIntent.putExtra("username", username)

            setResult(RESULT_OK, resultIntent)
            finish()

        } else {

            // Failed login
            val resultIntent = Intent()
            resultIntent.putExtra("success", false)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}