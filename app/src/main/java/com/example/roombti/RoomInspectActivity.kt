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
import com.example.roombti.databinding.ActivityRoomInspectBinding

class RoomInspectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomInspectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomInspectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set user name dynamically
        val user = intent.getSerializableExtra("user") as? UserData
        user?.let {
            binding.ersinKara.text = it.name ?: ""
        }
    }
    fun open_chat(view: View) {
        val intent = Intent(this, DMPageActivity::class.java)
        startActivity(intent)
    }
    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}