/**
 * Casos de prueba del Equipo 1 (FarmaVida): positivos, negativos y de borde.
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

    /** Inventario con los datos de la guía. */
    static FarmaVida datosGuia() {
        FarmaVida f = new FarmaVida();
        f.insertar(new Medicamento("M01", "Paracetamol", "Analgésico", 15, 2.50));
        f.insertar(new Medicamento("M02", "Amoxicilina", "Antibiótico", 3, 12.00));
        f.insertar(new Medicamento("M03", "Ibuprofeno", "Antiinflamatorio", 2, 4.50));
        f.insertar(new Medicamento("M04", "Vitamina C", "Vitamina", 20, 5.00));
        f.insertar(new Medicamento("M05", "Azitromicina", "Antibiótico", 5, 15.00));
        return f;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        FarmaVida f = datosGuia();
        verificar("a) Agotados = [Amoxicilina, Ibuprofeno, Azitromicina]",
                f.MedicamentosAgotados().toString().equals("[Amoxicilina, Ibuprofeno, Azitromicina]"));
        verificar("b) Promedio de Antibiótico = 13.5",
                casiIgual(f.PromedioPrecioPorCategoria("Antibiótico"), 13.5));
        verificar("b) Promedio de Vitamina = 5.0",
                casiIgual(f.PromedioPrecioPorCategoria("Vitamina"), 5.0));
        verificar("c) ActualizarStock(M03, 10) devuelve true", f.ActualizarStock("M03", 10));
        verificar("c) El stock de M03 pasa a 12", f.buscarPorCodigo("M03").getStock() == 12);
        verificar("c) Tras reponer, Ibuprofeno ya no aparece como agotado",
                f.MedicamentosAgotados().toString().equals("[Amoxicilina, Azitromicina]"));
        verificar("d) Precios en orden inverso = [15.0, 5.0, 4.5, 12.0, 2.5]",
                f.ListarPreciosInversos().toString().equals("[15.0, 5.0, 4.5, 12.0, 2.5]"));
        verificar("e) AplicarAumentoGeneral(10) modifica 5 medicamentos",
                f.AplicarAumentoGeneral(10) == 5);
        verificar("e) Paracetamol sube de 2.50 a 2.75", casiIgual(f.buscarPorCodigo("M01").getPrecio(), 2.75));
        FarmaVida limite = new FarmaVida();
        limite.insertar(new Medicamento("X1", "Prueba", "Vitamina", 5, 1.0));
        verificar("Borde: stock exactamente 5 cuenta como agotado", limite.MedicamentosAgotados().size() == 1);

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        FarmaVida solo = new FarmaVida();
        solo.insertar(new Medicamento("M01", "Paracetamol", "Analgésico", 15, 2.50));
        verificar("b) Categoría sin medicamentos devuelve 0.0", solo.PromedioPrecioPorCategoria("Vitamina") == 0.0);
        verificar("c) Código inexistente devuelve false", !f.ActualizarStock("M99", 5));
        verificar("c) Cantidad negativa lanza excepción", lanza(() -> f.ActualizarStock("M01", -3)));
        verificar("e) Porcentaje negativo lanza excepción", lanza(() -> f.AplicarAumentoGeneral(-5)));
        verificar("Categoría no válida lanza excepción",
                lanza(() -> new Medicamento("M06", "X", "Dulce", 1, 1.0)));
        verificar("Stock negativo lanza excepción",
                lanza(() -> new Medicamento("M07", "X", "Vitamina", -1, 1.0)));
        verificar("Código duplicado lanza excepción",
                lanza(() -> f.insertar(new Medicamento("M01", "Duplicado", "Vitamina", 1, 1.0))));

        System.out.println("\n=== PRUEBAS DE BORDE (lista vacía) ===");
        FarmaVida vacia = new FarmaVida();
        verificar("a) Agotados en lista vacía = []", vacia.MedicamentosAgotados().toString().equals("[]"));
        verificar("b) Promedio en lista vacía = 0.0", vacia.PromedioPrecioPorCategoria("Vitamina") == 0.0);
        verificar("c) ActualizarStock en lista vacía devuelve false", !vacia.ActualizarStock("M01", 1));
        verificar("d) ListarPreciosInversos en lista vacía está vacía", vacia.ListarPreciosInversos().isEmpty());
        verificar("e) AplicarAumentoGeneral en lista vacía modifica 0", vacia.AplicarAumentoGeneral(10) == 0);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
