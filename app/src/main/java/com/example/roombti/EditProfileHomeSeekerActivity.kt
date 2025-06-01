package com.example.roombti

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.app.AlertDialog
import android.widget.Toast
import android.text.InputType
import com.google.firebase.database.FirebaseDatabase
import android.widget.Spinner
import android.widget.ArrayAdapter

class EditProfileHomeSeekerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile_home_seeker)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<LinearLayout>(R.id.change_password_layout).setOnClickListener {
            showChangePasswordDialog()
        }
        findViewById<LinearLayout>(R.id.change_email_layout).setOnClickListener {
            showChangeEmailDialog()
        }
        findViewById<LinearLayout>(R.id.change_mbti_layout).setOnClickListener {
            showChangeMbtiDialog()
        }
        findViewById<LinearLayout>(R.id.change_university_layout).setOnClickListener {
            showChangeUniversityDialog()
        }
        findViewById<LinearLayout>(R.id.change_max_budget_layout).setOnClickListener {
            showChangeMaxBudgetDialog()
        }
        findViewById<LinearLayout>(R.id.change_min_budget_layout).setOnClickListener {
            showChangeMinBudgetDialog()
        }
        findViewById<LinearLayout>(R.id.change_smoking_opt_layout).setOnClickListener {
            showChangeSmokingDialog()
        }
        findViewById<LinearLayout>(R.id.change_pet_opt_layout).setOnClickListener {
            showChangePetDialog()
        }
        findViewById<LinearLayout>(R.id.change_max_housemates_layout).setOnClickListener {
            showChangeMaxHousematesDialog()
        }
        findViewById<LinearLayout>(R.id.change_min_housemates_layout).setOnClickListener {
            showChangeMinHousematesDialog()
        }
    }

    private fun showChangePasswordDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        AlertDialog.Builder(this)
            .setTitle("Change Password")
            .setMessage("Enter your new password:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newPassword = input.text.toString()
                if (newPassword.isNotEmpty()) {
                    updatePasswordInDatabase(newPassword)
                } else {
                    Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updatePasswordInDatabase(newPassword: String) {
        val userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        val userRef = FirebaseDatabase.getInstance().reference.child("user").child(userId)
        userRef.child("password").setValue(newPassword)
            .addOnSuccessListener {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update password: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showChangeEmailDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

        AlertDialog.Builder(this)
            .setTitle("Change Email")
            .setMessage("Enter your new email:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newEmail = input.text.toString()
                if (newEmail.isNotEmpty()) {
                    updateEmailInDatabase(newEmail)
                } else {
                    Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateEmailInDatabase(newEmail: String) {
        val userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        val userRef = FirebaseDatabase.getInstance().reference.child("user").child(userId)
        userRef.child("email").setValue(newEmail)
            .addOnSuccessListener {
                Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update email: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showChangeMbtiDialog() {
        val mbtiTypes = arrayOf(
            "INTJ", "INTP", "ENTJ", "ENTP",
            "INFJ", "INFP", "ENFJ", "ENFP",
            "ISTJ", "ISFJ", "ESTJ", "ESFJ",
            "ISTP", "ISFP", "ESTP", "ESFP"
        )
        val spinner = Spinner(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mbtiTypes)
        spinner.adapter = adapter

        AlertDialog.Builder(this)
            .setTitle("Change MBTI")
            .setView(spinner)
            .setPositiveButton("Change") { _, _ ->
                val newMbti = spinner.selectedItem.toString()
                updateMbtiInDatabase(newMbti)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateMbtiInDatabase(newMbti: String) {
        val userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        val userRef = FirebaseDatabase.getInstance().reference.child("user").child(userId)
        userRef.child("mbti").setValue(newMbti)
            .addOnSuccessListener {
                Toast.makeText(this, "MBTI updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update MBTI: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showChangeUniversityDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle("Change University")
            .setMessage("Enter your new university:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newUniversity = input.text.toString()
                if (newUniversity.isNotEmpty()) {
                    updateFieldInDatabase("university", newUniversity)
                } else {
                    Toast.makeText(this, "University cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeMaxBudgetDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Change Max. Budget")
            .setMessage("Enter your new max budget:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newValue = input.text.toString()
                if (newValue.isNotEmpty()) {
                    updateFieldInDatabase("maxBudget", newValue.toInt())
                } else {
                    Toast.makeText(this, "Max budget cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeMinBudgetDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Change Min. Budget")
            .setMessage("Enter your new min budget:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newValue = input.text.toString()
                if (newValue.isNotEmpty()) {
                    updateFieldInDatabase("minBudget", newValue.toInt())
                } else {
                    Toast.makeText(this, "Min budget cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeMaxHousematesDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Change Max. Housemates")
            .setMessage("Enter your new max housemates:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newValue = input.text.toString()
                if (newValue.isNotEmpty()) {
                    updateFieldInDatabase("maxHousemates", newValue.toInt())
                } else {
                    Toast.makeText(this, "Max housemates cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeMinHousematesDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("Change Min. Housemates")
            .setMessage("Enter your new min housemates:")
            .setView(input)
            .setPositiveButton("Change") { _, _ ->
                val newValue = input.text.toString()
                if (newValue.isNotEmpty()) {
                    updateFieldInDatabase("minHousemates", newValue.toInt())
                } else {
                    Toast.makeText(this, "Min housemates cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showChangeSmokingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Change Smoking Option")
            .setMessage("Allow smoking?")
            .setPositiveButton("Yes") { _, _ ->
                updateFieldInDatabase("allowSmoking", true)
            }
            .setNegativeButton("No") { _, _ ->
                updateFieldInDatabase("allowSmoking", false)
            }
            .show()
    }

    private fun showChangePetDialog() {
        AlertDialog.Builder(this)
            .setTitle("Change Pet Option")
            .setMessage("Allow pets?")
            .setPositiveButton("Yes") { _, _ ->
                updateFieldInDatabase("allowPets", true)
            }
            .setNegativeButton("No") { _, _ ->
                updateFieldInDatabase("allowPets", false)
            }
            .show()
    }

    private fun updateFieldInDatabase(field: String, value: Any) {
        val userId = intent.getStringExtra("USER_ID")
        if (userId == null) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show()
            return
        }
        val userRef = FirebaseDatabase.getInstance().reference.child("user").child(userId)
        userRef.child(field).setValue(value)
            .addOnSuccessListener {
                Toast.makeText(this, "$field updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update $field: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}