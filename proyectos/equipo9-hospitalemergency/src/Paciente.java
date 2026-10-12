/**
 * Entidad Paciente de la sala de emergencias del hospital 'HospitalEmergency'.
 * Atributos del enunciado: historialClinico, nombre, nivelPrioridad (1 a 5) y minutosEspera.
 * Nota: los atributos se nombran sin tildes (historialClinico, nivelPrioridad, minutosEspera).
 */
public class Paciente {

    private final String historialClinico;
    private final String nombre;
    private final int nivelPrioridad;
    private final int minutosEspera;

    public Paciente(String historialClinico, String nombre, int nivelPrioridad, int minutosEspera) {
        if (historialClinico == null || historialClinico.trim().isEmpty()) {
            throw new IllegalArgumentException("El historial clínico no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nivelPrioridad < 1 || nivelPrioridad > 5) {
            throw new IllegalArgumentException("El nivel de prioridad debe estar entre 1 y 5.");
        }
        if (minutosEspera < 0) {
            throw new IllegalArgumentException("Los minutos de espera no pueden ser negativos.");
        }
        this.historialClinico = historialClinico;
        this.nombre = nombre;
        this.nivelPrioridad = nivelPrioridad;
        this.minutosEspera = minutosEspera;
    }

    public String getHistorialClinico() {
        return historialClinico;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelPrioridad() {
        return nivelPrioridad;
    }

    public int getMinutosEspera() {
        return minutosEspera;
    }

    @Override
    public String toString() {
        return nombre + " (historial " + historialClinico + ", prioridad " + nivelPrioridad
                + ", espera " + minutosEspera + " min)";
    }
}
