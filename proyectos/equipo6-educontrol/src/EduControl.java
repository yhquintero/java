import java.util.ArrayList;
import java.util.Iterator;

/**
 * Procesamiento de las notas del parcial en EduControl.
 * Estructura: ArrayList de java.util, tal como la indica la guía para este equipo.
 */
public class EduControl {

    private final ArrayList<Estudiante> estudiantes = new ArrayList<>();

    /** Registra un estudiante. Interpretación: no se permiten IDs repetidos. */
    public void insertar(Estudiante e) {
        if (buscarPorId(e.getIdEstudiante()) != null) {
            throw new IllegalArgumentException("ID de estudiante duplicado: " + e.getIdEstudiante());
        }
        estudiantes.add(e);
    }

    public Estudiante buscarPorId(String id) {
        for (Estudiante e : estudiantes) {
            if (e.getIdEstudiante().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public int cantidad() {
        return estudiantes.size();
    }

    // ===================== Punto a) =====================
    /** Nombres de los estudiantes con nota igual o superior a la mínima. */
    public ArrayList<String> EstudiantesAprobados(double notaMinima) {
        ArrayList<String> nombres = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e.getNotaParcial() >= notaMinima) {
                nombres.add(e.getNombre());
            }
        }
        return nombres;
    }

    // ===================== Punto b) =====================
    /** Media aritmética de las notas del grupo. Devuelve 0.0 si no hay estudiantes. */
    public double PromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0.0;
        }
        double suma = 0;
        for (Estudiante e : estudiantes) {
            suma += e.getNotaParcial();
        }
        return suma / estudiantes.size();
    }

    // ===================== Punto c) =====================
    /**
     * Suma 5 puntos de bonificación a la nota de los estudiantes con asistencia &gt;= 90%,
     * sin superar 100. Devuelve cuántos estudiantes cumplían la condición.
     * Interpretación: la "nota final" de la guía es la nota del parcial almacenada.
     * Cada llamada vuelve a aplicar la bonificación, así que debe ejecutarse una sola vez.
     */
    public int AjustarNotasPorAsistencia() {
        int beneficiados = 0;
        for (Estudiante e : estudiantes) {
            if (e.getAsistencia() >= 90) {
                e.setNotaParcial(Math.min(100, e.getNotaParcial() + 5));
                beneficiados++;
            }
        }
        return beneficiados;
    }

    // ===================== Punto d) =====================
    /** Estudiante con el mayor porcentaje de asistencia. Devuelve null si no hay estudiantes. */
    public Estudiante BuscarEstudianteConMayorAsistencia() {
        Estudiante mayor = null;
        for (Estudiante e : estudiantes) {
            if (mayor == null || e.getAsistencia() > mayor.getAsistencia()) {
                mayor = e;
            }
        }
        return mayor;
    }

    // ===================== Punto e) =====================
    /** Elimina a los estudiantes con nota inferior a la nota de corte y devuelve cuántos eliminó. */
    public int EliminarEstudiantesReprobados(double notaCorte) {
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

    @Override
    public String toString() {
        return estudiantes.toString();
    }
}
