/**
 * Casos de prueba del Equipo 8 (WebBrowser): positivos, negativos y de borde.
 * Se verifican los punteros prev y next al eliminar el primer nodo, uno intermedio y el último.
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

    /** URLs de una lista de páginas, separadas por coma (ej. "A,B,C"). */
    static String urls(ListaDobleEnlazada<Pagina> lista) {
        StringBuilder sb = new StringBuilder();
        for (Pagina p : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(p.getUrl());
        }
        return sb.toString();
    }

    /** Textos de una lista de cadenas, separados por coma. */
    static String unir(ListaDobleEnlazada<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String s : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }

    /** Historial con cinco visitas (las URL son A..E para leer mejor los resultados). */
    static WebBrowser cincoVisitas() {
        WebBrowser h = new WebBrowser();
        h.registrarVisita(new Pagina("A", "Inicio de Java", "08:00"));
        h.registrarVisita(new Pagina("B", "Noticias de Python", "09:00"));
        h.registrarVisita(new Pagina("C", "Java avanzado", "10:00"));
        h.registrarVisita(new Pagina("D", "Tutorial de Git", "11:00"));
        h.registrarVisita(new Pagina("E", "Java en la nube", "12:00"));
        return h;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        WebBrowser h = cincoVisitas();
        verificar("Orden cronológico inicial = A,B,C,D,E", urls(h.getVisitas()).equals("A,B,C,D,E"));
        verificar("b) Historial reciente = E,D,C,B,A", urls(h.MostrarHistorialReciente()).equals("E,D,C,B,A"));
        verificar("Punteros coherentes al inicio", h.getVisitas().esConsistente());

        verificar("a) Eliminar el PRIMER nodo (A) elimina 1", h.EliminarPaginaPorUrl("A") == 1);
        verificar("a) Tras eliminar el primero = B,C,D,E", urls(h.getVisitas()).equals("B,C,D,E"));
        verificar("a) Punteros coherentes tras eliminar el primero", h.getVisitas().esConsistente());
        verificar("a) Recorrido inverso tras eliminar el primero = E,D,C,B",
                urls(h.MostrarHistorialReciente()).equals("E,D,C,B"));

        verificar("a) Eliminar un nodo INTERMEDIO (C) elimina 1", h.EliminarPaginaPorUrl("C") == 1);
        verificar("a) Tras eliminar el intermedio = B,D,E", urls(h.getVisitas()).equals("B,D,E"));
        verificar("a) Punteros coherentes tras eliminar el intermedio", h.getVisitas().esConsistente());
        verificar("a) Recorrido inverso tras eliminar el intermedio = E,D,B",
                urls(h.MostrarHistorialReciente()).equals("E,D,B"));

        verificar("a) Eliminar el ÚLTIMO nodo (E) elimina 1", h.EliminarPaginaPorUrl("E") == 1);
        verificar("a) Tras eliminar el último = B,D", urls(h.getVisitas()).equals("B,D"));
        verificar("a) Punteros coherentes tras eliminar el último", h.getVisitas().esConsistente());

        WebBrowser b = cincoVisitas();
        verificar("c) Buscar 'java' (sin distinguir mayúsculas) = A,C,E",
                urls(b.getVisitas()).equals("A,B,C,D,E")
                        && unir(b.BuscarPorPalabraClave("java")).equals("A,C,E"));
        verificar("c) Buscar 'PYTHON' encuentra la página B", unir(b.BuscarPorPalabraClave("PYTHON")).equals("B"));

        WebBrowser d = cincoVisitas();
        verificar("d) Vaciar visitas antes de 10:00 elimina 2 (A y B)", d.VaciarHistorialAntiguo("10:00") == 2);
        verificar("d) Quedan C,D,E (la visita de las 10:00 se conserva)", urls(d.getVisitas()).equals("C,D,E"));
        verificar("d) Punteros coherentes tras vaciar", d.getVisitas().esConsistente());

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        WebBrowser n = cincoVisitas();
        verificar("a) Eliminar una URL inexistente elimina 0", n.EliminarPaginaPorUrl("Z") == 0);
        verificar("c) Palabra clave sin coincidencias no devuelve URL alguna", unir(n.BuscarPorPalabraClave("COBOL")).equals(""));
        verificar("c) Palabra clave vacía lanza excepción", lanza(() -> n.BuscarPorPalabraClave("  ")));
        verificar("d) Hora límite con formato inválido lanza excepción",
                lanza(() -> n.VaciarHistorialAntiguo("9am")));
        verificar("d) Hora límite fuera de rango lanza excepción",
                lanza(() -> n.VaciarHistorialAntiguo("25:00")));
        verificar("d) Hora límite 00:00 no elimina ninguna visita", n.VaciarHistorialAntiguo("00:00") == 0);
        verificar("Página con hora inválida lanza excepción",
                lanza(() -> new Pagina("F", "Test", "25:00")));

        WebBrowser todas = cincoVisitas();
        verificar("a) Eliminar todas las visitas por URL deja el historial vacío",
                todas.EliminarPaginaPorUrl("A") + todas.EliminarPaginaPorUrl("B")
                        + todas.EliminarPaginaPorUrl("C") + todas.EliminarPaginaPorUrl("D")
                        + todas.EliminarPaginaPorUrl("E") == 5
                        && todas.getVisitas().isEmpty() && todas.getVisitas().esConsistente());

        System.out.println("\n=== PRUEBAS DE BORDE (historial vacío) ===");
        WebBrowser vacio = new WebBrowser();
        verificar("b) Historial reciente vacío no tiene páginas", urls(vacio.MostrarHistorialReciente()).equals(""));
        verificar("a) Eliminar en historial vacío elimina 0", vacio.EliminarPaginaPorUrl("A") == 0);
        verificar("c) Buscar en historial vacío no encuentra nada",
                vacio.BuscarPorPalabraClave("java").isEmpty());
        verificar("d) Vaciar historial vacío elimina 0", vacio.VaciarHistorialAntiguo("12:00") == 0);
        verificar("Historial vacío tiene punteros coherentes", vacio.getVisitas().esConsistente());

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
