package com.example.smartclip.ai

import android.content.res.AssetManager

class PSPPredictor {

    // Load native library
    companion object {
        var isNativeLoaded = false
        init {
            try {
                System.loadLibrary("native-lib")
                isNativeLoaded = true
            } catch (e: Throwable) {
                // Library failed to load
                isNativeLoaded = false
            }
        }
    }

    private var modelHandle: Long = 0

    // Native methods
    external fun initModel(assetManager: AssetManager, fileName: String): Long
    external fun runInference(modelHandle: Long, features: FloatArray): Float

    fun initialize(assets: AssetManager) {
        if (isNativeLoaded) {
            modelHandle = initModel(assets, "psp_model.ms")
        }
    }

    fun predictOutcome(features: FloatArray): Float {
        if (!isNativeLoaded || modelHandle == 0L) return 0.0f
        return runInference(modelHandle, features)
    }
}
