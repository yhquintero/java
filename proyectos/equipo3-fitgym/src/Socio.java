/**
 * Entidad Socio del centro deportivo 'FitGym'.
 * Atributos del enunciado: idSocio, nombre, tipoMembresia (Básica, Premium, VIP) y díasRestantes.
 * Nota: el atributo 'díasRestantes' se nombra 'diasRestantes' (sin tilde) para evitar problemas de codificación.
 */
public class Socio {

    /** Tipos de membresía permitidos según el enunciado (el orden es el de los conteos del punto c). */
    public static final String[] TIPOS_MEMBRESIA = {"Básica", "Premium", "VIP"};

    private final String idSocio;
    private final String nombre;
    private final String tipoMembresia;
    private int diasRestantes;

    public Socio(String idSocio, String nombre, String tipoMembresia, int diasRestantes) {
        if (idSocio == null || idSocio.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del socio no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (indiceTipo(tipoMembresia) < 0) {
            throw new IllegalArgumentException("Tipo de membresía no válido: " + tipoMembresia);
        }
        if (diasRestantes < 0) {
            throw new IllegalArgumentException("Los días restantes no pueden ser negativos.");
        }
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.tipoMembresia = tipoMembresia;
        this.diasRestantes = diasRestantes;
    }

    /** Devuelve la posición del tipo dentro de TIPOS_MEMBRESIA, o -1 si no es válido. */
    public static int indiceTipo(String tipo) {
        for (int i = 0; i < TIPOS_MEMBRESIA.length; i++) {
            if (TIPOS_MEMBRESIA[i].equals(tipo)) {
                return i;
            }
        }
        return -1;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public int getDiasRestantes() {
        return diasRestantes;
    }

    /** Suma días a la membresía (usado por RenovarMembresia). */
    void agregarDias(int dias) {
        diasRestantes += dias;
    }

    @Override
    public String toString() {
        return idSocio + " | " + nombre + " | membresía: " + tipoMembresia + " | días restantes: " + diasRestantes;
    }
}
