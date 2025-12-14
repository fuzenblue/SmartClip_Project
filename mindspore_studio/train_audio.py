import os
import numpy as np
import mindspore as ms
import mindspore.nn as nn
import mindspore.ops as ops
from mindspore import context, Tensor
from mindspore.train.callback import LossMonitor
import wave

# Configuration
DATASET_PATH = "./datasets/audio"
CLASSES = ["silence", "siren", "construction", "traffic"]
SAMPLE_RATE = 16000
DURATION = 1.0 # Seconds
EPOCHS = 50
BATCH_SIZE = 32

# Set MindSpore Context
context.set_context(mode=context.GRAPH_MODE, device_target="CPU")

class AudioDataset:
    """
    Custom Dataset loader for Audio files. 
    In a real scenario, this would use librosa to load .wav files and compute MFCCs.
    Here we mock the data loading for demonstration if files are missing.
    """
    def __init__(self, data_dir, classes):
        self.data_dir = data_dir
        self.classes = classes
        self.data = []
        self.labels = []
        self._load_data()

    def _load_data(self):
        print(f"Loading data from {self.data_dir}...")
        # Mocking data loading logic
        # For each class, look for files. If none, generate random features
        for idx, class_name in enumerate(self.classes):
            class_path = os.path.join(self.data_dir, class_name)
            if not os.path.exists(class_path):
                os.makedirs(class_path, exist_ok=True)
            
            files = os.listdir(class_path)
            if not files:
                print(f"No files found for {class_name}, generating 100 mock samples.")
                # Generate 100 random samples: Shape [1, 40, 100] (Channel, MFCC, Time)
                for _ in range(100):
                    feature = np.random.randn(1, 40, 50).astype(np.float32)
                    self.data.append(feature)
                    self.labels.append(np.int32(idx))
            else:
                # Real implementation: Load wav and compute simple Spectrogram/FFT features
                print(f"Found {len(files)} files in {class_name}. Processing...")
                for file_name in files:
                    if not file_name.endswith('.wav'): continue
                    
                    file_path = os.path.join(class_path, file_name)
                    try:
                        # Load audio using standard libraries (wave)
                        with wave.open(file_path, 'rb') as wf:
                            # Verify format
                            n_channels = wf.getnchannels()
                            sampwidth = wf.getsampwidth()
                            framerate = wf.getframerate()
                            n_frames = wf.getnframes()
                            
                            # Read raw bytes
                            raw_data = wf.readframes(n_frames)
                            
                            # Convert to numpy array
                            if sampwidth == 2:
                                dtype = np.int16
                            elif sampwidth == 1:
                                dtype = np.int8
                            else:
                                raise ValueError("Unsupported bit depth")
                                
                            y = np.frombuffer(raw_data, dtype=dtype).astype(np.float32)
                            
                            # Normalize
                            if sampwidth == 2:
                                y /= 32768.0
                            elif sampwidth == 1:
                                y = (y - 128) / 128.0
                                
                            # Mono conversion
                            if n_channels > 1:
                                y = y.reshape(-1, n_channels).mean(axis=1)
                        
                        # Resample explanation: Assuming 16k input as per requirement.
                        # For robustness, we could resample, but for now we skip complex resampling
                        # and rely on the dataset being correct or simple decimation.
                                
                        # Feature Extraction: Short-Time Fourier Transform (STFT) equivalent
                        # Target Shape: [1, 40, 50] -> 40 frequency bins, 50 time steps
                        
                        target_time_steps = 50
                        hop_length = len(y) // target_time_steps
                        if hop_length < 1: hop_length = 1
                        
                        spectrogram = []
                        for i in range(target_time_steps):
                            start = i * hop_length
                            end = start + hop_length
                            chunk = y[start:end]
                            
                            if len(chunk) < 10:
                                spec = np.zeros(40)
                            else:
                                # Windowing
                                window = np.hanning(len(chunk))
                                val = chunk * window
                                # FFT
                                fft_val = np.abs(np.fft.rfft(val))
                                # Take first 40 bins (Low frequency focus)
                                if len(fft_val) >= 40:
                                    spec = fft_val[:40]
                                else:
                                    spec = np.pad(fft_val, (0, 40 - len(fft_val)))
                                
                                # Log-transform for stability (Log-Spectrogram)
                                spec = np.log(spec + 1e-6)
                            
                            spectrogram.append(spec)
                        
                        # Convert to array [50, 40]
                        spectrogram = np.array(spectrogram).T # -> [40, 50]
                        
                        # Add channel dimension [1, 40, 50]
                        feature = spectrogram[np.newaxis, ...].astype(np.float32)
                        
                        self.data.append(feature)
                        self.labels.append(np.int32(idx))
                        
                    except Exception as e:
                        print(f"Error processing {file_name}: {e}")

    def __getitem__(self, index):
        return self.data[index], self.labels[index]

    def __len__(self):
        return len(self.data)

