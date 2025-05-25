package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombti.databinding.ActivityMainRoommatesBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRoommatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainRoommatesBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRoommatesBinding.inflate(layoutInflater)
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

        // --- RecyclerView ve Adapter entegrasyonu ---
        val recyclerView = binding.recyclerViewRoommates
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get current user's matches
        val currentUserId = mAuth.currentUser?.uid
        if (currentUserId != null) {
            CoroutineScope(Dispatchers.Main).launch {
                val matches = MatchingAlgorithm.getMatchingUsers(currentUserId, this@MainRoommatesActivity)
                val matchedUsers = matches.map { it.userData }
                recyclerView.adapter = RoommateAdapter(matchedUsers) { user ->
                    val intent = Intent(this@MainRoommatesActivity, PersonInspectActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                }
            }
        }
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}
