package com.example.roombti

import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMainRoommatesBinding
import com.example.roombti.databinding.ActivityMenuScreenBinding

class MainRoommatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainRoommatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRoommatesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun open_person(view: View) {
        val intent = Intent(this, PersonInspectActivity::class.java)
        startActivity(intent)
    }
    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}