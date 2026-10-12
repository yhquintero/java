# Equipo 3 — Centro Deportivo 'FitGym' (Lista simplemente enlazada)

**Integrantes:** Ariela Ivón Corrales Rodríguez, Mélany Fernández Díaz, Miriam Elena Pérez Hdez, Julio César León Gato, Ronal Hernández Castillo

**Problemática:** gestionar el registro de socios (ID, nombre, tipo de membresía y días restantes).

**Estructura de datos:** lista simplemente enlazada implementada a mano (`ListaEnlazada<T>`), sin colecciones de `java.util`.

## Estructura de archivos

```
equipo3-fitgym/
├── README.md
└── src/
    ├── ListaEnlazada.java   # Lista simplemente enlazada genérica (hecha a mano)
    ├── Socio.java           # Datos de cada socio y tipos de membresía válidos
    ├── FitGym.java  # Métodos a), b), c), d), e) de la guía
    ├── Main.java            # Demostración con los datos de la guía
    └── Pruebas.java         # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo3-fitgym
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** socios por vencer (incluido el borde del límite), orden por días, conteo por membresía, renovación y eliminación de socios sin días.
- **Negativos:** ID inexistente, días negativos, tipo de membresía no válido, ID duplicado.
- **Borde:** registro vacío y límite que no incluye a nadie.

## Interpretaciones adoptadas

- Los IDs de socio no se repiten.
- `MostrarOrdenadosPorDias` devuelve una copia ordenada (mayor a menor); el registro original no cambia.
- `CantidadSociosPorMembresia` devuelve el texto en el orden Básica, Premium, VIP.
- Los días adicionales negativos lanzan excepción.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
