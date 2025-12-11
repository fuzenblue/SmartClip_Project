package com.example.smartclip.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smartclip.data.model.TriggerEvent
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class MockBleService : Service() {

    private val timer = Timer()

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
        // Schedule task every 5 seconds
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val event = generateRandomEvent()
                broadcastEvent(event)
            }
        }, 0, 5000)
    }

    private fun generateRandomEvent(): TriggerEvent {
        // Randomly choose between Flicker (Type 1) or VOC (Type 2) logic simulation
        return if (Random.nextBoolean()) {
            TriggerEvent(type = "FLICKER", value = Random.nextInt(100, 150).toFloat()) // 100-150Hz
        } else {
            TriggerEvent(type = "VOC", value = Random.nextInt(0, 500).toFloat()) // 0-500 Gas Resistance
        }
    }

    private fun broadcastEvent(event: TriggerEvent) {
        val intent = Intent("com.example.smartclip.BLE_EVENT")
        intent.putExtra("TYPE", event.type)
        intent.putExtra("VALUE", event.value)
        intent.putExtra("TIMESTAMP", event.timestamp)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
