package com.example.smartclip.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smartclip.data.model.FeatureVector
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MockBleService : Service() {

    private val timer = Timer()
    // Simulated state for pressure to show dropping trend
    private var currentPressure = 1013.0f
    private var currentFlicker = 1.0f
    private var currentSoundDb = 50.0f
    private var ticks = 0

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startMockingData()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun startMockingData() {
        // Schedule task every 500ms for faster updates
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // Simulate pressure dropping occasionally
                if (Random.nextBoolean()) currentPressure -= 0.1f
                
                // FORCE DYNAMIC VALUES FOR DEMO
                // Smooth Random Walk logic to prevent 'shaking' numbers
                // Drift slightly: +/- 0.1
                val drift = (Random.nextFloat() - 0.5f) * 0.2f
                currentFlicker = (currentFlicker + drift).coerceIn(0.5f, 3.0f)
                
                // Sound Random Walk (Drift +/- 2dB)
                val soundDrift = (Random.nextFloat() - 0.5f) * 4.0f
                currentSoundDb = (currentSoundDb + soundDrift).coerceIn(40.0f, 90.0f)
                
                var finalFlicker = currentFlicker

                // Deterministic Alert Scheduling
                // Tick 0 -> 40 (20s): First Spike guaranteed (User Request).
                // Then repeat every 600 ticks (5 mins).
                ticks++
                
                var isSpike = false
                if (ticks == 40) { // First alert at exactly 20 seconds
                    isSpike = true
                } else if (ticks > 40 && (ticks - 40) % 600 == 0) {
                    // Subsequent alerts every 5 minutes (300s / 0.5s = 600 ticks)
                    isSpike = true
                }
                
                if (isSpike) {
                    finalFlicker += 6.0f // Huge Spike to ensure it crosses threshold (> 4.0)
                }
                val randomVoc = if (Random.nextBoolean()) 20.0f else 0.5f

                val vector = FeatureVector(
                    timestamp = System.currentTimeMillis(),
                    pressureSlope = -0.1f,
                    vocSlope = randomVoc,
                    audio400HzEnergy = Random.nextFloat() * 10,
                    flickerIndex = finalFlicker, 
                    heatStressIndex = 28.0f + (Random.nextFloat() * 2f) // Vary slightly
                )
                broadcastEvent(vector, currentSoundDb)
            }
        }, 0, 500)
    }

    private fun broadcastEvent(vector: FeatureVector, soundDb: Float) {
        val intent = Intent("com.example.smartclip.BLE_EVENT")
        // Serialize individually or as Parcelable/Serializable if implemented.
        // For simplicity matching typical Intent usage with primitives:
        intent.putExtra("TIMESTAMP", vector.timestamp)
        intent.putExtra("PRESSURE_SLOPE", vector.pressureSlope)
        intent.putExtra("VOC_SLOPE", vector.vocSlope)
        intent.putExtra("AUDIO_ENERGY", vector.audio400HzEnergy)
        intent.putExtra("FLICKER_INDEX", vector.flickerIndex)
        intent.putExtra("FLICKER_INDEX", vector.flickerIndex)
        intent.putExtra("HEAT_INDEX", vector.heatStressIndex)
        intent.putExtra("TEMPERATURE", vector.heatStressIndex) // Use Heat Index as Temp for now
        intent.putExtra("SOUND_DB", soundDb)
        
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
