/**
 * Pruebas del proyecto HospitalEmergency (Equipo 9). Insertar pacientes con prioridades 2, 5, 1, 4
 * y verificar el orden de atención, los tiempos de espera y el reordenamiento.
 * Incluye casos positivos y negativos (sala vacía, paciente nulo, datos fuera de rango).
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

    /** Lista de nombres en orden directo, por ejemplo [Luis, Pedro]. */
    static String nombres(ListaDobleEnlazada<Paciente> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (Paciente p : lista) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(p.getNombre());
        }
        return sb.append("]").toString();
    }

    /** Lista de nombres en orden inverso (de la cola a la cabeza), recorriendo los enlaces anterior. */
    static String nombresInverso(ListaDobleEnlazada<Paciente> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (ListaDobleEnlazada.Nodo<Paciente> n = lista.getUltimoNodo(); n != null; n = n.getAnterior()) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(n.getDato().getNombre());
        }
        return sb.append("]").toString();
    }

    /** Lista de nombres de los pacientes en el orden en que serían atendidos (sin modificar la sala). */
    static String ordenDeAtencion(HospitalEmergency sala) {
        return nombres(sala.getSalaDeEspera());
    }

    /**
     * Datos de prueba: pacientes con prioridades 2, 5, 1 y 4 (en ese orden de llegada).
     * Ana (2, 30 min), Luis (5, 10 min), Marta (1, 45 min), Pedro (4, 20 min).
     */
    static HospitalEmergency datosPrueba() {
        HospitalEmergency sala = new HospitalEmergency();
        sala.InsertarPorPrioridad(new Paciente("H001", "Ana", 2, 30));
        sala.InsertarPorPrioridad(new Paciente("H002", "Luis", 5, 10));
        sala.InsertarPorPrioridad(new Paciente("H003", "Marta", 1, 45));
        sala.InsertarPorPrioridad(new Paciente("H004", "Pedro", 4, 20));
        return sala;
    }

    public static void main(String[] args) {
        System.out.println("=== HospitalEmergency - Lista Doblemente Enlazada ===");

        System.out.println("\n-- Pacientes insertados con prioridades 2, 5, 1, 4 --");
        HospitalEmergency sala = datosPrueba();
        for (Paciente p : sala.getSalaDeEspera()) {
            System.out.println("  " + p);
        }
        verificar("Se insertaron 4 pacientes", sala.cantidad() == 4);
        verificar("Orden de atención por prioridad [Luis(5), Pedro(4), Ana(2), Marta(1)]",
                ordenDeAtencion(sala).equals("[Luis, Pedro, Ana, Marta]"));
        verificar("Enlaces prev/next consistentes tras las inserciones", sala.getSalaDeEspera().enlacesConsistentes());

        System.out.println("\n-- Punto a) AtenderSiguientePaciente() --");
        HospitalEmergency a1 = datosPrueba();
        StringBuilder atendidos = new StringBuilder("[");
        Paciente p;
        while ((p = a1.AtenderSiguientePaciente()) != null) {
            if (atendidos.length() > 1) {
                atendidos.append(", ");
            }
            atendidos.append(p.getNombre());
        }
        atendidos.append("]");
        verificar("Positivo: el orden de atención es [Luis, Pedro, Ana, Marta]",
                atendidos.toString().equals("[Luis, Pedro, Ana, Marta]"));
        verificar("Positivo: al terminar la sala queda vacía", a1.cantidad() == 0);
        verificar("Negativo: sala vacía devuelve null", new HospitalEmergency().AtenderSiguientePaciente() == null);
        HospitalEmergency a2 = datosPrueba();
        Paciente primero = a2.AtenderSiguientePaciente();
        verificar("Positivo: el primer paciente atendido es Luis (prioridad 5) y sale de la lista",
                primero != null && primero.getNombre().equals("Luis") && a2.cantidad() == 3
                        && a2.getSalaDeEspera().getPrimerNodo().getAnterior() == null);

        System.out.println("\n-- Punto b) InsertarPorPrioridad(Paciente nuevo) --");
        HospitalEmergency b1 = datosPrueba();
        b1.InsertarPorPrioridad(new Paciente("H005", "Sofía", 4, 50));
        verificar("Positivo: Sofía (prioridad 4) entra detrás de Pedro (igual prioridad, orden de llegada)",
                ordenDeAtencion(b1).equals("[Luis, Pedro, Sofía, Ana, Marta]"));
        b1.InsertarPorPrioridad(new Paciente("H006", "Carla", 5, 5));
        verificar("Positivo: Carla (prioridad 5) queda detrás de Luis (misma prioridad, FIFO) y delante de Pedro",
                ordenDeAtencion(b1).equals("[Luis, Carla, Pedro, Sofía, Ana, Marta]"));
        verificar("Punteros: enlaces consistentes tras insertar al inicio y en medio",
                b1.getSalaDeEspera().enlacesConsistentes());
        HospitalEmergency b2 = new HospitalEmergency();
        b2.InsertarPorPrioridad(new Paciente("H010", "Beto", 1, 5));
        b2.InsertarPorPrioridad(new Paciente("H011", "Tere", 3, 5));
        verificar("Positivo: inserción al final cuando todos tienen mayor prioridad [Tere, Beto]",
                ordenDeAtencion(b2).equals("[Tere, Beto]"));
        verificar("Negativo: insertar un paciente nulo lanza IllegalArgumentException",
                lanza(() -> datosPrueba().InsertarPorPrioridad(null)));
        verificar("Negativo: nivel de prioridad 0 lanza IllegalArgumentException",
                lanza(() -> new Paciente("H012", "Rosa", 0, 5)));
        verificar("Negativo: nivel de prioridad 6 lanza IllegalArgumentException",
                lanza(() -> new Paciente("H013", "Rosa", 6, 5)));

        System.out.println("\n-- Punto c) PacientesEnEsperaCritica(int minutosLimite) --");
        HospitalEmergency c1 = datosPrueba();
        c1.InsertarPorPrioridad(new Paciente("H005", "Sofía", 4, 50));
        verificar("Positivo: límite 25 min devuelve [Sofía, Ana, Marta] (en orden de la sala)",
                c1.PacientesEnEsperaCritica(25).toString().equals("[Sofía, Ana, Marta]"));
        verificar("Positivo: límite igual al máximo (50) no devuelve pacientes (estrictamente mayor)",
                c1.PacientesEnEsperaCritica(50).estaVacia());
        verificar("Negativo: sala vacía devuelve lista vacía",
                new HospitalEmergency().PacientesEnEsperaCritica(10).estaVacia());

        System.out.println("\n-- Punto d) ReordenarPorTiempoEspera() --");
        HospitalEmergency d1 = datosPrueba();
        d1.InsertarPorPrioridad(new Paciente("H005", "Sofía", 4, 50));
        d1.ReordenarPorTiempoEspera();
        verificar("Positivo: orden por tiempo de espera de mayor a menor [Sofía(50), Marta(45), Ana(30), Pedro(20), Luis(10)]",
                nombres(d1.getSalaDeEspera()).equals("[Sofía, Marta, Ana, Pedro, Luis]"));
        verificar("Punteros: primer nodo sin anterior y último sin siguiente tras reordenar",
                d1.getSalaDeEspera().getPrimerNodo().getAnterior() == null
                        && d1.getSalaDeEspera().getUltimoNodo().getSiguiente() == null);
        verificar("Punteros: enlaces consistentes en ambos sentidos tras reordenar",
                d1.getSalaDeEspera().enlacesConsistentes());
        verificar("Positivo: el recorrido inverso (por prev) refleja el reordenamiento [Luis, Pedro, Ana, Marta, Sofía]",
                nombresInverso(d1.getSalaDeEspera()).equals("[Luis, Pedro, Ana, Marta, Sofía]"));
        HospitalEmergency d2 = new HospitalEmergency();
        d2.InsertarPorPrioridad(new Paciente("H020", "Ana", 3, 30));
        d2.InsertarPorPrioridad(new Paciente("H021", "Carlos", 3, 30));
        d2.ReordenarPorTiempoEspera();
        verificar("Positivo: ordenación estable (a igual espera se conserva el orden previo [Ana, Carlos])",
                ordenDeAtencion(d2).equals("[Ana, Carlos]"));
        HospitalEmergency d3 = new HospitalEmergency();
        d3.ReordenarPorTiempoEspera();
        verificar("Negativo: reordenar una sala vacía no produce error y sigue vacía",
                d3.cantidad() == 0 && d3.getSalaDeEspera().enlacesConsistentes());

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: minutos de espera negativos lanzan excepción",
                lanza(() -> new Paciente("H030", "Rosa", 2, -1)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
