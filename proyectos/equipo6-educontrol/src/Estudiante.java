/**
 * Entidad Estudiante inscrito en el departamento de informática (gestión académica 'EduControl').
 * Atributos del enunciado: idEstudiante, nombre, notaParcial (0 a 100) y asistencia (porcentaje 0 a 100).
 */
public class Estudiante {

    private final String idEstudiante;
    private final String nombre;
    private double notaParcial;
    private final double asistencia;

    public Estudiante(String idEstudiante, String nombre, double notaParcial, double asistencia) {
        if (idEstudiante == null || idEstudiante.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del estudiante no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (notaParcial < 0 || notaParcial > 100) {
            throw new IllegalArgumentException("La nota parcial debe estar entre 0 y 100.");
        }
        if (asistencia < 0 || asistencia > 100) {
            throw new IllegalArgumentException("La asistencia debe estar entre 0 y 100.");
        }
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.notaParcial = notaParcial;
        this.asistencia = asistencia;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNotaParcial() {
        return notaParcial;
    }

    public double getAsistencia() {
        return asistencia;
    }

    /** Establece la nota ya ajustada (usado por AjustarNotasPorAsistencia). */
    void establecerNota(double nuevaNota) {
        this.notaParcial = nuevaNota;
    }

    @Override
    public String toString() {
        return idEstudiante + " | " + nombre + " | nota: " + notaParcial + " | asistencia: " + asistencia + "%";
    }
}
