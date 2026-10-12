/**
 * Lista de reproducción (playlist) del reproductor SoundFlow.
 * Estructura: ListaDobleEnlazada&lt;Cancion&gt; (lista doblemente enlazada hecha a mano).
 */
public class SoundFlow {

    private final ListaDobleEnlazada<Cancion> canciones = new ListaDobleEnlazada<>();

    /** Agrega una canción al final. Interpretación: no se permiten IDs repetidos. */
    public void insertar(Cancion c) {
        if (buscarPorId(c.getId()) != null) {
            throw new IllegalArgumentException("ID de canción duplicado: " + c.getId());
        }
        canciones.agregar(c);
    }

    public Cancion buscarPorId(String id) {
        return canciones.buscarSi(c -> c.getId().equals(id));
    }

    public ListaDobleEnlazada<Cancion> getCanciones() {
        return canciones;
    }

    // ===================== Punto a) =====================
    /**
     * Recorre la playlist en orden directo (cabeza a cola, siguiendo "siguiente")
     * y en orden inverso (cola a cabeza, siguiendo "prev"). Devuelve ambos recorridos como texto.
     */
    public String NavegarAdelanteYAtras() {
        return "Orden directo: " + canciones + "\n"
                + "Orden inverso: " + canciones.invertidaCopia();
    }

    // ===================== Punto b) =====================
    /**
     * Mueve la canción con ese ID al principio, reenlazando sus punteros prev y next.
     * Devuelve true si la canción existe (la guía no indica el tipo de retorno).
     */
    public boolean MoverCancionAlInicio(String id) {
        return canciones.moverAlInicio(c -> c.getId().equals(id));
    }

    // ===================== Punto c) =====================
    /** Tiempo total de reproducción en minutos y segundos, p. ej. "13 min 50 s". */
    public String DuracionTotalPlaylist() {
        int total = 0;
        for (Cancion c : canciones) {
            total += c.getDuracionSegundos();
        }
        return (total / 60) + " min " + (total % 60) + " s";
    }

    // ===================== Punto d) =====================
    /** Elimina todas las canciones del artista indicado y devuelve cuántas eliminó. */
    public int EliminarCancionPorArtista(String artista) {
        return canciones.eliminarSi(c -> c.getArtista().equalsIgnoreCase(artista));
    }

    /** Indica si los punteros prev y next de la playlist son coherentes. */
    public boolean esConsistente() {
        return canciones.esConsistente();
    }

    @Override
    public String toString() {
        return canciones.toString();
    }
}
