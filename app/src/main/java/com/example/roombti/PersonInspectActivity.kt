package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMainRoommatesBinding
import com.example.roombti.databinding.ActivityMenuScreenBinding
import com.example.roombti.databinding.ActivityPersonInspectBinding

class PersonInspectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonInspectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonInspectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        setContentView(R.layout.activity_person_inspect)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}