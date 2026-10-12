# Equipo 8 — Historial de Navegación 'WebBrowser' (Lista doblemente enlazada)

**Integrantes:** Anyelo Monterrey, Carlos Daniel Hernández Díaz, Laura García Miguel, Alejandro Marrinez Gonzalez

**Problemática:** administrar el historial de páginas visitadas para permitir navegar hacia adelante y hacia atrás (url, título de la página y hora de visita).

**Estructura de datos:** lista doblemente enlazada implementada a mano (`ListaDobleEnlazada<T>`), con punteros `prev` y `siguiente` y referencias a cabeza y cola.

## Estructura de archivos

```
equipo8-webbrowser/
├── README.md
└── src/
    ├── ListaDobleEnlazada.java   # Lista doblemente enlazada genérica (hecha a mano)
    ├── Pagina.java               # Datos de cada visita (url, título, hora HH:mm)
    ├── WebBrowser.java  # Métodos a), b), c), d) de la guía
    ├── Main.java                 # Demostración con 5 visitas de ejemplo
    └── Pruebas.java              # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo8-webbrowser
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a d)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** eliminar el primer nodo, un nodo intermedio y el último, verificando los punteros `prev`/`next` después de cada eliminación; historial reciente; búsqueda por palabra clave; vaciado de visitas antiguas.
- **Negativos:** URL inexistente, palabra clave vacía o sin coincidencias, hora con formato inválido, página con hora inválida.
- **Borde:** historial vacío, hora límite `00:00`, visita a la hora exacta límite, eliminar todas las visitas.

## Interpretaciones adoptadas

- La hora de visita se guarda como `LocalTime` en formato `HH:mm`.
- `EliminarPaginaPorUrl` elimina todas las visitas con esa URL (una URL puede visitarse varias veces) y devuelve cuántas eliminó.
- `VaciarHistorialAntiguo` elimina las visitas estrictamente anteriores a la hora límite; una visita a la hora exacta se conserva.
- `BuscarPorPalabraClave` no distingue mayúsculas y devuelve las URL en orden cronológico.
- `MostrarHistorialReciente` devuelve una copia en orden inverso (la más reciente primero).

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
