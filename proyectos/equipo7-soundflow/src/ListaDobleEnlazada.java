import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Lista doblemente enlazada implementada a mano (no usa colecciones de java.util).
 * Cada nodo tiene enlaces "prev" (anterior) y "siguiente" (next), y la lista guarda
 * referencias a la cabeza y a la cola para poder recorrerla en ambos sentidos.
 */
public class ListaDobleEnlazada<T> implements Iterable<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> prev;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int tamanio;

    public int size() {
        return tamanio;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    /** Agrega un elemento al final de la lista. */
    public void agregar(T dato) {
        insertarAlFinal(new Nodo<>(dato));
    }

    /** Inserta el dato antes del primer elemento que cumple la condición; si no hay ninguno, al final. */
    public void insertarAntesSi(T dato, Predicate<T> condicion) {
        Nodo<T> nuevo = new Nodo<>(dato);
        Nodo<T> actual = cabeza;
        while (actual != null && !condicion.test(actual.dato)) {
            actual = actual.siguiente;
        }
        if (actual == null) {
            insertarAlFinal(nuevo);
        } else {
            insertarAntes(nuevo, actual);
        }
    }

    /** Elimina y devuelve el primer elemento, o null si la lista está vacía. */
    public T quitarPrimero() {
        if (cabeza == null) {
            return null;
        }
        T dato = cabeza.dato;
        desenlazar(cabeza);
        return dato;
    }

    /** Devuelve el primer elemento que cumple la condición, o null si no existe. */
    public T buscarSi(Predicate<T> condicion) {
        Nodo<T> nodo = buscarNodo(condicion);
        return nodo == null ? null : nodo.dato;
    }

    /** Mueve al inicio el primer elemento que cumple la condición. Devuelve true si lo encontró. */
    public boolean moverAlInicio(Predicate<T> condicion) {
        Nodo<T> nodo = buscarNodo(condicion);
        if (nodo == null) {
            return false;
        }
        if (nodo != cabeza) {
            desenlazar(nodo);
            insertarAntes(nodo, cabeza);
        }
        return true;
    }

    /** Elimina todos los elementos que cumplen la condición y devuelve cuántos eliminó. */
    public int eliminarSi(Predicate<T> condicion) {
        int eliminados = 0;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> siguiente = actual.siguiente;
            if (condicion.test(actual.dato)) {
                desenlazar(actual);
                eliminados++;
            }
            actual = siguiente;
        }
        return eliminados;
    }

    /**
     * Ordena la lista reacomodando los nodos y actualizando prev y next (inserción estable).
     * cmp(a, b) &lt; 0 significa que a debe ir antes que b.
     */
    public void ordenar(Comparator<T> cmp) {
        Nodo<T> pendientes = cabeza;
        cabeza = null;
        cola = null;
        tamanio = 0;
        while (pendientes != null) {
            Nodo<T> siguiente = pendientes.siguiente;
            pendientes.prev = null;
            pendientes.siguiente = null;
            insertarOrdenado(pendientes, cmp);
            pendientes = siguiente;
        }
    }

    private void insertarOrdenado(Nodo<T> nodo, Comparator<T> cmp) {
        Nodo<T> actual = cabeza;
        while (actual != null && cmp.compare(actual.dato, nodo.dato) <= 0) {
            actual = actual.siguiente;
        }
        if (actual == null) {
            insertarAlFinal(nodo);
        } else {
            insertarAntes(nodo, actual);
        }
    }

    /** Devuelve una nueva lista con los mismos elementos en orden inverso (de la cola a la cabeza). */
    public ListaDobleEnlazada<T> invertidaCopia() {
        ListaDobleEnlazada<T> invertida = new ListaDobleEnlazada<>();
        for (Nodo<T> n = cola; n != null; n = n.prev) {
            invertida.agregar(n.dato);
        }
        return invertida;
    }

    /**
     * Verifica la coherencia de los punteros: cada nodo debe apuntar bien a su anterior y a su
     * siguiente, la cola debe ser el último nodo y el recorrido en ambos sentidos debe dar el tamaño.
     */
    public boolean esConsistente() {
        int haciaAdelante = 0;
        Nodo<T> anterior = null;
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            if (n.prev != anterior) {
                return false;
            }
            anterior = n;
            haciaAdelante++;
        }
        if (anterior != cola) {
            return false;
        }
        int haciaAtras = 0;
        for (Nodo<T> n = cola; n != null; n = n.prev) {
            haciaAtras++;
        }
        return haciaAdelante == tamanio && haciaAtras == tamanio;
    }

    // ----- Operaciones internas sobre los enlaces -----

    private Nodo<T> buscarNodo(Predicate<T> condicion) {
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            if (condicion.test(n.dato)) {
                return n;
            }
        }
        return null;
    }

    /** Enlaza un nuevo nodo al final de la lista. */
    private void insertarAlFinal(Nodo<T> nuevo) {
        nuevo.prev = cola;
        nuevo.siguiente = null;
        if (cola != null) {
            cola.siguiente = nuevo;
        } else {
            cabeza = nuevo;
        }
        cola = nuevo;
        tamanio++;
    }

    /** Enlaza un nuevo nodo justo antes de la referencia dada. */
    private void insertarAntes(Nodo<T> nuevo, Nodo<T> referencia) {
        nuevo.siguiente = referencia;
        nuevo.prev = referencia.prev;
        if (referencia.prev != null) {
            referencia.prev.siguiente = nuevo;
        } else {
            cabeza = nuevo;
        }
        referencia.prev = nuevo;
        tamanio++;
    }

    /** Quita el nodo de la lista reenlazando sus vecinos (prev y next). */
    private void desenlazar(Nodo<T> nodo) {
        if (nodo.prev != null) {
            nodo.prev.siguiente = nodo.siguiente;
        } else {
            cabeza = nodo.siguiente;
        }
        if (nodo.siguiente != null) {
            nodo.siguiente.prev = nodo.prev;
        } else {
            cola = nodo.prev;
        }
        nodo.prev = null;
        nodo.siguiente = null;
        tamanio--;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (actual == null) {
                    throw new NoSuchElementException();
                }
                T dato = actual.dato;
                actual = actual.siguiente;
                return dato;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (T dato : this) {
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(dato);
        }
        return sb.append("]").toString();
    }
}
