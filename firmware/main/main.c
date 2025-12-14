#include <stdio.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "app_config.h"
#include "sensor_manager.h"
#include "ble_service.h"
#include "audio_model_data.h"

void app_main(void)
{
    printf("SmartClip Firmware Starting...\n");
    printf("AI Model Loaded: Size = %d bytes\n", g_audio_model_data_len);
    
    // Initialize BLE
    ble_service_init();

    // Initialize Sensors
    sensor_manager_init();

    #if USE_MOCK_SENSORS
        printf("Mode: MOCK SENSORS\n");
    #else
        printf("Mode: REAL HARDWARE\n");
    #endif

    while (1) {
        // 1. Read Sensors
        SensorData_t data = read_sensors();
        
        // 2. Process Data (AI Integration)
        if (data.audio_buffer != NULL) {
             // In a real scenario, we would pass data.audio_buffer to the MindSpore interpreter
             // initialized with g_audio_model_data.
             // For now, we verify the data flow.
             printf("Running Inference on Audio Input (Len: %d) using Model (Size: %d)...\n", data.audio_len, g_audio_model_data_len);
             
             // Simple Threshold Logic for Demo (Replacing Neural Net for now)
             // If we find high energy in Mock Data -> Trigger
             // In real AI, 'class_id = model.Predict(data.audio_buffer)'
             
             // Mock Inference Result:
             // 0: Silence, 1: Siren, 2: Construction, 3: Traffic
             int predicted_class = 0; 
             
             // Simple "Energy" based heuristic for mock
             float energy = 0;
             for(int i=0; i< (data.audio_len > 100 ? 100 : data.audio_len); i++) {
                 energy += (data.audio_buffer[i] * data.audio_buffer[i]);
             }
             
             if (energy > 0.5) predicted_class = 1; // Loud -> Siren
             
             if (predicted_class == 1) {
                 printf("AI Detection: SIREN detected!\n");
                 ble_send_event(2, 80);
             }
        }

        // 3. Flicker Detection Logic
        if (data.light_freq > 100.0) {
            printf("Flicker Detected! Freq: %.2f Hz\n", data.light_freq);
            
            // Send BLE Alert (Event Type 1 = Flicker, Severity logic pending)
            ble_send_event(1, 90);
        }

        vTaskDelay(pdMS_TO_TICKS(1000)); // Delay 1 second
    }
}
