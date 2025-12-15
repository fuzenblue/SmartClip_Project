import os
import math
import struct
import wave

# Configuration
OUTPUT_REL_PATH = "datasets/audio/low_freq_drone/drone_400hz.wav"
SAMPLE_RATE = 44100
DURATION_SEC = 5
FREQUENCY = 400.0 # Hz

def generate_sine_wave():
    # Construct absolute path relative to script location
    script_dir = os.path.dirname(os.path.abspath(__file__))
    output_path = os.path.join(script_dir, OUTPUT_REL_PATH)
    
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    
    num_samples = int(SAMPLE_RATE * DURATION_SEC)
    
    with wave.open(output_path, 'w') as wav_file:
        wav_file.setnchannels(1) # Mono
        wav_file.setsampwidth(2) # 2 bytes per sample (16-bit)
        wav_file.setframerate(SAMPLE_RATE)
        
        for i in range(num_samples):
            t = float(i) / SAMPLE_RATE
            # Sine wave formula: A * sin(2 * pi * f * t)
            # Amplitude 32767 is max for 16-bit
            value = int(32767.0 * 0.5 * math.sin(2.0 * math.pi * FREQUENCY * t))
            data = struct.pack('<h', value)
            wav_file.writeframes(data)
            
    print(f"Generated {output_path}")

if __name__ == "__main__":
    generate_sine_wave()
