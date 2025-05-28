package com.aria.practica6.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aria.practica6.ui.theme.AppColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    currentTheme: AppColorTheme,
    onThemeSelected: (AppColorTheme) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración de Tema") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Selecciona el tema de color:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            ThemeOption(
                label = "Azul ESCOM",
                selected = currentTheme == AppColorTheme.ESCOM,
                onSelect = { onThemeSelected(AppColorTheme.ESCOM) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            ThemeOption(
                label = "Guinda IPN",
                selected = currentTheme == AppColorTheme.IPN,
                onSelect = { onThemeSelected(AppColorTheme.IPN) }
            )
        }
    }
}

@Composable
fun ThemeOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
    }
}
