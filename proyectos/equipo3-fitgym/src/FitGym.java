/**
 * Registro de socios del centro deportivo FitGym.
 * Estructura: ListaEnlazada&lt;Socio&gt; (lista simplemente enlazada hecha a mano).
 */
public class FitGym {

    private final ListaEnlazada<Socio> socios = new ListaEnlazada<>();

    /** Registra un socio. Interpretación: no se permiten IDs repetidos. */
    public void insertar(Socio s) {
        if (buscarPorId(s.getIdSocio()) != null) {
            throw new IllegalArgumentException("ID de socio duplicado: " + s.getIdSocio());
        }
        socios.agregar(s);
    }

    public Socio buscarPorId(String idSocio) {
        return socios.buscarSi(s -> s.getIdSocio().equals(idSocio));
    }

    // ===================== Punto a) =====================
    /** IDs de los socios cuyos días restantes son menores o iguales a diasLimite. */
    public ListaEnlazada<String> MembresiasPorVencer(int diasLimite) {
        ListaEnlazada<String> ids = new ListaEnlazada<>();
        for (Socio s : socios) {
            if (s.getDiasRestantes() <= diasLimite) {
                ids.agregar(s.getIdSocio());
            }
        }
        return ids;
    }

    // ===================== Punto b) =====================
    /**
     * Información de los socios ordenada de mayor a menor según los días restantes.
     * Se devuelve una copia ordenada; el registro original no cambia.
     */
    public ListaEnlazada<Socio> MostrarOrdenadosPorDias() {
        ListaEnlazada<Socio> copia = new ListaEnlazada<>();
        for (Socio s : socios) {
            copia.agregar(s);
        }
        copia.ordenar((a, b) -> Integer.compare(b.getDiasRestantes(), a.getDiasRestantes()));
        return copia;
    }

    // ===================== Punto c) =====================
    /** Texto con el conteo de socios por tipo de membresía, p. ej. "Básica: 2 | Premium: 2 | VIP: 1". */
    public String CantidadSociosPorMembresia() {
        StringBuilder sb = new StringBuilder();
        for (String tipo : Socio.TIPOS) {
            int cuenta = 0;
            for (Socio s : socios) {
                if (s.getTipoMembresia().equals(tipo)) {
                    cuenta++;
                }
            }
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            sb.append(tipo).append(": ").append(cuenta);
        }
        return sb.toString();
    }

    // ===================== Punto d) =====================
    /** Suma los días adicionales al socio con ese ID. true si existe, false si no. */
    public boolean RenovarMembresia(String idSocio, int diasAdicionales) {
        if (diasAdicionales < 0) {
            throw new IllegalArgumentException("Los días adicionales no pueden ser negativos");
        }
        Socio s = buscarPorId(idSocio);
        if (s == null) {
            return false;
        }
        s.sumarDias(diasAdicionales);
        return true;
    }

    // ===================== Punto e) =====================
    /** Elimina a los socios con 0 días restantes y devuelve cuántos eliminó. */
    public int EliminarSociosSinDias() {
        return socios.eliminarSi(s -> s.getDiasRestantes() == 0);
    }

    @Override
    public String toString() {
        return socios.toString();
    }
}
