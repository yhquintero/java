import java.util.Locale;

/**
 * Entidad Medicamento de la farmacia 'FarmaVida'.
 * Atributos del enunciado: código, nombre, categoría (Analgésico, Antibiótico, Antiinflamatorio, Vitamina),
 * stock y precio.
 */
public class Medicamento {

    /** Categorías permitidas según el enunciado. */
    public static final String[] CATEGORIAS_VALIDAS = {"Analgésico", "Antibiótico", "Antiinflamatorio", "Vitamina"};

    private final String codigo;
    private final String nombre;
    private final String categoria;
    private int stock;
    private double precio;

    public Medicamento(String codigo, String nombre, String categoria, int stock, double precio) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!esCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
    }

    public static boolean esCategoriaValida(String categoria) {
        for (String valida : CATEGORIAS_VALIDAS) {
            if (valida.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    /** Suma unidades al stock (usado por ActualizarStock). */
    void sumarStock(int cantidad) {
        stock += cantidad;
    }

    /** Incrementa el precio en un porcentaje (usado por AplicarAumentoGeneral). */
    void aumentarPrecio(double porcentaje) {
        precio = precio + precio * porcentaje / 100.0;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s - %s (%s) | stock: %d | precio: %.2f",
                codigo, nombre, categoria, stock, precio);
    }
}
