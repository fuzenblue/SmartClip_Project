package com.example.smartclip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smartclip.service.MockBleService
import com.example.smartclip.ai.PSPPredictor
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvRiskScore: TextView
    private lateinit var tvRiskValue: TextView
    private lateinit var tvLogConsole: TextView
    private lateinit var btnLogSymptom: Button
    
    // AI Predictor Stub
    private val pspPredictor = PSPPredictor()

    private val bleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                // Handle Real BLE Trigger Events
                if (it.action == "com.example.smartclip.BLE_TRIGGER_EVENT") {
                    val type = it.getIntExtra("TYPE", 0)
                    val severity = it.getIntExtra("SEVERITY", 0)
                    
                    val typeName = when(type) {
                        1 -> "FLICKER_TRIG"
                        2 -> "AUDIO_TRIG"
                        else -> "UNKNOWN_TRIG"
                    }
                    
                    updateDashboard(typeName, severity.toFloat(), System.currentTimeMillis())
                }
                // Fallback for Mock Data (Optional, if we keep MockService running)
                else {
                    val type = it.getStringExtra("TYPE") ?: "DATA"
                    // ... old logic if needed, but let's prioritize triggers
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Init UI
        tvRiskScore = findViewById(R.id.tvRiskScore)
        tvRiskValue = findViewById(R.id.tvRiskValue)
        tvLogConsole = findViewById(R.id.tvLogConsole)
        btnLogSymptom = findViewById(R.id.btnLogSymptom)

        tvLogConsole.text = "Waiting for BLE Triggers...\n"

        // For testing without hardware, we can validly keep MockService running
        // In production, we would scan and start RealBleService with an address
        // val serviceIntent = Intent(this, RealBleService::class.java)
        // serviceIntent.putExtra("DEVICE_ADDRESS", "XX:XX:XX:XX:XX:XX")
        // startService(serviceIntent)
        
        // Setup Button
        btnLogSymptom.setOnClickListener {
            Toast.makeText(this, "Logged symptom. AI Model Updating...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction("com.example.smartclip.BLE_TRIGGER_EVENT") // Real Config
        // filter.addAction("com.example.smartclip.BLE_EVENT")   // Mock Config (Uncomment if needed)
        LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bleReceiver)
    }

    private fun updateDashboard(type: String, value: Float, timestamp: Long) {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val timeStr = timeFormat.format(Date(timestamp))

        // Update Log
        val logEntry = "[$timeStr] $type Detected (Val: ${value.toInt()})\n"
        tvLogConsole.append(logEntry)

        // Simple Risk Logic (Simulation)
        // In real app, this logic comes from PSPPredictor.predictOutcome()
        val riskScore = if (type == "FLICKER" && value > 110) 85 else 20
        
        // Update AI Predictor (Just calling to verify JNI works)
        // val aiRisk = pspPredictor.predictOutcome(floatArrayOf(value)) 

        updateRiskUI(riskScore)
    }

    private fun updateRiskUI(score: Int) {
        tvRiskValue.text = "$score%"
        when {
            score >= 80 -> {
                tvRiskScore.text = "HIGH"
                tvRiskScore.setTextColor(Color.parseColor("#F44336")) // Red
            }
            score >= 50 -> {
                tvRiskScore.text = "MED"
                tvRiskScore.setTextColor(Color.parseColor("#FF9800")) // Orange
            }
            else -> {
                tvRiskScore.text = "LOW"
                tvRiskScore.setTextColor(Color.parseColor("#4CAF50")) // Green
            }
        }
    }
}
