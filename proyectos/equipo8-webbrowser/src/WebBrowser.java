import java.time.LocalTime;

/**
 * Historial de páginas visitadas del navegador WebBrowser.
 * Estructura: ListaDobleEnlazada&lt;Pagina&gt; (lista doblemente enlazada hecha a mano).
 * La visita más antigua está al inicio y la más reciente al final.
 */
public class WebBrowser {

    private final ListaDobleEnlazada<Pagina> visitas = new ListaDobleEnlazada<>();

    /** Registra una visita al final del historial (queda como la más reciente). */
    public void registrarVisita(Pagina p) {
        visitas.agregar(p);
    }

    public int cantidad() {
        return visitas.size();
    }

    public ListaDobleEnlazada<Pagina> getVisitas() {
        return visitas;
    }

    // ===================== Punto a) =====================
    /**
     * Elimina la página con esa URL reenlazando los nodos anterior (prev) y posterior (next).
     * Interpretación: si la URL aparece varias veces, se eliminan todas las visitas.
     * Devuelve cuántas visitas se eliminaron.
     */
    public int EliminarPaginaPorUrl(String url) {
        return visitas.eliminarSi(p -> p.getUrl().equals(url));
    }

    // ===================== Punto b) =====================
    /** Páginas desde la más reciente hasta la más antigua. */
    public ListaDobleEnlazada<Pagina> MostrarHistorialReciente() {
        return visitas.invertidaCopia();
    }

    // ===================== Punto c) =====================
    /** URLs cuyo título contiene la palabra clave (sin distinguir mayúsculas), en orden cronológico. */
    public ListaDobleEnlazada<String> BuscarPorPalabraClave(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra clave no puede estar vacía");
        }
        String clave = palabra.trim().toLowerCase();
        ListaDobleEnlazada<String> urls = new ListaDobleEnlazada<>();
        for (Pagina p : visitas) {
            if (p.getTituloPagina().toLowerCase().contains(clave)) {
                urls.agregar(p.getUrl());
            }
        }
        return urls;
    }

    // ===================== Punto d) =====================
    /**
     * Elimina las visitas realizadas estrictamente antes de la hora límite (formato HH:mm)
     * y devuelve cuántas eliminó. Una visita a la hora exacta se conserva.
     */
    public int VaciarHistorialAntiguo(String horaLimite) {
        LocalTime limite = LocalTime.parse(horaLimite);
        return visitas.eliminarSi(p -> p.getHoraVisita().isBefore(limite));
    }

    @Override
    public String toString() {
        return visitas.toString();
    }
}
