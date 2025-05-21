package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMbtiselectionBinding
import com.example.roombti.databinding.ActivityRegisterBinding

class MBTISelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMbtiselectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMbtiselectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mbtiNextButton.setOnClickListener {
            val registerMbti = binding.mbtiDropdownInput.selectedItem?.toString().orEmpty()
            if (registerMbti.isNotEmpty() && registerMbti != "Select") {
                val intent = Intent(this, RegisterActivity::class.java).putExtra("registerMbti", registerMbti)
                startActivity(intent)
            } else {
                Toast.makeText(this@MBTISelectionActivity, "Please select your MBTI type.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}