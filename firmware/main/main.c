#include <stdio.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "app_config.h"
#include "sensor_manager.h"
#include "ble_service.h"

void app_main(void)
{
    printf("SmartClip Firmware Starting...\n");
    
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
        
        // 2. Process Data (Placeholder for AI)
        // inference_results = run_model(data.audio_buffer);
        
        if (data.audio_buffer != NULL) {
             // In real app: float *features = extract_mfcc(data.audio_buffer);
             // int class_id = model_predict(features);
             // if (class_id == CLASS_SIREN) ble_send_event(2, 80);
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
