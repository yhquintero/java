/**
 * Casos de prueba del Equipo 6 (EduControl): positivos, negativos y de borde.
 * No usa librerías externas; cada verificación imprime [OK] o [FALLO].
 */
public class Pruebas {

    private static int aciertos = 0;
    private static int fallos = 0;

    /** Acción que puede lanzar excepción (para comprobar casos negativos). */
    interface Accion {
        void ejecutar();
    }

    static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            aciertos++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallos++;
            System.out.println("[FALLO] " + descripcion);
        }
    }

    /** Devuelve true si la acción lanza una excepción. */
    static boolean lanza(Accion accion) {
        try {
            accion.ejecutar();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    static boolean casiIgual(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    /** Estudiantes con los datos de la demostración. */
    static EduControl datosGuia() {
        EduControl g = new EduControl();
        g.insertar(new Estudiante("E01", "Ana", 40, 70));
        g.insertar(new Estudiante("E02", "Luis", 95, 100));
        g.insertar(new Estudiante("E03", "Marta", 65, 90));
        g.insertar(new Estudiante("E04", "Pedro", 78, 85));
        g.insertar(new Estudiante("E05", "Sofía", 88, 92));
        g.insertar(new Estudiante("E06", "Jorge", 52, 75));
        return g;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        EduControl g = datosGuia();
        verificar("a) Aprobados con nota >= 60 = [Luis, Marta, Pedro, Sofía]",
                g.EstudiantesAprobados(60).toString().equals("[Luis, Marta, Pedro, Sofía]"));
        verificar("a) Borde: nota mínima 40 incluye a Ana (nota igual a la mínima)",
                g.EstudiantesAprobados(40).toString().equals("[Ana, Luis, Marta, Pedro, Sofía, Jorge]"));
        verificar("b) Promedio general = 418 / 6", casiIgual(g.PromedioGeneral(), 418.0 / 6));
        verificar("c) Bonifican 3 estudiantes con asistencia >= 90%", g.AjustarNotasPorAsistencia() == 3);
        verificar("c) Marta (asistencia 90%) pasa de 65 a 70",
                casiIgual(g.buscarPorId("E03").getNotaParcial(), 70.0));
        verificar("d) Mayor asistencia = Luis (100%)",
                g.BuscarEstudianteConMayorAsistencia().getNombre().equals("Luis"));
        verificar("e) Eliminar notas < 60 elimina 2 estudiantes (Ana y Jorge)",
                g.EliminarEstudiantesReprobados(60) == 2);
        verificar("e) Quedan 4 estudiantes", g.cantidad() == 4);
        verificar("e) Ana ya no está en el registro", g.buscarPorId("E01") == null);

        EduControl tope = new EduControl();
        tope.insertar(new Estudiante("E07", "Test", 98, 95));
        tope.AjustarNotasPorAsistencia();
        verificar("c) La bonificación no supera 100 (98 + 5 queda en 100)",
                casiIgual(tope.buscarPorId("E07").getNotaParcial(), 100.0));

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        EduControl n = datosGuia();
        verificar("a) Nota mínima 101 no aprueba a nadie", n.EstudiantesAprobados(101).toString().equals("[]"));
        verificar("e) Corte 0 no elimina a nadie", n.EliminarEstudiantesReprobados(0) == 0);
        verificar("d) Buscar un ID inexistente devuelve null", n.buscarPorId("E99") == null);
        verificar("Nota mayor a 100 lanza excepción",
                lanza(() -> new Estudiante("E08", "X", 101, 80)));
        verificar("Nota negativa lanza excepción",
                lanza(() -> new Estudiante("E09", "X", -1, 80)));
        verificar("Asistencia mayor a 100 lanza excepción",
                lanza(() -> new Estudiante("E10", "X", 80, 120)));
        verificar("Cambiar la nota a un valor fuera de rango lanza excepción",
                lanza(() -> datosGuia().buscarPorId("E01").setNotaParcial(150)));
        verificar("ID de estudiante duplicado lanza excepción",
                lanza(() -> n.insertar(new Estudiante("E01", "Otro", 50, 80))));

        System.out.println("\n=== PRUEBAS DE BORDE (lista vacía) ===");
        EduControl vacia = new EduControl();
        verificar("a) Aprobados en lista vacía = []", vacia.EstudiantesAprobados(0).toString().equals("[]"));
        verificar("b) Promedio en lista vacía = 0.0", vacia.PromedioGeneral() == 0.0);
        verificar("c) Ajustar en lista vacía bonifica 0", vacia.AjustarNotasPorAsistencia() == 0);
        verificar("d) Mayor asistencia en lista vacía es null", vacia.BuscarEstudianteConMayorAsistencia() == null);
        verificar("e) Eliminar en lista vacía elimina 0", vacia.EliminarEstudiantesReprobados(60) == 0);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
