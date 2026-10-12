import java.util.Locale;

/** Producto informático del catálogo de TechMarket: id, nombre, marca y precio. */
public class Producto {

    private final String id;
    private final String nombre;
    private final String marca;
    private double precio;

    public Producto(String id, String nombre, String marca, double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }

    public String getMarca() { return marca; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s - %s (%s): %.2f", id, nombre, marca, precio);
    }
}
