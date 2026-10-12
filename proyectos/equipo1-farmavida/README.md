# Equipo 1 — Farmacia 'FarmaVida' (Lista simplemente enlazada)

**Integrantes:** Yaisel Pinos Durán, Merlyn Sosa Borges, Geonel Sánchez, Jorge Luis Reyes Pérez, Ray Sanjudo Mesa

**Problemática:** controlar los medicamentos de la farmacia (código, nombre, categoría, stock y precio).

**Estructura de datos:** lista simplemente enlazada implementada a mano (`ListaEnlazada<T>`), sin colecciones de `java.util`.

## Estructura de archivos

```
equipo1-farmavida/
├── README.md
└── src/
    ├── ListaEnlazada.java   # Lista simplemente enlazada genérica (hecha a mano)
    ├── Medicamento.java     # Datos de cada medicamento
    ├── FarmaVida.java        # Métodos a), b), c), d), e) de la guía
    ├── Main.java            # Demostración con los datos de la guía
    └── Pruebas.java         # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo1-farmavida
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** los cinco métodos con los datos de la guía (agotados, promedio, reposición, precios inversos, aumento).
- **Negativos:** categoría sin medicamentos, código inexistente, cantidades/porcentajes negativos, categoría no válida, stock negativo, código duplicado.
- **Borde:** lista vacía en todos los métodos y stock exactamente igual a 5.

## Interpretaciones adoptadas

- Los códigos de medicamento no se repiten.
- `ActualizarStock` y `AplicarAumentoGeneral` lanzan excepción con valores negativos.
- `ListarPreciosInversos` devuelve los precios (no los medicamentos) en orden inverso al de ingreso.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
