import java.util.ArrayList;

/**
 * Catálogo de productos informáticos de la tienda TechMarket.
 * Estructura: ArrayList de java.util, tal como la indica la guía para este equipo.
 */
public class TechMarket {

    private final ArrayList<Producto> productos = new ArrayList<>();

    /** Agrega un producto. Interpretación: no se permiten IDs repetidos. */
    public void insertar(Producto p) {
        if (buscarPorId(p.getId()) != null) {
            throw new IllegalArgumentException("ID de producto duplicado: " + p.getId());
        }
        productos.add(p);
    }

    public Producto buscarPorId(String id) {
        for (Producto p : productos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // ===================== Punto a) =====================
    /** Nombres de los productos cuyo precio está en el rango [precioMin, precioMax] (ambos inclusive). */
    public ArrayList<String> FiltrarPorRangoPrecio(double precioMin, double precioMax) {
        if (precioMin > precioMax) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor que el máximo");
        }
        ArrayList<String> nombres = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getPrecio() >= precioMin && p.getPrecio() <= precioMax) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    // ===================== Punto b) =====================
    /**
     * Reduce el precio en el porcentaje dado a todos los productos de la marca.
     * Devuelve cuántos productos se modificaron (dato adicional de utilidad).
     */
    public int AplicarDescuentoMarca(String marca, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100");
        }
        int modificados = 0;
        for (Producto p : productos) {
            if (p.getMarca().equals(marca)) {
                p.setPrecio(p.getPrecio() * (1 - porcentaje / 100.0));
                modificados++;
            }
        }
        return modificados;
    }

    // ===================== Punto c) =====================
    /** Producto con el precio más bajo. Devuelve null si el catálogo está vacío. */
    public Producto BuscarProductoBarato() {
        Producto masBarato = null;
        for (Producto p : productos) {
            if (masBarato == null || p.getPrecio() < masBarato.getPrecio()) {
                masBarato = p;
            }
        }
        return masBarato;
    }

    // ===================== Punto d) =====================
    /**
     * Actualiza el precio del producto con ese ID (según la descripción de la guía,
     * aunque el nombre del método diga "reponer stock"). true si existía, false si no.
     */
    public boolean ReponerStockProducto(String id, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El nuevo precio no puede ser negativo");
        }
        Producto p = buscarPorId(id);
        if (p == null) {
            return false;
        }
        p.setPrecio(nuevoPrecio);
        return true;
    }

    // ===================== Punto e) =====================
    /** Precio promedio de los productos de la marca. Devuelve 0.0 si no hay productos de esa marca. */
    public double PromedioPrecioPorMarca(String marca) {
        double suma = 0;
        int cuenta = 0;
        for (Producto p : productos) {
            if (p.getMarca().equals(marca)) {
                suma += p.getPrecio();
                cuenta++;
            }
        }
        return cuenta == 0 ? 0.0 : suma / cuenta;
    }

    @Override
    public String toString() {
        return productos.toString();
    }
}
