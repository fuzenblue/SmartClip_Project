# SmartClip Project

SmartClip is an intelligent wearable device designed to detect environmental hazards and enhance user safety. The system integrates embedded firmware, on-device AI (MindSpore), and a mobile application to monitor light flicker frequencies and audio events (such as sirens or traffic noise) in real-time.

## 🚀 Key Features

*   **Hazard Detection**:
    *   **High-Frequency Flicker**: Detects light flickering > 100Hz which may cause eye strain or neurological issues.
    *   **Audio Classification**: identifying sounds like sirens or traffic using a lightweight CNN model.
*   **On-Device AI**: Powered by **MindSpore Lite**, running a custom trained TinyCNN directly on the ESP32-S3.
*   **simulation Mode**: Built-in "Mock Sensor" mode allows development and testing without physical hardware.
*   **Bluetooth Connectivity**: Real-time alerts and data sync to the SmartClip Android App via BLE.

## 📂 Project Structure

The project is organized into three main components:

```
SmartClip_Project/
├── firmware/           # ESP32-S3 Firmware (C/C++, ESP-IDF)
│   ├── main/           # Core logic (Loop, BLE, Logic)
│   ├── components/     # Drivers & Managers (Sensor Manager, AI inference)
│   └── CMakeLists.txt  # Build configuration
│
├── mindspore_studio/   # AI Model Development
│   ├── train_audio.py  # Script to train TinyCNN model
│   └── ...             # Datasets and conversion tools
│
├── SmartClipApp/       # Mobile Application
│   └── app/            # Android Source Code (Java/Kotlin, Gradle)
│
└── IMPLEMENTATION_REPORT.md # Technical deep dive & logic explanation
```

## 🛠️ Technology Stack

*   **Hardware**: ESP32-S3 (Microcontroller), I2S Microphone, Photodiode.
*   **Firmware Framework**: ESP-IDF (FreeRTOS based).
*   **AI Framework**: MindSpore & MindSpore Lite (for Microcontrollers).
*   **Mobile**: Android (Gradle Build System).

## ⚙️ Configuration & Hardware

 The firmware supports a **Mock Mode** for testing logic without sensors. This is controlled in `app_config.h` or preprocessor definitions.

### Pin Mapping (`hardware_config.h`)
If connecting real hardware, the default pin configuration is:
*   **Microphone (I2S)**: SCK: 2, WS: 42, SD: 1
*   **Photodiode (ADC)**: Pin 6

## 🚦 Getting Started

### 1. Firmware Setup
1.  Install **ESP-IDF** (v5.x recommended).
2.  Navigate to `firmware/`.
3.  Set the target: `idf.py set-target esp32s3`.
4.  Build the project: `idf.py build`.
5.  Flash to device: `idf.py -p (PORT) flash monitor`.

### 2. AI Model
1.  Navigate to `mindspore_studio/`.
2.  Run `python train_audio.py` to generate the model.
3.  Convert the `.mindir` model to C++ code using `converter_lite`.
4.  Place the generated files into the firmware's `components` directory.

### 3. Mobile App
1.  Open `SmartClipApp` folder in **Android Studio**.
2.  Sync Gradle project.
3.  Build and Run on an Android device or Emulator.

## 📝 Implementation Details
For a detailed technical breakdown of the code logic (including `main.c`, `sensor_manager.c`, and the AI architecture), please refer to the [IMPLEMENTATION_REPORT.md](./IMPLEMENTATION_REPORT.md).

---
*Created for SmartClip Project Development.*
