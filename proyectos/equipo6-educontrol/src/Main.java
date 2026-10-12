/**
 * Demostración de los puntos a) a e) del Equipo 6 (EduControl).
 * La guía no da datos exactos para este equipo: se usan los siguientes
 * (notas entre 40 y 95 y asistencias entre 70% y 100%, como indica la guía).
 */
public class Main {

    public static void main(String[] args) {
        EduControl gestion = new EduControl();

        // (id, nombre, notaParcial, asistencia %)
        gestion.insertar(new Estudiante("E01", "Ana", 40, 70));
        gestion.insertar(new Estudiante("E02", "Luis", 95, 100));
        gestion.insertar(new Estudiante("E03", "Marta", 65, 90));
        gestion.insertar(new Estudiante("E04", "Pedro", 78, 85));
        gestion.insertar(new Estudiante("E05", "Sofía", 88, 92));
        gestion.insertar(new Estudiante("E06", "Jorge", 52, 75));

        System.out.println("Estudiantes iniciales: " + gestion);

        // Punto a)
        System.out.println("a) EstudiantesAprobados(60): " + gestion.EstudiantesAprobados(60));

        // Punto b)
        System.out.println("b) PromedioGeneral(): " + gestion.PromedioGeneral());

        // Punto c)
        System.out.println("c) AjustarNotasPorAsistencia() bonificó a: "
                + gestion.AjustarNotasPorAsistencia() + " estudiantes");

        // Punto d)
        System.out.println("d) BuscarEstudianteConMayorAsistencia(): "
                + gestion.BuscarEstudianteConMayorAsistencia());

        // Punto e)
        System.out.println("e) EliminarEstudiantesReprobados(60) eliminó: "
                + gestion.EliminarEstudiantesReprobados(60) + " estudiantes");

        System.out.println("Estudiantes finales: " + gestion);
    }
}
