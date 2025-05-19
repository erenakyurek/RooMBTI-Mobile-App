package com.example.roombti

import java.io.Serializable

data class User(
    val id: String,
    val name: String,
    val mbti: String,
    val gender: String,
    val photoUrl: String?,
    val age: Int,              // Yaş
    val university: String,    // Üniversite
    val location: String,      // Lokasyon
    val hasPet: Boolean,       // Evcil hayvanı var mı
    val smokes: Boolean        // Sigara içiyor mu
) : Serializable 