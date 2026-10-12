import java.util.Comparator;

/**
 * Control de vuelos programados de la aerolínea SkyFly.
 * Estructura: ListaEnlazada&lt;Vuelo&gt; (lista simplemente enlazada hecha a mano).
 */
public class SkyFly {

    private final ListaEnlazada<Vuelo> vuelos = new ListaEnlazada<>();

    /** Registra un vuelo. Interpretación: no se permiten números de vuelo repetidos. */
    public void insertar(Vuelo v) {
        if (buscarPorNumero(v.getNumeroVuelo()) != null) {
            throw new IllegalArgumentException("Número de vuelo duplicado: " + v.getNumeroVuelo());
        }
        vuelos.agregar(v);
    }

    public Vuelo buscarPorNumero(String numeroVuelo) {
        return vuelos.buscarSi(v -> v.getNumeroVuelo().equals(numeroVuelo));
    }

    // ===================== Punto a) =====================
    /** Números de vuelo cuyo estado es 'Demorado'. */
    public ListaEnlazada<String> VuelosDemorados() {
        ListaEnlazada<String> numeros = new ListaEnlazada<>();
        for (Vuelo v : vuelos) {
            if (Vuelo.DEMORADO.equals(v.getEstado())) {
                numeros.agregar(v.getNumeroVuelo());
            }
        }
        return numeros;
    }

    // ===================== Punto b) =====================
    /**
     * Información de los vuelos ordenada por duración de menor a mayor.
     * Se devuelve una copia ordenada; la lista original no cambia.
     */
    public ListaEnlazada<Vuelo> MostrarOrdenadosPorDuracion() {
        ListaEnlazada<Vuelo> copia = new ListaEnlazada<>();
        for (Vuelo v : vuelos) {
            copia.agregar(v);
        }
        copia.ordenar(Comparator.comparingInt(Vuelo::getDuracionMinutos));
        return copia;
    }

    // ===================== Punto c) =====================
    /**
     * Elimina todos los vuelos hacia el destino indicado y devuelve cuántos eliminó.
     * Interpretación: la guía dice "cancelar" en el nombre y "eliminar" en la descripción;
     * se implementa la descripción (se quitan los vuelos de la lista). Comparación sin distinguir mayúsculas.
     */
    public int CancelarVuelosADestino(String destino) {
        return vuelos.eliminarSi(v -> v.getDestino().equalsIgnoreCase(destino));
    }

    // ===================== Punto d) =====================
    /** Actualiza el estado del vuelo. true si se encontró, false en caso contrario. */
    public boolean CambiarEstadoVuelo(String numeroVuelo, String nuevoEstado) {
        if (!Vuelo.esEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
        Vuelo v = buscarPorNumero(numeroVuelo);
        if (v == null) {
            return false;
        }
        v.setEstado(nuevoEstado);
        return true;
    }

    // ===================== Punto e) =====================
    /** Vuelo con mayor duración en minutos. Devuelve null si la lista está vacía. */
    public Vuelo BuscarVueloMasLargo() {
        Vuelo mayor = null;
        for (Vuelo v : vuelos) {
            if (mayor == null || v.getDuracionMinutos() > mayor.getDuracionMinutos()) {
                mayor = v;
            }
        }
        return mayor;
    }

    @Override
    public String toString() {
        return vuelos.toString();
    }
}
