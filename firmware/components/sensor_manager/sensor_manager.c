#include "sensor_manager.h"
#include "../../main/app_config.h"
#include "../../main/mock_data.h"
#include "hardware_config.h"
#include <stddef.h> 
#include <string.h>
#include <stdio.h>

#if USE_MOCK_SENSORS == 0
    #include "driver/i2s.h"
    #include "driver/adc.h"
    #include "esp_adc/adc_oneshot.h"
    #include "driver/i2c.h"
    #include "esp_log.h"
    
    // Global handles
    static adc_oneshot_unit_handle_t adc1_handle;
    static const char *TAG = "SENSORS";
    
    // Real buffers
    static float s_real_audio_buf[I2S_BUF_LEN];
    static int16_t s_i2s_read_buff[I2S_BUF_LEN];
#endif

void sensor_manager_init(void) {
    #if USE_MOCK_SENSORS == 1
        printf("[SensorMgr] Initialized in MOCK mode.\n");
    #else
        printf("[SensorMgr] Initializing REAL HARDWARE...\n");
        
        // --- 1. Init I2S (Microphone) ---
        i2s_config_t i2s_config = {
            .mode = I2S_MODE_MASTER | I2S_MODE_RX,
            .sample_rate = SAMPLE_RATE,
            .bits_per_sample = I2S_BITS_PER_SAMPLE_16BIT,
            .channel_format = I2S_CHANNEL_FMT_ONLY_LEFT,
            .communication_format = I2S_COMM_FORMAT_STAND_I2S,
            .intr_alloc_flags = ESP_INTR_FLAG_LEVEL1,
            .dma_buf_count = 4,
            .dma_buf_len = I2S_BUF_LEN,
            .use_apll = false
        };
        i2s_pin_config_t pin_config = {
            .bck_io_num = I2S_MIC_SCK_PIN,
            .ws_io_num = I2S_MIC_WS_PIN,
            .data_out_num = I2S_PIN_NO_CHANGE,
            .data_in_num = I2S_MIC_SD_PIN
        };
        if (i2s_driver_install(I2S_PORT_NUM, &i2s_config, 0, NULL) != ESP_OK) {
            printf("Failed to install I2S driver\n");
        }
        if (i2s_set_pin(I2S_PORT_NUM, &pin_config) != ESP_OK) {
            printf("Failed to set I2S pins\n");
        }

        // --- 2. Init ADC (Photodiode) ---
        adc_oneshot_unit_init_cfg_t init_config1 = {
            .unit_id = ADC_UNIT,
        };
        if (adc_oneshot_new_unit(&init_config1, &adc1_handle) != ESP_OK) {
             printf("Failed to init ADC unit\n");
        }
        adc_oneshot_chan_cfg_t config = {
            .bitwidth = ADC_BITWIDTH_DEFAULT,
            .atten = ADC_ATTEN_DB_11,
        };
        adc_oneshot_config_channel(adc1_handle, ADC_CHANNEL, &config);

        printf("[SensorMgr] Hardware Initialized Successfully.\n");
    #endif
}

SensorData_t read_sensors() {
    SensorData_t data;

    #if USE_MOCK_SENSORS == 1
        // --- Mock Mode: Read from Array ---
        static int offset = 0;
        
        data.audio_buffer = (float*)&MOCK_AUDIO_SAMPLE[offset];
        data.light_freq = 120.0; // Simulated flicker detection

        offset += 512;
        if (offset >= sizeof(MOCK_AUDIO_SAMPLE)/sizeof(float)) offset = 0;
        
        data.audio_len = 512;

    #else
        // --- Real Hardware Mode ---
        
        // 1. Read Audio (I2S)
        size_t bytes_read = 0;
        esp_err_t res = i2s_read(I2S_PORT_NUM, s_i2s_read_buff, sizeof(s_i2s_read_buff), &bytes_read, 100 / portTICK_PERIOD_MS);
        
        if (res == ESP_OK && bytes_read > 0) {
            int samples = bytes_read / 2; // 16-bit
            // Convert to float for AI
            for (int i=0; i<samples; i++) {
                s_real_audio_buf[i] = (float)s_i2s_read_buff[i] / 32768.0f;
            }
            data.audio_buffer = s_real_audio_buf;
            data.audio_len = samples;
        } else {
            data.audio_buffer = NULL;
            data.audio_len = 0;
        }

        // 2. Read Light (ADC)
        int adc_raw;
        if (adc_oneshot_read(adc1_handle, ADC_CHANNEL, &adc_raw) == ESP_OK) {
            // Need historical data to calculate freq, here we just pass raw for now
            // In full impl, this would push to a buffer and run FFT/Period check
            // For now, simple threshold to simulate "reading"
            // data.light_freq = calculate_frequency(adc_buffer);
            data.light_freq = 0.0; // Placeholder
            
            // Allow simulated trigger if light is very high for testing
            if (adc_raw > 4000) data.light_freq = 101.0; 
        } else {
            data.light_freq = 0.0;
        }
    #endif

    return data;
}
