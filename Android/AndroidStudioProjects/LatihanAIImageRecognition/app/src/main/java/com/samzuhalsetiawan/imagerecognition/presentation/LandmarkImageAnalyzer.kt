package com.samzuhalsetiawan.imagerecognition.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.samzuhalsetiawan.imagerecognition.domain.Classification
import com.samzuhalsetiawan.imagerecognition.domain.LandmarkClassifier

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResult: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 120 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(256, 256)

            val result = classifier.classify(bitmap, rotationDegrees)
            onResult(result)
        }
        frameSkipCounter++
        image.close()
    }
}