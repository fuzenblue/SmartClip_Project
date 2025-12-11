package com.example.smartclip.ai

import android.content.res.AssetManager

class PSPPredictor {

    // Load native library
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private var modelHandle: Long = 0

    // Native methods
    external fun initModel(assetManager: AssetManager, fileName: String): Long
    external fun runInference(modelHandle: Long, features: FloatArray): Float

    fun initialize(assets: AssetManager) {
        modelHandle = initModel(assets, "psp_model.ms")
    }

    fun predictOutcome(features: FloatArray): Float {
        if (modelHandle == 0L) return 0.0f
        return runInference(modelHandle, features)
    }
}
