/**
 * Casos de prueba del Equipo 4 (BiblioTech): positivos, negativos y de borde.
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
    static BiblioTech datosGuia() {
        BiblioTech b = new BiblioTech();
        b.insertar(new Libro("L01", "Java a Fondo", "Ingeniería", 15));
        b.insertar(new Libro("L02", "Cien Años de Soledad", "Literatura", 8));
        b.insertar(new Libro("L03", "Estructuras de Datos", "Ingeniería", 22));
        b.insertar(new Libro("L04", "Breve Historia Universal", "Historia", 30));
        return b;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        BiblioTech b = datosGuia();
        verificar("a) Vencidos con límite 14 = [Java a Fondo, Estructuras de Datos, Breve Historia Universal]",
                b.LibrosVencidos(14).toString()
                        .equals("[Java a Fondo, Estructuras de Datos, Breve Historia Universal]"));
        verificar("a) Borde: límite 15 no incluye a Java a Fondo (se requiere mayor estricto)",
                b.LibrosVencidos(15).toString().equals("[Estructuras de Datos, Breve Historia Universal]"));
        verificar("b) Promedio de Ingeniería = 18.5", casiIgual(b.PromedioDiasPorCategoria("Ingeniería"), 18.5));
        verificar("b) Promedio de Literatura = 8.0", casiIgual(b.PromedioDiasPorCategoria("Literatura"), 8.0));
        verificar("c) RenovarPrestamo(L02, 5) devuelve true", b.RenovarPrestamo("L02", 5));
        verificar("c) L02 queda con 13 días", b.buscarPorCodigo("L02").getDiasDePrestamo() == 13);
        verificar("d) Eliminar la categoría Historia elimina 1 libro", b.EliminarLibrosPorCategoria("Historia") == 1);
        verificar("d) Tras eliminar Historia, L04 ya no está en el catálogo", b.buscarPorCodigo("L04") == null);
        verificar("e) Libro con más días = Estructuras de Datos (22)",
                b.BuscarLibroConMasDias().getTitulo().equals("Estructuras de Datos"));

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        BiblioTech n = datosGuia();
        verificar("a) Límite mayor que todos los préstamos devuelve []",
                n.LibrosVencidos(100).toString().equals("[]"));
        verificar("b) Categoría sin libros (Ciencias) devuelve 0.0",
                n.PromedioDiasPorCategoria("Ciencias") == 0.0);
        verificar("c) RenovarPrestamo con código inexistente devuelve false", !n.RenovarPrestamo("L99", 5));
        verificar("c) Días adicionales negativos lanzan excepción", lanza(() -> n.RenovarPrestamo("L01", -2)));
        verificar("d) Eliminar una categoría sin libros elimina 0", n.EliminarLibrosPorCategoria("Ciencias") == 0);
        verificar("Categoría no válida lanza excepción",
                lanza(() -> new Libro("L05", "Arte moderno", "Arte", 5)));
        verificar("Días de préstamo negativos lanzan excepción",
                lanza(() -> new Libro("L06", "Test", "Historia", -1)));
        verificar("Código duplicado lanza excepción",
                lanza(() -> n.insertar(new Libro("L01", "Otro", "Historia", 3))));

        System.out.println("\n=== PRUEBAS DE BORDE (catálogo vacío) ===");
        BiblioTech vacia = new BiblioTech();
        verificar("a) Vencidos en catálogo vacío = []", vacia.LibrosVencidos(0).toString().equals("[]"));
        verificar("b) Promedio en catálogo vacío = 0.0", vacia.PromedioDiasPorCategoria("Historia") == 0.0);
        verificar("c) Renovar en catálogo vacío devuelve false", !vacia.RenovarPrestamo("L01", 3));
        verificar("d) Eliminar en catálogo vacío elimina 0", vacia.EliminarLibrosPorCategoria("Historia") == 0);
        verificar("e) Libro con más días en catálogo vacío es null", vacia.BuscarLibroConMasDias() == null);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
