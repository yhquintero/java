import java.util.Locale;

/** Medicamento de la farmacia FarmaVida: código, nombre, categoría, stock y precio. */
public class Medicamento {

    /** Categorías permitidas según la guía. */
    public static final String[] CATEGORIAS = {"Analgésico", "Antibiótico", "Antiinflamatorio", "Vitamina"};

    private final String codigo;
    private final String nombre;
    private final String categoria;
    private int stock;
    private double precio;

    public Medicamento(String codigo, String nombre, String categoria, int stock, double precio) {
        if (!esCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }
        if (stock < 0 || precio < 0) {
            throw new IllegalArgumentException("El stock y el precio no pueden ser negativos");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
    }

    public static boolean esCategoriaValida(String categoria) {
        for (String c : CATEGORIAS) {
            if (c.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public String getCategoria() { return categoria; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s %s (%s) stock=%d precio=%.2f",
                codigo, nombre, categoria, stock, precio);
    }
}
