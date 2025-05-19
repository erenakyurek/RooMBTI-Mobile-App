package com.example.roombti

import java.io.Serializable

data class House(
    val id: String,
    val imageResId: Int, // drawable resource id
    val location: String,
    val capacity: Int,
    val currentPersonCount: Int,
    val price: Int // kişi başı fiyat
) : Serializable 