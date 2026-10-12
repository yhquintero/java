# Equipo 6 — Gestión Académica 'EduControl' (ArrayList)

**Integrantes:** Pedro Antonio Palacios González, Jesus Daniel Bruguera Gallardo, Leonardo Roque Moreno, Caleb Blanco Morejón, Christian M. Febles Suárez

**Problemática:** procesar las notas del parcial de las asignaturas (id, nombre, nota 0–100 y asistencia en %).

**Estructura de datos:** `java.util.ArrayList`, tal como indica la guía para este equipo.

## Estructura de archivos

```
equipo6-educontrol/
├── README.md
└── src/
    ├── Estudiante.java         # Datos de cada estudiante con validación de rangos
    ├── EduControl.java   # Métodos a), b), c), d), e) de la guía (sobre ArrayList)
    ├── Main.java               # Demostración con datos de ejemplo
    └── Pruebas.java            # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo6-educontrol
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** aprobados (incluido el borde de la nota mínima), promedio, bonificación por asistencia, mayor asistencia y eliminación de reprobados.
- **Negativos:** nota o asistencia fuera de 0–100, ID duplicado, ID inexistente, nota mínima inalcanzable.
- **Borde:** la bonificación nunca supera 100 y lista vacía en todos los métodos.

## Interpretaciones adoptadas

- La guía no da datos exactos para este equipo: se usaron notas entre 40 y 95 y asistencias entre 70% y 100%.
- "Nota final" se interpreta como la nota del parcial almacenada (`notaParcial`).
- `AjustarNotasPorAsistencia` solo debe ejecutarse una vez, porque cada llamada vuelve a sumar 5 puntos.
- `EstudiantesAprobados` y `EliminarEstudiantesReprobados` usan comparaciones según el enunciado: aprobado si la nota es `>=` la mínima y reprobado si es `<` la nota de corte.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
