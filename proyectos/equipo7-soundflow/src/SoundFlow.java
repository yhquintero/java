/**
 * Lista de reproducción (playlist) del reproductor de audio 'SoundFlow'.
 * Estructura del enunciado: Lista Doblemente Enlazada (ListaDobleEnlazada&lt;Cancion&gt;, implementada a mano).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class SoundFlow {

    private final ListaDobleEnlazada<Cancion> playlist = new ListaDobleEnlazada<>();

    /** Agrega una canción al final de la playlist (ingreso de datos). */
    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no puede ser nula.");
        }
        playlist.agregarAlFinal(cancion);
    }

    public int cantidad() {
        return playlist.size();
    }

    public ListaDobleEnlazada<Cancion> getPlaylist() {
        return playlist;
    }

    // =====================================================================
    // Punto a) NavegarAdelanteYAtras()
    // Recorre la playlist en orden directo (cabeza -> cola) y en orden inverso (cola -> cabeza),
    // y devuelve ambos recorridos como texto.
    // =====================================================================
    public String NavegarAdelanteYAtras() {
        return "Orden directo: " + playlist.toString() + "\nOrden inverso: " + playlist.toStringInverso();
    }

    // =====================================================================
    // Punto b) MoverCancionAlInicio(String id)
    // Ubica la canción indicada al principio de la playlist, modificando los enlaces
    // anterior y siguiente. Devuelve true si se encontró la canción, false si no.
    // =====================================================================
    public boolean MoverCancionAlInicio(String id) {
        ListaDobleEnlazada.Nodo<Cancion> nodo = playlist.buscarNodo(c -> c.getId().equals(id));
        if (nodo == null) {
            return false;
        }
        playlist.moverAlInicio(nodo);
        return true;
    }

    // =====================================================================
    // Punto c) DuracionTotalPlaylist()
    // Tiempo total de reproducción acumulado, expresado en minutos y segundos (por ejemplo "20 min 29 s").
    // Una playlist vacía devuelve "0 min 0 s".
    // =====================================================================
    public String DuracionTotalPlaylist() {
        long totalSegundos = 0;
        for (Cancion c : playlist) {
            totalSegundos += c.getDuracionSegundos();
        }
        return (totalSegundos / 60) + " min " + (totalSegundos % 60) + " s";
    }

    // =====================================================================
    // Punto d) EliminarCancionPorArtista(String artista)
    // Elimina todas las canciones del artista indicado y devuelve la cantidad eliminada.
    // Interpretación: la comparación de artista ignora mayúsculas/minúsculas.
    // =====================================================================
    public int EliminarCancionPorArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("El artista no puede estar vacío.");
        }
        return playlist.eliminarTodos(c -> c.getArtista().equalsIgnoreCase(artista));
    }
}
