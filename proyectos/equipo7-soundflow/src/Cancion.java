/** Canción de la playlist: id, título, artista y duración en segundos. */
public class Cancion {

    private final String id;
    private final String titulo;
    private final String artista;
    private final int duracionSegundos;

    public Cancion(String id, String titulo, String artista, int duracionSegundos) {
        if (duracionSegundos < 0) {
            throw new IllegalArgumentException("La duración no puede ser negativa");
        }
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSegundos = duracionSegundos;
    }

    public String getId() { return id; }

    public String getTitulo() { return titulo; }

    public String getArtista() { return artista; }

    public int getDuracionSegundos() { return duracionSegundos; }

    @Override
    public String toString() {
        return String.format("%s %s - %s (%d:%02d)", id, titulo, artista,
                duracionSegundos / 60, duracionSegundos % 60);
    }
}
