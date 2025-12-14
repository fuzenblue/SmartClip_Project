package com.example.smartclip.data.model

data class FeatureVector(
    val timestamp: Long,
    val pressureSlope: Float, // dP/dt
    val vocSlope: Float,
    val audio400HzEnergy: Float,
    val flickerIndex: Float,
    val heatStressIndex: Float
)
