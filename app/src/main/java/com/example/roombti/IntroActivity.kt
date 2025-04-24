package com.example.roombti

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.widget.Button

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro)

        //button
        val registerButton = findViewById<Button>(R.id.register_button_white)
        val loginButton = findViewById<Button>(R.id.login_button_blue)

        //button navigation
        registerButton.setOnClickListener {
            val intent = Intent(this, MBTISelectionActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener() {
            val intent = Intent(this, MBTISelectionActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}