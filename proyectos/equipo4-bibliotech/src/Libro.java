/** Libro prestado de la biblioteca BiblioTech: código, título, categoría y días de préstamo. */
public class Libro {

    /** Categorías permitidas según la guía. */
    public static final String[] CATEGORIAS = {"Ingeniería", "Historia", "Literatura", "Ciencias"};

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private int diasDePrestamo;

    public Libro(String codigo, String titulo, String categoria, int diasDePrestamo) {
        if (!esCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoría no válida: " + categoria);
        }
        if (diasDePrestamo < 0) {
            throw new IllegalArgumentException("Los días de préstamo no pueden ser negativos");
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.diasDePrestamo = diasDePrestamo;
    }

    public static boolean esCategoriaValida(String categoria) {
        for (String c : CATEGORIAS) {
            if (c.equals(categoria)) {
                return true;
            }
        }
        return false;
    }

    public String getCodigo() { return codigo; }

    public String getTitulo() { return titulo; }

    public String getCategoria() { return categoria; }

    public int getDiasDePrestamo() { return diasDePrestamo; }

    public void sumarDias(int dias) { this.diasDePrestamo += dias; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + "): " + diasDePrestamo + " días";
    }
}
