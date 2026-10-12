# Equipo 9 — Sistema de Emergencias 'HospitalEmergency' (Lista doblemente enlazada)

**Integrantes:** Isnaelys Temerez Rodriguez, Dalís Aladro Acosta, Adrián Felipe Armas García, Carlos Daniel Borrego Chang

**Problemática:** organizar los turnos de atención de la sala de emergencias según la gravedad (historial clínico, nombre, nivel de prioridad 1 a 5 y minutos de espera).

**Estructura de datos:** lista doblemente enlazada implementada a mano (`ListaDobleEnlazada<T>`), con punteros `prev` y `siguiente` y referencias a cabeza y cola.

## Estructura de archivos

```
equipo9-hospitalemergency/
├── README.md
└── src/
    ├── ListaDobleEnlazada.java  # Lista doblemente enlazada genérica (hecha a mano)
    ├── Paciente.java            # Datos de cada paciente con validación de prioridad 1–5
    ├── HospitalEmergency.java     # Métodos a), b), c), d) de la guía
    ├── Main.java                # Demostración con las prioridades 2, 5, 1 y 4
    └── Pruebas.java             # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo9-hospitalemergency
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a d)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** orden de atención con prioridades 2, 5, 1 y 4; empates de prioridad atendidos por orden de llegada; espera crítica (con el borde del límite); reordenamiento por tiempo de espera con `prev` y `next` coherentes.
- **Negativos:** prioridad fuera de 1–5, minutos de espera negativos, límite de espera que no encuentra pacientes.
- **Borde:** sala vacía (atender devuelve `null`, reordenar no falla).

## Interpretaciones adoptadas

- **Un nivel de prioridad más alto significa mayor prioridad** (5 es la máxima). Si se quisiera la convención médica en la que 1 es el más grave, basta con invertir la comparación en `InsertarPorPrioridad`.
- Entre pacientes con igual prioridad se respeta el orden de llegada (FIFO).
- `PacientesEnEsperaCritica` usa un límite estricto: "supera el límite" significa `espera > límite`.
- `ReordenarPorTiempoEspera` ordena de mayor a menor espera y es estable (conserva el orden previo en empates).

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
