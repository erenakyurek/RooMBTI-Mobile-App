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
import com.google.firebase.auth.FirebaseAuth

class HouseRenterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHouseRenterBinding
    private lateinit var mDbRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

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

        mAuth = FirebaseAuth.getInstance()

        binding.houserenterNextButton.setOnClickListener {
            val minHm = binding.houserenterHousemateNowInput.text.toString().toIntOrNull()
            val maxHm = binding.houserenterHousemateMaxInput.text.toString().toIntOrNull()
            val loc = binding.houserenterLocationInput.text.toString()
            val rent = binding.houserenterBudgetInput.text.toString().toIntOrNull()
            val smoke = binding.houserenterSmokeYes.isChecked
            val pet = binding.houserenterPetYes.isChecked

            if (minHm != null && maxHm != null && loc.isNotEmpty() && rent != null) {

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            val user = UserData(
                                name = name,
                                surname = surname,
                                gender = gender,
                                age = age,
                                email = email,
                                password = password,
                                mbti = mbti,
                                userType = userType,
                                minHousemates = minHm,
                                maxHousemates = maxHm,
                                location = loc,
                                rent = rent,
                                allowSmoking = smoke,
                                allowPets = pet,
                                id = mAuth.currentUser?.uid!!
                            )
                            mDbRef = FirebaseDatabase.getInstance().getReference()
                            mDbRef.child("user").child(mAuth.currentUser?.uid!!).setValue(user)
                            Toast.makeText(this, "Registration complete! Please log in.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginPageActivity::class.java))

                        }else {
                            Toast.makeText(this@HouseRenterActivity, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}