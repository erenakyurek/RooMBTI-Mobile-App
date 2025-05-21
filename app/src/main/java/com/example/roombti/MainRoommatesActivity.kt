package com.example.roombti

import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombti.databinding.ActivityMainRoommatesBinding

class MainRoommatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainRoommatesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRoommatesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- RecyclerView ve Adapter entegrasyonu ---
        val recyclerView = binding.recyclerViewRoommates
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Örnek userMap (gerçek uygulamada veritabanından gelecek)
        val userMap = mapOf(
            "1" to UserData(id = "1", name = "Tuba Selen A.", userType = "roommate", mbti = "ESTJ", gender = "female", age = 22, location = "Istanbul, Ataşehir", allowPets = false, allowSmoking = false),
            "2" to UserData(id = "2", name = "Eren Akyürek", userType = "roommate", mbti = "INTP", gender = "male", age = 23, location = "Istanbul, Beşiktaş", allowPets = true, allowSmoking = false),
            "3" to UserData(id = "3", name = "Can Sarper D.", userType = "roommate", mbti = "ENTJ", gender = "male", age = 24, location = "Ankara, Çankaya", allowPets = false, allowSmoking = true),
            "6" to UserData(id = "6", name = "Hasan G.", userType = "roommate", mbti = "ISFJ", gender = "male", age = 25, location = "Ankara, Sıhhiye", allowPets = false, allowSmoking = false),
            "7" to UserData(id = "7", name = "Enes Deniz", userType = "roommate", mbti = "ISFP", gender = "male", age = 23, location = "Izmir, Bornova", allowPets = true, allowSmoking = true),
            "8" to UserData(id = "8", name = "Doğu Baha A.", userType = "roommate", mbti = "ENFJ", gender = "male", age = 22, location = "Istanbul, Ataşehir", allowPets = false, allowSmoking = false)
        )

        val filteredList = userMap.values.filter { it.userType == "roommate" }
        recyclerView.adapter = RoommateAdapter(filteredList) { user ->
            val intent = Intent(this, PersonInspectActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
    fun open_person(view: View) {
        val intent = Intent(this, PersonInspectActivity::class.java)
        startActivity(intent)
    }
    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}