package com.aria.practica6.viewmodel

import androidx.lifecycle.ViewModel
import com.aria.practica6.ui.theme.AppColorTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {

    private val _colorTheme = MutableStateFlow(AppColorTheme.ESCOM) // Tema por defecto
    val colorTheme: StateFlow<AppColorTheme> = _colorTheme

    fun toggleTheme() {
        _colorTheme.value = when (_colorTheme.value) {
            AppColorTheme.ESCOM -> AppColorTheme.IPN
            AppColorTheme.IPN -> AppColorTheme.ESCOM
        }
    }

    fun setTheme(theme: AppColorTheme) {
        _colorTheme.value = theme
    }
}
