import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Lista doblemente enlazada genérica, implementada a mano.
 * Cada nodo tiene un enlace 'anterior' y otro 'siguiente'; la lista conoce su cabeza y su cola.
 * No usa java.util.LinkedList ni otras colecciones de java.util para almacenar datos.
 */
public class ListaDobleEnlazada<T> implements Iterable<T> {

    /** Nodo de la lista: guarda un dato y los enlaces al nodo anterior y al siguiente. */
    public static class Nodo<T> {
        private T dato;
        private Nodo<T> anterior;
        private Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }

        public T getDato() {
            return dato;
        }

        public Nodo<T> getAnterior() {
            return anterior;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
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

    public Nodo<T> getPrimerNodo() {
        return cabeza;
    }

    public Nodo<T> getUltimoNodo() {
        return cola;
    }

    /** Agrega un dato al final de la lista. */
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.anterior = cola;
        if (cola == null) {
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
        if (cabeza == null) {
            cola = nuevo;
        } else {
            cabeza.anterior = nuevo;
        }
        cabeza = nuevo;
        tamanio++;
    }

    /** Inserta un dato justo antes del nodo de referencia. Si la referencia es null, lo agrega al final. */
    public void insertarAntesDe(Nodo<T> referencia, T dato) {
        if (referencia == null) {
            agregarAlFinal(dato);
            return;
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = referencia;
        nuevo.anterior = referencia.anterior;
        if (referencia.anterior == null) {
            cabeza = nuevo;
        } else {
            referencia.anterior.siguiente = nuevo;
        }
        referencia.anterior = nuevo;
        tamanio++;
    }

    /** Devuelve el primer nodo cuyo dato cumple la condición, o null si ninguno la cumple. */
    public Nodo<T> buscarNodo(Predicate<T> condicion) {
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            if (condicion.test(n.dato)) {
                return n;
            }
        }
        return null;
    }

    /** Devuelve el primer dato que cumple la condición, o null si ninguno la cumple. */
    public T buscarPrimero(Predicate<T> condicion) {
        Nodo<T> nodo = buscarNodo(condicion);
        return nodo == null ? null : nodo.dato;
    }

    /**
     * Elimina un nodo de la lista reenlazando su anterior y su siguiente (incluye cabeza y cola).
     * Precondición: el nodo pertenece a esta lista (se obtiene con buscarNodo o getPrimerNodo).
     */
    public void eliminarNodo(Nodo<T> nodo) {
        if (nodo.anterior != null) {
            nodo.anterior.siguiente = nodo.siguiente;
        } else {
            cabeza = nodo.siguiente;
        }
        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        } else {
            cola = nodo.anterior;
        }
        nodo.anterior = null;
        nodo.siguiente = null;
        tamanio--;
    }

    /** Elimina todos los datos que cumplen la condición y devuelve cuántos se eliminaron. */
    public int eliminarTodos(Predicate<T> condicion) {
        int eliminados = 0;
        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> siguiente = actual.siguiente;   // se guarda antes de desenlazar
            if (condicion.test(actual.dato)) {
                eliminarNodo(actual);
                eliminados++;
            }
            actual = siguiente;
        }
        return eliminados;
    }

    /** Mueve un nodo de la lista al principio, reenlazando sus vecinos y los punteros de cabeza y cola. */
    public void moverAlInicio(Nodo<T> nodo) {
        if (nodo == cabeza) {
            return;
        }
        // Desenlazar: como no es la cabeza, tiene anterior.
        nodo.anterior.siguiente = nodo.siguiente;
        if (nodo.siguiente != null) {
            nodo.siguiente.anterior = nodo.anterior;
        } else {
            cola = nodo.anterior;                  // el nodo era la cola
        }
        // Enlazar al inicio.
        nodo.anterior = null;
        nodo.siguiente = cabeza;
        cabeza.anterior = nodo;
        cabeza = nodo;
    }

    /** Devuelve una NUEVA lista con los datos en orden inverso (del último al primero). */
    public ListaDobleEnlazada<T> copiaInversa() {
        ListaDobleEnlazada<T> resultado = new ListaDobleEnlazada<>();
        for (Nodo<T> n = cola; n != null; n = n.anterior) {
            resultado.agregarAlFinal(n.dato);
        }
        return resultado;
    }

    /**
     * Ordena la lista EN SITIO reenlazando los nodos (no se copian datos).
     * Ordenación por inserción estable, de menor a mayor según el comparador.
     * Los nodos quedan con sus enlaces 'anterior' y 'siguiente' correctamente actualizados.
     */
    public void ordenar(Comparator<T> comparador) {
        Nodo<T> pendientes = cabeza;   // nodos que aún no se han colocado
        cabeza = null;
        cola = null;
        while (pendientes != null) {
            Nodo<T> actual = pendientes;
            pendientes = pendientes.siguiente;
            // Primera posición de la lista ya ordenada antes de la cual debe ir 'actual'.
            Nodo<T> referencia = cabeza;
            while (referencia != null && comparador.compare(actual.dato, referencia.dato) >= 0) {
                referencia = referencia.siguiente;
            }
            actual.siguiente = referencia;
            if (referencia == null) {              // va al final
                actual.anterior = cola;
                if (cola == null) {
                    cabeza = actual;
                } else {
                    cola.siguiente = actual;
                }
                cola = actual;
            } else {                               // va antes de 'referencia'
                actual.anterior = referencia.anterior;
                if (referencia.anterior == null) {
                    cabeza = actual;
                } else {
                    referencia.anterior.siguiente = actual;
                }
                referencia.anterior = actual;
            }
        }
    }

    /** Comprueba que los enlaces 'anterior' y 'siguiente' sean coherentes en ambos sentidos. */
    public boolean enlacesConsistentes() {
        if (cabeza == null) {
            return cola == null && tamanio == 0;
        }
        if (cabeza.anterior != null || cola.siguiente != null) {
            return false;
        }
        int contador = 0;
        Nodo<T> previo = null;
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            if (n.anterior != previo) {
                return false;
            }
            previo = n;
            contador++;
        }
        return previo == cola && contador == tamanio;
    }

    /** Iterador en orden directo (de la cabeza a la cola). */
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

    /** Texto de la lista en orden directo (de la cabeza a la cola). */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Nodo<T> n = cabeza; n != null; n = n.siguiente) {
            sb.append(n.dato);
            if (n.siguiente != null) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }

    /** Texto de la lista en orden inverso (de la cola a la cabeza), recorriendo los enlaces 'anterior'. */
    public String toStringInverso() {
        StringBuilder sb = new StringBuilder("[");
        for (Nodo<T> n = cola; n != null; n = n.anterior) {
            sb.append(n.dato);
            if (n.anterior != null) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }
}
