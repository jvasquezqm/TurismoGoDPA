package com.example.turismogodpa.data.model

import com.google.firebase.Timestamp

data class PubAddModel(
    val titulo: String,
    val description: String,
    val lugar: String,
    val time: Timestamp,
    val type: String,
    val price: Double,
    val image: String,
    val state: String
)
