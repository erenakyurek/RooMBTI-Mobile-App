package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMenuScreenBinding
import com.google.firebase.auth.FirebaseAuth

class MenuScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuScreenBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.menuOption.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.logOut.setOnClickListener{
            mAuth.signOut()
            val intent = Intent(this@MenuScreenActivity, LoginPageActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.chats.setOnClickListener{
            val intent = Intent(this@MenuScreenActivity, ChatsActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

}