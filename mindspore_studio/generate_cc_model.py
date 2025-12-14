import os
import sys

def convert_to_c_array(input_file, output_file, array_name):
    print(f"Reading {input_file}...")
    try:
        with open(input_file, 'rb') as f:
            data = f.read()
    except FileNotFoundError:
        print(f"Error: {input_file} not found.")
        return

    print(f"Converting {len(data)} bytes to C array...")
    
    c_content = f"// Auto-generated from {input_file}\n"
    c_content += f"#include <stdint.h>\n\n"
    c_content += f"const unsigned char {array_name}[] = {{\n"
    
    # Convert bytes to hex string
    hex_data = [f"0x{b:02x}" for b in data]
    
    # Format nicely (12 bytes per line)
    for i in range(0, len(hex_data), 12):
        line = ", ".join(hex_data[i:i+12])
        c_content += "  " + line + ",\n"
        
    c_content += "};\n\n"
    c_content += f"const unsigned int {array_name}_len = {len(data)};\n"

    print(f"Writing to {output_file}...")
    with open(output_file, 'w') as f:
        f.write(c_content)
    
    print("Done.")

if __name__ == "__main__":
    # Check if model exists
    model_path = "audio_model.mindir"
    if not os.path.exists(model_path):
        print(f"Model file {model_path} not found!")
    else:
        # Generate header file for firmware
        # Target path: ../firmware/components/ms_model/audio_model_data.h
        # But for safety, let's put it in current dir first or ask user.
        # Based on structure: 
        target_dir = "../firmware/components/ms_model"
        if not os.path.exists(target_dir):
            os.makedirs(target_dir, exist_ok=True)
            
        output_path = os.path.join(target_dir, "audio_model_data.h")
        convert_to_c_array(model_path, output_path, "g_audio_model_data")
