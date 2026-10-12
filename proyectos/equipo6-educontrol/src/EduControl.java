import java.util.ArrayList;
import java.util.Iterator;

/**
 * Procesamiento de las notas del parcial de las asignaturas (gestión académica 'EduControl').
 * Estructura del enunciado: ArrayList (java.util.ArrayList, permitido expresamente por el enunciado).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class EduControl {

    /** Bonificación por asistencia y asistencia mínima para recibirla (punto c). */
    private static final double BONIFICACION = 5.0;
    private static final double ASISTENCIA_MINIMA_BONIFICACION = 90.0;
    private static final double NOTA_MAXIMA = 100.0;

    private final ArrayList<Estudiante> estudiantes = new ArrayList<>();

    /** Inscribe un estudiante en el grupo (ingreso de datos). */
    public void registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        estudiantes.add(estudiante);
    }

    public int cantidad() {
        return estudiantes.size();
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    private static void validarNota(double nota, String nombreParametro) {
        if (nota < 0 || nota > NOTA_MAXIMA) {
            throw new IllegalArgumentException(nombreParametro + " debe estar entre 0 y 100.");
        }
    }

    // =====================================================================
    // Punto a) EstudiantesAprobados(double notaMinima)
    // Nombres de los estudiantes con nota igual o superior a la mínima.
    // Interpretación: notaMinima fuera de [0, 100] lanza IllegalArgumentException.
    // =====================================================================
    public ArrayList<String> EstudiantesAprobados(double notaMinima) {
        validarNota(notaMinima, "La nota mínima");
        ArrayList<String> nombres = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e.getNotaParcial() >= notaMinima) {
                nombres.add(e.getNombre());
            }
        }
        return nombres;
    }

    // =====================================================================
    // Punto b) PromedioGeneral()
    // Media aritmética de todas las notas del grupo. Devuelve 0.0 si no hay estudiantes.
    // =====================================================================
    public double PromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Estudiante e : estudiantes) {
            suma += e.getNotaParcial();
        }
        return suma / estudiantes.size();
    }

    // =====================================================================
    // Punto c) AjustarNotasPorAsistencia()
    // Suma 5 puntos de bonificación a la nota de los estudiantes con asistencia >= 90%,
    // con un máximo de 100 puntos. Devuelve cuántos estudiantes cumplieron la condición.
    // =====================================================================
    public int AjustarNotasPorAsistencia() {
        int ajustados = 0;
        for (Estudiante e : estudiantes) {
            if (e.getAsistencia() >= ASISTENCIA_MINIMA_BONIFICACION) {
                double nuevaNota = Math.min(NOTA_MAXIMA, e.getNotaParcial() + BONIFICACION);
                e.establecerNota(nuevaNota);
                ajustados++;
            }
        }
        return ajustados;
    }

    // =====================================================================
    // Punto d) BuscarEstudianteConMayorAsistencia()
    // Información del estudiante con el porcentaje de asistencia más alto.
    // Si hay empate, gana el primero registrado. Devuelve null si no hay estudiantes.
    // =====================================================================
    public Estudiante BuscarEstudianteConMayorAsistencia() {
        Estudiante mayor = null;
        for (Estudiante e : estudiantes) {
            if (mayor == null || e.getAsistencia() > mayor.getAsistencia()) {
                mayor = e;
            }
        }
        return mayor;
    }

    // =====================================================================
    // Punto e) EliminarEstudiantesReprobados(double notaCorte)
    // Elimina a los estudiantes con nota inferior a la nota de corte y devuelve el total eliminado.
    // Interpretación: notaCorte fuera de [0, 100] lanza IllegalArgumentException.
    // =====================================================================
    public int EliminarEstudiantesReprobados(double notaCorte) {
        validarNota(notaCorte, "La nota de corte");
        int eliminados = 0;
        Iterator<Estudiante> it = estudiantes.iterator();
        while (it.hasNext()) {
            if (it.next().getNotaParcial() < notaCorte) {
                it.remove();
                eliminados++;
            }
        }
        return eliminados;
    }
}
