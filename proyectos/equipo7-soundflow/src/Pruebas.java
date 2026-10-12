/**
 * Casos de prueba del Equipo 7 (SoundFlow): positivos, negativos y de borde.
 * Incluyen la verificación de los punteros prev y next.
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

    /** IDs de las canciones en el orden de la lista, separados por coma (ej. "C01,C02"). */
    static String ids(ListaDobleEnlazada<Cancion> lista) {
        StringBuilder sb = new StringBuilder();
        for (Cancion c : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(c.getId());
        }
        return sb.toString();
    }

    /** SoundFlow con las 4 canciones de ejemplo (la guía pide insertar 4 canciones). */
    static SoundFlow datosGuia() {
        SoundFlow p = new SoundFlow();
        p.insertar(new Cancion("C01", "Amanecer", "Banda Norte", 210));
        p.insertar(new Cancion("C02", "Río", "Los Faros", 185));
        p.insertar(new Cancion("C03", "Noche Azul", "Banda Norte", 245));
        p.insertar(new Cancion("C04", "Último Tren", "Luna Roja", 190));
        return p;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        SoundFlow p = datosGuia();
        verificar("a) Recorrido directo = C01,C02,C03,C04", ids(p.getCanciones()).equals("C01,C02,C03,C04"));
        verificar("a) Recorrido inverso = C04,C03,C02,C01",
                ids(p.getCanciones().invertidaCopia()).equals("C04,C03,C02,C01"));
        verificar("a) El texto de navegación incluye ambos sentidos",
                p.NavegarAdelanteYAtras().contains("Orden directo: [C01 ")
                        && p.NavegarAdelanteYAtras().contains("Orden inverso: [C04 "));
        verificar("a) Punteros coherentes al inicio", p.esConsistente());

        verificar("b) MoverCancionAlInicio(C04) devuelve true", p.MoverCancionAlInicio("C04"));
        verificar("b) Orden tras mover la última = C04,C01,C02,C03",
                ids(p.getCanciones()).equals("C04,C01,C02,C03"));
        verificar("b) Recorrido inverso tras mover = C03,C02,C01,C04",
                ids(p.getCanciones().invertidaCopia()).equals("C03,C02,C01,C04"));
        verificar("b) Punteros prev y next coherentes tras mover la última", p.esConsistente());
        p.MoverCancionAlInicio("C02");
        verificar("b) Mover una canción intermedia (C02) = C02,C04,C01,C03",
                ids(p.getCanciones()).equals("C02,C04,C01,C03") && p.esConsistente());
        p.MoverCancionAlInicio("C03");
        verificar("b) Mover la cola (C03) = C03,C02,C04,C01 y coherente",
                ids(p.getCanciones()).equals("C03,C02,C04,C01") && p.esConsistente());
        verificar("b) Mover la canción que ya está al inicio no cambia nada",
                p.MoverCancionAlInicio("C03") && ids(p.getCanciones()).equals("C03,C02,C04,C01"));

        verificar("c) Duración total = 13 min 50 s (830 s)",
                datosGuia().DuracionTotalPlaylist().equals("13 min 50 s"));

        SoundFlow q = datosGuia();
        verificar("d) Eliminar canciones de Banda Norte elimina 2",
                q.EliminarCancionPorArtista("Banda Norte") == 2);
        verificar("d) Quedan C02 y C04 en orden", ids(q.getCanciones()).equals("C02,C04"));
        verificar("d) Punteros coherentes tras eliminar en medio y al final", q.esConsistente());
        verificar("d) Inverso tras eliminar = C04,C02", ids(q.getCanciones().invertidaCopia()).equals("C04,C02"));

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        SoundFlow n = datosGuia();
        verificar("b) Mover una canción inexistente devuelve false", !n.MoverCancionAlInicio("C99"));
        verificar("b) El orden no cambia al mover una inexistente",
                ids(n.getCanciones()).equals("C01,C02,C03,C04"));
        verificar("d) Eliminar de un artista inexistente elimina 0",
                n.EliminarCancionPorArtista("Desconocido") == 0);
        verificar("Duración negativa lanza excepción", lanza(() -> new Cancion("C05", "X", "Y", -10)));
        verificar("ID de canción duplicado lanza excepción",
                lanza(() -> n.insertar(new Cancion("C01", "Otra", "Otro", 100))));

        SoundFlow vaciada = datosGuia();
        vaciada.EliminarCancionPorArtista("Banda Norte");
        vaciada.EliminarCancionPorArtista("Los Faros");
        vaciada.EliminarCancionPorArtista("Luna Roja");
        verificar("d) Tras eliminar todas, la playlist queda vacía",
                vaciada.getCanciones().isEmpty() && vaciada.esConsistente());

        System.out.println("\n=== PRUEBAS DE BORDE (playlist vacía) ===");
        SoundFlow vacia = new SoundFlow();
        verificar("a) Navegar una playlist vacía muestra []",
                vacia.NavegarAdelanteYAtras().equals("Orden directo: []\nOrden inverso: []"));
        verificar("b) Mover en playlist vacía devuelve false", !vacia.MoverCancionAlInicio("C01"));
        verificar("c) Duración de playlist vacía = 0 min 0 s", vacia.DuracionTotalPlaylist().equals("0 min 0 s"));
        verificar("d) Eliminar en playlist vacía elimina 0", vacia.EliminarCancionPorArtista("Banda Norte") == 0);
        verificar("SoundFlow vacía tiene punteros coherentes", vacia.esConsistente());

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }
}
