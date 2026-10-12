# Equipo 7 — Reproductor de Música 'SoundFlow' (Lista doblemente enlazada)

**Integrantes:** Onel Alejandro Olivera Díaz, Pedro Luis Sarmiento Torres, Daniela González Alemán, María Fernanda Rodríguez Díaz

**Problemática:** controlar la lista de reproducción (playlist) de un reproductor de audio digital (id, título, artista y duración).

**Estructura de datos:** lista doblemente enlazada implementada a mano (`ListaDobleEnlazada<T>`), con punteros `prev` y `siguiente` y referencias a cabeza y cola.

## Estructura de archivos

```
equipo7-soundflow/
├── README.md
└── src/
    ├── ListaDobleEnlazada.java  # Lista doblemente enlazada genérica (hecha a mano)
    ├── Cancion.java             # Datos de cada canción
    ├── SoundFlow.java            # Métodos a), b), c), d) de la guía
    ├── Main.java                # Demostración con 4 canciones de ejemplo
    └── Pruebas.java             # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo7-soundflow
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a d)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** recorrido directo e inverso, mover la última, una intermedia y la cola al inicio, verificando los punteros `prev`/`next` tras cada cambio; duración total; eliminación por artista.
- **Negativos:** canción inexistente, artista inexistente, duración negativa, ID duplicado.
- **Borde:** playlist vacía y eliminación de todas las canciones.

## Interpretaciones adoptadas

- La guía no da títulos ni duraciones: se usan 4 canciones de ejemplo (C01 a C04).
- `NavegarAdelanteYAtras` devuelve un texto con ambos recorridos (directo e inverso).
- `MoverCancionAlInicio` devuelve `true`/`false` según exista la canción (la guía no indica el tipo de retorno).
- `DuracionTotalPlaylist` devuelve el texto "X min Y s".
- `esConsistente()` es un método auxiliar que verifica los punteros `prev` y `next` para las pruebas.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
