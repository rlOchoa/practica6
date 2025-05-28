# Práctica 6: Aplicación Android - Sensores, Cámara y Micrófono

**Nombre del paquete:** `com.aria.practica6`

**Entorno de desarrollo:** Android Studio 2024.2.2

**Compatibilidad:** Mínimo API 26 (Android 8.0), objetivo API 35 (VanillaIceCream)

**Tecnologías utilizadas:** Jetpack Compose, Kotlin DSL (build.gradle.kts), MVVM, CameraX, Accompanist Permissions

---

## ✅ Funcionalidades implementadas

* **📸 Acceso a cámara** con CameraX

  * Vista previa funcional en tiempo real
  * Captura de fotografías con almacenamiento en `MediaStore` (Android 10+)
  * Guardado en carpeta `DCIM/CameraPocha` (sillyname)

* **🎤 Acceso a micrófono**

  * Grabación manual desde UI
  * Grabación automática controlada por **sensor de proximidad**
  * Visualización del estado actual (grabando o no) con `MicrophoneStatusCard`

* **📡 Sensores integrados**

  * **Sensor de proximidad:** activa/desactiva grabación según cercanía del usuario
  * **Sensor de luz ambiental:** visible en HomeScreen (visualización en tiempo real)

* **🔐 Gestión de permisos** en tiempo de ejecución usando Accompanist

  * Cámara
  * Micrófono

* **🌓 Tema adaptativo** dependiente del sistema (modo claro/oscuro)

* **🎨 Selector de tema personalizado:** ESCOM (azul) o IPN (guinda)

* **🏗️ Arquitectura MVVM** con separación de UI y lógica de estado

  * `CameraViewModel`
  * `MicrophoneViewModel`
  * `SensorViewModel`

* **📱 Compatibilidad con Android 14+**

  * Uso de `MediaStore` para almacenamiento moderno
  * Manejo correcto de permisos en `AndroidManifest.xml`

---

## 🚧 Consideraciones y puntos no implementados

* ❌ No se integró el sensor de luz ambiental para control dinámico de exposición de cámara dado que parece que el teléfono restringe completamente esta implementación, ya que no se detecta ningún error en el IDE ni en LogCat.
* ❌ No se añadieron notificaciones persistentes para el uso de sensores.
* 🟡 No se utilizó `ForegroundService` para mantener grabaciones en segundo plano.

---

## 🗂️ Estructura de paquetes relevante

```
com.aria.practica6
├── camera
│   └── CameraXManager.kt
├── audio
│   └── AudioRecorderManager.kt
├── ui
│   ├── screen
│   │   ├── HomeScreen.kt
│   │   ├── CameraScreen.kt
│   │   └── MicrophoneScreen.kt
│   └── component
│       └── MicrophoneStatusCard.kt
├── viewmodel
│   ├── CameraViewModel.kt
│   ├── MicrophoneViewModel.kt
│   └── SensorViewModel.kt
└── ui.theme
    └── Theme.kt
```

---

## 🖼 Capturas de pantalla

| Vista de galería con audios/fotos guardados                                     | Sensores activos (tema azul)                                     |
| ------------------------------------------------------------------------------- | ---------------------------------------------------------------- |
| ![Screenshot_2025_05_28_02_07_37_569_com_mi_android_globalFileexplorer](https://github.com/user-attachments/assets/0d77d1be-716b-4b5d-9800-e560dda4fc1f) | ![Screenshot_2025-05-28-02-06-13-620_com aria practica6](https://github.com/user-attachments/assets/2604ed79-c703-4e85-91f9-2d7076c5bc8c) |

| Sensor proximidad activado                                       | Grabación activa                                                 |
| ---------------------------------------------------------------- | ---------------------------------------------------------------- |
| ![Screenshot_2025-05-28-02-06-24-991_com aria practica6](https://github.com/user-attachments/assets/af27597f-8885-4c02-ba22-e3776631525e) | ![Screenshot_2025-05-28-02-06-44-770_com aria practica6](https://github.com/user-attachments/assets/454acd55-38b7-4fd6-9216-7af631aa656d) |

| Grabación finalizada                                             | Configuración de tema                                            |
| ---------------------------------------------------------------- | ---------------------------------------------------------------- |
| ![Screenshot_2025-05-28-02-06-47-450_com aria practica6](https://github.com/user-attachments/assets/20dd9579-831d-4f24-9c81-0414a5d6e38b) | ![Screenshot_2025-05-28-02-06-53-569_com aria practica6](https://github.com/user-attachments/assets/7b175e7c-98a6-4d54-8ce8-882976325299) |

| Foto tomada con la app 📷      |
| ------------------------------ |
| ![photo_2025-05-28_02-22-30](https://github.com/user-attachments/assets/c10504e5-2d80-4202-b2be-e506f913f04a) |

---

## ✅ Conclusión

A pesar de las dificultades para crear esta aplicación, como el manejo de permisos dentro del android manifest, y los contantes errores al momento de utilizarse en un telefono real (dado que en emulador, los sensores no lograron funcionar), esta aplicación fue sorprendentemente más sencilla de realizar que prácticas anteriores, esta aplicación probablemente en posteriores revisiones se le implementen mejoras, como el manejo de cámara en "Modo Profesional" para cambio de lentes, WB, F, S, ISO, EV, etc.
