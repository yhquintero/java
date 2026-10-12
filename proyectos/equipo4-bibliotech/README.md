# Equipo 4 — Biblioteca Universitaria 'BiblioTech' (ArrayList)

**Integrantes:** Emily Elena Concha Morejón, Martha Alina Montané Arias, César Paredes Real, Dario Riverol Herrera, Lazaro Gabriel Garcia Enriquez

**Problemática:** gestionar los préstamos de libros (código, título, categoría y días de préstamo).

**Estructura de datos:** `java.util.ArrayList`, tal como indica la guía para este equipo.

## Estructura de archivos

```
equipo4-bibliotech/
├── README.md
└── src/
    ├── Libro.java       # Datos de cada libro prestado y categorías válidas
    ├── BiblioTech.java  # Métodos a), b), c), d), e) de la guía (sobre ArrayList)
    ├── Main.java        # Demostración con los datos de la guía
    └── Pruebas.java     # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo4-bibliotech
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** libros vencidos (incluido el borde del límite), promedio por categoría, renovación, eliminación por categoría y libro con más días.
- **Negativos:** límite sin vencidos, categoría sin libros, código inexistente, días negativos, categoría no válida, código duplicado.
- **Borde:** catálogo vacío en todos los métodos.

## Interpretaciones adoptadas

- Los códigos de libro no se repiten.
- `LibrosVencidos` usa un límite estricto: "supere el límite" significa `días > límite`.
- `BuscarLibroConMasDias` devuelve el objeto `Libro` (título y días); devuelve `null` si el catálogo está vacío.
- Se usa `java.util.ArrayList` porque la guía lo exige como estructura.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
