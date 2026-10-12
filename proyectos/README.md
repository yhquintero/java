# Proyectos de Estructuras de Datos (Examen Parcial, Guía Oficial)

Un proyecto Java **independiente** por equipo (Equipos 1 a 9 del PDF). Cada carpeta `equipoN-*/`
contiene su propia carpeta `src/` con sus clases, un `Main` (demostración de los puntos de la guía
con los datos de prueba sugeridos) y un `Pruebas` (casos positivos, negativos y de borde).
No hay dependencias entre proyectos ni librerías externas.

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

Desde la raíz del repositorio (ejemplo con el Equipo 1; cambie la carpeta para otro equipo):

```bash
cd proyectos/equipo1-farmavida
javac -encoding UTF-8 -d out src/*.java
java -cp out Main        # demostración de los puntos de la guía
java -cp out Pruebas     # casos de prueba: [OK] / [FALLO] y resumen final
```

Requisito: JDK 8 o superior. Los `.class` se generan en `out/` y no se versionan (ver `.gitignore`).
Cada `README.md` de proyecto explica la estructura de archivos, los casos de prueba, las
interpretaciones adoptadas y la declaración de uso de Inteligencia Artificial.
