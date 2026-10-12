import java.time.LocalTime;

/**
 * Página visitada en el navegador: url, título de la página y hora de visita (formato HH:mm).
 */
public class Pagina {

    private final String url;
    private final String tituloPagina;
    private final LocalTime horaVisita;

    /** La hora debe tener formato HH:mm; si no es válida se lanza DateTimeParseException. */
    public Pagina(String url, String tituloPagina, String horaVisita) {
        this.url = url;
        this.tituloPagina = tituloPagina;
        this.horaVisita = LocalTime.parse(horaVisita);
    }

    public String getUrl() { return url; }

    public String getTituloPagina() { return tituloPagina; }

    public LocalTime getHoraVisita() { return horaVisita; }

    @Override
    public String toString() {
        return "[" + horaVisita + "] " + tituloPagina + " - " + url;
    }
}
