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

#endif // MOCK_DATA_H
