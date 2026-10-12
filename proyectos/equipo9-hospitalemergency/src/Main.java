/**
 * Demostración de los puntos a) a d) del Equipo 9 (HospitalEmergency)
 * con los datos de prueba sugeridos en la guía (prioridades 2, 5, 1 y 4).
 */
public class Main {

    public static void main(String[] args) {
        HospitalEmergency sala = new HospitalEmergency();

        // (historialClinico, nombre, nivelPrioridad 1-5, minutosEspera)
        sala.InsertarPorPrioridad(new Paciente("HC-001", "Ana", 2, 30));
        sala.InsertarPorPrioridad(new Paciente("HC-002", "Luis", 5, 10));
        sala.InsertarPorPrioridad(new Paciente("HC-003", "Marta", 1, 45));
        sala.InsertarPorPrioridad(new Paciente("HC-004", "Pedro", 4, 20));

        System.out.println("Turnos (mayor prioridad primero):");
        System.out.println("  " + sala);

        // Punto a)
        Paciente atendido = sala.AtenderSiguientePaciente();
        System.out.println("a) AtenderSiguientePaciente(): " + atendido);

        // Punto c)
        System.out.println("c) PacientesEnEsperaCritica(25): " + sala.PacientesEnEsperaCritica(25));

        // Punto d)
        sala.ReordenarPorTiempoEspera();
        System.out.println("d) Tras ReordenarPorTiempoEspera() (mayor espera primero):");
        System.out.println("  " + sala);

        System.out.println("Turnos finales: " + sala);
    }
}
