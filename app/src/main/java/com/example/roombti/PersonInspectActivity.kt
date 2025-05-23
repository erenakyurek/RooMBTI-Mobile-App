package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PersonInspectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_inspect)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Tıklanan kullanıcıyı intent ile al ---
        val user = intent.getSerializableExtra("user") as? UserData
        user?.let {
            // İsim
            findViewById<TextView>(R.id.persond_inspect_name).text = it.name
            // Yaş
            findViewById<TextView>(R.id.person_inspect_age).text = if (it.age != null) "${it.age} years old" else "-"
            // MBTI
            findViewById<TextView>(R.id.person_inspect_mbti).text = it.mbti ?: "-"
            // Üniversite
            findViewById<TextView>(R.id.uni_info).text = ""
            // Lokasyon
            findViewById<TextView>(R.id.istanbul_at).text = it.location ?: "-"
            // Evcil hayvan
            findViewById<TextView>(R.id.has_no_pet).text = if (it.allowPets == true) "Has pet" else "Has no pet"
            // Sigara
            findViewById<TextView>(R.id.smokes).text = if (it.allowSmoking == true) "Smokes" else "Doesn't smoke"
            // Cinsiyet
            findViewById<TextView>(R.id.male).text = if (it.gender == "male") "Male" else "Female"
            // Cinsiyet ikonunu değiştir
            findViewById<View>(R.id.male_icon).setBackgroundResource(if (it.gender == "male") R.drawable.male_icon_black else R.drawable.female_icon)
        }
        // Telefon numarası gösterilmeyecek, isterseniz ilgili TextView'i gizleyebilirsiniz:
        findViewById<TextView>(R.id.person_inspect_phone_no)?.visibility = View.GONE
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }
}