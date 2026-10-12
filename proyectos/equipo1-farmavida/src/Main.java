/**
 * Demostración de los puntos a) a e) del Equipo 1 (FarmaVida)
 * con los datos de prueba sugeridos en la guía.
 */
public class Main {

    public static void main(String[] args) {
        FarmaVida farmacia = new FarmaVida();

        // Datos de prueba de la guía: (código, nombre, categoría, stock, precio)
        farmacia.insertar(new Medicamento("M01", "Paracetamol", "Analgésico", 15, 2.50));
        farmacia.insertar(new Medicamento("M02", "Amoxicilina", "Antibiótico", 3, 12.00));
        farmacia.insertar(new Medicamento("M03", "Ibuprofeno", "Antiinflamatorio", 2, 4.50));
        farmacia.insertar(new Medicamento("M04", "Vitamina C", "Vitamina", 20, 5.00));
        farmacia.insertar(new Medicamento("M05", "Azitromicina", "Antibiótico", 5, 15.00));

        System.out.println("Inventario inicial: " + farmacia);

        // Punto a)
        System.out.println("a) MedicamentosAgotados(): " + farmacia.MedicamentosAgotados());

        // Punto b)
        System.out.println("b) PromedioPrecioPorCategoria(\"Antibiótico\"): "
                + farmacia.PromedioPrecioPorCategoria("Antibiótico"));

        // Punto c)
        System.out.println("c) ActualizarStock(\"M03\", 10): " + farmacia.ActualizarStock("M03", 10));
        System.out.println("   ActualizarStock(\"M99\", 10): " + farmacia.ActualizarStock("M99", 10));

        // Punto d)
        System.out.println("d) ListarPreciosInversos(): " + farmacia.ListarPreciosInversos());

        // Punto e)
        System.out.println("e) AplicarAumentoGeneral(10): " + farmacia.AplicarAumentoGeneral(10)
                + " medicamentos modificados");

        System.out.println("Inventario final: " + farmacia);
    }
}
