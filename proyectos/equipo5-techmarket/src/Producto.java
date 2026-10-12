/**
 * Entidad Producto informático del catálogo de la tienda en línea 'TechMarket'.
 * Atributos del enunciado: id, nombre, marca y precio.
 */
public class Producto {

    private final String id;
    private final String nombre;
    private final String marca;
    private double precio;

    public Producto(String id, String nombre, String marca, double precio) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id no puede estar vacío.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecio() {
        return precio;
    }

    /** Establece un nuevo precio (usado por ReponerStockProducto). */
    void establecerPrecio(double nuevoPrecio) {
        this.precio = nuevoPrecio;
    }

    /** Reduce el precio en un porcentaje (usado por AplicarDescuentoMarca). */
    void aplicarDescuento(double porcentaje) {
        this.precio = precio - precio * porcentaje / 100.0;
    }

    @Override
    public String toString() {
        return id + " | " + nombre + " | marca: " + marca + " | precio: " + precio;
    }
}
