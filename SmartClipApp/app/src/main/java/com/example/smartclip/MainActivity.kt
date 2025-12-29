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
    private lateinit var cardAlert: View
    private lateinit var btnFAB: Button
    private lateinit var btnDemoTrigger: Button
    
    // Modal Elements
    private lateinit var btnSaveRecord: Button
    private lateinit var seekBarPain: android.widget.SeekBar
    private lateinit var tvPainLevel: TextView
    
    // Sync Elements
    private lateinit var tvSyncStatus: TextView

    // Insight Elements
    private lateinit var btnReset: Button
    
    // BLE Logic
    private val pspPredictor = PSPPredictor()
    private val bleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == "com.example.smartclip.BLE_TRIGGER_EVENT") {
                     // In real app, check values. For this request, we map loosely.
                     val flicker = it.getFloatExtra("FLICKER_INDEX", 0f)
                     if (flicker > 0.1f) {
                         triggerAlertState()
                     }
                } else {
                     // Mock Logic
                     val flicker = it.getFloatExtra("FLICKER_INDEX", 0f)
                     if (flicker > 0.1f) {
                         triggerAlertState()
                     }
                }
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
        
        btnSaveRecord = findViewById(R.id.btnSaveRecord)
        seekBarPain = findViewById(R.id.seekBarPain)
        tvPainLevel = findViewById(R.id.tvPainLevel)
        
        tvSyncStatus = findViewById(R.id.tvSyncStatus)
        btnReset = findViewById(R.id.btnReset)

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
        
        progressRisk.progress = 64
        // Use the custom red drawable by default as per image design
        // progressRisk.progressDrawable = getDrawable(R.drawable.gauge_progress_red) // Already set in XML
        tvGaugeValue.text = "64%"
        tvGaugeValue.setTextColor(getColor(R.color.design_text_dark))
        
        layoutDashboard.setBackgroundColor(getColor(R.color.design_bg))
    }

    // STAGE 2: Trigger Event
    private fun triggerAlertState() {
        // For the new design, "Trigger" might just mean updating the numbers or showing the overlay.
        // Let's make the numbers scary.
        
        progressRisk.progress = 95
        tvGaugeValue.text = "95%"
        
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
        layoutInsights.visibility = View.VISIBLE
        
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
}
