# 📄 Smart Clip Implementation Report (Technical Deep Dive)

This report details the core logic of the Smart Clip firmware and AI system. It includes **Code Snippets** to explain critical sections, their function, and how they interconnect across the project.

---

## **1. Firmware Core Logic (`main.c`)**

### **The "Brain" of the Device**
`main.c` is the entry point. It doesn't handle hardware directly; it delegates tasks to managers. Its job is to run the infinite loop, collect data, and decide when to alert.

### **Critical Code Snippet**
```c
while (1) {
    // 1. Data Collection Phase
    // Connects to: sensor_manager.c
    SensorData_t data = read_sensors();

    // 2. Logic Phase: Flicker Detection
    // Checks if the 'light_freq' returned by sensor manager exceeds threshold
    if (data.light_freq > 100.0) {
        printf("Flicker Detected! Freq: %.2f Hz\n", data.light_freq);
        
        // 3. Action Phase: Alerting
        // Connects to: ble_service.c to notify mobile app
        ble_send_event(1, 90);
    }

    // 4. Power Management (Simple Delay)
    vTaskDelay(pdMS_TO_TICKS(1000)); 
}
```

### **Analysis**
*   **What it does:** It runs a continuous cycle: **Read -> Analyze -> Act**.
*   **Interconnection:** 
    *   Calls `read_sensors()` defined in `sensor_manager`. The main loop doesn't care if the data is real or fake; it just processes it.
    *   Calls `ble_send_event()` via `ble_service`.

---

## **2. The Hardware Abstraction Layer (`sensor_manager.c`)**

### **The "Switch" Mechanism**
This is the most complex file. It abstracts the hardware so you can code without the physical board. It uses a **Preprocessor Directive** (`#if`) to compile different code based on your config.

### **Critical Code Snippet: Mock vs Real Switching**
```c
SensorData_t read_sensors() {
    SensorData_t data;

    #if USE_MOCK_SENSORS == 1
        // --- PATH A: Simulation Mode ---
        // Pointers are directed to static arrays (MOCK_AUDIO_SAMPLE)
        // defined in "mock_data.h"
        static int offset = 0;
        data.audio_buffer = (float*)&MOCK_AUDIO_SAMPLE[offset];
        
        // Hardcoded simulation of a flicker trigger
        data.light_freq = 120.0; 

    #else
        // --- PATH B: Real Hardware Mode ---
        // Actually talks to ESP32 Drivers
        size_t bytes_read = 0;
        i2s_read(..., s_i2s_read_buff, ...); // Read Microphone
        adc_oneshot_read(..., &adc_raw);     // Read Photodiode
        
        // Convert raw integer data to AI-ready floats
        data.audio_buffer = s_real_audio_buf;
    #endif

    return data;
}
```

### **Analysis**
*   **How it works:** 
    *   If `USE_MOCK_SENSORS` is 1 (in `app_config.h`), the compiler *deletes* Path B and only compiles Path A.
    *   If 0, it compiles Path B logic (calling `i2s_driver_install`, etc.).
*   **Why it's important:** It prevents build errors. If you tried to compile `i2s_read` on a PC (not ESP32), it would crash. This logic protects the build.

---

## **3. AI Model Architecture (`train_audio.py`)**

### **The "Tiny Brain" (Neural Network)**
This Python script defines the architecture of the AI. It uses **Conv2d** (Convolutional Neural Network) which is standard for analyzing images and audio spectrograms.

### **Critical Code Snippet: TinyCNN Structure**
```python
class TinyCNN(nn.Cell):
    def __init__(self, num_classes=4):
        # 1. Feature Extractor (Convolution)
        # Looks for patterns (lines, edges) in the audio spectrogram
        self.conv1 = nn.Conv2d(1, 16, kernel_size=3, padding=1)
        self.pool = nn.MaxPool2d(kernel_size=2, stride=2) // Shrinks data size
        
        # 2. Classifier (Fully Connected)
        # Decides what the features mean (Siren? Traffic?)
        self.fc1 = nn.Dense(32 * 10 * 12, 64)
        self.fc2 = nn.Dense(64, num_classes)

    def construct(self, x):
        # Data flows through layers sequentially
        x = self.pool(self.relu(self.conv1(x)))
        x = self.flatten(x)
        x = self.fc2(self.relu(self.fc1(x)))
        return x
```

### **Analysis**
*   **What it does:** It translates raw audio features (MFCC) into probabilities.
*   **Connection to Firmware:** 
    *   This python code exports a `.mindir` file.
    *   That file is converted to C++ code by the `converter_lite` tool.
    *   The resulting C++ code is what eventually gets called inside `firmware` (in future steps).

---

## **4. Configuration (`hardware_config.h`)**

### **The "Map"**
This file maps logical names to physical pins on the ESP32-S3.

### **Critical Code Snippet**
```c
#define I2S_MIC_SCK_PIN   2   // Microphone Clock
#define I2S_MIC_WS_PIN    42  // Word Select
#define I2S_MIC_SD_PIN    1   // Data Pin
#define ADC_LIGHT_PIN     6   // Photodiode Input
```

### **Analysis**
*   **Why it's crucial:** If you change your wiring on the breadboard, you ONLY change this file. You never touch `sensor_manager.c`. This makes the code maintainable and clean.

---

## **Summary of Data Flow**

1.  **Input:** `sensor_manager.c` reads data (Real Pin OR Mock Array).
2.  **Transport:** Returns a `SensorData_t` struct to `main.c`.
3.  **Process:** `main.c` checks `light_freq` (in future, handles Audio AI).
4.  **Output:** If risk detected -> `ble_service.c` -> Mobile App.
