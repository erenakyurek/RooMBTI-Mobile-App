package com.example.roombti

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityPersonInspectBinding

class PersonInspectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonInspectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonInspectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Tıklanan kullanıcıyı intent ile al ---
        val user = intent.getSerializableExtra("user") as? UserData
        user?.let {
            // İsim
            binding.persondInspectName.text = it.name
            // Yaş
            binding.personInspectAge.text = if (it.age != null) "${it.age} years old" else "-"
            // MBTI
            binding.personInspectMbti.text = it.mbti ?: "-"
            // Üniversite
            binding.uniInfo.text = ""
            // Lokasyon
            binding.istanbulAt.text = it.location ?: "-"
            // Evcil hayvan
            binding.hasNoPet.text = if (it.allowPets == true) "Has pet" else "Has no pet"
            // Sigara
            binding.smokes.text = if (it.allowSmoking == true) "Smokes" else "Doesn't smoke"
            // Cinsiyet
            binding.gender.text = if (it.gender == "Male") "Male" else "Female"
            // Cinsiyet ikonunu değiştir
            binding.maleIcon.setBackgroundResource(if (it.gender == "Male") R.drawable.male_icon_black else R.drawable.female_icon_black)
        }
        // Telefon numarası gösterilmeyecek
        binding.personInspectPhoneNo.visibility = View.GONE
    }

    fun open_menu(view: View) {
        val intent = Intent(this, MenuScreenActivity::class.java)
        startActivity(intent)
    }

    fun open_chat(view: View) {
        val intent = Intent(this, DMPageActivity::class.java)
        startActivity(intent)
    }
}