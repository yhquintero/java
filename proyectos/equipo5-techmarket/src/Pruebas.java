/**
 * Casos de prueba del Equipo 5 (TechMarket): positivos, negativos y de borde.
 * No usa librerías externas; cada verificación imprime [OK] o [FALLO].
 */
public class Pruebas {

    private static int aciertos = 0;
    private static int fallos = 0;

    /** Acción que puede lanzar excepción (para comprobar casos negativos). */
    interface Accion {
        void ejecutar();
    }

    static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            aciertos++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallos++;
            System.out.println("[FALLO] " + descripcion);
        }
    }

    /** Devuelve true si la acción lanza una excepción. */
    static boolean lanza(Accion accion) {
        try {
            accion.ejecutar();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    static boolean casiIgual(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    /** Catálogo con los datos de la guía. */
    static TechMarket datosGuia() {
        TechMarket c = new TechMarket();
        c.insertar(new Producto("P01", "Laptop Gaming", "ASUS", 1200.0));
        c.insertar(new Producto("P02", "Mouse Inalámbrico", "Logitech", 25.0));
        c.insertar(new Producto("P03", "Teclado Mecánico", "ASUS", 80.0));
        c.insertar(new Producto("P04", "Monitor 24\"", "Samsung", 180.0));
        return c;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        TechMarket c = datosGuia();
        verificar("a) Rango 25..180 (inclusive) = [Mouse Inalámbrico, Teclado Mecánico, Monitor 24\"]",
                c.FiltrarPorRangoPrecio(25, 180).toString()
                        .equals("[Mouse Inalámbrico, Teclado Mecánico, Monitor 24\"]"));
        verificar("a) Rango 1000..2000 = [Laptop Gaming]",
                c.FiltrarPorRangoPrecio(1000, 2000).toString().equals("[Laptop Gaming]"));
        verificar("b) AplicarDescuentoMarca(ASUS, 10) modifica 2 productos",
                c.AplicarDescuentoMarca("ASUS", 10) == 2);
        verificar("b) Laptop Gaming baja a 1080.0", casiIgual(c.buscarPorId("P01").getPrecio(), 1080.0));
        verificar("c) Producto más barato = P02 (25.0)", c.BuscarProductoBarato().getId().equals("P02"));
        verificar("d) ReponerStockProducto(P02, 22.5) devuelve true", c.ReponerStockProducto("P02", 22.5));
        verificar("d) El precio de P02 queda en 22.5", casiIgual(c.buscarPorId("P02").getPrecio(), 22.5));
        verificar("e) Promedio ASUS tras el descuento = 576.0",
                casiIgual(c.PromedioPrecioPorMarca("ASUS"), 576.0));
        verificar("e) Promedio ASUS en datos originales = 640.0",
                casiIgual(datosGuia().PromedioPrecioPorMarca("ASUS"), 640.0));
        verificar("Borde: rango de un solo valor exacto (180..180) incluye a Monitor 24\"",
                datosGuia().FiltrarPorRangoPrecio(180, 180).toString().equals("[Monitor 24\"]"));

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        TechMarket n = datosGuia();
        verificar("a) Rango sin productos devuelve []", n.FiltrarPorRangoPrecio(300, 500).toString().equals("[]"));
        verificar("a) Rango con mínimo mayor que máximo lanza excepción",
                lanza(() -> n.FiltrarPorRangoPrecio(500, 100)));
        verificar("b) Marca inexistente no modifica productos", n.AplicarDescuentoMarca("Sony", 10) == 0);
        verificar("b) Descuento mayor a 100% lanza excepción", lanza(() -> n.AplicarDescuentoMarca("ASUS", 150)));
        verificar("d) ReponerStockProducto con ID inexistente devuelve false", !n.ReponerStockProducto("P99", 5.0));
        verificar("d) Precio negativo lanza excepción", lanza(() -> n.ReponerStockProducto("P01", -1.0)));
        verificar("e) Marca sin productos devuelve 0.0", n.PromedioPrecioPorMarca("Sony") == 0.0);
        verificar("Precio negativo al crear producto lanza excepción",
                lanza(() -> new Producto("P05", "Cable", "Genérica", -3.0)));
        verificar("ID de producto duplicado lanza excepción",
                lanza(() -> n.insertar(new Producto("P01", "Otro", "ASUS", 10.0))));

        System.out.println("\n=== PRUEBAS DE BORDE (catálogo vacío) ===");
        TechMarket vacio = new TechMarket();
        verificar("a) Filtrar en catálogo vacío = []", vacio.FiltrarPorRangoPrecio(0, 1000).toString().equals("[]"));
        verificar("b) Descuento en catálogo vacío modifica 0", vacio.AplicarDescuentoMarca("ASUS", 10) == 0);
        verificar("c) Producto barato en catálogo vacío es null", vacio.BuscarProductoBarato() == null);
        verificar("d) Reponer precio en catálogo vacío devuelve false", !vacio.ReponerStockProducto("P01", 5.0));
        verificar("e) Promedio en catálogo vacío = 0.0", vacio.PromedioPrecioPorMarca("ASUS") == 0.0);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
