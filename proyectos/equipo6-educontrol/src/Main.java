/**
 * Pruebas del proyecto EduControl (Equipo 6). Incluye casos positivos y negativos
 * (grupo vacío, valores fuera de rango, excepciones).
 */
public class Main {

    private static int totalPruebas = 0;
    private static int pruebasSuperadas = 0;

    /** Registra el resultado de una prueba y la imprime. */
    static void verificar(String descripcion, boolean condicion) {
        totalPruebas++;
        if (condicion) {
            pruebasSuperadas++;
        }
        System.out.println((condicion ? "  [OK]    " : "  [FALLO] ") + descripcion);
    }

    /** Devuelve true si la acción lanza alguna excepción de tiempo de ejecución. */
    static boolean lanza(Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    static boolean iguales(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    static Estudiante buscar(EduControl grupo, String id) {
        for (Estudiante e : grupo.getEstudiantes()) {
            if (e.getIdEstudiante().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Datos de prueba: notas entre 40 y 95 y asistencias entre 70% y 100%, como indica el enunciado.
     * (Los nombres y valores concretos son de ejemplo; el enunciado no los especifica.)
     */
    static EduControl datosPrueba() {
        EduControl grupo = new EduControl();
        grupo.registrarEstudiante(new Estudiante("E01", "Ana", 95, 100));
        grupo.registrarEstudiante(new Estudiante("E02", "Luis", 40, 70));
        grupo.registrarEstudiante(new Estudiante("E03", "María", 72, 90));
        grupo.registrarEstudiante(new Estudiante("E04", "Pedro", 58, 85));
        grupo.registrarEstudiante(new Estudiante("E05", "Sofía", 85, 92));
        return grupo;
    }

    public static void main(String[] args) {
        System.out.println("=== EduControl - ArrayList ===");

        System.out.println("\n-- Datos insertados (caso de prueba) --");
        EduControl grupo = datosPrueba();
        for (Estudiante e : grupo.getEstudiantes()) {
            System.out.println("  " + e);
        }
        verificar("Se insertaron 5 estudiantes", grupo.cantidad() == 5);

        System.out.println("\n-- Punto a) EstudiantesAprobados(double notaMinima) --");
        verificar("Positivo: nota mínima 60 devuelve [Ana, María, Sofía]",
                grupo.EstudiantesAprobados(60).toString().equals("[Ana, María, Sofía]"));
        verificar("Positivo: la nota igual a la mínima cuenta (58 devuelve también a Pedro)",
                grupo.EstudiantesAprobados(58).toString().equals("[Ana, María, Pedro, Sofía]"));
        verificar("Negativo: nota mínima 101 lanza IllegalArgumentException",
                lanza(() -> grupo.EstudiantesAprobados(101)));
        verificar("Negativo: grupo vacío devuelve lista vacía",
                new EduControl().EstudiantesAprobados(50).isEmpty());

        System.out.println("\n-- Punto b) PromedioGeneral() --");
        verificar("Positivo: promedio de (95+40+72+58+85)/5 = 70.0",
                iguales(grupo.PromedioGeneral(), 70.0));
        verificar("Negativo: grupo vacío devuelve 0.0", iguales(new EduControl().PromedioGeneral(), 0.0));

        System.out.println("\n-- Punto c) AjustarNotasPorAsistencia() --");
        EduControl c1 = datosPrueba();
        verificar("Positivo: ajusta a 3 estudiantes con asistencia >= 90% (Ana, María, Sofía)",
                c1.AjustarNotasPorAsistencia() == 3);
        verificar("Positivo: Ana pasa de 95 a 100 (la nota llega exactamente al máximo)",
                iguales(buscar(c1, "E01").getNotaParcial(), 100.0));
        verificar("Positivo: María pasa de 72 a 77 y Sofía de 85 a 90",
                iguales(buscar(c1, "E03").getNotaParcial(), 77.0) && iguales(buscar(c1, "E05").getNotaParcial(), 90.0));
        verificar("Positivo: Pedro (asistencia 85%) no recibe bonificación",
                iguales(buscar(c1, "E04").getNotaParcial(), 58.0));
        verificar("Positivo: promedio tras el ajuste = (100+40+77+58+90)/5 = 73.0",
                iguales(c1.PromedioGeneral(), 73.0));
        EduControl tope = new EduControl();
        tope.registrarEstudiante(new Estudiante("E09", "Diego", 98, 95));
        tope.AjustarNotasPorAsistencia();
        verificar("Positivo: tope máximo de 100 (98 + 5 se queda en 100)",
                iguales(tope.getEstudiantes().get(0).getNotaParcial(), 100.0));
        verificar("Negativo: grupo vacío devuelve 0 ajustes", new EduControl().AjustarNotasPorAsistencia() == 0);

        System.out.println("\n-- Punto d) BuscarEstudianteConMayorAsistencia() --");
        Estudiante mayorAsistencia = datosPrueba().BuscarEstudianteConMayorAsistencia();
        verificar("Positivo: el estudiante con mayor asistencia es Ana (100%)",
                mayorAsistencia != null && mayorAsistencia.getNombre().equals("Ana"));
        verificar("Negativo: grupo vacío devuelve null",
                new EduControl().BuscarEstudianteConMayorAsistencia() == null);

        System.out.println("\n-- Punto e) EliminarEstudiantesReprobados(double notaCorte) --");
        EduControl e1 = datosPrueba();
        verificar("Positivo: con nota de corte 60 se eliminan 2 (Luis 40 y Pedro 58)",
                e1.EliminarEstudiantesReprobados(60) == 2);
        verificar("Positivo: quedan Ana, María y Sofía",
                e1.getEstudiantes().size() == 3 && e1.EstudiantesAprobados(0).toString().equals("[Ana, María, Sofía]"));
        verificar("Negativo: nota de corte 0 no elimina a nadie", datosPrueba().EliminarEstudiantesReprobados(0) == 0);
        verificar("Negativo: nota de corte fuera de rango lanza IllegalArgumentException",
                lanza(() -> datosPrueba().EliminarEstudiantesReprobados(-1)));
        verificar("Negativo: grupo vacío devuelve 0", new EduControl().EliminarEstudiantesReprobados(60) == 0);

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: nota fuera de 0..100 lanza excepción",
                lanza(() -> new Estudiante("E10", "Raúl", 101, 80)));
        verificar("Negativo: asistencia fuera de 0..100 lanza excepción",
                lanza(() -> new Estudiante("E11", "Raúl", 80, -5)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new EduControl().registrarEstudiante(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
