#include <android/log.h>
#include <jni.h>
#include <math.h>
#include <string>


// ... (keep initModel as is)

extern "C" JNIEXPORT jfloat JNICALL
Java_com_example_smartclip_ai_PSPPredictor_runInference(
    JNIEnv *env, jobject /* this */, jlong modelHandle,
    jfloatArray inputFeatures) {

  // Get array elements
  jfloat *features = env->GetFloatArrayElements(inputFeatures, 0);

  // Learned Weights from MindSpore training
  // Features: [Pressure_Slope, VOC_Slope, Audio_Energy, Flicker_Index]
  const float w0 = -0.591902f; // Pressure
  const float w1 = 0.013285f;  // VOC
  const float w2 = 0.982292f;  // Audio
  const float w3 = 3.668700f;  // Flicker
  const float b = -2.518616f;  // Bias

  // Linear Combination: z = Wx + b
  float z = (features[0] * w0) + (features[1] * w1) + (features[2] * w2) +
            (features[3] * w3) + b;

  // Sigmoid Activation: 1 / (1 + e^-z)
  float risk = 1.0f / (1.0f + exp(-z));

  // Release array
  env->ReleaseFloatArrayElements(inputFeatures, features, 0);

  return risk;
}
