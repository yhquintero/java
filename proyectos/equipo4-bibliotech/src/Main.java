import java.util.ArrayList;

/**
 * Pruebas del proyecto BiblioTech (Equipo 4). Incluye casos positivos y negativos
 * (catálogo vacío, código no encontrado, excepciones).
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

    static Libro buscar(BiblioTech biblioteca, String codigo) {
        for (Libro l : biblioteca.getCatalogo()) {
            if (l.getCodigo().equals(codigo)) {
                return l;
            }
        }
        return null;
    }

    /** Datos de prueba del enunciado (una instancia nueva en cada llamada). */
    static BiblioTech datosPrueba() {
        BiblioTech biblioteca = new BiblioTech();
        biblioteca.registrarLibro(new Libro("L01", "Java a Fondo", "Ingeniería", 15));
        biblioteca.registrarLibro(new Libro("L02", "Cien Años de Soledad", "Literatura", 8));
        biblioteca.registrarLibro(new Libro("L03", "Estructuras de Datos", "Ingeniería", 22));
        biblioteca.registrarLibro(new Libro("L04", "Breve Historia Universal", "Historia", 30));
        return biblioteca;
    }

    public static void main(String[] args) {
        System.out.println("=== BiblioTech - ArrayList ===");

        System.out.println("\n-- Datos insertados (caso de prueba del enunciado) --");
        BiblioTech biblioteca = datosPrueba();
        for (Libro l : biblioteca.getCatalogo()) {
            System.out.println("  " + l);
        }
        verificar("Se insertaron 4 libros", biblioteca.cantidad() == 4);

        System.out.println("\n-- Punto a) LibrosVencidos(int diasLimite) --");
        verificar("Positivo: límite 20 devuelve [Estructuras de Datos, Breve Historia Universal]",
                biblioteca.LibrosVencidos(20).toString().equals("[Estructuras de Datos, Breve Historia Universal]"));
        verificar("Positivo: límite 0 devuelve los 4 títulos",
                biblioteca.LibrosVencidos(0).size() == 4);
        verificar("Negativo: límite igual al máximo (30) no devuelve libros (estrictamente mayor)",
                biblioteca.LibrosVencidos(30).isEmpty());
        verificar("Negativo: catálogo vacío devuelve lista vacía",
                new BiblioTech().LibrosVencidos(10).isEmpty());

        System.out.println("\n-- Punto b) PromedioDiasPorCategoria(String categoria) --");
        verificar("Positivo: promedio de Ingeniería = (15 + 22) / 2 = 18.5",
                iguales(biblioteca.PromedioDiasPorCategoria("Ingeniería"), 18.5));
        verificar("Positivo: promedio de Historia = 30.0",
                iguales(biblioteca.PromedioDiasPorCategoria("Historia"), 30.0));
        verificar("Negativo: categoría sin libros (Ciencias) devuelve 0.0",
                iguales(biblioteca.PromedioDiasPorCategoria("Ciencias"), 0.0));
        verificar("Negativo: catálogo vacío devuelve 0.0",
                iguales(new BiblioTech().PromedioDiasPorCategoria("Historia"), 0.0));

        System.out.println("\n-- Punto c) RenovarPrestamo(String codigo, int diasAdicionales) --");
        BiblioTech c1 = datosPrueba();
        verificar("Positivo: RenovarPrestamo(L02, 7) devuelve true", c1.RenovarPrestamo("L02", 7));
        verificar("Positivo: L02 pasa de 8 a 15 días", buscar(c1, "L02").getDiasDePrestamo() == 15);
        verificar("Negativo: código inexistente (L99) devuelve false", !c1.RenovarPrestamo("L99", 3));
        verificar("Negativo: catálogo vacío devuelve false", !new BiblioTech().RenovarPrestamo("L01", 3));
        verificar("Negativo: días adicionales negativos lanzan IllegalArgumentException",
                lanza(() -> datosPrueba().RenovarPrestamo("L01", -2)));

        System.out.println("\n-- Punto d) EliminarLibrosPorCategoria(String categoria) --");
        BiblioTech d1 = datosPrueba();
        verificar("Positivo: eliminar Literatura devuelve 1", d1.EliminarLibrosPorCategoria("Literatura") == 1);
        verificar("Positivo: quedan 3 libros y ninguno es de Literatura",
                d1.cantidad() == 3 && buscar(d1, "L02") == null);
        verificar("Negativo: categoría sin libros (Ciencias) devuelve 0",
                datosPrueba().EliminarLibrosPorCategoria("Ciencias") == 0);
        verificar("Negativo: catálogo vacío devuelve 0",
                new BiblioTech().EliminarLibrosPorCategoria("Historia") == 0);

        System.out.println("\n-- Punto e) BuscarLibroConMasDias() --");
        Libro mayor = datosPrueba().BuscarLibroConMasDias();
        verificar("Positivo: el libro con más días es 'Breve Historia Universal' con 30 días",
                mayor != null && mayor.getTitulo().equals("Breve Historia Universal") && mayor.getDiasDePrestamo() == 30);
        verificar("Negativo: catálogo vacío devuelve null", new BiblioTech().BuscarLibroConMasDias() == null);

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: categoría fuera del catálogo lanza excepción",
                lanza(() -> new Libro("L09", "Novela", "Romance", 5)));
        verificar("Negativo: días de préstamo negativos lanzan excepción",
                lanza(() -> new Libro("L10", "Ensayo", "Historia", -4)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new BiblioTech().registrarLibro(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
