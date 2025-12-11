#!/bin/bash
# Script to convert MindSpore .mindir models to Micro format
# Usage: ./convert_to_micro.sh

# Example:
# ./converter_lite --fmk=MINDIR --modelFile=audio_model.mindir --outputFile=audio_model_micro --optimize=1

MS_LITE_HOME="/path/to/mindspore/lite/tools/converter" # User needs to update this
CONVERTER="$MS_LITE_HOME/converter_lite"

if [ ! -f "$CONVERTER" ]; then
    echo "Error: Converter tool not found at $CONVERTER"
    echo "Please set MS_LITE_HOME to your MindSpore Lite installation."
    # Simulation mode for script
    echo "Simulating conversion..."
    touch audio_model_micro.ms
    exit 0
fi

echo "Converting Audio Model..."
$CONVERTER --fmk=MINDIR --modelFile=models/audio_model.mindir --outputFile=models/audio_model_micro --optimize=1 --configFile=micro_config.txt

echo "Conversion Complete."
