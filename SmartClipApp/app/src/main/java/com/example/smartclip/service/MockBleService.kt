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
                val randomFlicker = Random.nextFloat() * 5f
                val randomVoc = if (Random.nextBoolean()) 20.0f else 0.5f

                val vector = FeatureVector(
                    timestamp = System.currentTimeMillis(),
                    pressureSlope = -0.1f,
                    vocSlope = randomVoc,
                    audio400HzEnergy = Random.nextFloat() * 10,
                    flickerIndex = randomFlicker, 
                    heatStressIndex = 28.0f
                )
                broadcastEvent(vector)
            }
        }, 0, 500)
    }

    private fun broadcastEvent(vector: FeatureVector) {
        val intent = Intent("com.example.smartclip.BLE_EVENT")
        // Serialize individually or as Parcelable/Serializable if implemented.
        // For simplicity matching typical Intent usage with primitives:
        intent.putExtra("TIMESTAMP", vector.timestamp)
        intent.putExtra("PRESSURE_SLOPE", vector.pressureSlope)
        intent.putExtra("VOC_SLOPE", vector.vocSlope)
        intent.putExtra("AUDIO_ENERGY", vector.audio400HzEnergy)
        intent.putExtra("FLICKER_INDEX", vector.flickerIndex)
        intent.putExtra("HEAT_INDEX", vector.heatStressIndex)
        
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
