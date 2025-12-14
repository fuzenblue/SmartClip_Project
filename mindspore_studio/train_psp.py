import numpy as np
import mindspore.nn as nn
import mindspore.ops as ops
import mindspore.context as context
from mindspore import Tensor
import mindspore.dataset as ds

# --- 1. Synthetic Data Generation ---
# Features: [Pressure_Slope, VOC_Slope, Audio_Energy, Flicker_Index]
# Label: [Risk_Score] (0.0 - 1.0)

def generate_psp_data(num_samples=1000):
    print("Generating Synthetic PSP Data...")
    
    # 1. Random Features
    # Pressure Slope: -2.0 to +2.0 (Drop is negative)
    pressure = np.random.uniform(-2.0, 2.0, num_samples).astype(np.float32)
    # VOC Slope: 0 to 100
    voc = np.random.uniform(0, 100, num_samples).astype(np.float32)
    # Audio Energy: 0 to 1.0
    audio = np.random.uniform(0, 1.0, num_samples).astype(np.float32)
    # Flicker Index: 0 to 1.0
    flicker = np.random.uniform(0, 1.0, num_samples).astype(np.float32)
    
    # 2. Logic for Ground Truth Risk (Rule-based to Train AI)
    # Formula: Risk = (PressureDrop * Weight) + (VOC * Weight) + ...
    # Heuristic Weights:
    # Pressure Drop (-1.0 or less) -> High Risk
    # VOC Spike (> 50) -> Medium Risk
    # Audio Siren (> 0.8) -> High Risk
    # Flicker (> 0.5) -> High Risk
    
    risk_logits = np.zeros(num_samples)
    
    # Apply rules
    risk_logits += (pressure < -0.5) * 2.0  # Significant Pressure Drop
    risk_logits += (voc > 30) * 1.5         # VOC Spike
    risk_logits += (audio > 0.7) * 1.0      # Loud Noise
    risk_logits += (flicker > 0.4) * 2.5    # Flicker
    
    # Normalize to 0-1 Sigmoid-like
    risk_labels = 1 / (1 + np.exp(-(risk_logits - 3.0))) # Bias shift
    risk_labels = risk_labels.astype(np.float32).reshape(-1, 1)
    
    # Combine features
    features = np.stack([pressure, voc, audio, flicker], axis=1)
    
    return features, risk_labels

# --- 2. Model Definition (Linear Regression / Perceptron) ---
class PSPModel(nn.Cell):
    def __init__(self):
        super(PSPModel, self).__init__()
        self.dense = nn.Dense(4, 1, activation='sigmoid')

    def construct(self, x):
        return self.dense(x)

# --- 3. Training Loop ---
def train_psp():
    context.set_context(device_target="CPU") # Simple CPU training
    
    features, labels = generate_psp_data()
    
    # Create Dataset
    dataset = ds.NumpySlicesDataset({"data": features, "label": labels}, shuffle=True)
    dataset = dataset.batch(32)
    
    net = PSPModel()
    loss_fn = nn.MSELoss()
    optimizer = nn.Adam(net.trainable_params(), learning_rate=0.01)
    
    model = nn.TrainOneStepCell(nn.WithLossCell(net, loss_fn), optimizer)
    
    print("Starting PSP Training...")
    epochs = 100
    for epoch in range(epochs):
        step = 0
        total_loss = 0
        for d in dataset.create_dict_iterator():
            loss = model(d["data"], d["label"])
            total_loss += loss.asnumpy()
            step += 1
        
        if epoch % 10 == 0:
            print(f"Epoch {epoch}: Avg Loss = {total_loss/step:.4f}")
            
    print("Training Complete.")
    
    # Print Learned Weights
    print("\n--- Learned Parameters ---")
    for param in net.trainable_params():
        print(f"Name: {param.name}")
        print(f"Value: {param.data.asnumpy()}")
        
    print("\nUse these values to update native-lib.cpp!")

if __name__ == "__main__":
    try:
        train_psp()
    except Exception as e:
        print(f"Error: {e}")
