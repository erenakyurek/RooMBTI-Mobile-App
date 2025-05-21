package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityLoginPageBinding
import com.google.firebase.database.*

class LoginPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseDatabase  = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("users")

        binding.loginButtonBlue.setOnClickListener {
            val email = binding.loginEmailInput.text.toString().trim()
            val password = binding.loginPasswordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        Toast.makeText(this@LoginPageActivity, "Email not found.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    // there should be only one matching child
                    for (userSnap in snapshot.children) {
                        val user = userSnap.getValue(UserData::class.java)
                        if (user != null && user.password == password) {
                            // we have a successful login
                            when (user.userType) { // read the userType field
                                "houserenter" -> { startActivity(Intent(this@LoginPageActivity, MainRoommatesActivity::class.java).putExtra("USER_ID", user.id))
                                }
                                "homeseeker" -> { startActivity(Intent(this@LoginPageActivity, MainHousesActivity::class.java).putExtra("USER_ID", user.id))
                                }
                                else -> { // fallback if userType wasn’t set
                                    Toast.makeText(this@LoginPageActivity, "User type unknown—please complete registration again.", Toast.LENGTH_LONG).show()
                                }
                            }
                            finish()
                            return
                        }
                    }
                    // if we get here, email matched but password was wrong
                    Toast.makeText(this@LoginPageActivity, "Incorrect password.", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@LoginPageActivity,
                        "Database error: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    }
}