/**
 * Demostración de los puntos a) a e) del Equipo 3 (FitGym)
 * con los datos de prueba sugeridos en la guía.
 */
public class Main {

    public static void main(String[] args) {
        FitGym registro = new FitGym();

        // Datos de prueba de la guía: (idSocio, nombre, tipoMembresia, díasRestantes)
        registro.insertar(new Socio("S01", "Carlos", "Básica", 5));
        registro.insertar(new Socio("S02", "Ana", "Premium", 30));
        registro.insertar(new Socio("S03", "Pedro", "VIP", 2));
        registro.insertar(new Socio("S04", "María", "Básica", 15));
        registro.insertar(new Socio("S05", "Lucía", "Premium", 60));

        System.out.println("Socios iniciales: " + registro);

        // Punto a)
        System.out.println("a) MembresiasPorVencer(5): " + registro.MembresiasPorVencer(5));

        // Punto b)
        System.out.println("b) MostrarOrdenadosPorDias():");
        for (Socio s : registro.MostrarOrdenadosPorDias()) {
            System.out.println("     " + s);
        }

        // Punto c)
        System.out.println("c) CantidadSociosPorMembresia(): " + registro.CantidadSociosPorMembresia());

        // Punto d)
        System.out.println("d) RenovarMembresia(\"S03\", 30): " + registro.RenovarMembresia("S03", 30));
        System.out.println("   RenovarMembresia(\"S99\", 30): " + registro.RenovarMembresia("S99", 30));

        // Punto e)
        System.out.println("e) EliminarSociosSinDias() eliminó: " + registro.EliminarSociosSinDias() + " socios");

        System.out.println("Socios finales: " + registro);
    }
}
