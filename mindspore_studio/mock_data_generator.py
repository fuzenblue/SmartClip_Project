# mock_data_generator.py
import numpy as np
import os

# Create directory if it doesn't exist
output_dir = "../firmware/main"
if not os.path.exists(output_dir):
    try:
        os.makedirs(output_dir)
    except OSError:
        pass

# 1. Generate Mock Audio Data (Sine Wave at 400Hz for detection)
# Sample Rate 16000Hz, 400Hz = 40 samples/cycle
sr = 16000
freq = 400
duration = 1.0 # 1 second
t = np.linspace(0, duration, int(sr * duration), endpoint=False)
audio_data = np.sin(2 * np.pi * freq * t) * 0.5 

# 2. Generate Mock Light Data (Flicker 100Hz simulation)
# High/Low toggle
light_data = [4095 if i % 2 == 0 else 0 for i in range(128)]

# 3. Generate Mock Pressure (Pressure Drop Scenario)
# Drop from 1010 to 1000 hPa
pressure_data = np.linspace(1010.0, 1000.0, 100)

# 4. Generate Mock VOC (Spike Scenario)
# Spike down in resistance means high gas
voc_data = np.linspace(50000, 10000, 100).astype(int)

with open(os.path.join(output_dir, "mock_data.h"), "w") as f:
    f.write("#ifndef MOCK_DATA_H\n")
    f.write("#define MOCK_DATA_H\n\n")
    f.write("#include <stdint.h>\n\n")
    
    # Write Audio Data
    f.write(f"const float MOCK_AUDIO_SAMPLE[] = {{ {', '.join(map(str, audio_data))} }};\n\n")
    
    # Write Light Data
    f.write(f"const uint16_t MOCK_LIGHT_FLICKER[] = {{ {', '.join(map(str, light_data))} }};\n\n")

    # Write Pressure Data
    f.write(f"const float MOCK_PRESSURE_DROP_SCENARIO[] = {{ {', '.join(map(str, pressure_data))} }};\n\n")
    
    # Write VOC Data
    f.write(f"const uint32_t MOCK_VOC_SPIKE_SCENARIO[] = {{ {', '.join(map(str, voc_data))} }};\n")
    
    f.write("\n#endif // MOCK_DATA_H")

print(f"Generated {os.path.join(output_dir, 'mock_data.h')}")
