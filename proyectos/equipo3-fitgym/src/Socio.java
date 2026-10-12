/** Socio del centro deportivo FitGym: ID, nombre, tipo de membresía y días restantes. */
public class Socio {

    public static final String BASICA = "Básica";
    public static final String PREMIUM = "Premium";
    public static final String VIP = "VIP";
    /** Tipos de membresía válidos, en el orden en que se muestran. */
    public static final String[] TIPOS = {BASICA, PREMIUM, VIP};

    private final String idSocio;
    private final String nombre;
    private final String tipoMembresia;
    private int diasRestantes;

    public Socio(String idSocio, String nombre, String tipoMembresia, int diasRestantes) {
        if (!esTipoValido(tipoMembresia)) {
            throw new IllegalArgumentException("Tipo de membresía no válido: " + tipoMembresia);
        }
        if (diasRestantes < 0) {
            throw new IllegalArgumentException("Los días restantes no pueden ser negativos");
        }
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.tipoMembresia = tipoMembresia;
        this.diasRestantes = diasRestantes;
    }

    public static boolean esTipoValido(String tipo) {
        for (String t : TIPOS) {
            if (t.equals(tipo)) {
                return true;
            }
        }
        return false;
    }

    public String getIdSocio() { return idSocio; }

    public String getNombre() { return nombre; }

    public String getTipoMembresia() { return tipoMembresia; }

    public int getDiasRestantes() { return diasRestantes; }

    public void sumarDias(int dias) { this.diasRestantes += dias; }

    @Override
    public String toString() {
        return idSocio + " | " + nombre + " | " + tipoMembresia + " | " + diasRestantes + " días";
    }
}
