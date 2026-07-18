# Registro de Empleados - Examen Final

Aplicación Android para gestionar el registro de empleados de una empresa.
Desarrollada con Kotlin y Jetpack Compose.

## Funcionalidades

- Registro de empleados con nombre completo, cargo, departamento, salario y
  fecha de contratación
- Lista vertical de empleados registrados (LazyColumn)
- Cada ítem muestra el nombre destacado arriba y sus demás datos en una fila
  horizontal deslizable (LazyRow)
- Eliminación individual de empleados
- Persistencia de datos ante cambios de configuración (rotación de pantalla)
  mediante ViewModel y rememberSaveable
- Tema personalizado con soporte para modo claro y oscuro
- Registro en Logcat de los métodos del ciclo de vida de la Activity
  (onStart, onStop, onDestroy)

## Estructura del proyecto

```
app/src/main/java/com/example/registroempleados/
├── MainActivity.kt
│   ├── Empleado              → data class con los datos de un empleado
│   ├── EmpleadosViewModel     → ViewModel con MutableStateFlow<List<Empleado>>
│   ├── MainActivity            → Activity principal, contiene ademas
│   │                              onStart/onStop/onDestroy con Log.i
│   ├── PantallaPrincipal       → LazyColumn que agrupa formulario y lista
│   ├── FormularioEmpleado      → formulario de ingreso (campos con
│   │                              rememberSaveable)
│   └── ItemEmpleado            → cada card de la lista, con LazyRow y boton
│                                  de eliminacion
└── ui/theme/
    ├── Color.kt                → paleta de colores (claro/oscuro)
    ├── Theme.kt                 → RegistroEmpleadosTheme
    └── Type.kt                  → tipografia personalizada
```

## Arquitectura

Los datos (lista de empleados) se manejan en `EmpleadosViewModel`, separado
de la interfaz. Esto sigue las practicas recomendadas de arquitectura de
apps para Android: la UI (Composables) solo muestra el estado y notifica
eventos, mientras que el ViewModel mantiene los datos vivos durante toda la
vida de la Activity, incluso si esta se destruye y recrea (por ejemplo al
rotar la pantalla). Los campos del formulario usan `rememberSaveable` por
la misma razon, a nivel mas local.

## Tema personalizado

El tema fue generado con Material Design Theme Builder
(m3.material.io/theme-builder), exportado en formato Jetpack Compose y
aplicado en la carpeta `ui/theme/`. Se adapta automaticamente al modo claro
u oscuro segun la configuracion del sistema operativo.

## Ciclo de vida

`MainActivity` sobrescribe `onStart()`, `onStop()` y `onDestroy()`, cada uno
registrando un mensaje en Logcat con `Log.i(TAG, "nombre del metodo")`, para
poder observar el ciclo de vida de la Activity durante pruebas manuales
(minimizar la app, rotarla, cerrarla).

## Tecnologías utilizadas

- Lenguaje: Kotlin
- UI: Jetpack Compose
- Arquitectura: ViewModel + StateFlow
- Sistema de diseño: Material Design 3

## Cómo ejecutar la aplicación

1. Clonar el repositorio:
   ```
   git clone https://github.com/seitan-moon/iapdm-oto-2026-yanira-cano.git
   ```
2. Abrir la carpeta del proyecto con Android Studio
3. Esperar a que finalice la sincronización de Gradle
4. Conectar un dispositivo físico (recomendado) o un emulador
5. Ejecutar la app (botón Run)