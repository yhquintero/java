/**
 * Entidad Libro prestado en la biblioteca universitaria 'BiblioTech'.
 * Atributos del enunciado: código, título, categoría (Ingeniería, Historia, Literatura, Ciencias)
 * y días de préstamo. Nota: 'díasDePréstamo' se nombra 'diasDePrestamo' (sin tilde).
 */
public class Libro {

    /** Categorías permitidas según el enunciado. */
    public static final String[] CATEGORIAS_VALIDAS = {"Ingeniería", "Historia", "Literatura", "Ciencias"};

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private int diasDePrestamo;

    public Libro(String codigo, String titulo, String categoria, int diasDePrestamo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (!esCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }
        if (diasDePrestamo < 0) {
            throw new IllegalArgumentException("Los días de préstamo no pueden ser negativos.");
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.diasDePrestamo = diasDePrestamo;
    }

    public static boolean esCategoriaValida(String categoria) {
        for (String valida : CATEGORIAS_VALIDAS) {
            if (valida.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDiasDePrestamo() {
        return diasDePrestamo;
    }

    /** Suma días al préstamo (usado por RenovarPrestamo). */
    void agregarDias(int dias) {
        diasDePrestamo += dias;
    }

    @Override
    public String toString() {
        return codigo + " | " + titulo + " | " + categoria + " | " + diasDePrestamo + " días";
    }
}
