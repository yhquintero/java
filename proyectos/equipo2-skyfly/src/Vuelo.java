/**
 * Entidad Vuelo de la aerolínea 'SkyFly'.
 * Atributos del enunciado: numeroVuelo, destino, estado (A tiempo, Demorado, Cancelado) y duracionMinutos.
 */
public class Vuelo {

    public static final String A_TIEMPO = "A tiempo";
    public static final String DEMORADO = "Demorado";
    public static final String CANCELADO = "Cancelado";

    /** Estados permitidos según el enunciado. */
    public static final String[] ESTADOS_VALIDOS = {A_TIEMPO, DEMORADO, CANCELADO};

    private final String numeroVuelo;
    private final String destino;
    private String estado;
    private final int duracionMinutos;

    public Vuelo(String numeroVuelo, String destino, String estado, int duracionMinutos) {
        if (numeroVuelo == null || numeroVuelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de vuelo no puede estar vacío.");
        }
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        if (!esEstadoValido(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
        if (duracionMinutos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        }
        this.numeroVuelo = numeroVuelo;
        this.destino = destino;
        this.estado = estado;
        this.duracionMinutos = duracionMinutos;
    }

    public static boolean esEstadoValido(String estado) {
        for (String valido : ESTADOS_VALIDOS) {
            if (valido.equals(estado)) {
                return true;
            }
        }
        return false;
    }

    public String getNumeroVuelo() {
        return numeroVuelo;
    }

    public String getDestino() {
        return destino;
    }

    public String getEstado() {
        return estado;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    /** Cambia el estado del vuelo (usado por CambiarEstadoVuelo). */
    void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @Override
    public String toString() {
        return numeroVuelo + " | destino: " + destino + " | estado: " + estado + " | duración: " + duracionMinutos + " min";
    }
}
