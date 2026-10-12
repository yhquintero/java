/**
 * Control de vuelos programados de la aerolínea 'SkyFly'.
 * Estructura del enunciado: Lista Simplemente Enlazada (ListaEnlazada&lt;Vuelo&gt;, implementada a mano).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class SkyFly {

    private final ListaEnlazada<Vuelo> vuelos = new ListaEnlazada<>();

    /** Inserta un vuelo al final de la lista (ingreso de datos). */
    public void registrarVuelo(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser nulo.");
        }
        vuelos.agregarAlFinal(vuelo);
    }

    public int cantidad() {
        return vuelos.size();
    }

    public ListaEnlazada<Vuelo> getVuelos() {
        return vuelos;
    }

    // =====================================================================
    // Punto a) VuelosDemorados()
    // Listado con los números de vuelo cuyo estado es 'Demorado'.
    // =====================================================================
    public ListaEnlazada<String> VuelosDemorados() {
        ListaEnlazada<String> numeros = new ListaEnlazada<>();
        for (Vuelo v : vuelos) {
            if (Vuelo.DEMORADO.equals(v.getEstado())) {
                numeros.agregarAlFinal(v.getNumeroVuelo());
            }
        }
        return numeros;
    }

    // =====================================================================
    // Punto b) MostrarOrdenadosPorDuracion()
    // Información de los vuelos ordenada por duración de menor a mayor.
    // Interpretación: se devuelve una copia ordenada (estable); la lista original no cambia.
    // =====================================================================
    public ListaEnlazada<Vuelo> MostrarOrdenadosPorDuracion() {
        return vuelos.copiaOrdenada((a, b) -> Integer.compare(a.getDuracionMinutos(), b.getDuracionMinutos()));
    }

    // =====================================================================
    // Punto c) CancelarVuelosADestino(String destino)
    // Elimina todos los vuelos hacia el destino indicado y devuelve cuántos se eliminaron.
    // Interpretación: la comparación de destino ignora mayúsculas/minúsculas.
    // =====================================================================
    public int CancelarVuelosADestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        return vuelos.eliminarTodos(v -> v.getDestino().equalsIgnoreCase(destino));
    }

    // =====================================================================
    // Punto d) CambiarEstadoVuelo(String numeroVuelo, String nuevoEstado)
    // Busca el vuelo por su número y actualiza su estado. true si se encontró, false si no.
    // Interpretación: un estado fuera de (A tiempo, Demorado, Cancelado) lanza IllegalArgumentException.
    // =====================================================================
    public boolean CambiarEstadoVuelo(String numeroVuelo, String nuevoEstado) {
        if (!Vuelo.esEstadoValido(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
        Vuelo encontrado = vuelos.buscarPrimero(v -> v.getNumeroVuelo().equals(numeroVuelo));
        if (encontrado == null) {
            return false;
        }
        encontrado.cambiarEstado(nuevoEstado);
        return true;
    }

    // =====================================================================
    // Punto e) BuscarVueloMasLargo()
    // Información del vuelo con mayor duración en minutos. Si hay empate, gana el primero registrado.
    // Devuelve null si la lista está vacía.
    // =====================================================================
    public Vuelo BuscarVueloMasLargo() {
        Vuelo masLargo = null;
        for (Vuelo v : vuelos) {
            if (masLargo == null || v.getDuracionMinutos() > masLargo.getDuracionMinutos()) {
                masLargo = v;
            }
        }
        return masLargo;
    }
}
