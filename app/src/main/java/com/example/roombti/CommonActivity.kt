package com.example.roombti

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import com.example.roombti.databinding.ActivityCommonBinding
import com.google.firebase.database.*

class CommonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommonBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    //private lateinit var userId: String

    private lateinit var registerName: String
    private lateinit var registerSurname: String
    private lateinit var registerGender: String
    private var registerAge: Int = 0
    private lateinit var registerEmail: String
    private lateinit var registerPassword: String
    private lateinit var registerMbti: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get the userId you put into the Intent back in RegisterActivity
        //userId = intent.getStringExtra("USER_ID").orEmpty()

        // 2) init your Realtime Database reference exactly as before
        firebaseDatabase  = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        registerName = intent.getStringExtra("registerName").orEmpty()
        registerSurname = intent.getStringExtra("registerSurname").orEmpty()
        registerGender = intent.getStringExtra("registerGender").orEmpty()
        registerAge = intent.getIntExtra   ("registerAge", 0)
        registerEmail = intent.getStringExtra("registerEmail").orEmpty()
        registerPassword = intent.getStringExtra("registerPassword").orEmpty()
        registerMbti = intent.getStringExtra("registerMbti").orEmpty()

        binding.lookingRoommateSelectButton.setOnClickListener {
            Intent(this, HouseRenterActivity::class.java).also { intent ->
                intent.putExtra("registerName", registerName)
                intent.putExtra("registerSurname", registerSurname)
                intent.putExtra("registerGender", registerGender)
                intent.putExtra("registerAge", registerAge)
                intent.putExtra("registerEmail", registerEmail)
                intent.putExtra("registerPassword", registerPassword)
                intent.putExtra("registerMbti", registerMbti)
                intent.putExtra("userType", "houserenter")
                startActivity(intent)
            }
            finish()
        }

        binding.lookingHomeSelectButton.setOnClickListener {
            Intent(this, HomeSeekerActivity::class.java).also { intent ->
                intent.putExtra("registerName", registerName)
                intent.putExtra("registerSurname", registerSurname)
                intent.putExtra("registerGender", registerGender)
                intent.putExtra("registerAge", registerAge)
                intent.putExtra("registerEmail", registerEmail)
                intent.putExtra("registerPassword", registerPassword)
                intent.putExtra("registerMbti", registerMbti)
                intent.putExtra("userType", "homeseeker")
                startActivity(intent)
            }
            finish()
        }
    }
}