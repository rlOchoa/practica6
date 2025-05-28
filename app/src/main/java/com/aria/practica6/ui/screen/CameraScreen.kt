package com.aria.practica6.ui.screen

import android.Manifest
import android.content.Context
import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aria.practica6.ui.component.CameraOverlay
import com.aria.practica6.viewmodel.CameraViewModel
import com.aria.practica6.viewmodel.SensorViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }

    if (cameraPermission.status.isGranted) {
        CameraPreviewWithOverlay(context)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("Permiso de cámara requerido.")
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun CameraPreviewWithOverlay(
    context: Context,
    sensorViewModel: SensorViewModel = viewModel(),
    cameraViewModel: CameraViewModel = viewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val sensors by sensorViewModel.sensorStates.collectAsState()
    val lightValue = sensors["Luz Ambiental"]?.value ?: 0f
    val lastImageUri by cameraViewModel.lastImageUri.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)

    var previewView: PreviewView? by remember { mutableStateOf(null) }

    // Vincular la cámara solo si ya se tiene permiso y la vista está lista
    LaunchedEffect(cameraPermission.status.isGranted, previewView) {
        if (cameraPermission.status.isGranted && previewView != null) {
            cameraViewModel.cameraManager.bindPreview(previewView!!, lifecycleOwner)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Button(
                onClick = {
                    cameraViewModel.takePhoto { error ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(error)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Tomar Foto")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PreviewView(ctx).also { view ->
                        view.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        view.scaleType = PreviewView.ScaleType.FILL_CENTER
                        view.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        previewView = view
                    }
                }
            )

            // Overlay parcial, con transparencia o efectos livianos
            CameraOverlay(lightValue = lightValue)
        }
    }
}