# Equipo 2 — Aerolínea 'SkyFly' (Lista simplemente enlazada)

**Integrantes:** Katherine Alvarez Amador, Adrian Jesus Pelaez Rodriguez, Adrian Enrique Reinoso Gómez, Edgar Guillermo Hernández Domínguez, Alejandro Javier Montesino Ruiz

**Problemática:** controlar los vuelos programados (número, destino, estado y duración en minutos).

**Estructura de datos:** lista simplemente enlazada implementada a mano (`ListaEnlazada<T>`), sin colecciones de `java.util`.

## Estructura de archivos

```
equipo2-skyfly/
├── README.md
└── src/
    ├── ListaEnlazada.java   # Lista simplemente enlazada genérica (hecha a mano)
    ├── Vuelo.java           # Datos de cada vuelo y estados válidos
    ├── SkyFly.java   # Métodos a), b), c), d), e) de la guía
    ├── Main.java            # Demostración con los datos de la guía
    └── Pruebas.java         # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo2-skyfly
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** demorados, orden por duración, cancelación por destino, cambio de estado y vuelo más largo.
- **Negativos:** destino o número inexistente, estado no válido, duración negativa, número duplicado.
- **Borde:** lista vacía en todos los métodos y comparación de destino sin distinguir mayúsculas.

## Interpretaciones adoptadas

- Los números de vuelo no se repiten.
- `CancelarVuelosADestino` implementa la descripción de la guía ("elimina todos los vuelos"), aunque el nombre diga "cancelar".
- `MostrarOrdenadosPorDuracion` devuelve una copia ordenada; la lista original no cambia.
- `BuscarVueloMasLargo` devuelve `null` si la lista está vacía.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
