#ifndef HARDWARE_CONFIG_H
#define HARDWARE_CONFIG_H

// --- I2S Microphone (INMP441) ---
#define I2S_MIC_SCK_PIN   2
#define I2S_MIC_WS_PIN    42
#define I2S_MIC_SD_PIN    1
#define I2S_PORT_NUM      0
#define SAMPLE_RATE       16000
#define I2S_BUF_LEN       512

// --- ADC Photodiode ---
#define ADC_LIGHT_PIN     6
// Note: ESP32-S3 ADC channels match GPIOs differently, 
// usually GPIO6 is ADC1_CHANNEL_5 on ESP32-S3.
#define ADC_UNIT          ADC_UNIT_1
#define ADC_CHANNEL       ADC_CHANNEL_5 

// --- I2C VOC Sensor (BME680/ENS160) ---
#define I2C_SDA_PIN       4
#define I2C_SCL_PIN       5
#define I2C_PORT_NUM      0
#define I2C_FREQ_HZ       100000

#endif // HARDWARE_CONFIG_H
