package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainHousesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainHousesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHousesBinding.inflate(layoutInflater)
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

        // Örnek houseMap (gerçek uygulamada veritabanından gelecek)
        val houseMap = mapOf(
            "1" to House("1", R.drawable.room1, "Istanbul, Atasehir", 4, 3, 3850),
            "2" to House("2", R.drawable.room2, "Istanbul, Kadikoy", 3, 1, 6800),
            "3" to House("3", R.drawable.room3, "Izmir, Bornova", 2, 2, 4200)
        )
        val houseList = houseMap.values.toList()
        recyclerView.adapter = HouseAdapter(houseList) { house ->
            val intent = Intent(this, RoomInspectActivity::class.java)
            intent.putExtra("house", house)
            startActivity(intent)
        }
    }
    fun open_house(view: View) {
        val intent = Intent(this, RoomInspectActivity::class.java)
        startActivity(intent)
    }
    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}