/**
 * Casos de prueba del Equipo 3 (FitGym): positivos, negativos y de borde.
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

    /** IDs de una lista de socios, separados por coma (ej. "S05,S02"). */
    static String ids(ListaEnlazada<Socio> lista) {
        StringBuilder sb = new StringBuilder();
        for (Socio s : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(s.getIdSocio());
        }
        return sb.toString();
    }

    /** Socios con los datos de la guía. */
    static FitGym datosGuia() {
        FitGym r = new FitGym();
        r.insertar(new Socio("S01", "Carlos", "Básica", 5));
        r.insertar(new Socio("S02", "Ana", "Premium", 30));
        r.insertar(new Socio("S03", "Pedro", "VIP", 2));
        r.insertar(new Socio("S04", "María", "Básica", 15));
        r.insertar(new Socio("S05", "Lucía", "Premium", 60));
        return r;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        FitGym r = datosGuia();
        verificar("a) Membresías por vencer (<=5 días) = [S01, S03]",
                r.MembresiasPorVencer(5).toString().equals("[S01, S03]"));
        verificar("a) Límite que incluye a todos = [S01, S02, S03, S04, S05]",
                r.MembresiasPorVencer(60).toString().equals("[S01, S02, S03, S04, S05]"));
        verificar("a) Borde: límite igual a 2 incluye al socio con 2 días (S03)",
                r.MembresiasPorVencer(2).toString().equals("[S03]"));
        verificar("b) Orden de mayor a menor días = S05,S02,S04,S01,S03",
                ids(r.MostrarOrdenadosPorDias()).equals("S05,S02,S04,S01,S03"));
        verificar("c) Conteo por membresía = Básica: 2 | Premium: 2 | VIP: 1",
                r.CantidadSociosPorMembresia().equals("Básica: 2 | Premium: 2 | VIP: 1"));
        verificar("d) RenovarMembresia(S03, 30) devuelve true", r.RenovarMembresia("S03", 30));
        verificar("d) S03 queda con 32 días", r.buscarPorId("S03").getDiasRestantes() == 32);
        verificar("e) Sin socios con 0 días, no se elimina ninguno", r.EliminarSociosSinDias() == 0);
        FitGym conCero = datosGuia();
        conCero.insertar(new Socio("S06", "Jorge", "VIP", 0));
        verificar("e) Elimina al socio con 0 días", conCero.EliminarSociosSinDias() == 1);
        verificar("e) Tras eliminar quedan 5 socios", ids(conCero.MostrarOrdenadosPorDias()).split(",").length == 5);

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        verificar("a) Límite menor que todos los días devuelve []",
                r.MembresiasPorVencer(1).toString().equals("[]"));
        verificar("d) RenovarMembresia con ID inexistente devuelve false", !r.RenovarMembresia("S99", 10));
        verificar("d) Días adicionales negativos lanzan excepción",
                lanza(() -> r.RenovarMembresia("S01", -1)));
        verificar("Tipo de membresía no válido lanza excepción",
                lanza(() -> new Socio("S07", "Luis", "Oro", 10)));
        verificar("Días restantes negativos lanzan excepción",
                lanza(() -> new Socio("S08", "Luis", "Básica", -4)));
        verificar("ID de socio duplicado lanza excepción",
                lanza(() -> datosGuia().insertar(new Socio("S01", "Otro", "Básica", 1))));

        System.out.println("\n=== PRUEBAS DE BORDE (registro vacío) ===");
        FitGym vacio = new FitGym();
        verificar("a) Por vencer en registro vacío = []", vacio.MembresiasPorVencer(10).toString().equals("[]"));
        verificar("b) Ordenar registro vacío = []", vacio.MostrarOrdenadosPorDias().toString().equals("[]"));
        verificar("c) Conteo en registro vacío = Básica: 0 | Premium: 0 | VIP: 0",
                vacio.CantidadSociosPorMembresia().equals("Básica: 0 | Premium: 0 | VIP: 0"));
        verificar("d) Renovar en registro vacío devuelve false", !vacio.RenovarMembresia("S01", 5));
        verificar("e) Eliminar en registro vacío elimina 0", vacio.EliminarSociosSinDias() == 0);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
