import java.util.ArrayList;
import java.util.Iterator;

/**
 * Módulo de préstamos de la biblioteca universitaria 'BiblioTech'.
 * Estructura del enunciado: ArrayList (java.util.ArrayList, permitido expresamente por el enunciado).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class BiblioTech {

    private final ArrayList<Libro> catalogo = new ArrayList<>();

    /** Registra un libro prestado en el catálogo (ingreso de datos). */
    public void registrarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo.");
        }
        catalogo.add(libro);
    }

    public int cantidad() {
        return catalogo.size();
    }

    public ArrayList<Libro> getCatalogo() {
        return catalogo;
    }

    // =====================================================================
    // Punto a) LibrosVencidos(int diasLimite)
    // Listado de títulos cuyos días de préstamo superan (estrictamente) el límite indicado.
    // =====================================================================
    public ArrayList<String> LibrosVencidos(int diasLimite) {
        ArrayList<String> titulos = new ArrayList<>();
        for (Libro libro : catalogo) {
            if (libro.getDiasDePrestamo() > diasLimite) {
                titulos.add(libro.getTitulo());
            }
        }
        return titulos;
    }

    // =====================================================================
    // Punto b) PromedioDiasPorCategoria(String categoria)
    // Promedio de días de préstamo de los libros de la categoría indicada.
    // Interpretación: si no hay libros de esa categoría, devuelve 0.0.
    // =====================================================================
    public double PromedioDiasPorCategoria(String categoria) {
        double suma = 0.0;
        int contador = 0;
        for (Libro libro : catalogo) {
            if (libro.getCategoria().equalsIgnoreCase(categoria)) {
                suma += libro.getDiasDePrestamo();
                contador++;
            }
        }
        return contador == 0 ? 0.0 : suma / contador;
    }

    // =====================================================================
    // Punto c) RenovarPrestamo(String codigo, int diasAdicionales)
    // Busca el libro por código y le suma los días adicionales. true si se encontró, false si no.
    // Interpretación: diasAdicionales negativos lanzan IllegalArgumentException.
    // =====================================================================
    public boolean RenovarPrestamo(String codigo, int diasAdicionales) {
        if (diasAdicionales < 0) {
            throw new IllegalArgumentException("Los días adicionales no pueden ser negativos.");
        }
        for (Libro libro : catalogo) {
            if (libro.getCodigo().equals(codigo)) {
                libro.agregarDias(diasAdicionales);
                return true;
            }
        }
        return false;
    }

    // =====================================================================
    // Punto d) EliminarLibrosPorCategoria(String categoria)
    // Elimina del catálogo todos los libros de la categoría indicada y devuelve la cantidad eliminada.
    // =====================================================================
    public int EliminarLibrosPorCategoria(String categoria) {
        int eliminados = 0;
        Iterator<Libro> it = catalogo.iterator();
        while (it.hasNext()) {
            if (it.next().getCategoria().equalsIgnoreCase(categoria)) {
                it.remove();
                eliminados++;
            }
        }
        return eliminados;
    }

    // =====================================================================
    // Punto e) BuscarLibroConMasDias()
    // Devuelve el libro con mayor tiempo de préstamo (su título y sus días).
    // Si hay empate, gana el primero registrado. Devuelve null si el catálogo está vacío.
    // =====================================================================
    public Libro BuscarLibroConMasDias() {
        Libro mayor = null;
        for (Libro libro : catalogo) {
            if (mayor == null || libro.getDiasDePrestamo() > mayor.getDiasDePrestamo()) {
                mayor = libro;
            }
        }
        return mayor;
    }
}
