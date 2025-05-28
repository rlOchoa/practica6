package com.aria.practica6.audio

import android.content.ContentValues
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AudioRecorderManager(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var currentUri: Uri? = null

    fun startRecording(onStart: (Uri) -> Unit, onError: (String) -> Unit) {
        try {
            val resolver = context.contentResolver
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())

            val contentValues = ContentValues().apply {
                put(MediaStore.Audio.Media.DISPLAY_NAME, "AUDIO_$timestamp.m4a")
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp4")
                put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/PochaAudio")
                put(MediaStore.Audio.Media.IS_MUSIC, 1)
            }

            val audioUri = resolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (audioUri == null) {
                onError("No se pudo crear el archivo de grabación.")
                return
            }

            currentUri = audioUri

            val parcelFileDescriptor = resolver.openFileDescriptor(audioUri, "w") ?: run {
                onError("No se pudo abrir el archivo de grabación.")
                return
            }

            val fileDescriptor = parcelFileDescriptor.fileDescriptor

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(fileDescriptor)
                prepare()
                start()
            }

            onStart(audioUri)

        } catch (e: Exception) {
            Log.e("AudioRecorder", "Error al iniciar grabación: ${e.message}", e)
            onError("Error al iniciar grabación: ${e.message}")
        }
    }

    fun stopRecording(onStop: (Uri?) -> Unit, onError: (String) -> Unit) {
        try {
            mediaRecorder?.apply {
                stop()
                reset()
                release()
            }
            mediaRecorder = null
            onStop(currentUri)
            currentUri = null
        } catch (e: Exception) {
            Log.e("AudioRecorder", "Error al detener grabación: ${e.message}", e)
            onError("Error al detener grabación: ${e.message}")
        }
    }

    fun isCurrentlyRecording(): Boolean {
        return mediaRecorder != null
    }
}