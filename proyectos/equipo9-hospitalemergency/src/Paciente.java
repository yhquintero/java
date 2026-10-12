/**
 * Paciente de la sala de emergencias: historial clínico, nombre, nivel de prioridad (1 a 5)
 * y minutos de espera. Interpretación: un nivel MÁS ALTO significa MAYOR prioridad (5 = máxima).
 */
public class Paciente {

    private final String historialClinico;
    private final String nombre;
    private final int nivelPrioridad;
    private final int minutosEspera;

    public Paciente(String historialClinico, String nombre, int nivelPrioridad, int minutosEspera) {
        if (nivelPrioridad < 1 || nivelPrioridad > 5) {
            throw new IllegalArgumentException("El nivel de prioridad debe estar entre 1 y 5");
        }
        if (minutosEspera < 0) {
            throw new IllegalArgumentException("Los minutos de espera no pueden ser negativos");
        }
        this.historialClinico = historialClinico;
        this.nombre = nombre;
        this.nivelPrioridad = nivelPrioridad;
        this.minutosEspera = minutosEspera;
    }

    public String getHistorialClinico() { return historialClinico; }

    public String getNombre() { return nombre; }

    public int getNivelPrioridad() { return nivelPrioridad; }

    public int getMinutosEspera() { return minutosEspera; }

    @Override
    public String toString() {
        return historialClinico + " | " + nombre + " | prioridad=" + nivelPrioridad
                + " | espera=" + minutosEspera + " min";
    }
}
