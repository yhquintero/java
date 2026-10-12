/**
 * Demostración de los puntos a) a d) del Equipo 8 (WebBrowser).
 * La guía no da datos exactos: se usan 5 visitas de ejemplo.
 */
public class Main {

    public static void main(String[] args) {
        WebBrowser historial = new WebBrowser();

        // (url, título de la página, hora de visita HH:mm)
        historial.registrarVisita(new Pagina("https://docs.oracle.com/javase", "Documentación oficial de Java", "09:00"));
        historial.registrarVisita(new Pagina("https://www.google.com", "Buscador Google", "09:30"));
        historial.registrarVisita(new Pagina("https://github.com", "GitHub - Repositorios", "10:00"));
        historial.registrarVisita(new Pagina("https://es.wikipedia.org", "Wikipedia en español", "10:45"));
        historial.registrarVisita(new Pagina("https://www.python.org", "Python - Lenguaje de programación", "11:20"));

        System.out.println("Historial inicial (más antigua primero):");
        System.out.println("  " + historial);

        // Punto a)
        System.out.println("a) EliminarPaginaPorUrl(\"https://www.google.com\") eliminó: "
                + historial.EliminarPaginaPorUrl("https://www.google.com") + " visita(s)");

        // Punto b)
        System.out.println("b) MostrarHistorialReciente() (más reciente primero):");
        System.out.println("  " + historial.MostrarHistorialReciente());

        // Punto c)
        System.out.println("c) BuscarPorPalabraClave(\"python\"): " + historial.BuscarPorPalabraClave("python"));

        // Punto d)
        System.out.println("d) VaciarHistorialAntiguo(\"10:00\") eliminó: "
                + historial.VaciarHistorialAntiguo("10:00") + " visita(s)");
        System.out.println("Historial final: " + historial);
    }
}
