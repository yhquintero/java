import java.util.Locale;

/**
 * Entidad Pagina del historial de navegación del navegador 'WebBrowser'.
 * Atributos del enunciado: url, títuloPágina y horaVisita.
 * Nota: los atributos se nombran 'tituloPagina' (sin tildes) para evitar problemas de codificación.
 * La horaVisita se representa como texto "HH:mm" (24 horas).
 */
public class Pagina {

    private final String url;
    private final String tituloPagina;
    private final String horaVisita;
    private final int minutosVisita;   // hora convertida a minutos desde las 00:00 (para comparar)

    public Pagina(String url, String tituloPagina, String horaVisita) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL no puede estar vacía.");
        }
        if (tituloPagina == null || tituloPagina.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la página no puede estar vacío.");
        }
        this.url = url;
        this.tituloPagina = tituloPagina;
        this.horaVisita = horaVisita;
        this.minutosVisita = aMinutos(horaVisita);
    }

    /**
     * Convierte una hora "HH:mm" a minutos desde las 00:00.
     * Lanza IllegalArgumentException si el formato o los valores no son válidos.
     */
    public static int aMinutos(String hora) {
        if (hora == null || !hora.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            throw new IllegalArgumentException("Hora no válida (formato HH:mm): " + hora);
        }
        int horas = Integer.parseInt(hora.substring(0, 2));
        int minutos = Integer.parseInt(hora.substring(3, 5));
        return horas * 60 + minutos;
    }

    public String getUrl() {
        return url;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public String getHoraVisita() {
        return horaVisita;
    }

    /** Minutos desde las 00:00 de la visita (uso interno para comparar horas). */
    int getMinutosVisita() {
        return minutosVisita;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s | %s | %s", horaVisita, tituloPagina, url);
    }
}
