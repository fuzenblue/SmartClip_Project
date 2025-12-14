package com.example.smartclip.service

import android.app.Service
import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.util.Log
import java.util.UUID

class RealBleService : Service() {

    private var bluetoothGatt: BluetoothGatt? = null
    private val TAG = "RealBleService"

    // UUIDs must match Firmware (ble_service.c)
    // Placeholder UUIDs - REPLACE THESE WITH YOUR FIRMWARE UUIDS!
    private val SERVICE_UUID = UUID.fromString("000000FF-0000-1000-8000-00805F9B34FB") 
    private val CHAR_UUID    = UUID.fromString("0000FF01-0000-1000-8000-00805F9B34FB")

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.d(TAG, "Connected to GATT server.")
                gatt?.discoverServices()
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.d(TAG, "Disconnected from GATT server.")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val service = gatt?.getService(SERVICE_UUID)
                val characteristic = service?.getCharacteristic(CHAR_UUID)
                
                // Enable Notifications
                gatt?.setCharacteristicNotification(characteristic, true)
                val descriptor = characteristic?.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
                descriptor?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt?.writeDescriptor(descriptor)
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            // Firmware sends 2 bytes: [EventType, Severity]
            val value = characteristic.value
            if (value.size >= 2) {
                val eventType = value[0].toInt()
                val severity = value[1].toInt()
                Log.d(TAG, "Received BLE Event: Type $eventType, Severity $severity")
                
                broadcastEvent(eventType, severity)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val deviceAddress = intent?.getStringExtra("DEVICE_ADDRESS")
        if (deviceAddress != null) {
            connect(deviceAddress)
        }
        return START_NOT_STICKY
    }

    private fun connect(address: String): Boolean {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device = adapter.getRemoteDevice(address)
        bluetoothGatt = device.connectGatt(this, false, gattCallback)
        return true
    }
    
    private fun broadcastEvent(type: Int, severity: Int) {
        val intent = Intent("com.example.smartclip.BLE_TRIGGER_EVENT")
        intent.putExtra("TYPE", type)
        intent.putExtra("SEVERITY", severity)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
