import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Lista simplemente enlazada genérica, implementada a mano (nodos propios con enlace 'siguiente').
 * No usa java.util.LinkedList ni otras colecciones de java.util para almacenar datos.
 */
public class ListaEnlazada<T> implements Iterable<T> {

    /** Nodo de la lista: guarda un dato y el enlace al siguiente nodo. */
    private static class Nodo<T> {
        T dato;
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

    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Agrega un dato al final de la lista. */
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            cola.siguiente = nuevo;
        }
        cola = nuevo;
        tamanio++;
    }

    /** Agrega un dato al inicio de la lista. */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        if (cola == null) {
            cola = nuevo;
        }
        tamanio++;
    }

    /** Devuelve el dato en la posición indicada (0 = primero). */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    /** Devuelve el primer dato que cumple la condición, o null si ninguno la cumple. */
    public T buscarPrimero(Predicate<T> condicion) {
        for (T dato : this) {
            if (condicion.test(dato)) {
                return dato;
            }
        }
        return null;
    }

    /** Elimina todos los datos que cumplen la condición y devuelve cuántos se eliminaron. */
    public int eliminarTodos(Predicate<T> condicion) {
        int eliminados = 0;
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (condicion.test(actual.dato)) {
                if (anterior == null) {
                    cabeza = actual.siguiente;   // se elimina la cabeza
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                if (actual == cola) {
                    cola = anterior;             // se elimina la cola
                }
                tamanio--;
                eliminados++;
            } else {
                anterior = actual;
            }
            actual = actual.siguiente;
        }
        return eliminados;
    }

    /**
     * Devuelve una NUEVA lista ordenada según el comparador (ordenación por inserción, estable).
     * La lista original no se modifica.
     */
    public ListaEnlazada<T> copiaOrdenada(Comparator<T> comparador) {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();
        for (T dato : this) {
            resultado.insertarOrdenado(dato, comparador);
        }
        return resultado;
    }

    /** Inserta el dato en la posición ordenada; los iguales quedan después de los existentes. */
    private void insertarOrdenado(T dato, Comparator<T> comparador) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null || comparador.compare(dato, cabeza.dato) < 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            if (cola == null) {
                cola = nuevo;
            }
        } else {
            Nodo<T> anterior = cabeza;
            while (anterior.siguiente != null && comparador.compare(dato, anterior.siguiente.dato) >= 0) {
                anterior = anterior.siguiente;
            }
            nuevo.siguiente = anterior.siguiente;
            anterior.siguiente = nuevo;
            if (nuevo.siguiente == null) {
                cola = nuevo;
            }
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
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) {
                sb.append(", ");
            }
            actual = actual.siguiente;
        }
        return sb.append("]").toString();
    }
}
