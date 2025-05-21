package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityIntroBinding
import com.example.roombti.databinding.ActivityRegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var registerMbti: String

    //private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cinsiyet Spinner'ını doldur
        val genderSpinner = binding.registerGender
        val genderOptions = listOf("Please select", "Male", "Female")
        val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        registerMbti = intent.getStringExtra("registerMbti") ?: ""

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.registerNextButton.setOnClickListener{
            val registerName = binding.registerName.text.toString()
            val registerSurname = binding.registerSurname.text.toString()
            val registerGender = binding.registerGender.selectedItem.toString()
            val registerAgeStr = binding.registerAge.text.toString()
            val registerAge = registerAgeStr.toIntOrNull()
            val registerEmail = binding.registerEmail.text.toString()
            val registerPassword = binding.registerPassword.text.toString()

            if (registerName.isNotEmpty() && registerSurname.isNotEmpty()
                && registerGender != "Please select" && registerGender.isNotEmpty() && registerAge != null
                && registerEmail.isNotEmpty() && registerPassword.isNotEmpty()){
                checkEmail(registerName, registerSurname, registerGender, registerAge, registerEmail, registerPassword)
            } else {
                if (registerGender == "Please select") {
                    Toast.makeText(this@RegisterActivity, "Please select a gender.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@RegisterActivity, "Please fill all fields.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkEmail(name: String, surname: String, gender: String, age: Int, email: String, password: String){
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){

                    Intent(this@RegisterActivity, CommonActivity::class.java).also { intent ->
                        intent.putExtra("registerName", name)
                        intent.putExtra("registerSurname", surname)
                        intent.putExtra("registerGender", gender)
                        intent.putExtra("registerAge", age)
                        intent.putExtra("registerEmail", email)
                        intent.putExtra("registerPassword", password)
                        intent.putExtra("registerMbti", registerMbti)
                        startActivity(intent)
                    }
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Email used before.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@RegisterActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}