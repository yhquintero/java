/**
 * Pruebas del proyecto FarmaVida (Equipo 1). Incluye casos positivos y negativos
 * (lista vacía, elemento no encontrado, excepciones).
 * Ejecución: ver instrucciones de compilación en la documentación del proyecto.
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

    static Medicamento buscar(FarmaVida farmacia, String codigo) {
        for (Medicamento m : farmacia.getMedicamentos()) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        return null;
    }

    /** Datos de prueba del enunciado (se crea una instancia nueva en cada llamada). */
    static FarmaVida datosPrueba() {
        FarmaVida farmacia = new FarmaVida();
        farmacia.registrarMedicamento(new Medicamento("M01", "Paracetamol", "Analgésico", 15, 2.50));
        farmacia.registrarMedicamento(new Medicamento("M02", "Amoxicilina", "Antibiótico", 3, 12.00));
        farmacia.registrarMedicamento(new Medicamento("M03", "Ibuprofeno", "Antiinflamatorio", 2, 4.50));
        farmacia.registrarMedicamento(new Medicamento("M04", "Vitamina C", "Vitamina", 20, 5.00));
        farmacia.registrarMedicamento(new Medicamento("M05", "Azitromicina", "Antibiótico", 5, 15.00));
        return farmacia;
    }

    public static void main(String[] args) {
        System.out.println("=== FarmaVida - Lista Simplemente Enlazada ===");

        System.out.println("\n-- Datos insertados (caso de prueba del enunciado) --");
        FarmaVida farmacia = datosPrueba();
        for (Medicamento m : farmacia.getMedicamentos()) {
            System.out.println("  " + m);
        }
        verificar("Se insertaron 5 medicamentos", farmacia.cantidad() == 5);

        System.out.println("\n-- Punto a) MedicamentosAgotados() --");
        verificar("Positivo: devuelve Amoxicilina, Ibuprofeno y Azitromicina (stock <= 5)",
                farmacia.MedicamentosAgotados().toString().equals("[Amoxicilina, Ibuprofeno, Azitromicina]"));
        verificar("Negativo: lista vacía devuelve una lista vacía",
                new FarmaVida().MedicamentosAgotados().estaVacia());

        System.out.println("\n-- Punto b) PromedioPrecioPorCategoria(String categoria) --");
        verificar("Positivo: promedio de Antibiótico = (12.00 + 15.00) / 2 = 13.5",
                iguales(farmacia.PromedioPrecioPorCategoria("Antibiótico"), 13.5));
        verificar("Positivo: promedio de Vitamina = 5.0",
                iguales(farmacia.PromedioPrecioPorCategoria("Vitamina"), 5.0));
        verificar("Negativo: categoría sin medicamentos devuelve 0.0",
                iguales(new FarmaVida().PromedioPrecioPorCategoria("Vitamina"), 0.0));
        FarmaVida soloParacetamol = new FarmaVida();
        soloParacetamol.registrarMedicamento(new Medicamento("M01", "Paracetamol", "Analgésico", 15, 2.50));
        verificar("Negativo: Antibiótico no existe en la lista y devuelve 0.0",
                iguales(soloParacetamol.PromedioPrecioPorCategoria("Antibiótico"), 0.0));

        System.out.println("\n-- Punto c) ActualizarStock(String codigo, int cantidadAgregada) --");
        FarmaVida f1 = datosPrueba();
        verificar("Positivo: ActualizarStock(M02, 10) devuelve true", f1.ActualizarStock("M02", 10));
        verificar("Positivo: el stock de M02 pasa de 3 a 13", buscar(f1, "M02").getStock() == 13);
        verificar("Negativo: código inexistente (M99) devuelve false", !f1.ActualizarStock("M99", 5));
        verificar("Negativo: cantidad negativa lanza IllegalArgumentException",
                lanza(() -> f1.ActualizarStock("M01", -3)));
        verificar("Negativo: lista vacía devuelve false", !new FarmaVida().ActualizarStock("M01", 1));

        System.out.println("\n-- Punto d) ListarPreciosInversos() --");
        verificar("Positivo: precios en orden inverso [15.0, 5.0, 4.5, 12.0, 2.5]",
                datosPrueba().ListarPreciosInversos().toString().equals("[15.0, 5.0, 4.5, 12.0, 2.5]"));
        verificar("Negativo: lista vacía devuelve lista vacía",
                new FarmaVida().ListarPreciosInversos().estaVacia());

        System.out.println("\n-- Punto e) AplicarAumentoGeneral(double porcentaje) --");
        FarmaVida f2 = datosPrueba();
        int modificados = f2.AplicarAumentoGeneral(10);
        verificar("Positivo: devuelve 5 medicamentos modificados", modificados == 5);
        verificar("Positivo: Paracetamol pasa de 2.50 a 2.75", iguales(buscar(f2, "M01").getPrecio(), 2.75));
        verificar("Positivo: Azitromicina pasa de 15.00 a 16.50", iguales(buscar(f2, "M05").getPrecio(), 16.5));
        verificar("Negativo: porcentaje negativo lanza IllegalArgumentException",
                lanza(() -> datosPrueba().AplicarAumentoGeneral(-5)));
        verificar("Negativo: lista vacía devuelve 0 modificados",
                new FarmaVida().AplicarAumentoGeneral(10) == 0);

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: categoría fuera del catálogo lanza excepción",
                lanza(() -> new Medicamento("M06", "Aspirina", "Cardiaco", 1, 1.0)));
        verificar("Negativo: precio negativo lanza excepción",
                lanza(() -> new Medicamento("M07", "Aspirina", "Analgésico", 1, -1.0)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new FarmaVida().registrarMedicamento(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
