# 🎓 UniChile — Campus Digital

Aplicación móvil universitaria desarrollada en **Java** para **Android Studio**, orientada a estudiantes de X universidad en Chile. Permite gestionar información académica, horarios, noticias institucionales y acceder al mapa del campus, todo desde el dispositivo móvil.

---

## 📋 Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Base de Datos](#base-de-datos)
- [Módulos de la Aplicación](#módulos-de-la-aplicación)
- [Credenciales de Prueba](#credenciales-de-prueba)
- [Capturas de Pantalla](#capturas-de-pantalla)
- [Autores](#autores)

---

## 📌 Descripción General

**UniChile** es una app Android de tipo campus digital que centraliza los servicios académicos más importantes para el estudiante universitario chileno. Utiliza **SQLite** como base de datos local para persistencia de datos y **SharedPreferences** para gestión de sesión. La interfaz sigue los lineamientos de **Material Design** con una paleta de colores institucional azul/rojo.

---

## ✨ Características

- 🔐 **Login seguro** con validación contra base de datos SQLite y sesión persistente
- 🏠 **Panel de inicio** con saludo dinámico, promedio general, próxima clase del día y últimas noticias
- 📊 **Notas académicas** por asignatura con detalle expandible de evaluaciones y promedio ponderado
- 🗓️ **Horario semanal** con selector de días y visualización de sala, horario y docente
- 📰 **Noticias institucionales** categorizadas con badges de colores
- 🗺️ **Mapa del campus** interactivo usando Leaflet.js sobre OpenStreetMap, centrado en Santiago de Chile
- 🚀 **Splash screen** con redirección automática según estado de sesión

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 8 | Lenguaje principal |
| Android Studio | Hedgehog / Iguana | IDE de desarrollo |
| Android SDK | API 34 (Android 14) | Target |
| Android SDK mínimo | API 24 (Android 7.0) | Compatibilidad |
| SQLite | — | Base de datos local |
| Material Components | 1.11.0 | UI / Design System |
| ViewBinding | — | Enlace de vistas |
| RecyclerView | 1.3.2 | Listas dinámicas |
| CardView | 1.0.0 | Tarjetas de contenido |
| Leaflet.js | 1.9.4 | Mapa interactivo (WebView) |
| SharedPreferences | — | Gestión de sesión |

---

## ✅ Requisitos Previos

- Android Studio **Hedgehog (2023.1.1)** o superior
- JDK **17** o superior
- Gradle **8.4**
- Dispositivo físico o emulador con Android **7.0 (API 24)** o superior
- Conexión a internet (para cargar el mapa Leaflet desde CDN)

---

## ⚙️ Instalación y Configuración

### 1. Clonar o descomprimir el proyecto

```bash
# Si descargaste el ZIP, descomprime y abre la carpeta en Android Studio
# File → Open → Selecciona la carpeta UniChileApp
```

### 2. Sincronizar Gradle

Al abrir el proyecto, Android Studio detectará automáticamente el `build.gradle`. Haz clic en **"Sync Now"** cuando aparezca la notificación, o ve a:

```
File → Sync Project with Gradle Files
```

### 3. Ejecutar la aplicación

Conecta un dispositivo físico o inicia un emulador, luego presiona ▶️ **Run**.

```
Run → Run 'app'  (Shift + F10)
```

> ⚠️ Si el mapa no carga, verifica que el emulador tenga acceso a internet activo.

---

## 📁 Estructura del Proyecto

```
UniChileApp/
├── app/
│   ├── build.gradle                        # Dependencias del módulo
│   └── src/main/
│       ├── AndroidManifest.xml             # Declaración de actividades y permisos
│       ├── assets/
│       │   └── mapa.html                   # Mapa interactivo Leaflet.js
│       ├── java/com/unichile/app/
│       │   ├── activities/
│       │   │   ├── SplashActivity.java     # Pantalla de carga inicial
│       │   │   ├── LoginActivity.java      # Autenticación de estudiante
│       │   │   └── MainActivity.java       # Contenedor principal con BottomNav
│       │   ├── fragments/
│       │   │   ├── HomeFragment.java       # Dashboard principal
│       │   │   ├── NotasFragment.java      # Notas por asignatura
│       │   │   ├── HorarioFragment.java    # Horario semanal
│       │   │   ├── NoticiasFragment.java   # Noticias institucionales
│       │   │   └── MapaFragment.java       # Mapa del campus (WebView)
│       │   ├── adapters/
│       │   │   ├── NotasAdapter.java       # RecyclerView notas expandibles
│       │   │   ├── HorarioAdapter.java     # RecyclerView bloques de clase
│       │   │   └── NoticiasAdapter.java    # RecyclerView tarjetas de noticias
│       │   ├── database/
│       │   │   └── DBHelper.java           # SQLiteOpenHelper + datos de prueba
│       │   ├── models/
│       │   │   ├── Estudiante.java
│       │   │   ├── Materia.java
│       │   │   ├── Nota.java
│       │   │   └── Noticia.java
│       │   └── utils/
│       │       └── SessionManager.java     # SharedPreferences de sesión
│       └── res/
│           ├── layout/                     # 11 layouts XML
│           ├── drawable/                   # Íconos vectoriales
│           ├── menu/                       # Menú BottomNavigationView
│           ├── values/                     # Colors, strings, themes
│           └── color/                      # Selector BottomNav
├── build.gradle                            # Configuración raíz
├── settings.gradle
└── gradle.properties
```

---

## 🗄️ Base de Datos

La base de datos se crea automáticamente al instalar la app mediante `DBHelper.java` (extiende `SQLiteOpenHelper`). Contiene 4 tablas con datos de prueba precargados.

### Diagrama de Tablas

```
┌─────────────────────┐     ┌──────────────────────────┐
│     estudiantes      │     │         materias          │
├─────────────────────┤     ├──────────────────────────┤
│ id       INTEGER PK │     │ id         INTEGER PK     │
│ nombre   TEXT       │     │ nombre     TEXT           │
│ apellido TEXT       │     │ profesor   TEXT           │
│ email    TEXT UNIQUE│     │ sala       TEXT           │
│ password TEXT       │     │ dia        TEXT           │
│ carrera  TEXT       │     │ hora_inicio TEXT          │
│ semestre INTEGER    │     │ hora_fin   TEXT           │
│ rut      TEXT       │     │ color      TEXT           │
└─────────────────────┘     │ creditos   INTEGER        │
                            └──────────┬───────────────┘
                                       │ 1:N
                            ┌──────────▼───────────────┐
                            │          notas            │
                            ├──────────────────────────┤
                            │ id          INTEGER PK    │
                            │ materia_id  INTEGER FK    │
                            │ evaluacion  TEXT          │
                            │ valor       REAL          │
                            │ porcentaje  INTEGER       │
                            │ fecha       TEXT          │
                            └──────────────────────────┘

┌─────────────────────┐
│       noticias       │
├─────────────────────┤
│ id          INTEGER PK│
│ titulo      TEXT    │
│ descripcion TEXT    │
│ fecha       TEXT    │
│ categoria   TEXT    │
└─────────────────────┘
```

### Datos de Prueba Precargados

- **1 estudiante** de Ingeniería en Informática, semestre 5
- **5 asignaturas** con doble sesión semanal (10 registros en tabla `materias`)
- **18 notas** distribuidas entre las asignaturas con porcentajes ponderados
- **6 noticias** institucionales de distintas categorías

---

## 📱 Módulos de la Aplicación

### 🔐 Login
Valida credenciales contra la tabla `estudiantes` en SQLite. Si el login es exitoso, guarda la sesión con `SessionManager` (SharedPreferences). La sesión persiste entre cierres de la app.

### 🏠 Inicio (HomeFragment)
- Saludo dinámico según la hora del día (mañana / tarde / noche)
- Promedio general calculado desde todas las asignaturas
- Próxima clase del día actual (obtenida del día de la semana en tiempo real)
- Vista previa de las 2 últimas noticias con acceso rápido al módulo completo
- Mantener presionado el nombre abre el diálogo de cierre de sesión

### 📊 Notas (NotasFragment)
- Lista de asignaturas con promedio ponderado visible en badge de colores:
  - 🟢 Verde: nota ≥ 5.5
  - 🟡 Amarillo: nota entre 4.0 y 5.4
  - 🔴 Rojo: nota < 4.0
- Toca cualquier asignatura para expandir el detalle de evaluaciones
- Cálculo automático: `Σ(valor × porcentaje / 100)`

### 🗓️ Horario (HorarioFragment)
- Selector de días Lunes a Viernes con botones dinámicos
- Al abrir, selecciona automáticamente el día actual
- Muestra hora de inicio/fin, nombre de la asignatura, sala y docente
- Mensaje especial si no hay clases ese día

### 📰 Noticias (NoticiasFragment)
- Tarjetas de noticias con badge de categoría:

| Categoría | Color |
|---|---|
| Académico | Azul |
| Eventos | Verde |
| Infraestructura | Naranja |
| Becas | Morado |
| Deportes | Verde oscuro |
| Servicios | Índigo |

### 🗺️ Mapa (MapaFragment)
Carga `assets/mapa.html` en un `WebView` con JavaScript habilitado. Usa **Leaflet.js + OpenStreetMap** con 7 marcadores del campus en Santiago:

- 🏛️ Casa Central (Av. Libertador B. O'Higgins 1058)
- 📚 Biblioteca Central
- 💻 Laboratorio de Informática
- 🍽️ Casino Universitario
- ⚽ Estadio Universitario
- 📋 Dirección de Estudios
- 🏥 Centro de Salud Estudiantil

---

## 🔑 Credenciales de Prueba

```
Correo:     estudiante@unichile.cl
Contraseña: 12345
```

---

## 🖼️ Capturas de Pantalla

```
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│   Splash     │  │    Login     │  │    Inicio    │
│              │  │              │  │              │
│  🎓 UniChile │  │  📧 Correo   │  │  Hola 👋     │
│  Campus      │  │  🔒 Password │  │  Luciel      │
│  Digital     │  │  [Ingresar]  │  │  Prom: 5.6   │
└──────────────┘  └──────────────┘  └──────────────┘

┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│    Notas     │  │   Horario    │  │   Noticias   │
│              │  │              │  │              │
│ Bases de     │  │ Lun Mar Mié  │  │ [Académico]  │
│ Datos   6.0▼ │  │ 08:00-09:30  │  │ Matrícula    │
│ ▸ Control 6.5│  │ Estructuras  │  │ 2026...      │
│ ▸ Proyecto..│  │ Lab 301      │  │              │
└──────────────┘  └──────────────┘  └──────────────┘
```

---

## 👩‍💻 Autores

Desarrollado como proyecto académico para curso de **Desarrollo de Aplicaciones Móviles**.

| Nombre | Rol |
|---|---|

| Docente Sabina Romero|  Supervisión técnica  | Desarrollador/a principal
 
---

## 📄 Licencia

Este proyecto es de uso académico. Todos los derechos reservados © Sabina Romero 2026

---

> 💡 **Tip para extender el proyecto:** Para conectar a una API REST real, reemplaza las consultas SQLite en `DBHelper.java` por llamadas con `HttpURLConnection` o agrega la dependencia **Retrofit 2** en `build.gradle`.