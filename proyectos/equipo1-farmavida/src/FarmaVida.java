/**
 * Control de medicamentos de la farmacia FarmaVida.
 * Estructura: ListaEnlazada&lt;Medicamento&gt; (lista simplemente enlazada hecha a mano).
 */
public class FarmaVida {

    private final ListaEnlazada<Medicamento> medicamentos = new ListaEnlazada<>();

    /** Inserta un medicamento. Interpretación: no se permiten códigos repetidos. */
    public void insertar(Medicamento m) {
        if (buscarPorCodigo(m.getCodigo()) != null) {
            throw new IllegalArgumentException("Código duplicado: " + m.getCodigo());
        }
        medicamentos.agregar(m);
    }

    public Medicamento buscarPorCodigo(String codigo) {
        return medicamentos.buscarSi(m -> m.getCodigo().equals(codigo));
    }

    // ===================== Punto a) =====================
    /** Nombres de los medicamentos con stock menor o igual a 5 unidades. */
    public ListaEnlazada<String> MedicamentosAgotados() {
        ListaEnlazada<String> nombres = new ListaEnlazada<>();
        for (Medicamento m : medicamentos) {
            if (m.getStock() <= 5) {
                nombres.agregar(m.getNombre());
            }
        }
        return nombres;
    }

    // ===================== Punto b) =====================
    /** Precio promedio de la categoría indicada. Devuelve 0.0 si no hay medicamentos en ella. */
    public double PromedioPrecioPorCategoria(String categoria) {
        double suma = 0;
        int cuenta = 0;
        for (Medicamento m : medicamentos) {
            if (m.getCategoria().equals(categoria)) {
                suma += m.getPrecio();
                cuenta++;
            }
        }
        return cuenta == 0 ? 0.0 : suma / cuenta;
    }

    // ===================== Punto c) =====================
    /**
     * Suma la cantidad entregada al stock del medicamento con ese código.
     * Devuelve true si existe y false si no. Interpretación: una cantidad negativa lanza excepción.
     */
    public boolean ActualizarStock(String codigo, int cantidadAgregada) {
        if (cantidadAgregada < 0) {
            throw new IllegalArgumentException("La cantidad agregada no puede ser negativa");
        }
        Medicamento m = buscarPorCodigo(codigo);
        if (m == null) {
            return false;
        }
        m.setStock(m.getStock() + cantidadAgregada);
        return true;
    }

    // ===================== Punto d) =====================
    /** Precios en orden inverso al de ingreso: cada precio se antepone al construir la lista. */
    public ListaEnlazada<Double> ListarPreciosInversos() {
        ListaEnlazada<Double> precios = new ListaEnlazada<>();
        for (Medicamento m : medicamentos) {
            precios.agregarAlInicio(m.getPrecio());
        }
        return precios;
    }

    // ===================== Punto e) =====================
    /**
     * Incrementa el precio de todos los medicamentos en el porcentaje dado
     * y devuelve la cantidad de medicamentos modificados.
     * Interpretación: el porcentaje de aumento no puede ser negativo.
     */
    public int AplicarAumentoGeneral(double porcentaje) {
        if (porcentaje < 0) {
            throw new IllegalArgumentException("El porcentaje de aumento no puede ser negativo");
        }
        int modificados = 0;
        for (Medicamento m : medicamentos) {
            m.setPrecio(m.getPrecio() * (1 + porcentaje / 100.0));
            modificados++;
        }
        return modificados;
    }

    @Override
    public String toString() {
        return medicamentos.toString();
    }
}
