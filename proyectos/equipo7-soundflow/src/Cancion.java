/**
 * Entidad Cancion de la lista de reproducción (playlist) del reproductor 'SoundFlow'.
 * Atributos del enunciado: id, título, artista y duración en segundos.
 * Nota: 'duraciónSegundos' se nombra 'duracionSegundos' (sin tilde).
 */
public class Cancion {

    private final String id;
    private final String titulo;
    private final String artista;
    private final int duracionSegundos;

    public Cancion(String id, String titulo, String artista, int duracionSegundos) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id no puede estar vacío.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("El artista no puede estar vacío.");
        }
        if (duracionSegundos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor que cero.");
        }
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSegundos = duracionSegundos;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    @Override
    public String toString() {
        return id + " '" + titulo + "' (" + artista + ", " + duracionSegundos + " s)";
    }
}
