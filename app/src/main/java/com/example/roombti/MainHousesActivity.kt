package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMainHousesBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainHousesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainHousesBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHousesBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize MBTI matrix
        MatchingAlgorithm.initializeMBTIMatrix(this)

        // --- RecyclerView ve HouseAdapter entegrasyonu ---
        val recyclerView = binding.recyclerViewHouses
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Get current user's matches
        val currentUserId = mAuth.currentUser?.uid
        if (currentUserId != null) {
            Log.d("MainHousesActivity", "Current User ID: $currentUserId")
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val matches = MatchingAlgorithm.getMatchingUsers(currentUserId, this@MainHousesActivity)
                    Log.d("MainHousesActivity", "Number of matches found: ${matches.size}")
                    if (matches.isEmpty()) {
                        Toast.makeText(this@MainHousesActivity, "No matches found", Toast.LENGTH_SHORT).show()
                    }
                    val matchedHouses = matches.map { it.userData }
                    recyclerView.adapter = HouseAdapter(matchedHouses) { user ->
                        val intent = Intent(this@MainHousesActivity, RoomInspectActivity::class.java)
                        intent.putExtra("user", user)
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Log.e("MainHousesActivity", "Error getting matches", e)
                    Toast.makeText(this@MainHousesActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.e("MainHousesActivity", "Current user ID is null")
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}