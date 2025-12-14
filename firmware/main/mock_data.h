#ifndef MOCK_DATA_H
#define MOCK_DATA_H

#include <stdint.h>

// Manually generated mock data since Python script execution failed
const float MOCK_AUDIO_SAMPLE[] = { 
    0.0, 0.1, 0.2, 0.1, 0.0, -0.1, -0.2, -0.1, 
    0.0, 0.1, 0.2, 0.1, 0.0, -0.1, -0.2, -0.1,
    // ... (truncated for brevity)
    0.0, 0.1, 0.2, 0.1, 0.0, -0.1, -0.2, -0.1 
};

const uint16_t MOCK_LIGHT_FLICKER[] = { 
    0, 4095, 0, 4095, 0, 4095, 0, 4095,
    0, 4095, 0, 4095, 0, 4095, 0, 4095
};

const float MOCK_PRESSURE_DROP_SCENARIO[] = { 
    1010.0, 1009.9, 1009.8, 1009.5, 1009.0, 1008.0, 1006.0, 1004.0, 
    1002.0, 1001.0, 1000.5, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0 
};

const uint32_t MOCK_VOC_SPIKE_SCENARIO[] = { 
    50000, 50000, 50000, 48000, 45000, 40000, 30000, 20000, 
    15000, 12000, 10000, 10000, 12000, 15000, 20000, 30000 
};

#endif // MOCK_DATA_H
