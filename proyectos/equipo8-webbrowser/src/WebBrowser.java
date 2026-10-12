import java.util.Locale;

/**
 * Historial de páginas visitadas del navegador 'WebBrowser'.
 * Estructura del enunciado: Lista Doblemente Enlazada (ListaDobleEnlazada&lt;Pagina&gt;, implementada a mano).
 * Orden de almacenamiento: cronológico (la primera visita está en la cabeza; la más reciente, en la cola).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class WebBrowser {

    private final ListaDobleEnlazada<Pagina> historial = new ListaDobleEnlazada<>();

    /** Registra una visita al final del historial (la más reciente queda en la cola). */
    public void registrarVisita(Pagina pagina) {
        if (pagina == null) {
            throw new IllegalArgumentException("La página no puede ser nula.");
        }
        historial.agregarAlFinal(pagina);
    }

    public int cantidad() {
        return historial.size();
    }

    public ListaDobleEnlazada<Pagina> getHistorial() {
        return historial;
    }

    // =====================================================================
    // Punto a) EliminarPaginaPorUrl(String url)
    // Elimina una página específica reenlazando correctamente los nodos anterior y posterior
    // (sirve para cabeza, nodo intermedio y cola). Devuelve true si se eliminó, false si no existe.
    // Interpretación: si la URL aparece varias veces, se elimina la visita más antigua.
    // =====================================================================
    public boolean EliminarPaginaPorUrl(String url) {
        ListaDobleEnlazada.Nodo<Pagina> nodo = historial.buscarNodo(p -> p.getUrl().equals(url));
        if (nodo == null) {
            return false;
        }
        historial.eliminarNodo(nodo);
        return true;
    }

    // =====================================================================
    // Punto b) MostrarHistorialReciente()
    // Devuelve la lista de páginas desde la más reciente hasta la más antigua.
    // Se devuelve una nueva lista; el historial original no cambia.
    // =====================================================================
    public ListaDobleEnlazada<Pagina> MostrarHistorialReciente() {
        return historial.copiaInversa();
    }

    // =====================================================================
    // Punto c) BuscarPorPalabraClave(String palabra)
    // Devuelve las URL cuyo título contiene la palabra clave (sin distinguir mayúsculas),
    // en orden cronológico. Una palabra vacía lanza IllegalArgumentException.
    // =====================================================================
    public ListaDobleEnlazada<String> BuscarPorPalabraClave(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            throw new IllegalArgumentException("La palabra clave no puede estar vacía.");
        }
        String clave = palabra.toLowerCase(Locale.ROOT);
        ListaDobleEnlazada<String> urls = new ListaDobleEnlazada<>();
        for (Pagina p : historial) {
            if (p.getTituloPagina().toLowerCase(Locale.ROOT).contains(clave)) {
                urls.agregarAlFinal(p.getUrl());
            }
        }
        return urls;
    }

    // =====================================================================
    // Punto d) VaciarHistorialAntiguo(String horaLimite)
    // Elimina las visitas realizadas ANTES de la hora límite (formato HH:mm) y devuelve la cantidad.
    // Una visita a exactamente la hora límite se conserva.
    // =====================================================================
    public int VaciarHistorialAntiguo(String horaLimite) {
        int limite = Pagina.aMinutos(horaLimite);   // valida el formato y lanza excepción si es incorrecto
        return historial.eliminarTodos(p -> p.getMinutosVisita() < limite);
    }
}
