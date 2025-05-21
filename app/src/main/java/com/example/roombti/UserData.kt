package com.example.roombti

import java.io.Serializable

data class UserData(
    val id: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val email: String? = null,
    val password: String? = null,
    val mbti: String? = null,
    // type of user:
    val userType: String?       = null, // "roommate" or "home"
    // roommate-seeker prefs:
    val minHousemates: Int?     = null,
    val maxHousemates: Int?     = null,
    val location: String?       = null,
    val minBudget: Int?         = null,
    val maxBudget: Int?         = null,
    val rent: Int?              = null,
    val allowSmoking: Boolean?  = null,
    val allowPets: Boolean?     = null,
    // home-lister details:
    val currentHousemates: Int? = null,
    val totalHousemates: Int?   = null,
    val rentPerPerson: Int?     = null,
    val photos: List<String>?   = null  // URLs after uploading to Storage
) : Serializable 