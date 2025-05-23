package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMainHousesBinding
import com.google.firebase.auth.FirebaseAuth

class MainHousesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainHousesBinding
    private lateinit var mAuth:FirebaseAuth

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

        // --- RecyclerView ve HouseAdapter entegrasyonu ---
        val recyclerView = binding.recyclerViewHouses
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Örnek userList (userType = "home" olanlar)
        val userList = listOf(
            UserData(id = "1", name = "Ev Sahibi 1", userType = "home", location = "Istanbul, Atasehir", totalHousemates = 4, currentHousemates = 3, rentPerPerson = 3850, photos = listOf()),
            UserData(id = "2", name = "Ev Sahibi 2", userType = "home", location = "Istanbul, Kadikoy", totalHousemates = 3, currentHousemates = 1, rentPerPerson = 6800, photos = listOf()),
            UserData(id = "3", name = "Ev Sahibi 3", userType = "home", location = "Izmir, Bornova", totalHousemates = 2, currentHousemates = 2, rentPerPerson = 4200, photos = listOf())
        )
        recyclerView.adapter = HouseAdapter(userList) { user ->
            val intent = Intent(this, RoomInspectActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}