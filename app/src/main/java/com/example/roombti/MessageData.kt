package com.example.roombti

import java.io.Serializable

data class MessageData(
    val message: String? = null,
    val senderID: String? = null,
) : Serializable