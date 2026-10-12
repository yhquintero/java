# Proyectos de Estructuras de Datos (Examen Parcial, Guía Oficial)

Un proyecto Java **independiente** por equipo (Equipos 1 a 9 del PDF). Cada carpeta `equipoN-*/src/`
contiene sus propias clases y su propio `Main` con casos de prueba positivos y negativos. No hay
dependencias entre proyectos ni librerías externas.

| Carpeta | Equipo | Problemática | Estructura |
|---|---|---|---|
| `equipo1-farmavida` | 1 | Farmacia 'FarmaVida' | Lista simplemente enlazada |
| `equipo2-skyfly` | 2 | Aerolínea 'SkyFly' | Lista simplemente enlazada |
| `equipo3-fitgym` | 3 | Centro deportivo 'FitGym' | Lista simplemente enlazada |
| `equipo4-bibliotech` | 4 | Biblioteca 'BiblioTech' | ArrayList |
| `equipo5-techmarket` | 5 | Tienda en línea 'TechMarket' | ArrayList |
| `equipo6-educontrol` | 6 | Gestión académica 'EduControl' | ArrayList |
| `equipo7-soundflow` | 7 | Reproductor 'SoundFlow' | Lista doblemente enlazada |
| `equipo8-webbrowser` | 8 | Navegador 'WebBrowser' | Lista doblemente enlazada |
| `equipo9-hospitalemergency` | 9 | Emergencias 'HospitalEmergency' | Lista doblemente enlazada |

## Compilar y ejecutar un proyecto

Desde la carpeta raíz de `proyectos/` (ejemplo con el Equipo 1; cambie la carpeta para otro equipo):

```bash
javac -encoding UTF-8 -d out/equipo1-farmavida equipo1-farmavida/src/*.java
java -cp out/equipo1-farmavida Main
```

Requisito: JDK 8 o superior. Cada `Main` imprime `[OK]` / `[FALLO]` por prueba y un resumen final.
