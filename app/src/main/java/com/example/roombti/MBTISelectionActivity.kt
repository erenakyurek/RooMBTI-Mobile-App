package com.example.roombti

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.roombti.databinding.ActivityMbtiselectionBinding
import com.example.roombti.databinding.ActivityRegisterBinding

class MBTISelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMbtiselectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMbtiselectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val testText = binding.mbtiTestLinkText.text.toString()
        val spannableString = SpannableString(testText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.16personalities.com/tr/%C3%BCcretsiz-ki%C5%9Filik-testi"))
                startActivity(intent)
            }
        }
        
        spannableString.setSpan(
            clickableSpan,
            0,
            testText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        
        binding.mbtiTestLinkText.apply {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
        }

        binding.mbtiNextButton.setOnClickListener {
            val registerMbti = binding.mbtiDropdownInput.selectedItem?.toString().orEmpty()
            if (registerMbti.isNotEmpty() && registerMbti != "Select") {
                val intent = Intent(this, RegisterActivity::class.java).putExtra("registerMbti", registerMbti)
                startActivity(intent)
            } else {
                Toast.makeText(this@MBTISelectionActivity, "Please select your MBTI type.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}