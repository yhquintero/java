# Equipo 5 — Tienda en Línea 'TechMarket' (ArrayList)

**Integrantes:** Lauren Rosa Barrios Bosque, Liz Anel López Vives, Osmany Manuel Ungo Seara, Jorge Luis Hernández Avila, José Javier Fernández Bravo

**Problemática:** administrar el catálogo de productos informáticos (id, nombre, marca y precio).

**Estructura de datos:** `java.util.ArrayList`, tal como indica la guía para este equipo.

## Estructura de archivos

```
equipo5-techmarket/
├── README.md
└── src/
    ├── Producto.java            # Datos de cada producto
    ├── TechMarket.java  # Métodos a), b), c), d), e) de la guía (sobre ArrayList)
    ├── Main.java                # Demostración con los datos de la guía
    └── Pruebas.java             # Casos de prueba positivos, negativos y de borde
```

## Compilar y ejecutar

```bash
cd proyectos/equipo5-techmarket
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos a) a e)
java -cp out Pruebas     # casos de prueba
```

## Casos de prueba

- **Positivos:** filtro por rango (incluido el límite exacto), descuento por marca, producto más barato, cambio de precio y promedio por marca.
- **Negativos:** rango sin productos, rango invertido, marca inexistente, descuento fuera de 0–100, ID inexistente, precio negativo, ID duplicado.
- **Borde:** catálogo vacío en todos los métodos y rango de un solo valor.

## Interpretaciones adoptadas

- Los IDs de producto no se repiten.
- `FiltrarPorRangoPrecio` incluye ambos extremos (precio mayor o igual que el mínimo y menor o igual que el máximo).
- `AplicarDescuentoMarca` devuelve cuántos productos modificó y valida que el porcentaje esté entre 0 y 100.
- `ReponerStockProducto` actualiza el precio, como describe la guía, aunque el nombre del método diga "stock".
- `BuscarProductoBarato` devuelve `null` si el catálogo está vacío.

## Declaración de uso de Inteligencia Artificial

El código de este proyecto fue generado con asistencia de un agente de IA (Arena.ai Agent Mode) a partir de los enunciados de la guía. La IA se usó para redactar el código base, los casos de prueba y este README. Cada integrante debe revisar y entender el código antes de la exposición.
