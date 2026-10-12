/**
 * Pruebas del proyecto SoundFlow (Equipo 7). Incluye casos positivos y negativos
 * y verificación de los punteros prev (anterior) y next (siguiente).
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

    /** Lista de ids en orden directo, por ejemplo [C01, C02]. */
    static String idsDirecto(ListaDobleEnlazada<Cancion> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (Cancion c : lista) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(c.getId());
        }
        return sb.append("]").toString();
    }

    /** Lista de ids en orden inverso (de la cola a la cabeza), recorriendo los enlaces anterior. */
    static String idsInverso(ListaDobleEnlazada<Cancion> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (ListaDobleEnlazada.Nodo<Cancion> n = lista.getUltimoNodo(); n != null; n = n.getAnterior()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(n.getDato().getId());
        }
        return sb.append("]").toString();
    }

    /** Datos de prueba: 4 canciones (una instancia nueva en cada llamada). */
    static SoundFlow datosPrueba() {
        SoundFlow reproductor = new SoundFlow();
        reproductor.agregarCancion(new Cancion("C01", "Bohemian Rhapsody", "Queen", 354));
        reproductor.agregarCancion(new Cancion("C02", "Imagine", "John Lennon", 183));
        reproductor.agregarCancion(new Cancion("C03", "Hotel California", "Eagles", 391));
        reproductor.agregarCancion(new Cancion("C04", "Smells Like Teen Spirit", "Nirvana", 301));
        return reproductor;
    }

    public static void main(String[] args) {
        System.out.println("=== SoundFlow - Lista Doblemente Enlazada ===");

        System.out.println("\n-- Datos insertados (4 canciones, caso de prueba del enunciado) --");
        SoundFlow sf = datosPrueba();
        for (Cancion c : sf.getPlaylist()) {
            System.out.println("  " + c);
        }
        verificar("Se insertaron 4 canciones", sf.cantidad() == 4);
        verificar("Enlaces prev/next consistentes tras la inserción", sf.getPlaylist().enlacesConsistentes());

        System.out.println("\n-- Punto a) NavegarAdelanteYAtras() --");
        verificar("Positivo: orden directo = [C01, C02, C03, C04]",
                idsDirecto(sf.getPlaylist()).equals("[C01, C02, C03, C04]"));
        verificar("Positivo: orden inverso = [C04, C03, C02, C01] (recorrido con prev)",
                idsInverso(sf.getPlaylist()).equals("[C04, C03, C02, C01]"));
        verificar("Positivo: el texto contiene ambos recorridos",
                sf.NavegarAdelanteYAtras().startsWith("Orden directo: [C01 ")
                        && sf.NavegarAdelanteYAtras().contains("\nOrden inverso: [C04 "));
        verificar("Negativo: playlist vacía devuelve listas vacías",
                new SoundFlow().NavegarAdelanteYAtras().equals("Orden directo: []\nOrden inverso: []"));

        System.out.println("\n-- Punto b) MoverCancionAlInicio(String id): mover la última (C04) al inicio --");
        SoundFlow b1 = datosPrueba();
        verificar("Positivo: MoverCancionAlInicio(C04) devuelve true", b1.MoverCancionAlInicio("C04"));
        verificar("Positivo: orden directo ahora [C04, C01, C02, C03]",
                idsDirecto(b1.getPlaylist()).equals("[C04, C01, C02, C03]"));
        verificar("Positivo: orden inverso ahora [C03, C02, C01, C04]",
                idsInverso(b1.getPlaylist()).equals("[C03, C02, C01, C04]"));
        ListaDobleEnlazada.Nodo<Cancion> primero = b1.getPlaylist().getPrimerNodo();
        ListaDobleEnlazada.Nodo<Cancion> ultimo = b1.getPlaylist().getUltimoNodo();
        verificar("Punteros: primer nodo (C04) tiene prev = null y next = C01",
                primero.getAnterior() == null && primero.getSiguiente().getDato().getId().equals("C01"));
        verificar("Punteros: C01 tiene prev = C04 (el nuevo primero)",
                primero.getSiguiente().getAnterior() == primero);
        verificar("Punteros: último nodo (C03) tiene next = null y prev = C02",
                ultimo.getSiguiente() == null && ultimo.getAnterior().getDato().getId().equals("C02"));
        verificar("Punteros: enlaces consistentes en ambos sentidos", b1.getPlaylist().enlacesConsistentes());
        verificar("Positivo: mover una canción que ya está al inicio no cambia el orden",
                b1.MoverCancionAlInicio("C04") && idsDirecto(b1.getPlaylist()).equals("[C04, C01, C02, C03]"));
        verificar("Positivo: mover una canción intermedia (C02) al inicio",
                b1.MoverCancionAlInicio("C02") && idsDirecto(b1.getPlaylist()).equals("[C02, C04, C01, C03]")
                        && b1.getPlaylist().enlacesConsistentes());
        verificar("Negativo: id inexistente (C99) devuelve false", !datosPrueba().MoverCancionAlInicio("C99"));
        verificar("Negativo: playlist vacía devuelve false", !new SoundFlow().MoverCancionAlInicio("C01"));

        System.out.println("\n-- Punto c) DuracionTotalPlaylist() --");
        verificar("Positivo: 354 + 183 + 391 + 301 = 1229 s = '20 min 29 s'",
                datosPrueba().DuracionTotalPlaylist().equals("20 min 29 s"));
        SoundFlow corta = new SoundFlow();
        corta.agregarCancion(new Cancion("C07", "Intro", "Demo", 59));
        verificar("Positivo: 59 s = '0 min 59 s'", corta.DuracionTotalPlaylist().equals("0 min 59 s"));
        verificar("Negativo: playlist vacía devuelve '0 min 0 s'",
                new SoundFlow().DuracionTotalPlaylist().equals("0 min 0 s"));

        System.out.println("\n-- Punto d) EliminarCancionPorArtista(String artista) --");
        SoundFlow d1 = datosPrueba();
        d1.agregarCancion(new Cancion("C05", "Another One Bites the Dust", "Queen", 216));
        verificar("Positivo: eliminar canciones de Queen devuelve 2", d1.EliminarCancionPorArtista("Queen") == 2);
        verificar("Punteros: tras eliminar quedan [C02, C03, C04] en orden directo y consistentes",
                idsDirecto(d1.getPlaylist()).equals("[C02, C03, C04]") && d1.getPlaylist().enlacesConsistentes());
        verificar("Positivo: eliminar la cola (Nirvana) mantiene la consistencia",
                datosPrueba().EliminarCancionPorArtista("Nirvana") == 1
                        && consistenteTrasEliminar("Nirvana"));
        verificar("Negativo: artista inexistente devuelve 0", datosPrueba().EliminarCancionPorArtista("Shakira") == 0);
        verificar("Negativo: playlist vacía devuelve 0", new SoundFlow().EliminarCancionPorArtista("Queen") == 0);
        verificar("Negativo: artista nulo lanza IllegalArgumentException",
                lanza(() -> datosPrueba().EliminarCancionPorArtista(null)));

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: duración cero lanza excepción",
                lanza(() -> new Cancion("C08", "Silencio", "Nadie", 0)));
        verificar("Negativo: agregar null lanza excepción",
                lanza(() -> new SoundFlow().agregarCancion(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }

    /** Elimina las canciones de un artista y comprueba que los enlaces siguen siendo consistentes. */
    static boolean consistenteTrasEliminar(String artista) {
        SoundFlow sf = datosPrueba();
        sf.EliminarCancionPorArtista(artista);
        return sf.getPlaylist().enlacesConsistentes()
                && idsInverso(sf.getPlaylist()).equals("[C03, C02, C01]");
    }
}
