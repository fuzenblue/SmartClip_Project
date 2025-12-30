package com.example.smartclip

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smartclip.service.MockBleService
import com.example.smartclip.ai.PSPPredictor
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // UI Components
    private lateinit var layoutDashboard: View
    private lateinit var layoutSymptomModal: View
    private lateinit var layoutSyncing: View
    private lateinit var layoutInsights: View
    
    // Dashboard Elements
    private lateinit var tvEnvironmentStatus: TextView
    private lateinit var viewStatusIndicator: View
    private lateinit var progressRisk: android.widget.ProgressBar
    private lateinit var tvGaugeValue: TextView
    private lateinit var tvSyncStatus: TextView
    private lateinit var btnReset: Button
    
    // Navigation
    private lateinit var navHome: View
    private lateinit var navInsight: View
    private lateinit var navProfile: View
    private lateinit var btnDemoTrigger: Button
    private lateinit var tvTempVal: TextView
    private lateinit var tvSoundVal: TextView
    private lateinit var progressSound: android.widget.ProgressBar
    private lateinit var btnConnect: Button
    private lateinit var cardAlert: View
    private lateinit var btnFAB: Button
    private lateinit var btnUpdateModel: Button
    private lateinit var layoutConnectedStatus: View
    private var isConnected = false
    private var lastAlertTime: Long = 0
    private val ALERT_COOLDOWN_SUBSEQUENT = 5 * 60 * 1000L // 5 minutes
    
    // Modal Elements
    private lateinit var btnSaveRecord: Button
    private lateinit var seekBarPain: android.widget.SeekBar
    private lateinit var tvPainLevel: TextView
    

    // BLE Logic
    private val pspPredictor = PSPPredictor()
    private var isAlertShowing = false

    private val bleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!isConnected) return // Step 2: Only show data flow if connected

            intent?.let {
                val flicker = it.getFloatExtra("FLICKER_INDEX", 0f)
                val temp = it.getFloatExtra("TEMPERATURE", 0f)
                
                if (temp > 0) {
                     tvTempVal.text = String.format("%.1f °C", temp)
                     // Smooth Animation
                     val targetProgress = (flicker * 20).toInt().coerceIn(0, 100)
                     android.animation.ObjectAnimator.ofInt(progressRisk, "progress", targetProgress)
                        .setDuration(400)
                        .start()
                     tvGaugeValue.text = "$targetProgress%"
                }
                
                // Check for ABNORMAL values (User Request)
                val currentTime = System.currentTimeMillis()
                val isFirstAlert = lastAlertTime == 0L
                // Allow first alert immediately (or shortly after start), subsequent after 5 mins
                val canShowAlert = isFirstAlert || (currentTime - lastAlertTime > ALERT_COOLDOWN_SUBSEQUENT)

                if (flicker > 4.0f && canShowAlert) {
                    lastAlertTime = currentTime
                    showAbnormalDialog(flicker)
                } 
                
                // Normal "Risk" Logic - ONLY show overlay if risk is actually high
                // Previous threshold 1.0f was too low (normal drift is 0.5-3.0).
                // Now setting to 3.5f so it only appears during spikes
                if (flicker > 3.5f) { 
                    triggerAlertState()
                } else {
                    // Start fading it out if we return to normal?
                    // For now, let's strictly hide it if we are safe
                    cardAlert.visibility = View.GONE
                }
                
                // Sound Level Logic
                val soundDb = it.getFloatExtra("SOUND_DB", 50f)
                val roundedDb = soundDb.toInt()
                tvSoundVal.text = "$roundedDb dB"
                progressSound.progress = roundedDb
                
                // Dynamic Color for Progress Bar
                val color = when {
                    roundedDb < 60 -> Color.parseColor("#4CAF50") // Green
                    roundedDb < 75 -> Color.parseColor("#FFC107") // Yellow
                    roundedDb < 85 -> Color.parseColor("#FF9800") // Orange
                    else -> Color.parseColor("#F44336") // Red
                }
                progressSound.progressTintList = android.content.res.ColorStateList.valueOf(color)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind Views
        layoutDashboard = findViewById(R.id.layoutDashboard)
        layoutSymptomModal = findViewById(R.id.layoutSymptomModal)
        layoutSyncing = findViewById(R.id.layoutSyncing)
        layoutInsights = findViewById(R.id.layoutInsights)
        
        // tvEnvironmentStatus = findViewById(R.id.tvEnvironmentStatus)
        // viewStatusIndicator = findViewById(R.id.viewStatusIndicator)
        progressRisk = findViewById(R.id.progressRisk)
        tvGaugeValue = findViewById(R.id.tvGaugeValue)
        cardAlert = findViewById(R.id.cardAlert)
        btnFAB = findViewById(R.id.btnFAB)
        btnDemoTrigger = findViewById(R.id.btnDemoTrigger)
        tvTempVal = findViewById(R.id.tvTempVal)
        tvSoundVal = findViewById(R.id.tvSoundVal)
        progressSound = findViewById(R.id.progressSound)
        
        btnConnect = findViewById(R.id.btnConnect)
        btnUpdateModel = findViewById(R.id.btnUpdateModel)
        layoutConnectedStatus = findViewById(R.id.layoutConnectedStatus)
        
        btnSaveRecord = findViewById(R.id.btnSaveRecord)
        seekBarPain = findViewById(R.id.seekBarPain)
        tvPainLevel = findViewById(R.id.tvPainLevel)
        
        tvSyncStatus = findViewById(R.id.tvSyncStatus)
        btnReset = findViewById(R.id.btnReset)
        
        navHome = findViewById(R.id.navHome)
        navInsight = findViewById(R.id.navInsight)
        navProfile = findViewById(R.id.navProfile)

        // Initial State: Normal
        setNormalState()

        // Interaction Listeners (The "User Logic")
        
        // Stage 1 -> 2: Simulation Button (or BLE)
        btnDemoTrigger.setOnClickListener {
            triggerAlertState()
        }

        // Stage 2 -> 3: User says "I'm Unwell"
        btnFAB.setOnClickListener {
            openSymptomLog()
        }

        // Stage 3 -> 4: Save Record
        btnSaveRecord.setOnClickListener {
            startCloudProcessing()
        }
        
        // Stage 3 Input Logic
        seekBarPain.setOnSeekBarChangeListener(object: android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: android.widget.SeekBar?, v: Int, p2: Boolean) {
                tvPainLevel.text = "Pain Level: $v"
            }
            override fun onStartTrackingTouch(p0: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(p0: android.widget.SeekBar?) {}
        })

        // Stage 5 -> 1: Reset
        btnReset.setOnClickListener {
            setNormalState()
        }
        // Stage 1: Connection Toggle
        btnConnect.setOnClickListener {
            toggleConnection()
        }
        
        // Stage 4: Check for Updates
        btnUpdateModel.setOnClickListener {
            checkForUpdates()
        }
        
        // Navigation Logic
        navHome.setOnClickListener {
            setNormalState() // Go back to dashboard
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }
        
        navInsight.setOnClickListener {
            showInsights()
            Toast.makeText(this, "Insights", Toast.LENGTH_SHORT).show()
        }
        
        navProfile.setOnClickListener {
             Toast.makeText(this, "Profile (Coming Soon)", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun toggleConnection() {
        isConnected = !isConnected
        if (isConnected) {
            // "Connect" -> Connected
            btnConnect.visibility = View.GONE
            layoutConnectedStatus.visibility = View.VISIBLE
            
            // Start Data Flow (Step 2)
            val intent = Intent(this, MockBleService::class.java)
            startService(intent)
            Toast.makeText(this, "Smart Clip Connected", Toast.LENGTH_SHORT).show()
        } else {
            // Disconnect logic if we had a disconnect button
            val intent = Intent(this, MockBleService::class.java)
            stopService(intent)
        }
    }
    
    private fun checkForUpdates() {
        btnUpdateModel.text = "Checking..."
        btnUpdateModel.isEnabled = false
        
        // Simulate delay
        btnUpdateModel.postDelayed({
            btnUpdateModel.text = "Check for Model Updates"
            btnUpdateModel.isEnabled = true
            Toast.makeText(this, "Model Updated Successfully (v1.2)", Toast.LENGTH_LONG).show()
        }, 2000)
    }

    // STAGE 1: Normal State
    private fun setNormalState() {
        // Reset Visibility
        layoutDashboard.visibility = View.VISIBLE
        layoutSymptomModal.visibility = View.GONE
        layoutSyncing.visibility = View.GONE
        layoutInsights.visibility = View.GONE
        cardAlert.visibility = View.GONE 

        // Set UI values (New Design defaults)
        // Set UI values (New Design defaults)
        // tvEnvironmentStatus.text = "Smart Clip Connected" 
        // viewStatusIndicator.background.setTint(getColor(R.color.status_safe))
        
        progressRisk.progress = 0
        // Use the custom red drawable by default as per image design
        // progressRisk.progressDrawable = getDrawable(R.drawable.gauge_progress_red) // Already set in XML
        tvGaugeValue.text = "0%"
        tvGaugeValue.setTextColor(getColor(R.color.design_text_dark))
        
        layoutDashboard.setBackgroundColor(getColor(R.color.design_bg))
    }

    // STAGE 2: Trigger Event
    private fun triggerAlertState() {
        // For the new design, "Trigger" might just mean updating the numbers or showing the overlay.
        // Let's make the numbers scary.
        
        progressRisk.progress = 75
        tvGaugeValue.text = "75%"
        
        // Show the alert overlay just in case, or just update values?
        // User flow requires Stage 2 -> 3 via "I'm Unwell".
        // The "I Feel Pain" button is always visible in the new design, so user can click it anytime.
        // We will pop the overlay to keep the "Event" feel.
        
        cardAlert.visibility = View.VISIBLE
    }

    // STAGE 3: Feedback Loop
    private fun openSymptomLog() {
        // Pop up the modal
        layoutSymptomModal.visibility = View.VISIBLE
        layoutSymptomModal.alpha = 0f
        layoutSymptomModal.animate().alpha(1f).setDuration(300).start()
    }

    // STAGE 4: Cloud Processing
    private fun startCloudProcessing() {
        layoutSymptomModal.visibility = View.GONE
        layoutSyncing.visibility = View.VISIBLE
        
        Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
        
        // Simulate flow: Uploading -> Personalizing -> Updated
        tvSyncStatus.text = "Uploading Data to Huawei ModelArts..."
        
        btnSaveRecord.postDelayed({
            tvSyncStatus.text = "Personalizing Model..."
        }, 1500)
        
        btnSaveRecord.postDelayed({
             tvSyncStatus.text = "Model Updated! v1.2"
        }, 3000)
        
        btnSaveRecord.postDelayed({
            showInsights()
        }, 4000)
    }

    // STAGE 5: Insight & Result
    private fun showInsights() {
        layoutSyncing.visibility = View.GONE
        layoutDashboard.visibility = View.GONE // Hide Dashboard
        layoutInsights.visibility = View.VISIBLE // Show Insights
        
        // In real app, we'd load the graph data here
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter()
        filter.addAction("com.example.smartclip.BLE_TRIGGER_EVENT")
        filter.addAction("com.example.smartclip.BLE_EVENT")
        LocalBroadcastManager.getInstance(this).registerReceiver(bleReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bleReceiver)
    }

    private fun showAbnormalDialog(value: Float) {
        if (isAlertShowing) return

        isAlertShowing = true
        val formatted = String.format("%.2f", value)
        
        AlertDialog.Builder(this)
            .setTitle("⚠️ Abnormal Alert")
            .setMessage("Abnormal Sensor Value Detected!\nFlicker Index: $formatted\n\nPlease check your environment.")
            .setCancelable(false)
            .setPositiveButton("Dismiss") { dialog, _ ->
                isAlertShowing = false
                dialog.dismiss()
                // Optional: Reset state logic if needed
                setNormalState()
            }
            .setNegativeButton("Log Issue") { dialog, _ ->
                isAlertShowing = false
                dialog.dismiss()
                openSymptomLog()
            }
            .show()
    }
}
