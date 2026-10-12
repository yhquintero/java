import java.util.Locale;

/** Estudiante inscrito: id, nombre, nota del parcial (0 a 100) y asistencia (porcentaje 0 a 100). */
public class Estudiante {

    private final String idEstudiante;
    private final String nombre;
    private double notaParcial;
    private final double asistencia;

    public Estudiante(String idEstudiante, String nombre, double notaParcial, double asistencia) {
        if (notaParcial < 0 || notaParcial > 100) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100");
        }
        if (asistencia < 0 || asistencia > 100) {
            throw new IllegalArgumentException("La asistencia debe estar entre 0 y 100");
        }
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.notaParcial = notaParcial;
        this.asistencia = asistencia;
    }

    public String getIdEstudiante() { return idEstudiante; }

    public String getNombre() { return nombre; }

    public double getNotaParcial() { return notaParcial; }

    /** Asigna la nota validando el rango 0 a 100. */
    public void setNotaParcial(double nota) {
        if (nota < 0 || nota > 100) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 100");
        }
        this.notaParcial = nota;
    }

    public double getAsistencia() { return asistencia; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s - %s | nota=%.2f | asistencia=%.1f%%",
                idEstudiante, nombre, notaParcial, asistencia);
    }
}
