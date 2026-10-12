/**
 * Control de medicamentos de la farmacia 'FarmaVida'.
 * Estructura del enunciado: Lista Simplemente Enlazada (ListaEnlazada&lt;Medicamento&gt;, implementada a mano).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class FarmaVida {

    /** Umbral de stock para considerar un medicamento agotado (punto a). */
    private static final int UMBRAL_AGOTADO = 5;

    private final ListaEnlazada<Medicamento> medicamentos = new ListaEnlazada<>();

    /** Inserta un medicamento al final de la lista (ingreso de datos). */
    public void registrarMedicamento(Medicamento medicamento) {
        if (medicamento == null) {
            throw new IllegalArgumentException("El medicamento no puede ser nulo.");
        }
        medicamentos.agregarAlFinal(medicamento);
    }

    public int cantidad() {
        return medicamentos.size();
    }

    public ListaEnlazada<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    // =====================================================================
    // Punto a) MedicamentosAgotados()
    // Devuelve los nombres de los medicamentos cuyo stock sea <= 5 unidades.
    // =====================================================================
    public ListaEnlazada<String> MedicamentosAgotados() {
        ListaEnlazada<String> nombresAgotados = new ListaEnlazada<>();
        for (Medicamento m : medicamentos) {
            if (m.getStock() <= UMBRAL_AGOTADO) {
                nombresAgotados.agregarAlFinal(m.getNombre());
            }
        }
        return nombresAgotados;
    }

    // =====================================================================
    // Punto b) PromedioPrecioPorCategoria(String categoria)
    // Precio promedio de la categoría indicada. Devuelve 0.0 si no existen medicamentos de esa categoría.
    // Interpretación: la comparación de categoría ignora mayúsculas/minúsculas.
    // =====================================================================
    public double PromedioPrecioPorCategoria(String categoria) {
        double suma = 0.0;
        int contador = 0;
        for (Medicamento m : medicamentos) {
            if (m.getCategoria().equalsIgnoreCase(categoria)) {
                suma += m.getPrecio();
                contador++;
            }
        }
        return contador == 0 ? 0.0 : suma / contador;
    }

    // =====================================================================
    // Punto c) ActualizarStock(String codigo, int cantidadAgregada)
    // Busca por código y suma la cantidad entregada. true si existe, false si no.
    // Interpretación: una cantidad negativa no es válida y lanza IllegalArgumentException.
    // =====================================================================
    public boolean ActualizarStock(String codigo, int cantidadAgregada) {
        if (cantidadAgregada < 0) {
            throw new IllegalArgumentException("La cantidad agregada no puede ser negativa.");
        }
        Medicamento encontrado = medicamentos.buscarPrimero(m -> m.getCodigo().equals(codigo));
        if (encontrado == null) {
            return false;
        }
        encontrado.sumarStock(cantidadAgregada);
        return true;
    }

    // =====================================================================
    // Punto d) ListarPreciosInversos()
    // Lista de precios en orden inverso al que fueron ingresados.
    // Se recorre la lista original y cada precio se coloca al inicio de la nueva lista.
    // =====================================================================
    public ListaEnlazada<Double> ListarPreciosInversos() {
        ListaEnlazada<Double> preciosInversos = new ListaEnlazada<>();
        for (Medicamento m : medicamentos) {
            preciosInversos.agregarAlInicio(m.getPrecio());
        }
        return preciosInversos;
    }

    // =====================================================================
    // Punto e) AplicarAumentoGeneral(double porcentaje)
    // Incrementa el precio de todos los medicamentos en el porcentaje dado y devuelve
    // la cantidad de medicamentos modificados.
    // Interpretación: un porcentaje negativo lanza IllegalArgumentException.
    // =====================================================================
    public int AplicarAumentoGeneral(double porcentaje) {
        if (porcentaje < 0) {
            throw new IllegalArgumentException("El porcentaje de aumento no puede ser negativo.");
        }
        int modificados = 0;
        for (Medicamento m : medicamentos) {
            m.aumentarPrecio(porcentaje);
            modificados++;
        }
        return modificados;
    }
}
