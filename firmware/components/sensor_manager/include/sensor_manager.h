#ifndef SENSOR_MANAGER_H
#define SENSOR_MANAGER_H

// Structure for sensor data
typedef struct {
    float* audio_buffer;
    int    audio_len;
    float  light_freq;
} SensorData_t;

/**
 * @brief Initialize sensor hardware (schedules I2S, I2C, ADC drivers)
 */
void sensor_manager_init(void);

/**
 * @brief Read data from sensors (or mock data)
 * 
 * @return SensorData_t containing pointers to data buffers
 */
SensorData_t read_sensors();

#endif // SENSOR_MANAGER_H
