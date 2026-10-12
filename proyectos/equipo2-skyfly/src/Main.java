/**
 * Pruebas del proyecto SkyFly (Equipo 2). Incluye casos positivos y negativos
 * (lista vacía, vuelo no encontrado, excepciones).
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

    /** Convierte una lista de vuelos en la lista de sus números, por ejemplo [SK104, SK101]. */
    static String numeros(ListaEnlazada<Vuelo> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (Vuelo v : lista) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(v.getNumeroVuelo());
        }
        return sb.append("]").toString();
    }

    static Vuelo buscar(SkyFly aerolinea, String numero) {
        for (Vuelo v : aerolinea.getVuelos()) {
            if (v.getNumeroVuelo().equals(numero)) {
                return v;
            }
        }
        return null;
    }

    /** Datos de prueba del enunciado (una instancia nueva en cada llamada). */
    static SkyFly datosPrueba() {
        SkyFly aerolinea = new SkyFly();
        aerolinea.registrarVuelo(new Vuelo("SK101", "Madrid", Vuelo.A_TIEMPO, 120));
        aerolinea.registrarVuelo(new Vuelo("SK102", "Cancún", Vuelo.DEMORADO, 210));
        aerolinea.registrarVuelo(new Vuelo("SK103", "Madrid", Vuelo.CANCELADO, 120));
        aerolinea.registrarVuelo(new Vuelo("SK104", "Miami", Vuelo.DEMORADO, 90));
        aerolinea.registrarVuelo(new Vuelo("SK105", "Bogotá", Vuelo.A_TIEMPO, 150));
        return aerolinea;
    }

    public static void main(String[] args) {
        System.out.println("=== SkyFly - Lista Simplemente Enlazada ===");

        System.out.println("\n-- Datos insertados (caso de prueba del enunciado) --");
        SkyFly aerolinea = datosPrueba();
        for (Vuelo v : aerolinea.getVuelos()) {
            System.out.println("  " + v);
        }
        verificar("Se insertaron 5 vuelos", aerolinea.cantidad() == 5);

        System.out.println("\n-- Punto a) VuelosDemorados() --");
        verificar("Positivo: devuelve [SK102, SK104] (estado Demorado)",
                aerolinea.VuelosDemorados().toString().equals("[SK102, SK104]"));
        verificar("Negativo: sin vuelos demorados devuelve lista vacía",
                new SkyFly().VuelosDemorados().estaVacia());

        System.out.println("\n-- Punto b) MostrarOrdenadosPorDuracion() --");
        ListaEnlazada<Vuelo> ordenados = aerolinea.MostrarOrdenadosPorDuracion();
        verificar("Positivo: orden por duración [SK104(90), SK101(120), SK103(120), SK105(150), SK102(210)]",
                numeros(ordenados).equals("[SK104, SK101, SK103, SK105, SK102]"));
        verificar("Positivo: la lista original no se altera (primero sigue siendo SK101)",
                aerolinea.getVuelos().obtener(0).getNumeroVuelo().equals("SK101"));
        verificar("Negativo: lista vacía devuelve lista vacía",
                new SkyFly().MostrarOrdenadosPorDuracion().estaVacia());

        System.out.println("\n-- Punto c) CancelarVuelosADestino(String destino) --");
        SkyFly c1 = datosPrueba();
        verificar("Positivo: cancelar destino Madrid elimina 2 vuelos", c1.CancelarVuelosADestino("Madrid") == 2);
        verificar("Positivo: quedan 3 vuelos y ninguno es a Madrid",
                c1.cantidad() == 3 && c1.getVuelos().buscarPrimero(v -> v.getDestino().equals("Madrid")) == null);
        verificar("Negativo: destino sin vuelos devuelve 0", datosPrueba().CancelarVuelosADestino("Tokio") == 0);
        verificar("Negativo: lista vacía devuelve 0", new SkyFly().CancelarVuelosADestino("Madrid") == 0);
        verificar("Negativo: destino nulo lanza IllegalArgumentException",
                lanza(() -> datosPrueba().CancelarVuelosADestino(null)));

        System.out.println("\n-- Punto d) CambiarEstadoVuelo(String numeroVuelo, String nuevoEstado) --");
        SkyFly d1 = datosPrueba();
        verificar("Positivo: SK102 pasa a 'A tiempo' y devuelve true",
                d1.CambiarEstadoVuelo("SK102", Vuelo.A_TIEMPO) && buscar(d1, "SK102").getEstado().equals(Vuelo.A_TIEMPO));
        verificar("Negativo: vuelo inexistente (SK999) devuelve false",
                !d1.CambiarEstadoVuelo("SK999", Vuelo.CANCELADO));
        verificar("Negativo: lista vacía devuelve false", !new SkyFly().CambiarEstadoVuelo("SK101", Vuelo.DEMORADO));
        verificar("Negativo: estado inválido lanza IllegalArgumentException",
                lanza(() -> datosPrueba().CambiarEstadoVuelo("SK101", "Volando")));

        System.out.println("\n-- Punto e) BuscarVueloMasLargo() --");
        Vuelo masLargo = datosPrueba().BuscarVueloMasLargo();
        verificar("Positivo: el vuelo más largo es SK102 con 210 minutos",
                masLargo != null && masLargo.getNumeroVuelo().equals("SK102") && masLargo.getDuracionMinutos() == 210);
        verificar("Negativo: lista vacía devuelve null", new SkyFly().BuscarVueloMasLargo() == null);

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: estado fuera del catálogo lanza excepción",
                lanza(() -> new Vuelo("SK200", "Lima", "Perdido", 80)));
        verificar("Negativo: duración cero lanza excepción",
                lanza(() -> new Vuelo("SK201", "Lima", Vuelo.A_TIEMPO, 0)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new SkyFly().registrarVuelo(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
