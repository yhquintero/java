import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Lista simplemente enlazada implementada a mano (no usa colecciones de java.util).
 * Cada nodo guarda un dato y un enlace "siguiente" al nodo que le sigue.
 */
public class ListaEnlazada<T> implements Iterable<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> cabeza;
    private int tamanio;

    public int size() {
        return tamanio;
    }

    public boolean isEmpty() {
        return tamanio == 0;
    }

    /** Agrega un elemento al final de la lista. */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    /** Agrega un elemento al inicio de la lista. */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        tamanio++;
    }

    /** Devuelve el primer elemento que cumple la condición, o null si no existe. */
    public T buscarSi(Predicate<T> condicion) {
        for (T dato : this) {
            if (condicion.test(dato)) {
                return dato;
            }
        }
        return null;
    }

    /** Elimina todos los elementos que cumplen la condición y devuelve cuántos eliminó. */
    public int eliminarSi(Predicate<T> condicion) {
        int eliminados = 0;
        while (cabeza != null && condicion.test(cabeza.dato)) {
            cabeza = cabeza.siguiente;
            eliminados++;
        }
        Nodo<T> actual = cabeza;
        while (actual != null && actual.siguiente != null) {
            if (condicion.test(actual.siguiente.dato)) {
                actual.siguiente = actual.siguiente.siguiente;
                eliminados++;
            } else {
                actual = actual.siguiente;
            }
        }
        tamanio -= eliminados;
        return eliminados;
    }

    /**
     * Ordena la lista reacomodando los nodos (inserción estable).
     * cmp(a, b) &lt; 0 significa que a debe ir antes que b.
     */
    public void ordenar(Comparator<T> cmp) {
        Nodo<T> pendientes = cabeza;
        cabeza = null;
        tamanio = 0;
        while (pendientes != null) {
            Nodo<T> siguiente = pendientes.siguiente;
            pendientes.siguiente = null;
            insertarOrdenado(pendientes, cmp);
            pendientes = siguiente;
        }
    }

    private void insertarOrdenado(Nodo<T> nodo, Comparator<T> cmp) {
        if (cabeza == null || cmp.compare(cabeza.dato, nodo.dato) > 0) {
            nodo.siguiente = cabeza;
            cabeza = nodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null && cmp.compare(actual.siguiente.dato, nodo.dato) <= 0) {
                actual = actual.siguiente;
            }
            nodo.siguiente = actual.siguiente;
            actual.siguiente = nodo;
        }
        tamanio++;
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
