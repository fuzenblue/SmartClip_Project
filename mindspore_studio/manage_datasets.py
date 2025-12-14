import os
import shutil
import csv
import math
import struct
import wave

# Adjust paths relative to where the script is run (mindspore_studio)
SRC_BASE = "../ESC-50-master/audio"
META_FILE = "../ESC-50-master/meta/esc50.csv"
DEST_BASE = "datasets/audio"

# Map ESC-50 categories to our folders
MAPPING = {
    'siren': 'siren',
    'car_horn': 'traffic',
    'engine': 'traffic',
    'vacuum_cleaner': 'low_freq_drone', # Use as noise example
    'airplane': 'low_freq_drone',
}

def create_sine_wave(filename, freq, duration=5.0, sr=44100):
    print(f"Generating {filename}...")
    try:
        with wave.open(filename, 'w') as w:
            w.setnchannels(1) # Mono
            w.setsampwidth(2) # 16-bit
            w.setframerate(sr)
            
            n_samples = int(duration * sr)
            data = bytearray()
            
            for i in range(n_samples):
                t = i / sr
                value = 0.5 * math.sin(2 * math.pi * freq * t)
                # Scale to int16
                sample = int(value * 32767)
                data.extend(struct.pack('<h', sample))
            
            w.writeframes(data)
    except Exception as e:
        print(f"Error creating wave file: {e}")

def create_silence(filename, duration=5.0, sr=44100):
    print(f"Generating {filename}...")
    try:
        with wave.open(filename, 'w') as w:
            w.setnchannels(1)
            w.setsampwidth(2)
            w.setframerate(sr)
            
            n_samples = int(duration * sr)
            # Silence is just zeros
            data = bytearray(n_samples * 2) 
            w.writeframes(data)
    except Exception as e:
        print(f"Error creating silence file: {e}")

def main():
    # Ensure destination exists
    if not os.path.exists(DEST_BASE):
        os.makedirs(DEST_BASE)
    
    # Create subdirs
    for d in ['siren', 'traffic', 'low_freq_drone', 'silence']:
        path = os.path.join(DEST_BASE, d)
        if not os.path.exists(path):
            os.makedirs(path)

    # 1. Copy from ESC-50
    if os.path.exists(META_FILE):
        print(f"Reading metadata from {META_FILE}...")
        count = 0
        try:
            with open(META_FILE, 'r') as f:
                reader = csv.DictReader(f)
                for row in reader:
                    fname = row['filename']
                    cat = row['category']
                    
                    if cat in MAPPING:
                        target_dir = MAPPING[cat]
                        src = os.path.join(SRC_BASE, fname)
                        dst = os.path.join(DEST_BASE, target_dir, fname)
                        
                        if os.path.exists(src):
                            shutil.copy2(src, dst)
                            count += 1
                            if count % 10 == 0:
                                print(f"Copied {count} files...", end='\r')
            print(f"\nCopied total {count} files from ESC-50.")
        except Exception as e:
            print(f"Error processing CSV: {e}")
    else:
        print("Meta file not found! Skipping copy.")

    # 2. Generate Synthetic 400Hz Drone
    create_sine_wave(os.path.join(DEST_BASE, "low_freq_drone", "synthetic_400hz.wav"), 400)

    # 3. Generate Silence
    create_silence(os.path.join(DEST_BASE, "silence", "synthetic_silence.wav"))

if __name__ == "__main__":
    main()
