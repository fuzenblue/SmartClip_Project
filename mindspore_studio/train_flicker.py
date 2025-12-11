import numpy as np
import os

# Configuration
OUTPUT_FILE = "./datasets/synthetic_light.csv"
SAMPLE_RATE = 1000  # Hz (High enough to catch 100-200Hz)
DURATION = 2.0      # Seconds
TARGET_FREQS = [100, 120, 200]

def generate_synthetic_data():
    """
    Generates synthetic light data with specific flicker frequencies.
    """
    print(f"Generating synthetic light data to {OUTPUT_FILE}...")
    
    data_lines = ["timestamp_ms,adc_value,label"]
    t = np.linspace(0, DURATION, int(SAMPLE_RATE * DURATION), endpoint=False)
    
    # 1. Normal Light (DC + Noise)
    dc_level = 2048
    noise = np.random.normal(0, 50, len(t))
    normal_signal = dc_level + noise
    
    for i, val in enumerate(normal_signal):
        data_lines.append(f"{i},{int(np.clip(val, 0, 4095))},normal")
        
    start_ms = len(normal_signal)
    
    # 2. Flicker at 100Hz
    freq = 100
    signal_100 = dc_level + 1000 * np.sin(2 * np.pi * freq * t) + np.random.normal(0, 50, len(t))
    
    for i, val in enumerate(signal_100):
        data_lines.append(f"{start_ms + i},{int(np.clip(val, 0, 4095))},flicker_100hz")

    # Write to CSV
    dataset_dir = os.path.dirname(OUTPUT_FILE)
    if not os.path.exists(dataset_dir):
        os.makedirs(dataset_dir)
        
    with open(OUTPUT_FILE, "w") as f:
        f.write("\n".join(data_lines))
    print("Data generation complete.")
    return normal_signal, signal_100

def train_flicker_logic():
    print("Validating Flicker Logic (FFT Simulation)...")
    
    # Generate data
    normal, flicker_100 = generate_synthetic_data()
    
    # Logic Validation: Can we detect 100Hz?
    # Simple FFT on the flicker signal
    fft_vals = np.fft.rfft(flicker_100)
    fft_freqs = np.fft.rfftfreq(len(flicker_100), d=1/SAMPLE_RATE)
    
    magnitude = np.abs(fft_vals)
    peak_idx = np.argmax(magnitude[1:]) + 1 # Ignore DC
    peak_freq = fft_freqs[peak_idx]
    
    print(f"Detected Peak Frequency: {peak_freq:.2f} Hz")
    
    if 95 < peak_freq < 105:
        print("PASS: Flicker at 100Hz correctly identified.")
    else:
        print(f"FAIL: Expected ~100Hz, got {peak_freq:.2f} Hz")

if __name__ == "__main__":
    try:
        train_flicker_logic()
    except Exception as e:
        print(f"Validation failed: {e}")
