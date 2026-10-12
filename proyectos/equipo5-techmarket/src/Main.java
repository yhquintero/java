/**
 * Demostración de los puntos a) a e) del Equipo 5 (TechMarket)
 * con los datos de prueba sugeridos en la guía.
 */
public class Main {

    public static void main(String[] args) {
        TechMarket catalogo = new TechMarket();

        // Datos de prueba de la guía: (id, nombre, marca, precio)
        catalogo.insertar(new Producto("P01", "Laptop Gaming", "ASUS", 1200.0));
        catalogo.insertar(new Producto("P02", "Mouse Inalámbrico", "Logitech", 25.0));
        catalogo.insertar(new Producto("P03", "Teclado Mecánico", "ASUS", 80.0));
        catalogo.insertar(new Producto("P04", "Monitor 24\"", "Samsung", 180.0));

        System.out.println("Catálogo inicial: " + catalogo);

        // Punto a)
        System.out.println("a) FiltrarPorRangoPrecio(25, 180): " + catalogo.FiltrarPorRangoPrecio(25, 180));

        // Punto b)
        System.out.println("b) AplicarDescuentoMarca(\"ASUS\", 10) modificó: "
                + catalogo.AplicarDescuentoMarca("ASUS", 10) + " productos");

        // Punto c)
        System.out.println("c) BuscarProductoBarato(): " + catalogo.BuscarProductoBarato());

        // Punto d)
        System.out.println("d) ReponerStockProducto(\"P02\", 22.5): " + catalogo.ReponerStockProducto("P02", 22.5));
        System.out.println("   ReponerStockProducto(\"P99\", 22.5): " + catalogo.ReponerStockProducto("P99", 22.5));

        // Punto e)
        System.out.println("e) PromedioPrecioPorMarca(\"ASUS\"): " + catalogo.PromedioPrecioPorMarca("ASUS"));

        System.out.println("Catálogo final: " + catalogo);
    }
}
