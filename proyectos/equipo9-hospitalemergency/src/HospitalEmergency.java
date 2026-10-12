/**
 * Turnos de atención de la sala de emergencias del hospital.
 * Estructura: ListaDobleEnlazada&lt;Paciente&gt; (lista doblemente enlazada hecha a mano).
 * La cabeza de la lista es el siguiente paciente a atender.
 */
public class HospitalEmergency {

    private final ListaDobleEnlazada<Paciente> turnos = new ListaDobleEnlazada<>();

    public int cantidad() {
        return turnos.size();
    }

    public ListaDobleEnlazada<Paciente> getTurnos() {
        return turnos;
    }

    // ===================== Punto a) =====================
    /** Elimina y devuelve el primer paciente de la lista, o null si no hay pacientes. */
    public Paciente AtenderSiguientePaciente() {
        return turnos.quitarPrimero();
    }

    // ===================== Punto b) =====================
    /**
     * Inserta al paciente antes del primero que tenga menor prioridad, de modo que los de mayor
     * prioridad queden al inicio. Entre pacientes con igual prioridad se respeta el orden de llegada.
     */
    public void InsertarPorPrioridad(Paciente nuevo) {
        turnos.insertarAntesSi(nuevo, p -> p.getNivelPrioridad() < nuevo.getNivelPrioridad());
    }

    // ===================== Punto c) =====================
    /** Nombres de los pacientes cuyo tiempo de espera supera el límite (estrictamente mayor). */
    public ListaDobleEnlazada<String> PacientesEnEsperaCritica(int minutosLimite) {
        ListaDobleEnlazada<String> nombres = new ListaDobleEnlazada<>();
        for (Paciente p : turnos) {
            if (p.getMinutosEspera() > minutosLimite) {
                nombres.agregar(p.getNombre());
            }
        }
        return nombres;
    }

    // ===================== Punto d) =====================
    /**
     * Reordena la lista de mayor a menor tiempo de espera, actualizando los enlaces prev y next.
     * Entre pacientes con igual espera se conserva el orden previo (orden estable).
     */
    public void ReordenarPorTiempoEspera() {
        turnos.ordenar((a, b) -> Integer.compare(b.getMinutosEspera(), a.getMinutosEspera()));
    }

    @Override
    public String toString() {
        return turnos.toString();
    }
}
