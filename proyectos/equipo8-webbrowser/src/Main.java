/**
 * Pruebas del proyecto WebBrowser (Equipo 8). Incluye la eliminación del primer nodo, de un nodo
 * intermedio y del último nodo, comprobando la consistencia de los punteros prev y next,
 * además de casos positivos y negativos.
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

    /** Lista de títulos en orden directo, por ejemplo [Google, Java]. */
    static String titulos(ListaDobleEnlazada<Pagina> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (Pagina p : lista) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(p.getTituloPagina());
        }
        return sb.append("]").toString();
    }

    /** Lista de URLs de la historia en orden inverso (de la cola a la cabeza). */
    static String urlsInverso(ListaDobleEnlazada<Pagina> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (ListaDobleEnlazada.Nodo<Pagina> n = lista.getUltimoNodo(); n != null; n = n.getAnterior()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(n.getDato().getUrl());
        }
        return sb.append("]").toString();
    }

    /** Datos de prueba: 5 visitas en orden cronológico (una instancia nueva en cada llamada). */
    static WebBrowser datosPrueba() {
        WebBrowser navegador = new WebBrowser();
        navegador.registrarVisita(new Pagina("https://www.google.com", "Google", "08:00"));
        navegador.registrarVisita(new Pagina("https://es.wikipedia.org/wiki/Java", "Java - Wikipedia, la enciclopedia libre", "09:30"));
        navegador.registrarVisita(new Pagina("https://docs.oracle.com/javase", "Documentación de Java SE", "10:15"));
        navegador.registrarVisita(new Pagina("https://www.github.com", "GitHub: Where the world builds software", "11:00"));
        navegador.registrarVisita(new Pagina("https://www.youtube.com", "Video tutorial de estructuras de datos", "12:45"));
        return navegador;
    }

    public static void main(String[] args) {
        System.out.println("=== WebBrowser - Lista Doblemente Enlazada ===");

        System.out.println("\n-- Datos insertados (5 visitas, caso de prueba) --");
        WebBrowser wb = datosPrueba();
        for (Pagina p : wb.getHistorial()) {
            System.out.println("  " + p);
        }
        verificar("Se insertaron 5 visitas", wb.cantidad() == 5);
        verificar("Enlaces prev/next consistentes tras la inserción", wb.getHistorial().enlacesConsistentes());

        System.out.println("\n-- Punto a) EliminarPaginaPorUrl(String url) --");
        WebBrowser a = datosPrueba();
        verificar("Primer nodo: EliminarPaginaPorUrl(google) devuelve true",
                a.EliminarPaginaPorUrl("https://www.google.com"));
        verificar("Primer nodo: la nueva cabeza es Wikipedia y su prev es null",
                a.getHistorial().getPrimerNodo().getDato().getTituloPagina().startsWith("Java - Wikipedia")
                        && a.getHistorial().getPrimerNodo().getAnterior() == null);
        verificar("Primer nodo: enlaces consistentes", a.getHistorial().enlacesConsistentes());

        verificar("Nodo intermedio: EliminarPaginaPorUrl(docs.oracle) devuelve true",
                a.EliminarPaginaPorUrl("https://docs.oracle.com/javase"));
        ListaDobleEnlazada.Nodo<Pagina> wiki = a.getHistorial().getPrimerNodo();
        verificar("Nodo intermedio: Wikipedia.next = GitHub y GitHub.prev = Wikipedia",
                wiki.getSiguiente().getDato().getUrl().equals("https://www.github.com")
                        && wiki.getSiguiente().getAnterior() == wiki);
        verificar("Nodo intermedio: enlaces consistentes", a.getHistorial().enlacesConsistentes());

        verificar("Último nodo: EliminarPaginaPorUrl(youtube) devuelve true",
                a.EliminarPaginaPorUrl("https://www.youtube.com"));
        verificar("Último nodo: la nueva cola es GitHub y su next es null",
                a.getHistorial().getUltimoNodo().getDato().getUrl().equals("https://www.github.com")
                        && a.getHistorial().getUltimoNodo().getSiguiente() == null);
        verificar("Último nodo: enlaces consistentes y quedan 2 visitas",
                a.getHistorial().enlacesConsistentes() && a.cantidad() == 2);
        verificar("Negativo: URL inexistente devuelve false",
                !a.EliminarPaginaPorUrl("https://no-existe.com"));
        verificar("Negativo: historial vacío devuelve false",
                !new WebBrowser().EliminarPaginaPorUrl("https://www.google.com"));

        System.out.println("\n-- Punto b) MostrarHistorialReciente() --");
        ListaDobleEnlazada<Pagina> reciente = datosPrueba().MostrarHistorialReciente();
        verificar("Positivo: de la más reciente a la más antigua (YouTube, GitHub, Docs, Java, Google)",
                titulos(reciente).equals("[Video tutorial de estructuras de datos, GitHub: Where the world builds software, "
                        + "Documentación de Java SE, Java - Wikipedia, la enciclopedia libre, Google]"));
        verificar("Negativo: historial vacío devuelve lista vacía", new WebBrowser().MostrarHistorialReciente().estaVacia());

        System.out.println("\n-- Punto c) BuscarPorPalabraClave(String palabra) --");
        WebBrowser c1 = datosPrueba();
        verificar("Positivo: 'java' encuentra Wikipedia y Docs (sin distinguir mayúsculas)",
                c1.BuscarPorPalabraClave("java").toString()
                        .equals("[https://es.wikipedia.org/wiki/Java, https://docs.oracle.com/javase]"));
        verificar("Positivo: 'JAVA' en mayúsculas devuelve el mismo resultado",
                c1.BuscarPorPalabraClave("JAVA").size() == 2);
        verificar("Negativo: palabra sin coincidencias devuelve lista vacía",
                c1.BuscarPorPalabraClave("python").estaVacia());
        verificar("Negativo: historial vacío devuelve lista vacía",
                new WebBrowser().BuscarPorPalabraClave("java").estaVacia());
        verificar("Negativo: palabra vacía lanza IllegalArgumentException",
                lanza(() -> datosPrueba().BuscarPorPalabraClave("  ")));

        System.out.println("\n-- Punto d) VaciarHistorialAntiguo(String horaLimite) --");
        WebBrowser d1 = datosPrueba();
        verificar("Positivo: límite 10:15 elimina 2 visitas (08:00 y 09:30)",
                d1.VaciarHistorialAntiguo("10:15") == 2);
        verificar("Positivo: la visita a las 10:15 (igual al límite) se conserva y queda como cabeza",
                d1.cantidad() == 3 && d1.getHistorial().getPrimerNodo().getDato().getHoraVisita().equals("10:15"));
        verificar("Punteros: enlaces consistentes tras vaciar", d1.getHistorial().enlacesConsistentes());
        verificar("Negativo: límite 00:00 no elimina nada", datosPrueba().VaciarHistorialAntiguo("00:00") == 0);
        verificar("Negativo: historial vacío devuelve 0", new WebBrowser().VaciarHistorialAntiguo("12:00") == 0);
        verificar("Negativo: hora con formato inválido lanza IllegalArgumentException",
                lanza(() -> datosPrueba().VaciarHistorialAntiguo("25:00")));

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: hora de visita mal formada lanza excepción",
                lanza(() -> new Pagina("https://x.com", "X", "8:00")));
        verificar("Negativo: URL vacía lanza excepción",
                lanza(() -> new Pagina(" ", "X", "08:00")));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new WebBrowser().registrarVisita(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
