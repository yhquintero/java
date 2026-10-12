/**
 * Casos de prueba del Equipo 2 (SkyFly): positivos, negativos y de borde.
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

    /** Números de vuelo de una lista, separados por coma (ej. "SK101,SK102"). */
    static String numeros(ListaEnlazada<Vuelo> lista) {
        StringBuilder sb = new StringBuilder();
        for (Vuelo v : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(v.getNumeroVuelo());
        }
        return sb.toString();
    }

    /** Vuelos con los datos de la guía. */
    static SkyFly datosGuia() {
        SkyFly c = new SkyFly();
        c.insertar(new Vuelo("SK101", "Madrid", "A tiempo", 120));
        c.insertar(new Vuelo("SK102", "Cancún", "Demorado", 210));
        c.insertar(new Vuelo("SK103", "Madrid", "Cancelado", 120));
        c.insertar(new Vuelo("SK104", "Miami", "Demorado", 90));
        c.insertar(new Vuelo("SK105", "Bogotá", "A tiempo", 150));
        return c;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        SkyFly c = datosGuia();
        verificar("a) Demorados = [SK102, SK104]",
                c.VuelosDemorados().toString().equals("[SK102, SK104]"));
        verificar("b) Orden por duración = SK104,SK101,SK103,SK105,SK102",
                numeros(c.MostrarOrdenadosPorDuracion()).equals("SK104,SK101,SK103,SK105,SK102"));
        verificar("b) La lista original conserva su orden de ingreso (SK101 sigue primero)",
                c.toString().startsWith("[SK101 |") && c.toString().endsWith("SK105 | Bogotá | A tiempo | 150 min]"));
        verificar("c) CancelarVuelosADestino(Madrid) elimina 2 vuelos",
                c.CancelarVuelosADestino("Madrid") == 2);
        verificar("c) SK101 ya no existe tras cancelar Madrid", c.buscarPorNumero("SK101") == null);
        verificar("d) CambiarEstadoVuelo(SK105, Cancelado) devuelve true",
                c.CambiarEstadoVuelo("SK105", "Cancelado"));
        verificar("d) El estado de SK105 quedó en Cancelado",
                c.buscarPorNumero("SK105").getEstado().equals("Cancelado"));
        verificar("e) Vuelo más largo = SK102 (210 min)",
                c.BuscarVueloMasLargo().getNumeroVuelo().equals("SK102"));
        verificar("Borde: destino en minúsculas también se cancela",
                datosGuia().CancelarVuelosADestino("miami") == 1);

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        verificar("c) Destino inexistente elimina 0 vuelos", datosGuia().CancelarVuelosADestino("Tokio") == 0);
        verificar("d) Número de vuelo inexistente devuelve false",
                !datosGuia().CambiarEstadoVuelo("SK999", "Demorado"));
        verificar("d) Estado no válido lanza excepción",
                lanza(() -> datosGuia().CambiarEstadoVuelo("SK101", "Volando")));
        SkyFly sinDemoras = new SkyFly();
        sinDemoras.insertar(new Vuelo("SK200", "Lima", "A tiempo", 300));
        verificar("a) Sin vuelos demorados devuelve []", sinDemoras.VuelosDemorados().toString().equals("[]"));
        verificar("Vuelo con estado no válido lanza excepción",
                lanza(() -> new Vuelo("SK300", "Lima", "Perdido", 10)));
        verificar("Vuelo con duración negativa lanza excepción",
                lanza(() -> new Vuelo("SK301", "Lima", "A tiempo", -5)));
        verificar("Número de vuelo duplicado lanza excepción",
                lanza(() -> datosGuia().insertar(new Vuelo("SK101", "Lima", "A tiempo", 10))));

        System.out.println("\n=== PRUEBAS DE BORDE (lista vacía) ===");
        SkyFly vacio = new SkyFly();
        verificar("a) Demorados en lista vacía = []", vacio.VuelosDemorados().toString().equals("[]"));
        verificar("b) Ordenar lista vacía = []", vacio.MostrarOrdenadosPorDuracion().toString().equals("[]"));
        verificar("c) Cancelar en lista vacía elimina 0", vacio.CancelarVuelosADestino("Madrid") == 0);
        verificar("d) Cambiar estado en lista vacía devuelve false",
                !vacio.CambiarEstadoVuelo("SK101", "Demorado"));
        verificar("e) Vuelo más largo en lista vacía es null", vacio.BuscarVueloMasLargo() == null);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
