/** Vuelo programado de la aerolínea SkyFly: número, destino, estado y duración en minutos. */
public class Vuelo {

    public static final String A_TIEMPO = "A tiempo";
    public static final String DEMORADO = "Demorado";
    public static final String CANCELADO = "Cancelado";
    private static final String[] ESTADOS = {A_TIEMPO, DEMORADO, CANCELADO};

    private final String numeroVuelo;
    private final String destino;
    private String estado;
    private final int duracionMinutos;

    public Vuelo(String numeroVuelo, String destino, String estado, int duracionMinutos) {
        if (!esEstadoValido(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
        if (duracionMinutos < 0) {
            throw new IllegalArgumentException("La duración no puede ser negativa");
        }
        this.numeroVuelo = numeroVuelo;
        this.destino = destino;
        this.estado = estado;
        this.duracionMinutos = duracionMinutos;
    }

    public static boolean esEstadoValido(String estado) {
        for (String e : ESTADOS) {
            if (e.equals(estado)) {
                return true;
            }
        }
        return false;
    }

    public String getNumeroVuelo() { return numeroVuelo; }

    public String getDestino() { return destino; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) {
        if (!esEstadoValido(estado)) {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }
        this.estado = estado;
    }

    public int getDuracionMinutos() { return duracionMinutos; }

    @Override
    public String toString() {
        return numeroVuelo + " | " + destino + " | " + estado + " | " + duracionMinutos + " min";
    }
}
