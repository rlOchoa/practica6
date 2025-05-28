package com.aria.practica6.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.aria.practica6.camera.CameraXManager
import java.io.File
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    val cameraManager = CameraXManager(context)

    private val _lastImageUri = MutableStateFlow<Uri?>(null)
    val lastImageUri: StateFlow<Uri?> = _lastImageUri

    fun takePhoto(onError: (String) -> Unit) {
        cameraManager.takePhoto(
            onImageSaved = { uri ->
                _lastImageUri.update { uri }
            },
            onError = onError
        )
    }
}