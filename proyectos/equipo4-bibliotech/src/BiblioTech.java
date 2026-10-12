import java.util.ArrayList;
import java.util.Iterator;

/**
 * Módulo de préstamos de la biblioteca BiblioTech.
 * Estructura: ArrayList de java.util, tal como la indica la guía para este equipo.
 */
public class BiblioTech {

    private final ArrayList<Libro> libros = new ArrayList<>();

    /** Registra un libro prestado. Interpretación: no se permiten códigos repetidos. */
    public void insertar(Libro libro) {
        if (buscarPorCodigo(libro.getCodigo()) != null) {
            throw new IllegalArgumentException("Código duplicado: " + libro.getCodigo());
        }
        libros.add(libro);
    }

    public Libro buscarPorCodigo(String codigo) {
        for (Libro l : libros) {
            if (l.getCodigo().equals(codigo)) {
                return l;
            }
        }
        return null;
    }

    // ===================== Punto a) =====================
    /** Títulos cuyos días de préstamo superan el límite indicado (estrictamente mayor). */
    public ArrayList<String> LibrosVencidos(int diasLimite) {
        ArrayList<String> titulos = new ArrayList<>();
        for (Libro l : libros) {
            if (l.getDiasDePrestamo() > diasLimite) {
                titulos.add(l.getTitulo());
            }
        }
        return titulos;
    }

    // ===================== Punto b) =====================
    /** Promedio de días de préstamo de los libros de la categoría. Devuelve 0.0 si no hay libros en ella. */
    public double PromedioDiasPorCategoria(String categoria) {
        double suma = 0;
        int cuenta = 0;
        for (Libro l : libros) {
            if (l.getCategoria().equals(categoria)) {
                suma += l.getDiasDePrestamo();
                cuenta++;
            }
        }
        return cuenta == 0 ? 0.0 : suma / cuenta;
    }

    // ===================== Punto c) =====================
    /** Suma los días adicionales al préstamo del libro con ese código. true si existe, false si no. */
    public boolean RenovarPrestamo(String codigo, int diasAdicionales) {
        if (diasAdicionales < 0) {
            throw new IllegalArgumentException("Los días adicionales no pueden ser negativos");
        }
        Libro libro = buscarPorCodigo(codigo);
        if (libro == null) {
            return false;
        }
        libro.sumarDias(diasAdicionales);
        return true;
    }

    // ===================== Punto d) =====================
    /** Elimina del catálogo todos los libros de la categoría y devuelve cuántos eliminó. */
    public int EliminarLibrosPorCategoria(String categoria) {
        int eliminados = 0;
        Iterator<Libro> it = libros.iterator();
        while (it.hasNext()) {
            if (it.next().getCategoria().equals(categoria)) {
                it.remove();
                eliminados++;
            }
        }
        return eliminados;
    }

    // ===================== Punto e) =====================
    /**
     * Libro con el mayor tiempo de préstamo (título y días).
     * Devuelve null si no hay libros. Si hay empate, gana el primero registrado.
     */
    public Libro BuscarLibroConMasDias() {
        Libro mayor = null;
        for (Libro l : libros) {
            if (mayor == null || l.getDiasDePrestamo() > mayor.getDiasDePrestamo()) {
                mayor = l;
            }
        }
        return mayor;
    }

    @Override
    public String toString() {
        return libros.toString();
    }
}
