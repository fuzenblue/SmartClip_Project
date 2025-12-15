import os
import random
import csv

# Configuration
OUTPUT_FILE = "datasets/env_anomaly_data.csv"
SAMPLE_RATE_HZ = 1  # 1 sample per second
DURATION_SEC = 600  # 10 minutes

def linspace(start, stop, num):
    if num == 1:
        return [start]
    step = (stop - start) / (num - 1)
    return [start + i * step for i in range(num)]

def generate_pressure_drop(n_samples):
    # Base pressure ~1013 hPa
    pressure = [random.gauss(1013.25, 0.1) for _ in range(n_samples)]
    
    # Introduce a drop event in the middle
    start_drop = n_samples // 3
    end_drop = start_drop + 30 # 30 seconds drop
    
    # Linear drop to 1000 hPa
    drop_values = linspace(1013, 980, end_drop - start_drop)
    for i, val in enumerate(drop_values):
        pressure[start_drop + i] = val
        
    # Recovery
    rec_values = linspace(980, 1013, 30)
    for i, val in enumerate(rec_values):
        pressure[end_drop + i] = val
    
    return pressure

def generate_voc_spike(n_samples):
    # Base VOC (Gas Resistance) ~50000 Ohms (clean air)
    # Spike means resistance drops (more gas)
    voc = [random.gauss(50000, 500) for _ in range(n_samples)]
    
    # Introduce spike (resistance drop)
    start_spike = 2 * n_samples // 3
    end_spike = start_spike + 60 # 1 minute spike
    
    # Rapid drop
    drop_len = 10
    drop_values = linspace(50000, 10000, drop_len)
    for i, val in enumerate(drop_values):
        voc[start_spike + i] = val
        
    # Hold
    hold_len = (end_spike - 20) - (start_spike + 10)
    for i in range(hold_len):
        voc[start_spike + 10 + i] = random.gauss(10000, 200)
    
    # Recovery
    rec_len = 20
    rec_values = linspace(10000, 50000, rec_len)
    for i, val in enumerate(rec_values):
        voc[end_spike - 20 + i] = val
        
    return voc

def main():
    n_samples = DURATION_SEC * SAMPLE_RATE_HZ
    
    pressure = generate_pressure_drop(n_samples)
    voc = generate_voc_spike(n_samples)
    
    # Ensure directory exists
    os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)
    
    with open(OUTPUT_FILE, 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['timestamp', 'pressure_hpa', 'gas_resistance_ohm'])
        for i in range(n_samples):
            writer.writerow([i, f"{pressure[i]:.2f}", f"{voc[i]:.0f}"])
            
    print(f"Generated {OUTPUT_FILE} with {n_samples} samples.")

if __name__ == "__main__":
    main()

