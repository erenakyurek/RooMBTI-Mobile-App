package com.example.roombti

import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Örnek userMap (gerçek uygulamada veritabanından gelecek)
        val userMap = mapOf(
            "1" to User("1", "Tuba Selen A.", "ESTJ", "female", null, 22, "Yeditepe University", "Istanbul, Ataşehir", false, false),
            "2" to User("2", "Eren Akyürek", "INTP", "male", null, 23, "Boğaziçi University", "Istanbul, Beşiktaş", true, false),
            "3" to User("3", "Can Sarper D.", "ENTJ", "male", null, 24, "ODTÜ", "Ankara, Çankaya", false, true),
            "4" to User("4", "Belma Soysal", "ESTP", "female", null, 21, "İTÜ", "Istanbul, Maslak", true, true),
            "5" to User("5", "Beril Güler", "INFJ", "female", null, 22, "Yeditepe University", "Istanbul, Ataşehir", false, false),
            "6" to User("6", "Hasan G.", "ISFJ", "male", null, 25, "Hacettepe University", "Ankara, Sıhhiye", false, false),
            "7" to User("7", "Enes Deniz", "ISFP", "male", null, 23, "Ege University", "Izmir, Bornova", true, true),
            "8" to User("8", "Doğu Baha A.", "ENFJ", "male", null, 22, "Yeditepe University", "Istanbul, Ataşehir", false, false)
        )
        val userList = userMap.values.toList()
        recyclerView.adapter = RoommateAdapter(userList) { user ->
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