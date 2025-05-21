package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityHouseRenterBinding
import com.example.roombti.databinding.ActivityIntroBinding
import com.google.firebase.database.*
import android.widget.Toast

class HouseRenterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHouseRenterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String

    private lateinit var name: String
    private lateinit var surname: String
    private lateinit var gender: String
    private var age: Int = 0
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var mbti: String
    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHouseRenterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = intent.getStringExtra("registerName").orEmpty()
        surname = intent.getStringExtra("registerSurname").orEmpty()
        gender = intent.getStringExtra("registerGender").orEmpty()
        age = intent.getIntExtra("registerAge", 0)
        email = intent.getStringExtra("registerEmail").orEmpty()
        password = intent.getStringExtra("registerPassword").orEmpty()
        mbti = intent.getStringExtra("registerMbti").orEmpty()
        userType = intent.getStringExtra("userType").orEmpty()

        val usersRef = FirebaseDatabase.getInstance().reference.child("users")

        binding.houserenterNextButton.setOnClickListener {
            val minHm = binding.houserenterHousemateNowInput.text.toString().toIntOrNull()
            val maxHm = binding.houserenterHousemateMaxInput.text.toString().toIntOrNull()
            val loc = binding.houserenterLocationInput.text.toString()
            val rent = binding.houserenterBudgetInput.text.toString().toIntOrNull()
            val smoke = binding.houserenterSmokeYes.isChecked
            val pet = binding.houserenterPetYes.isChecked

            if (minHm != null && maxHm != null && loc.isNotEmpty() && rent != null) {

                val userMap = hashMapOf<String, Any>(
                    "name" to name,
                    "surname" to surname,
                    "gender" to gender,
                    "age" to age,
                    "email" to email,
                    "password" to password,
                    "mbti" to mbti,
                    "userType" to userType,
                    "minHousemates" to minHm,
                    "maxHousemates" to maxHm,
                    "location" to loc,
                    "rent" to rent,
                    "allowSmoking" to smoke,
                    "allowPets" to pet
                )

                val newUserRef = usersRef.push()
                newUserRef.setValue(userMap)
                    .addOnSuccessListener {
                    Toast.makeText(this, "Registration complete! Please log in.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginPageActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error saving data: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}