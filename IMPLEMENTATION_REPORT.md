# SmartClip Implementation Report

## 1. Project Overview
SmartClip is an intelligent wearable device designed to detect hyper-local environmental triggers for migraine sufferers. It utilizes a sensor fusion approach on the ESP32-S3 microcontroller to monitor rapid pressure changes, specific noise frequencies (400Hz), light flicker, and volatile organic compounds (VOCs). The system integrates with an Android application via BLE for real-time alerts and utilizes on-device AI (MindSpore) for efficient processing.

## 2. System Architecture

The system is composed of three main layers:

### 2.1 Device Layer (Firmware)
*   **Hardware**: ESP32-S3 DevKitC (Xtensa LX7).
*   **Sensors**:
    *   **BME680**: Environmental sensor (Pressure, Temperature, Humidity, VOC gas).
    *   **INMP441**: I2S MEMS Microphone for high-fidelity audio analysis.
    *   **Photodiode**: High-speed light sensor for flicker detection.
*   **Software Stack**: ESP-IDF (FreeRTOS), MindSpore Micro (for feature extraction).

### 2.2 Mobile Layer (Application)
*   **Platform**: Android (Kotlin/Java).
*   **Core Functions**: BLE Central role, Risk Prediction Inference (MindSpore Lite), User Feedback Interface.
*   **AI Model**: Runs a `.ms` (MindSpore Lite) model to calculate migraine risk probability based on sensor vectors.

### 2.3 Cloud Layer (Intelligence)
*   **Platform**: Huawei ModelArts.
*   **Function**: Aggregates labeled datasets (Sensor Data + User Pain Level) to retrain and personalize the risk prediction model, which is then delivered via OTA updates.

---

## 3. Detailed Implementation Logic

### 3.1 Firmware Logic (`firmware/main/`)
The ESP32 firmware is responsible for reading raw sensor data, performing digital signal processing (DSP), and running lightweight AI inference.

#### A. Pressure Drop Detection (Barometric Trigger)
*   **Logic**: The system calculates the **Pressure Slope ($dP/dt$)** over a sliding window.
*   **Implementation**:
    *   Reads BME680 pressure data at 1Hz.
    *   Maintains a circular buffer of recent pressure values.
    *   Calculates the gradient: `slope = (current_pressure - pressure_t_minus_10_min) / 10`.
    *   **Trigger**: If the slope falls below a threshold (e.g., -6 hPa/hr), a "Barometric Trigger" flag is set.

#### B. Audio Spectrum Analysis (Phonophobia)
*   **Logic**: Detects specific "drone noises" around **400Hz** which are known migraine triggers, distinct from general loudness.
*   **Implementation**:
    *   Uses I2S to read audio samples from INMP441.
    *   Applies **FFT (Fast Fourier Transform)** using `esp-dsp` library.
    *   Analyzes energy bins corresponding to 350Hz - 450Hz.
    *   **Trigger**: If energy in this band exceeds the noise floor by >10dB for a sustained period, an "Audio Trigger" is flagged.

#### C. Light Flicker Analysis
*   **Logic**: Distinguishes between Natural Light (DC) and Artificial Light (AC/PWM) which may cause eye strain.
*   **Implementation**:
    *   High-speed ADC sampling of the photodiode (at > 1kHz).
    *   Performs Zero-crossing detection or Waveform analysis.
    *   **Trigger**: High-frequency oscillation (e.g., 100Hz from 50Hz mains) indicates artificial lighting.

### 3.2 Mobile Application Logic (`SmartClipApp/`)

#### A. BLE Communication (`RealBleService.kt`)
The app acts as a BLE Central device scanning for the "SmartClip" peripheral.
*   **Service UUID**: Custom UUID for Migraine Service.
*   **Characteristic**: Notification-enabled characteristic that streams the **Feature Vector**.
*   **Data Packet**: A byte array containing:
    *   `[0-3]`: Timestamp
    *   `[4-7]`: Pressure Slope (Float)
    *   `[8-11]`: Audio Energy (Float)
    *   `[12]`: Trigger Flags (Bitmask)

#### B. Risk Inference (MindSpore Lite)
*   The app loads a pre-trained `psp_model.ms` file.
*   Incoming BLE vectors are fed into the model.
*   **Output**: A risk score (0.0 - 1.0).
*   **Action**: If Score > 0.7, a high-priority notification is shown to the user.

---

## 4. File Structure & Organization

```
SmartClip_Project/
├── firmware/
│   ├── main/
│   │   ├── main.c              # Core application loop & state machine
│   │   ├── sensor_manager.c    # Advanced sensor reading & mocking logic
│   │   ├── ble_service.c       # BLE GATT Server implementation
│   │   └── mock_data.h         # Synthetic datasets for testing without hardware
│   └── components/
│       └── ms_model/           # MindSpore Micro runtime & model data
│
├── SmartClipApp/
│   └── app/src/main/
│       ├── java/com/example/smartclip/
│       │   ├── service/        # Background BLE handling (RealBleService.kt)
│       │   └── ui/             # Dashboard & Visualization
│       └── assets/             # Contains .ms model files
│
└── mindspore_studio/           # AI Model Training
    ├── train_audio.py          # Script for training the audio classifier
    └── datasets/               # Raw audio/sensor datasets
```

## 5. Current Status (Integration)
As of the latest Progress Report (v1.2):
*   **Firmware**: Fully capable of mocking sensor data and sending real BLE notifications.
*   **Mobile App**: Successfully connects to the firmware, subscribes to notifications, and parses the custom data packet.
*   **AI**: Audio model is integrated into the firmware; Risk Prediction model is deployable to the mobile app.
