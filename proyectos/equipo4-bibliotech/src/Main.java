/**
 * Demostración de los puntos a) a e) del Equipo 4 (BiblioTech)
 * con los datos de prueba sugeridos en la guía.
 */
public class Main {

    public static void main(String[] args) {
        BiblioTech biblioteca = new BiblioTech();

        // Datos de prueba de la guía: (código, título, categoría, días de préstamo)
        biblioteca.insertar(new Libro("L01", "Java a Fondo", "Ingeniería", 15));
        biblioteca.insertar(new Libro("L02", "Cien Años de Soledad", "Literatura", 8));
        biblioteca.insertar(new Libro("L03", "Estructuras de Datos", "Ingeniería", 22));
        biblioteca.insertar(new Libro("L04", "Breve Historia Universal", "Historia", 30));

        System.out.println("Catálogo inicial: " + biblioteca);

        // Punto a)
        System.out.println("a) LibrosVencidos(14): " + biblioteca.LibrosVencidos(14));

        // Punto b)
        System.out.println("b) PromedioDiasPorCategoria(\"Ingeniería\"): "
                + biblioteca.PromedioDiasPorCategoria("Ingeniería"));

        // Punto c)
        System.out.println("c) RenovarPrestamo(\"L02\", 5): " + biblioteca.RenovarPrestamo("L02", 5));
        System.out.println("   RenovarPrestamo(\"L99\", 5): " + biblioteca.RenovarPrestamo("L99", 5));

        // Punto d)
        System.out.println("d) EliminarLibrosPorCategoria(\"Historia\") eliminó: "
                + biblioteca.EliminarLibrosPorCategoria("Historia") + " libro(s)");

        // Punto e)
        System.out.println("e) BuscarLibroConMasDias(): " + biblioteca.BuscarLibroConMasDias());

        System.out.println("Catálogo final: " + biblioteca);
    }
}
