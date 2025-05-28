package com.aria.practica6.viewmodel

import android.app.Application
import android.hardware.Sensor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.aria.practica6.model.SensorData
import com.aria.practica6.sensor.SensorManagerWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SensorViewModel(application: Application) : AndroidViewModel(application) {

    private val sensorWrapper = SensorManagerWrapper(application.applicationContext)

    private val _sensorStates = MutableStateFlow<Map<String, SensorData>>(emptyMap())
    val sensorStates: StateFlow<Map<String, SensorData>> = _sensorStates

    init {
        observeSensor("Proximidad", Sensor.TYPE_PROXIMITY)
        observeSensor("Luz Ambiental", Sensor.TYPE_LIGHT)
    }

    private fun observeSensor(name: String, type: Int) {
        viewModelScope.launch {
            sensorWrapper.getSensorData(type).collect { value ->
                _sensorStates.update { current ->
                    current + (name to SensorData(name, value))
                }
            }
        }
    }
}