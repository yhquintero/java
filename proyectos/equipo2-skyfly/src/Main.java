/**
 * Demostración de los puntos a) a e) del Equipo 2 (SkyFly)
 * con los datos de prueba sugeridos en la guía.
 */
public class Main {

    public static void main(String[] args) {
        SkyFly control = new SkyFly();

        // Datos de prueba de la guía: (número, destino, estado, duración en minutos)
        control.insertar(new Vuelo("SK101", "Madrid", "A tiempo", 120));
        control.insertar(new Vuelo("SK102", "Cancún", "Demorado", 210));
        control.insertar(new Vuelo("SK103", "Madrid", "Cancelado", 120));
        control.insertar(new Vuelo("SK104", "Miami", "Demorado", 90));
        control.insertar(new Vuelo("SK105", "Bogotá", "A tiempo", 150));

        System.out.println("Vuelos iniciales: " + control);

        // Punto a)
        System.out.println("a) VuelosDemorados(): " + control.VuelosDemorados());

        // Punto b)
        System.out.println("b) MostrarOrdenadosPorDuracion():");
        for (Vuelo v : control.MostrarOrdenadosPorDuracion()) {
            System.out.println("     " + v);
        }

        // Punto c)
        System.out.println("c) CancelarVuelosADestino(\"Madrid\") eliminó: "
                + control.CancelarVuelosADestino("Madrid") + " vuelos");

        // Punto d)
        System.out.println("d) CambiarEstadoVuelo(\"SK105\", \"Cancelado\"): "
                + control.CambiarEstadoVuelo("SK105", "Cancelado"));
        System.out.println("   CambiarEstadoVuelo(\"SK999\", \"Demorado\"): "
                + control.CambiarEstadoVuelo("SK999", "Demorado"));

        // Punto e)
        System.out.println("e) BuscarVueloMasLargo(): " + control.BuscarVueloMasLargo());

        System.out.println("Vuelos finales: " + control);
    }
}
