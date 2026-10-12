/**
 * Casos de prueba del Equipo 9 (HospitalEmergency): positivos, negativos y de borde.
 * Verifican orden de atención, tiempos de espera y reordenamiento con los enlaces prev y next.
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

    /** Valores de prioridad de la lista, en orden, separados por coma (ej. "5,4,2,1"). */
    static String prioridades(ListaDobleEnlazada<Paciente> lista) {
        StringBuilder sb = new StringBuilder();
        for (Paciente p : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(p.getNivelPrioridad());
        }
        return sb.toString();
    }

    /** Minutos de espera de la lista, en orden, separados por coma. */
    static String esperas(ListaDobleEnlazada<Paciente> lista) {
        StringBuilder sb = new StringBuilder();
        for (Paciente p : lista) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(p.getMinutosEspera());
        }
        return sb.toString();
    }

    /** Atiende a todos los pacientes y devuelve sus nombres en el orden de atención. */
    static String ordenDeAtencion(HospitalEmergency sala) {
        StringBuilder sb = new StringBuilder();
        Paciente p = sala.AtenderSiguientePaciente();
        while (p != null) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(p.getNombre());
            p = sala.AtenderSiguientePaciente();
        }
        return sb.toString();
    }

    /** Sala con los pacientes de la guía (prioridades 2, 5, 1, 4). */
    static HospitalEmergency datosGuia() {
        HospitalEmergency s = new HospitalEmergency();
        s.InsertarPorPrioridad(new Paciente("HC-001", "Ana", 2, 30));
        s.InsertarPorPrioridad(new Paciente("HC-002", "Luis", 5, 10));
        s.InsertarPorPrioridad(new Paciente("HC-003", "Marta", 1, 45));
        s.InsertarPorPrioridad(new Paciente("HC-004", "Pedro", 4, 20));
        return s;
    }

    public static void main(String[] args) {
        System.out.println("=== PRUEBAS POSITIVAS (flujo normal) ===");
        HospitalEmergency s = datosGuia();
        verificar("b) Orden por prioridad al insertar = 5,4,2,1", prioridades(s.getTurnos()).equals("5,4,2,1"));
        verificar("b) Punteros coherentes tras las inserciones", s.getTurnos().esConsistente());
        verificar("c) Espera crítica (> 25 min) = [Ana, Marta]",
                s.PacientesEnEsperaCritica(25).toString().equals("[Ana, Marta]"));
        verificar("c) Borde: espera igual al límite (30) no cuenta como crítica",
                s.PacientesEnEsperaCritica(30).toString().equals("[Marta]"));

        verificar("a) El primero en atenderse es Luis (prioridad 5)",
                datosGuia().AtenderSiguientePaciente().getNombre().equals("Luis"));
        HospitalEmergency atencion = datosGuia();
        verificar("a) Orden completo de atención = Luis,Pedro,Ana,Marta",
                ordenDeAtencion(atencion).equals("Luis,Pedro,Ana,Marta"));
        verificar("a) Sala vacía tras atender a todos", atencion.cantidad() == 0);

        HospitalEmergency iguales = new HospitalEmergency();
        iguales.InsertarPorPrioridad(new Paciente("HC-010", "A", 3, 5));
        iguales.InsertarPorPrioridad(new Paciente("HC-011", "B", 3, 6));
        iguales.InsertarPorPrioridad(new Paciente("HC-012", "C", 5, 7));
        verificar("b) Igual prioridad respeta llegada: orden = C,A,B",
                ordenDeAtencion(iguales).equals("C,A,B"));

        HospitalEmergency r = datosGuia();
        r.ReordenarPorTiempoEspera();
        verificar("d) Reordenar por espera (mayor a menor) = 45,30,20,10",
                esperas(r.getTurnos()).equals("45,30,20,10"));
        verificar("d) Punteros prev y next coherentes tras reordenar", r.getTurnos().esConsistente());
        verificar("d) Tras reordenar, el primero es Marta (45 min)",
                r.AtenderSiguientePaciente().getNombre().equals("Marta"));

        System.out.println("\n=== PRUEBAS NEGATIVAS (errores y elementos no encontrados) ===");
        verificar("Prioridad 0 lanza excepción",
                lanza(() -> new Paciente("HC-020", "X", 0, 5)));
        verificar("Prioridad 6 lanza excepción",
                lanza(() -> new Paciente("HC-021", "X", 6, 5)));
        verificar("Minutos de espera negativos lanzan excepción",
                lanza(() -> new Paciente("HC-022", "X", 3, -5)));
        verificar("c) Límite de espera muy alto no encuentra pacientes críticos",
                datosGuia().PacientesEnEsperaCritica(1000).toString().equals("[]"));

        System.out.println("\n=== PRUEBAS DE BORDE (sala vacía) ===");
        HospitalEmergency vacia = new HospitalEmergency();
        verificar("a) Atender en sala vacía devuelve null", vacia.AtenderSiguientePaciente() == null);
        verificar("c) Espera crítica en sala vacía = []", vacia.PacientesEnEsperaCritica(0).toString().equals("[]"));
        vacia.ReordenarPorTiempoEspera();
        verificar("d) Reordenar sala vacía no falla y mantiene punteros coherentes",
                vacia.getTurnos().esConsistente() && vacia.cantidad() == 0);
        verificar("b) Insertar en sala vacía deja un solo paciente",
                insertarUnoYContar() == 1);

        System.out.println("\nResultado: " + aciertos + " OK, " + fallos + " FALLO(S)");
        if (fallos > 0) {
            System.exit(1);
        }
    }

    /** Inserta un paciente en una sala vacía y devuelve la cantidad de turnos. */
    static int insertarUnoYContar() {
        HospitalEmergency s = new HospitalEmergency();
        s.InsertarPorPrioridad(new Paciente("HC-030", "Solo", 1, 0));
        return s.cantidad();
    }
}
