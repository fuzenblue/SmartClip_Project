package com.example.smartclip.data.model

data class TriggerEvent(
    val type: String, // "FLICKER", "VOC", "AUDIO"
    val value: Float,
    val timestamp: Long = System.currentTimeMillis()
)
