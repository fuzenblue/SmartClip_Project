/*
 * SmartClip Firmware Logic Test - FULL SENSORS (Light, Mic, Gas)
 *
 * Hardware Mapping (Wokwi Simulation):
 * 1. Flicker (Light) -> Pot 1 (GPIO 34) -> Red LED (GPIO 4)
 * 2. Siren (Mic)     -> Pot 2 (GPIO 32) -> Blue LED (GPIO 13)
 * 3. VOC (Gas)       -> Pot 3 (GPIO 33) -> Green LED (GPIO 14)
 */

// --- Pin Definitions ---
// Inputs
#define PIN_SENSOR_LIGHT 34
#define PIN_SENSOR_MIC 32
#define PIN_SENSOR_GAS 33 // New: Simulated VOC Sensor

// Outputs
#define PIN_LED_FLICKER 4 // Red
#define PIN_LED_SIREN 13  // Blue
#define PIN_LED_GAS 14    // Green: New VOC Alert

// --- Thresholds ---
#define THRESH_FLICKER 3000
#define THRESH_SIREN 2000
#define THRESH_GAS 1500 // Threshold for Bad Air Quality

void setup() {
  Serial.begin(115200);

  // Inputs
  pinMode(PIN_SENSOR_LIGHT, INPUT);
  pinMode(PIN_SENSOR_MIC, INPUT);
  pinMode(PIN_SENSOR_GAS, INPUT);

  // Outputs
  pinMode(PIN_LED_FLICKER, OUTPUT);
  pinMode(PIN_LED_SIREN, OUTPUT);
  pinMode(PIN_LED_GAS, OUTPUT);

  // STARTUP TEST: Sequence all 3 LEDs
  Serial.println("--- SENSOR SYSTEM TEST ---");
  for (int i = 0; i < 2; i++) {
    digitalWrite(PIN_LED_FLICKER, HIGH);
    delay(200);
    digitalWrite(PIN_LED_SIREN, HIGH);
    delay(200);
    digitalWrite(PIN_LED_GAS, HIGH);
    delay(200);

    digitalWrite(PIN_LED_FLICKER, LOW);
    digitalWrite(PIN_LED_SIREN, LOW);
    digitalWrite(PIN_LED_GAS, LOW);
    delay(200);
  }
  Serial.println("System Ready: Light(Pot1), Mic(Pot2), Gas(Pot3)");
}

void loop() {
  int lightRaw = analogRead(PIN_SENSOR_LIGHT);
  int micRaw = analogRead(PIN_SENSOR_MIC);
  int gasRaw = analogRead(PIN_SENSOR_GAS);

  // Print all values
  Serial.printf("Light: %d | Mic: %d | Gas: %d", lightRaw, micRaw, gasRaw);

  // 1. Flicker Logic (Red)
  if (lightRaw > THRESH_FLICKER) {
    digitalWrite(PIN_LED_FLICKER, HIGH);
    Serial.print(" [FLICKER]");
  } else {
    digitalWrite(PIN_LED_FLICKER, LOW);
  }

  // 2. Siren Logic (Blue)
  if (micRaw > THRESH_SIREN) {
    digitalWrite(PIN_LED_SIREN, HIGH);
    Serial.print(" [SIREN]");
  } else {
    digitalWrite(PIN_LED_SIREN, LOW);
  }

  // 3. VOC/Gas Logic (Green)
  // If simulated gas level is high -> Warning
  if (gasRaw > THRESH_GAS) {
    digitalWrite(PIN_LED_GAS, HIGH);
    Serial.print(" [GAS WARNING]");
  } else {
    digitalWrite(PIN_LED_GAS, LOW);
  }

  Serial.println();
  delay(200);
}
