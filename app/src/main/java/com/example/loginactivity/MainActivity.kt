package com.example.loginactivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val VALIDATION_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Maroon status bar
        window.statusBarColor = Color.rgb(128, 0, 0)

        // White status bar icons
        window.decorView.systemUiVisibility = 0

        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<View>(R.id.rootLayout)

        val usernameEditText =
            findViewById<EditText>(R.id.usernameEditText)

        val passwordEditText =
            findViewById<EditText>(R.id.passwordEditText)

        val loginButton =
            findViewById<Button>(R.id.loginButton)

        val loginForm =
            findViewById<LinearLayout>(R.id.loginForm)

        val welcomeMessage =
            findViewById<TextView>(R.id.welcomeMessage)

        // Keep content below the status bar
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { view, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                0,
                systemBars.top,
                0,
                0
            )

            insets
        }

        // LOGIN button ClickListener
        loginButton.setOnClickListener {

            val username =
                usernameEditText.text.toString().trim()

            val password =
                passwordEditText.text.toString()

            // ========================================
            // 1. EMPTY FIELDS
            // ========================================
            if (username.isEmpty() || password.isEmpty()) {

                loginForm.visibility = View.VISIBLE

                AlertDialog.Builder(this)
                    .setTitle("Login Error")
                    .setMessage("Please fill in all fields.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

            // ========================================
            // 2. SEND DATA TO VALIDATION ACTIVITY
            // ========================================
            else {

                val intent =
                    Intent(this, ValidationActivity::class.java)

                intent.putExtra("username", username)
                intent.putExtra("password", password)

                startActivityForResult(
                    intent,
                    VALIDATION_REQUEST
                )
            }
        }
    }

    // ========================================
    // RECEIVE RESULT FROM VALIDATION ACTIVITY
    // ========================================
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VALIDATION_REQUEST &&
            resultCode == Activity.RESULT_OK
        ) {

            val success =
                data?.getBooleanExtra("success", false) ?: false

            // ========================================
            // SUCCESSFUL LOGIN
            // ========================================
            if (success) {

                val username =
                    data?.getStringExtra("username") ?: ""

                val loginForm =
                    findViewById<LinearLayout>(R.id.loginForm)

                val welcomeMessage =
                    findViewById<TextView>(R.id.welcomeMessage)

                welcomeMessage.text =
                    "Welcome, $username!\uD83D\uDC9C"

                welcomeMessage.gravity = Gravity.CENTER

                val messageParams =
                    welcomeMessage.layoutParams
                            as FrameLayout.LayoutParams

                messageParams.gravity = Gravity.CENTER
                messageParams.topMargin = 0

                welcomeMessage.layoutParams = messageParams

                // Hide login form
                loginForm.visibility = View.GONE

                // Show welcome message
                welcomeMessage.visibility = View.VISIBLE
            }

            // ========================================
            // WRONG USERNAME/PASSWORD
            // ========================================
            else {

                val loginForm =
                    findViewById<LinearLayout>(R.id.loginForm)

                val passwordEditText =
                    findViewById<EditText>(R.id.passwordEditText)

                // Show login form again
                loginForm.visibility = View.VISIBLE

                // Clear password
                passwordEditText.text.clear()

                // Show error popup
                AlertDialog.Builder(this)
                    .setTitle("Login Error")
                    .setMessage("Incorrect username or password.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}