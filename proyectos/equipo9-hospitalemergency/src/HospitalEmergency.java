/**
 * Organización de los turnos de atención de la sala de emergencias 'HospitalEmergency'.
 * Estructura del enunciado: Lista Doblemente Enlazada (ListaDobleEnlazada&lt;Paciente&gt;, implementada a mano).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 *
 * INTERPRETACIÓN DE LA PRIORIDAD (ambigüedad del enunciado):
 * "mayor prioridad al inicio" se interpreta como: un nivelPrioridad MAYOR (5) significa MAYOR prioridad,
 * por lo que el paciente de nivel 5 se atiende antes que el de nivel 1.
 * Los pacientes con el mismo nivel se atienden en orden de llegada (FIFO).
 * Para invertir la convención (1 = más urgente), basta con cambiar el método tienePrioridadSobre.
 */
public class HospitalEmergency {

    private final ListaDobleEnlazada<Paciente> salaDeEspera = new ListaDobleEnlazada<>();

    /** Indica si el paciente 'a' tiene mayor prioridad que el paciente 'b' (ver interpretación arriba). */
    static boolean tienePrioridadSobre(Paciente a, Paciente b) {
        return a.getNivelPrioridad() > b.getNivelPrioridad();
    }

    public int cantidad() {
        return salaDeEspera.size();
    }

    public ListaDobleEnlazada<Paciente> getSalaDeEspera() {
        return salaDeEspera;
    }

    // =====================================================================
    // Punto a) AtenderSiguientePaciente()
    // Elimina y devuelve el primer paciente de la lista (el siguiente en ser atendido).
    // Devuelve null si no hay pacientes en espera.
    // =====================================================================
    public Paciente AtenderSiguientePaciente() {
        ListaDobleEnlazada.Nodo<Paciente> primero = salaDeEspera.getPrimerNodo();
        if (primero == null) {
            return null;
        }
        salaDeEspera.eliminarNodo(primero);
        return primero.getDato();
    }

    // =====================================================================
    // Punto b) InsertarPorPrioridad(Paciente nuevo)
    // Inserta al paciente en la posición adecuada según su nivel de prioridad:
    // delante del primer paciente con menor prioridad (los iguales quedan detrás, FIFO).
    // Un paciente nulo lanza IllegalArgumentException.
    // =====================================================================
    public void InsertarPorPrioridad(Paciente nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo.");
        }
        ListaDobleEnlazada.Nodo<Paciente> posicion =
                salaDeEspera.buscarNodo(actual -> tienePrioridadSobre(nuevo, actual));
        salaDeEspera.insertarAntesDe(posicion, nuevo);   // posicion == null => al final
    }

    // =====================================================================
    // Punto c) PacientesEnEsperaCritica(int minutosLimite)
    // Nombres de los pacientes cuyo tiempo de espera supera (estrictamente) el límite.
    // =====================================================================
    public ListaDobleEnlazada<String> PacientesEnEsperaCritica(int minutosLimite) {
        ListaDobleEnlazada<String> nombres = new ListaDobleEnlazada<>();
        for (Paciente p : salaDeEspera) {
            if (p.getMinutosEspera() > minutosLimite) {
                nombres.agregarAlFinal(p.getNombre());
            }
        }
        return nombres;
    }

    // =====================================================================
    // Punto d) ReordenarPorTiempoEspera()
    // Ordena la lista de MAYOR a MENOR tiempo de espera, reenlazando los nodos (prev y next).
    // La ordenación es estable: a igual tiempo de espera se conserva el orden previo.
    // =====================================================================
    public void ReordenarPorTiempoEspera() {
        salaDeEspera.ordenar((a, b) -> Integer.compare(b.getMinutosEspera(), a.getMinutosEspera()));
    }
}
