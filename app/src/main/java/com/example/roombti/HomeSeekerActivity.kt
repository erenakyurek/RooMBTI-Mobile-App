package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityHomeSeekerBinding
import com.example.roombti.databinding.ActivityIntroBinding
import com.google.firebase.database.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class HomeSeekerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeSeekerBinding
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
        binding = ActivityHomeSeekerBinding.inflate(layoutInflater)
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
        mAuth = FirebaseAuth.getInstance()

        binding.homeseekerNextButton.setOnClickListener {
            val minHm = binding.seekerHousemateMinInput.text.toString().toIntOrNull()
            val maxHm = binding.seekerHousemateMaxInput.text.toString().toIntOrNull()
            val loc = binding.seekerLocationInput.text.toString()
            val minB = binding.houseseekerMinBudgetInput.text.toString().toIntOrNull()
            val maxB = binding.houseseekerMaxBudgetInput.text.toString().toIntOrNull()
            val smoke = binding.houseseekerSmokeYes.isChecked
            val pet = binding.houseseekerPetYes.isChecked
            val university = binding.houseseekerUniversityInput.text.toString()

            if (minHm!=null && maxHm!=null && loc.isNotEmpty() && minB!=null && maxB!=null) {


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
                                minBudget = minB,
                                maxBudget = maxB,
                                allowSmoking = smoke,
                                allowPets = pet,
                                university = university,
                                id = mAuth.currentUser?.uid!!
                            )
                            mDbRef = FirebaseDatabase.getInstance().getReference()
                            mDbRef.child("user").child(mAuth.currentUser?.uid!!).setValue(user)
                            Toast.makeText(this, "Registration complete! Please log in.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginPageActivity::class.java))
                        }else {
                            Toast.makeText(this@HomeSeekerActivity, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill all required.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}