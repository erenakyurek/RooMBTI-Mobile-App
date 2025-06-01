package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMenuScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MenuScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuScreenBinding
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "MenuScreenActivity"

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

        binding.editProfileContainer.setOnClickListener {
            val currentUser = mAuth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                Log.d(TAG, "Fetching user data for ID: $userId")
                
                val databaseReference = FirebaseDatabase.getInstance().reference.child("user")
                databaseReference.child(userId).get()
                    .addOnSuccessListener { snapshot ->
                        if (!snapshot.exists()) {
                            Log.e(TAG, "User data not found in database")
                            Toast.makeText(this, "User not found in database", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }
                        
                        val userType = snapshot.child("userType").getValue(String::class.java)
                        Log.d(TAG, "User type: $userType")
                        
                        try {
                            when (userType) {
                                "houserenter" -> {
                                    Log.d(TAG, "Starting EditProfileHouseRenterActivity")
                                    val intent = Intent(this, EditProfileHouseRenterActivity::class.java)
                                    intent.putExtra("USER_ID", userId)
                                    startActivity(intent)
                                }
                                "homeseeker" -> {
                                    Log.d(TAG, "Starting EditProfileHomeSeekerActivity")
                                    val intent = Intent(this, EditProfileHomeSeekerActivity::class.java)
                                    intent.putExtra("USER_ID", userId)
                                    startActivity(intent)
                                }
                                else -> {
                                    Log.e(TAG, "Unknown user type: $userType")
                                    Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error starting edit profile activity", e)
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error fetching user data", e)
                        Toast.makeText(this, "Error fetching user data: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Log.e(TAG, "No current user found")
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}