/**
 * Pruebas del proyecto FitGym (Equipo 3). Incluye casos positivos y negativos
 * (lista vacía, socio no encontrado, excepciones).
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

    /** Convierte la lista de socios en la lista de sus IDs, por ejemplo [S05, S02]. */
    static String ids(ListaEnlazada<Socio> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (Socio s : lista) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(s.getIdSocio());
        }
        return sb.append("]").toString();
    }

    static Socio buscar(FitGym gym, String id) {
        for (Socio s : gym.getSocios()) {
            if (s.getIdSocio().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /** Datos de prueba del enunciado (una instancia nueva en cada llamada). */
    static FitGym datosPrueba() {
        FitGym gym = new FitGym();
        gym.registrarSocio(new Socio("S01", "Carlos", "Básica", 5));
        gym.registrarSocio(new Socio("S02", "Ana", "Premium", 30));
        gym.registrarSocio(new Socio("S03", "Pedro", "VIP", 2));
        gym.registrarSocio(new Socio("S04", "María", "Básica", 15));
        gym.registrarSocio(new Socio("S05", "Lucía", "Premium", 60));
        return gym;
    }

    public static void main(String[] args) {
        System.out.println("=== FitGym - Lista Simplemente Enlazada ===");

        System.out.println("\n-- Datos insertados (caso de prueba del enunciado) --");
        FitGym gym = datosPrueba();
        for (Socio s : gym.getSocios()) {
            System.out.println("  " + s);
        }
        verificar("Se insertaron 5 socios", gym.cantidad() == 5);

        System.out.println("\n-- Punto a) MembresiasPorVencer(int diasLimite) --");
        verificar("Positivo: límite 15 devuelve [S01, S03, S04] (incluye el caso 15 = 15)",
                gym.MembresiasPorVencer(15).toString().equals("[S01, S03, S04]"));
        verificar("Positivo: límite 60 devuelve los 5 socios",
                gym.MembresiasPorVencer(60).toString().equals("[S01, S02, S03, S04, S05]"));
        verificar("Negativo: límite -1 no encuentra socios", gym.MembresiasPorVencer(-1).estaVacia());
        verificar("Negativo: lista vacía devuelve lista vacía", new FitGym().MembresiasPorVencer(10).estaVacia());

        System.out.println("\n-- Punto b) MostrarOrdenadosPorDias() --");
        verificar("Positivo: orden de mayor a menor [S05(60), S02(30), S04(15), S01(5), S03(2)]",
                ids(gym.MostrarOrdenadosPorDias()).equals("[S05, S02, S04, S01, S03]"));
        verificar("Positivo: la lista original no se altera (primero sigue siendo S01)",
                gym.getSocios().obtener(0).getIdSocio().equals("S01"));
        verificar("Negativo: lista vacía devuelve lista vacía",
                new FitGym().MostrarOrdenadosPorDias().estaVacia());

        System.out.println("\n-- Punto c) CantidadSociosPorMembresia() --");
        verificar("Positivo: 'Básica: 2 | Premium: 2 | VIP: 1'",
                gym.CantidadSociosPorMembresia().equals("Básica: 2 | Premium: 2 | VIP: 1"));
        verificar("Negativo: lista vacía devuelve 'Básica: 0 | Premium: 0 | VIP: 0'",
                new FitGym().CantidadSociosPorMembresia().equals("Básica: 0 | Premium: 0 | VIP: 0"));

        System.out.println("\n-- Punto d) RenovarMembresia(String idSocio, int diasAdicionales) --");
        FitGym d1 = datosPrueba();
        verificar("Positivo: RenovarMembresia(S03, 30) devuelve true", d1.RenovarMembresia("S03", 30));
        verificar("Positivo: S03 pasa de 2 a 32 días", buscar(d1, "S03").getDiasRestantes() == 32);
        verificar("Negativo: socio inexistente (S99) devuelve false", !d1.RenovarMembresia("S99", 10));
        verificar("Negativo: lista vacía devuelve false", !new FitGym().RenovarMembresia("S01", 10));
        verificar("Negativo: días adicionales negativos lanzan IllegalArgumentException",
                lanza(() -> datosPrueba().RenovarMembresia("S01", -5)));

        System.out.println("\n-- Punto e) EliminarSociosSinDias() --");
        FitGym e1 = datosPrueba();
        e1.registrarSocio(new Socio("S06", "Jorge", "Básica", 0));
        e1.registrarSocio(new Socio("S07", "Elena", "VIP", 0));
        verificar("Positivo: elimina los 2 socios con 0 días y devuelve 2", e1.EliminarSociosSinDias() == 2);
        verificar("Positivo: quedan 5 socios y ninguno tiene 0 días",
                e1.cantidad() == 5 && e1.MembresiasPorVencer(0).estaVacia());
        verificar("Negativo: sin socios con 0 días devuelve 0", datosPrueba().EliminarSociosSinDias() == 0);
        verificar("Negativo: lista vacía devuelve 0", new FitGym().EliminarSociosSinDias() == 0);

        System.out.println("\n-- Validaciones de la entidad --");
        verificar("Negativo: tipo de membresía fuera del catálogo lanza excepción",
                lanza(() -> new Socio("S08", "Luis", "Gold", 10)));
        verificar("Negativo: días restantes negativos lanzan excepción",
                lanza(() -> new Socio("S09", "Luis", "Básica", -1)));
        verificar("Negativo: registrar null lanza excepción",
                lanza(() -> new FitGym().registrarSocio(null)));

        System.out.println("\n=== RESULTADO: " + pruebasSuperadas + " de " + totalPruebas + " pruebas superadas ===");
        if (pruebasSuperadas != totalPruebas) {
            System.exit(1);
        }
    }
}
