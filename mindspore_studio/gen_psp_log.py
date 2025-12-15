import os
import random
import csv

# Configuration
OUTPUT_FILE = "datasets/psp_user_behavior.csv"
NUM_ENTRIES = 1000

def generate_psp_log():
    # Simulate features
    # Slope: Change in movement/activity
    slope = [random.gauss(0, 1) for _ in range(NUM_ENTRIES)]
    
    # Energy: Intensity of movement
    energy = [abs(random.gauss(50, 20)) for _ in range(NUM_ENTRIES)]
    
    # Index: Custom index
    idx_val = [random.randint(0, 9) for _ in range(NUM_ENTRIES)]
    
    # Label: 0 = Idle, 1 = Active, 2 = Anomalous
    labels = []
    for s, e in zip(slope, energy):
        if e > 80:
            labels.append(2) # Anomaly
        elif e > 30:
            labels.append(1) # Active
        else:
            labels.append(0) # Idle
            
    # Ensure directory exists
    os.makedirs(os.path.dirname(OUTPUT_FILE), exist_ok=True)
            
    with open(OUTPUT_FILE, 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['slope', 'energy', 'index_val', 'label'])
        for i in range(NUM_ENTRIES):
            writer.writerow([f"{slope[i]:.4f}", f"{energy[i]:.4f}", idx_val[i], labels[i]])
            
    print(f"Generated {OUTPUT_FILE} with {NUM_ENTRIES} entries.")

if __name__ == "__main__":
    generate_psp_log()