class TinyCNN(nn.Cell):
    """
    A small 1D CNN model for audio classification on MCUs.
    Structure: Conv2d -> ReLU -> MaxPool -> Flatten -> Dense
    """
    def __init__(self, num_classes=4):
        super(TinyCNN, self).__init__()
        # Input shape expected: [Batch, 1, MFCC_Bands, TimeSteps]
        # Using Conv2d for spectrogram
        self.conv1 = nn.Conv2d(1, 16, kernel_size=3, pad_mode='pad', padding=1)
        self.relu = nn.ReLU()
        self.pool = nn.MaxPool2d(kernel_size=2, stride=2)
        self.conv2 = nn.Conv2d(16, 32, kernel_size=3, pad_mode='pad', padding=1)
        self.flatten = nn.Flatten()
        # Calculate linear size based on input [1, 40, 50] -> Pool -> [16, 20, 25] -> Pool -> [32, 10, 12]
        self.fc1 = nn.Dense(32 * 10 * 12, 64)
        self.fc2 = nn.Dense(64, num_classes)

    def construct(self, x):
        x = self.conv1(x)
        x = self.relu(x)
        x = self.pool(x)
        
        x = self.conv2(x)
        x = self.relu(x)
        x = self.pool(x)
        
        x = self.flatten(x)
        x = self.fc1(x)
        x = self.relu(x)
        x = self.fc2(x)
        return x

def train():
    print("Initializing Training Pipeline...")
    
    # 1. Prepare Dataset
    dataset_generator = AudioDataset(DATASET_PATH, CLASSES)
    # Convert to MindSpore Dataset
    loader = ms.dataset.GeneratorDataset(dataset_generator, ["data", "label"], shuffle=True)
    loader = loader.batch(BATCH_SIZE)

    # 2. Define Model
    network = TinyCNN(num_classes=len(CLASSES))
    print("Model Structure defined.")

    # 3. Loss & Optimizer
    loss_fn = nn.SoftmaxCrossEntropyWithLogits(sparse=True, reduction='mean')
    optimizer = nn.Adam(network.trainable_params(), learning_rate=0.0005)

    # 4. Train
    model = ms.Model(network, loss_fn=loss_fn, optimizer=optimizer, metrics={'accuracy'})
    
    print(f"Starting Training for {EPOCHS} epochs...")
    model.train(EPOCHS, loader, callbacks=[LossMonitor(per_print_times=10)])
    
    # 5. Export
    print("Exporting model to 'audio_model.mindir'...")
    # Create dummy input for export
    input_spec = Tensor(np.zeros([1, 1, 40, 50], np.float32))
    ms.export(network, input_spec, file_name='audio_model', file_format='MINDIR')
    print("Model exported successfully.")

if __name__ == "__main__":
    try:
        train()
    except Exception as e:
        print(f"Training failed (Likely missing libraries on this environment): {e}")
        print("Please ensure 'mindspore' and 'numpy' are installed.")
