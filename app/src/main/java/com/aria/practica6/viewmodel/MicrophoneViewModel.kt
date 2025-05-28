package com.aria.practica6.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aria.practica6.audio.AudioRecorderManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MicrophoneViewModel(
    private val recorderManager: AudioRecorderManager,
    private val sensorViewModel: SensorViewModel
) : ViewModel() {

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording

    private val _lastAudioUri = MutableStateFlow<Uri?>(null)
    val lastAudioUri: StateFlow<Uri?> = _lastAudioUri

    init {
        observeProximitySensor()
    }

    private fun observeProximitySensor() {
        viewModelScope.launch {
            sensorViewModel.sensorStates
                .map { it["Proximidad"]?.value ?: -1f }
                .distinctUntilChanged()
                .collect { proximityValue ->
                    if (proximityValue in 0.0f..1.0f && !recorderManager.isCurrentlyRecording()) {
                        recorderManager.startRecording(
                            onStart = { uri ->
                                _isRecording.value = true
                                _lastAudioUri.value = uri
                            },
                            onError = { error ->
                                // registrar error
                            }
                        )
                    } else if (proximityValue > 1.0f && recorderManager.isCurrentlyRecording()) {
                        recorderManager.stopRecording(
                            onStop = { uri ->
                                _isRecording.value = false
                                _lastAudioUri.value = uri
                            },
                            onError = { error ->
                                // registrar error
                            }
                        )
                    }
                }
        }
    }

    fun startRecording(onStart: (Uri) -> Unit, onError: (String) -> Unit) {
        recorderManager.startRecording(
            onStart = { uri ->
                _isRecording.value = true
                _lastAudioUri.value = uri
                onStart(uri)
            },
            onError = { error ->
                onError(error)
            }
        )
    }

    fun stopRecording(onStop: (Uri?) -> Unit, onError: (String) -> Unit) {
        recorderManager.stopRecording(
            onStop = { uri ->
                _isRecording.value = false
                _lastAudioUri.value = uri
                onStop(uri)
            },
            onError = { error ->
                onError(error)
            }
        )
    }
}
