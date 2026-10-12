/**
 * Registro de socios del centro deportivo 'FitGym'.
 * Estructura del enunciado: Lista Simplemente Enlazada (ListaEnlazada&lt;Socio&gt;, implementada a mano).
 * Los nombres de los métodos se mantienen tal como los define el enunciado.
 */
public class FitGym {

    private final ListaEnlazada<Socio> socios = new ListaEnlazada<>();

    /** Inserta un socio al final de la lista (ingreso de datos). */
    public void registrarSocio(Socio socio) {
        if (socio == null) {
            throw new IllegalArgumentException("El socio no puede ser nulo.");
        }
        socios.agregarAlFinal(socio);
    }

    public int cantidad() {
        return socios.size();
    }

    public ListaEnlazada<Socio> getSocios() {
        return socios;
    }

    // =====================================================================
    // Punto a) MembresiasPorVencer(int diasLimite)
    // IDs de los socios cuyos días restantes son menores o iguales a diasLimite (límite inclusivo).
    // =====================================================================
    public ListaEnlazada<String> MembresiasPorVencer(int diasLimite) {
        ListaEnlazada<String> ids = new ListaEnlazada<>();
        for (Socio s : socios) {
            if (s.getDiasRestantes() <= diasLimite) {
                ids.agregarAlFinal(s.getIdSocio());
            }
        }
        return ids;
    }

    // =====================================================================
    // Punto b) MostrarOrdenadosPorDias()
    // Información de los socios ordenada de mayor a menor según los días restantes.
    // Interpretación: se devuelve una copia ordenada (estable); la lista original no cambia.
    // =====================================================================
    public ListaEnlazada<Socio> MostrarOrdenadosPorDias() {
        return socios.copiaOrdenada((a, b) -> Integer.compare(b.getDiasRestantes(), a.getDiasRestantes()));
    }

    // =====================================================================
    // Punto c) CantidadSociosPorMembresia()
    // String con el conteo de socios agrupados por tipo de membresía, en el orden Básica, Premium, VIP.
    // Los tipos sin socios aparecen con 0.
    // =====================================================================
    public String CantidadSociosPorMembresia() {
        int[] conteos = new int[Socio.TIPOS_MEMBRESIA.length];
        for (Socio s : socios) {
            conteos[Socio.indiceTipo(s.getTipoMembresia())]++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Socio.TIPOS_MEMBRESIA.length; i++) {
            if (i > 0) {
                sb.append(" | ");
            }
            sb.append(Socio.TIPOS_MEMBRESIA[i]).append(": ").append(conteos[i]);
        }
        return sb.toString();
    }

    // =====================================================================
    // Punto d) RenovarMembresia(String idSocio, int diasAdicionales)
    // Busca el socio por ID y le suma los días adicionales. true si existe, false si no.
    // Interpretación: diasAdicionales negativos lanzan IllegalArgumentException.
    // =====================================================================
    public boolean RenovarMembresia(String idSocio, int diasAdicionales) {
        if (diasAdicionales < 0) {
            throw new IllegalArgumentException("Los días adicionales no pueden ser negativos.");
        }
        Socio encontrado = socios.buscarPrimero(s -> s.getIdSocio().equals(idSocio));
        if (encontrado == null) {
            return false;
        }
        encontrado.agregarDias(diasAdicionales);
        return true;
    }

    // =====================================================================
    // Punto e) EliminarSociosSinDias()
    // Elimina a todos los socios con 0 días restantes y devuelve la cantidad eliminada.
    // =====================================================================
    public int EliminarSociosSinDias() {
        return socios.eliminarTodos(s -> s.getDiasRestantes() == 0);
    }
}
