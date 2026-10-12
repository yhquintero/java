/**
 * Pruebas del proyecto TechMarket (Equipo 5). Incluye casos positivos y negativos
 * (catálogo vacío, id no encontrado, rangos inválidos, excepciones).
 */
public class Main {

    private static int totalPruebas = 0;
    private static int pruebasSuperadas = 0;

    /** Registra el resultado de una prueba y la imprime. */
    static void verificar(String descripcion, boolean condicion) {
        totalPruebas++;
        if (condicion) {
            pruebasSuperadas++;
        }
        System.out.println((condicion ? "  [OK]    " : "  [FALLO] ") + descripcion);
    }

    /** Devuelve true si la acción lanza alguna excepción de tiempo de ejecución. */
    static boolean lanza(Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (RuntimeException e) {
            return true;
        }
    }

    static boolean iguales(double a, double b) {
        return Math.abs(a - b) < 1e-9;
    }

    static Producto buscar(TechMarket tienda, String id) {
        for (Producto p : tienda.getCatalogo()) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /** Datos de prueba del enunciado (una instancia nueva en cada llamada). */
    static TechMarket datosPrueba() {
        TechMarket tienda = new TechMarket();
        tienda.registrarProducto(new Producto("P01", "Laptop Gaming", "ASUS", 1200.0));
        tienda.registrarProducto(new Producto("P02", "Mouse Inalámbrico", "Logitech", 25.0));
        tienda.registrarProducto(new Producto("P03", "Teclado Mecánico", "ASUS", 80.0));
        tienda.registrarProducto(new Producto("P04", "Monitor 24\"", "Samsung", 180.0));
        return tienda;
    }

    public static void main(String[] args) {
        System.out.println("=== TechMarket - ArrayList ===");

        System.out.println("\n-- Datos insertados (caso de prueba del enunciado) --");
        TechMarket tienda = datosPrueba();
        for (Producto p : tienda.getCatalogo()) {
            System.out.println("  " + p);
        }
        verificar("Se insertaron 4 productos", tienda.cantidad() == 4);

        System.out.println("\n-- Punto a) FiltrarPorRangoPrecio(double precioMin, double precioMax) --");
        verificar("Positivo: rango [25, 180] devuelve [Mouse Inalámbrico, Teclado Mecánico, Monitor 24\"]",
                tienda.FiltrarPorRangoPrecio(25, 180).toString()
                        .equals("[Mouse Inalámbrico, Teclado Mecánico, Monitor 24\"]"));
        verificar("Positivo: el rango es inclusivo ([80, 80] devuelve Teclado Mecánico)",
                tienda.FiltrarPorRangoPrecio(80, 80).toString().equals("[Teclado Mecánico]"));
        verificar("Negativo: rango sin productos ([500, 1000]) devuelve lista vacía",
                tienda.FiltrarPorRangoPrecio(500, 1000).isEmpty());
        verificar("Negativo: precioMin mayor que precioMax lanza IllegalArgumentException",
                lanza(() -> tienda.FiltrarPorRangoPrecio(200, 10)));
        verificar("Negativo: catálogo vacío devuelve lista vacía",
                new TechMarket().FiltrarPorRangoPrecio(0, 1000).isEmpty());

        System.out.println("\n-- Punto b) AplicarDescuentoMarca(String marca, double porcentaje) --");
        TechMarket b1 = datosPrueba();
        verificar("Positivo: descuento del 10% a ASUS afecta a 2 productos", b1.AplicarDescuentoMarca("ASUS", 10) == 2);
        verificar("Positivo: Laptop Gaming pasa de 1200.0 a 1080.0", iguales(buscar(b1, "P01").getPrecio(), 1080.0));
        verificar("Positivo: Teclado Mecánico pasa de 80.0 a 72.0", iguales(buscar(b1, "P03").getPrecio(), 72.0));
        verificar("Positivo: productos de otras marcas no cambian (Logitech sigue en 25.0)",
                iguales(buscar(b1, "P02").getPrecio(), 25.0));
        verificar("Negativo: marca sin productos devuelve 0", datosPrueba().AplicarDescuentoMarca("Apple", 10) == 0);
        verificar("Negativo: porcentaje mayor que 100 lanza IllegalArgumentException",
                lanza(() -> datosPrueba().AplicarDescuentoMarca("ASUS", 150)));
        verificar("Negativo: catálogo vacío devuelve 0", new TechMarket().AplicarDescuentoMarca("ASUS", 10) == 0);

        System.out.println("\n-- Punto c) BuscarProductoBarato() --");
        Producto barato = datosPrueba().BuscarProductoBarato();
        verificar("Positivo: el producto más barato es P02 Mouse Inalámbrico (25.0)",
                barato != null && barato.getId().equals("P02") && iguales(barato.getPrecio(), 25.0));
        verificar("Negativo: catálogo vacío devuelve null", new TechMarket().BuscarProductoBarato() == null);

        System.out.println("\n-- Punto d) ReponerStockProducto(String id, double nuevoPrecio) --");
        TechMarket d1 = datosPrueba();
        verificar("Positivo: ReponerStockProducto(P03, 90.0) devuelve true", d1.ReponerStockProducto("P03", 90.0));
        verificar("Positivo: P03 queda con precio 90.0", iguales(buscar(d1, "P03").getPrecio(), 90.0));
        verificar("Negativo: id inexistente (P99) devuelve false", !d1.ReponerStockProducto("P99", 10.0));
        verificar("Negativo: catálogo vacío devuelve false", !new TechMarket().ReponerStockProducto("P01", 10.0));
        verificar("Negativo: nuevo precio negativo lanza IllegalArgumentException",
                lanza(() -> datosPrueba().ReponerStockProducto("P01", -1.0)));

        System.out.println("\n-- Punto e) PromedioPrecioPorMarca(String marca) --");
        verificar("Positivo: promedio de ASUS = (1200 + 80) / 2 = 640.0",
                iguales(datosPrueba().PromedioPrecioPorMarca("ASUS"), 640.0));
        verificar("Positivo: promedio de Samsung = 180.0",
                iguales(datosPrueba().PromedioPrecioPorMarca("Samsung"), 180.0));
        verificar("Negativo: marca sin productos devuelve 0.0",
                iguales(datosPrueba().PromedioPrecioPorMarca("Apple"), 0.0));
        verificar("Negativo: catálogo vacío devuelve 0.0",
                iguales(new TechMarket().PromedioPrecioPorMarca("ASUS"), 0.0));

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: precio negativo al crear producto lanza excepción",
                lanza(() -> new Producto("P05", "Cable USB", "Genérica", -3.0)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new TechMarket().registrarProducto(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
