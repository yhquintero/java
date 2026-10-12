import java.util.ArrayList;

/**
 * Administración del catálogo de productos informáticos de la tienda en línea 'TechMarket'.
 * Estructura del enunciado: ArrayList (java.util.ArrayList, permitido expresamente por el enunciado).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class TechMarket {

    private final ArrayList<Producto> catalogo = new ArrayList<>();

    /** Registra un producto en el catálogo (ingreso de datos). */
    public void registrarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        catalogo.add(producto);
    }

    public int cantidad() {
        return catalogo.size();
    }

    public ArrayList<Producto> getCatalogo() {
        return catalogo;
    }

    // =====================================================================
    // Punto a) FiltrarPorRangoPrecio(double precioMin, double precioMax)
    // Nombres de los productos cuyo precio está en el rango inclusivo [precioMin, precioMax].
    // Interpretación: precioMin > precioMax lanza IllegalArgumentException.
    // =====================================================================
    public ArrayList<String> FiltrarPorRangoPrecio(double precioMin, double precioMax) {
        if (precioMin > precioMax) {
            throw new IllegalArgumentException("El precio mínimo no puede ser mayor que el máximo.");
        }
        ArrayList<String> nombres = new ArrayList<>();
        for (Producto p : catalogo) {
            if (p.getPrecio() >= precioMin && p.getPrecio() <= precioMax) {
                nombres.add(p.getNombre());
            }
        }
        return nombres;
    }

    // =====================================================================
    // Punto b) AplicarDescuentoMarca(String marca, double porcentaje)
    // Reduce el precio en el porcentaje dado a todos los productos de la marca indicada.
    // Interpretación: se devuelve la cantidad de productos modificados (el enunciado no lo especifica
    // y es útil para verificar la operación). El porcentaje debe estar entre 0 y 100.
    // =====================================================================
    public int AplicarDescuentoMarca(String marca, double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        int modificados = 0;
        for (Producto p : catalogo) {
            if (p.getMarca().equalsIgnoreCase(marca)) {
                p.aplicarDescuento(porcentaje);
                modificados++;
            }
        }
        return modificados;
    }

    // =====================================================================
    // Punto c) BuscarProductoBarato()
    // Información del producto con el precio más bajo registrado. Si hay empate, gana el primero.
    // Devuelve null si el catálogo está vacío.
    // =====================================================================
    public Producto BuscarProductoBarato() {
        Producto masBarato = null;
        for (Producto p : catalogo) {
            if (masBarato == null || p.getPrecio() < masBarato.getPrecio()) {
                masBarato = p;
            }
        }
        return masBarato;
    }

    // =====================================================================
    // Punto d) ReponerStockProducto(String id, double nuevoPrecio)
    // Actualiza el precio de un producto dado su ID. true si existía, false en caso contrario.
    // Interpretación: aunque el nombre del método menciona "stock", el enunciado indica que
    // la operación actualiza el PRECIO; se implementa según lo que describe el enunciado.
    // Un nuevo precio negativo lanza IllegalArgumentException.
    // =====================================================================
    public boolean ReponerStockProducto(String id, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El nuevo precio no puede ser negativo.");
        }
        for (Producto p : catalogo) {
            if (p.getId().equals(id)) {
                p.establecerPrecio(nuevoPrecio);
                return true;
            }
        }
        return false;
    }

    // =====================================================================
    // Punto e) PromedioPrecioPorMarca(String marca)
    // Precio promedio de todos los productos de la marca indicada.
    // Devuelve 0.0 si no existen productos de esa marca.
    // =====================================================================
    public double PromedioPrecioPorMarca(String marca) {
        double suma = 0.0;
        int contador = 0;
        for (Producto p : catalogo) {
            if (p.getMarca().equalsIgnoreCase(marca)) {
                suma += p.getPrecio();
                contador++;
            }
        }
        return contador == 0 ? 0.0 : suma / contador;
    }
}
