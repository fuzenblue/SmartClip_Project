# mock_data_generator.py
import numpy as np
import os

# Create directory if it doesn't exist
output_dir = "../firmware/main"
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# 1. Generate Mock Audio Data
# Assume we read an audio file into an array, here we just use random data or specific pattern
# Length 1024 samples
audio_data = np.sin(np.linspace(0, 100, 1024)) * 0.5

# 2. Generate Mock Light Data (Flicker 100Hz simulation)
# High value for light, low for dark
light_data = [4095 if i % 2 == 0 else 0 for i in range(128)]

with open(os.path.join(output_dir, "mock_data.h"), "w") as f:
    f.write("#ifndef MOCK_DATA_H\n")
    f.write("#define MOCK_DATA_H\n\n")
    f.write("#include <stdint.h>\n\n")
    
    # Write Audio Data
    f.write(f"const float MOCK_AUDIO_SAMPLE[] = {{ {', '.join(map(str, audio_data))} }};\n\n")
    
    # Write Light Data
    f.write(f"const uint16_t MOCK_LIGHT_FLICKER[] = {{ {', '.join(map(str, light_data))} }};\n")
    
    f.write("\n#endif // MOCK_DATA_H")

print(f"Generated {os.path.join(output_dir, 'mock_data.h')}")
