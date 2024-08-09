package org.lotka.xenonx.presentation.screen.camera.compose

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.core.content.ContextCompat


 fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
    applicationContext: android.content.Context
) {

    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                // Get rotation degrees from the image
                val rotationDegrees = image.imageInfo.rotationDegrees.toFloat()

                // Convert ImageProxy to Bitmap
                val bitmap =
                    image.toBitmap() // Assume this function correctly converts ImageProxy to Bitmap

                // Create a Matrix for rotation
                val matrix = Matrix().apply {
                    postRotate(rotationDegrees)
                }

                // Calculate the rotated bitmap size
                val rotatedBitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    0,
                    bitmap.width,
                    bitmap.height,
                    matrix,
                    true
                )

                // Handle the case when the rotation results in invalid dimensions
                if (rotatedBitmap.width > 0 && rotatedBitmap.height > 0) {
                    onPhotoTaken(rotatedBitmap)
                } else {
                    Log.e(
                        "Camera",
                        "Invalid dimensions after rotation: width = ${rotatedBitmap.width}, height = ${rotatedBitmap.height}"
                    )
                }

                image.close() // Don't forget to close the image to free resources
            }


            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}