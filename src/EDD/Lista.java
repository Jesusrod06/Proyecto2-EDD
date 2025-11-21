/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import javax.swing.JOptionPane;

public class Lista {

    private Nodo pFirst;
    private int size;

    /**
     * Constructor que inicializa una lista vacía.
     */
    public Lista() {
        this.pFirst = null;
        this.size = 0;
    }

    /**
     * Devuelve el nodo que es el primer elemento de la lista.
     *
     * @return El primer nodo de la lista.
     */
    public Nodo getpFirst() {
        return pFirst;
    }

    /**
     * Establece el primer nodo de la lista.
     *
     * @param pFirst El nuevo primer nodo de la lista.
     */
    public void setpFirst(Nodo pFirst) {
        this.pFirst = pFirst;
    }

    /**
     * Devuelve el tamaño actual de la lista.
     *
     * @return El tamaño de la lista.
     */
    public int getSize() {
        return size;
    }

    /**
     * Establece el tamaño de la lista.
     *
     * @param size El nuevo tamaño de la lista.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Verifica si la lista está vacía.
     *
     * @return {@code true} si la lista no contiene elementos, {@code false} en
     * caso contrario.
     */
    public boolean isEmpty() {
        return this.pFirst == null;
    }

    /**
     * Inserta un nuevo elemento al inicio de la lista.
     *
     * @param dato El dato que se va a insertar.
     */
    public void insertarInicio(Object dato) {
        Nodo pNew = new Nodo();
        pNew.setDato(dato);

        if (isEmpty()) {
            this.pFirst = pNew;
            this.pFirst.setPnext(null);
        } else {
            pNew.setPnext(pFirst);
            pFirst = pNew;
        }
        size++;
    }

    /**
     * Inserta un nuevo elemento al final de la lista.
     *
     * @param dato El dato que se va a insertar.
     */
    public void insertarFinal(Object dato) {
        Nodo pNew = new Nodo(dato);
        if (isEmpty()) {
            pFirst = pNew;
        } else {
            Nodo aux = pFirst;
            while (aux.getPnext() != null) {
                aux = aux.getPnext();
            }
            aux.setPnext(pNew);
        }
        size++;
    }

    /**
     * Inserta un nuevo elemento en una posición específica de la lista.
     *
     * @param posicion La posición en la cual insertar el nuevo elemento.
     * @param valor El valor del nuevo elemento.
     */
    public void insertarPorPosicion(int posicion, Object valor) {
        if (posicion >= 0 && posicion < size) {
            Nodo nuevo = new Nodo(valor);
            if (posicion == 0) {
                nuevo.setPnext(pFirst);
                pFirst = nuevo;
            } else {
                Nodo aux = pFirst;
                for (int i = 0; i < posicion - 1; i++) {
                    aux = aux.getPnext();
                }
                Nodo siguiente = aux.getPnext();
                aux.setPnext(nuevo);
                nuevo.setPnext(siguiente);
            }
            size++;
        }
    }

    /**
     * Inserta un nuevo elemento después de un nodo de referencia.
     *
     * @param ref El nodo de referencia.
     * @param valor El valor del nuevo nodo a insertar.
     */
    public void insertarPorReferencia(Object ref, Object valor) {
        Nodo nuevo = new Nodo();
        nuevo.setDato(valor);

        if (!isEmpty() && buscar(ref)) {
            Nodo aux = pFirst;
            while (aux.getDato() != ref) {
                aux = aux.getPnext();
            }
            Nodo siguiente = aux.getPnext();
            aux.setPnext(nuevo);
            nuevo.setPnext(siguiente);
            size++;
        }
    }

    /**
     * Transforma la lista en una representación de cadena de texto.
     *
     * @return Una cadena que representa los elementos de la lista.
     */
    public String transformar() {
        if (!isEmpty()) {
            Nodo aux = pFirst;
            StringBuilder expresion = new StringBuilder();
            for (int i = 0; i < size; i++) {
                expresion.append(aux.getDato().toString()).append("\n");
                aux = aux.getPnext();
            }
            return expresion.toString();
        }
        return "Lista vacía";
    }

    /**
     * Muestra los elementos de la lista en un cuadro de diálogo y en la
     * consola.
     */
    public void mostrar() {
        if (!isEmpty()) {
            Nodo aux = pFirst;
            StringBuilder expresion = new StringBuilder();
            while (aux != null) {
                expresion.append(aux.getDato().toString()).append("\n");
                aux = aux.getPnext();
            }
            JOptionPane.showMessageDialog(null, expresion.toString());
            System.out.println(expresion);
        } else {
            JOptionPane.showMessageDialog(null, "La lista está vacía");
        }
    }

    /**
     * Elimina el primer elemento de la lista.
     *
     * @return {@code true} si se eliminó correctamente, {@code false} si la
     * lista está vacía.
     */
    public boolean eliminarInicio() {
        if (!isEmpty()) {
            pFirst = pFirst.getPnext();
            size--;
            return true;
        }
        return false;
    }

    /**
     * Elimina el último elemento de la lista.
     */
    public void eliminarFinal() {
        if (!isEmpty()) {
            if (size == 1) {
                eliminar();
            } else {
                Nodo aux = pFirst;
                while (aux.getPnext().getPnext() != null) {
                    aux = aux.getPnext();
                }
                aux.setPnext(null);
                size--;
            }
        }
    }

    /**
     * Elimina un nodo por referencia a su valor.
     *
     * @param referencia El valor del nodo a eliminar.
     */
    public void eliminarPorReferencia(Object referencia) {
        if (buscar(referencia)) {
            if (pFirst.getDato() == referencia) {
                pFirst = pFirst.getPnext();
            } else {
                Nodo aux = pFirst;
                while (aux.getPnext().getDato() != referencia) {
                    aux = aux.getPnext();
                }
                Nodo siguiente = aux.getPnext().getPnext();
                aux.setPnext(siguiente);
            }
            size--;
        }
    }

    /**
     * Elimina un nodo en una posición específica.
     *
     * @param posicion La posición del nodo a eliminar.
     */
    public void eliminarPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < size) {
            if (posicion == 0) {
                pFirst = pFirst.getPnext();
            } else {
                Nodo aux = pFirst;
                for (int i = 0; i < posicion - 1; i++) {
                    aux = aux.getPnext();
                }
                aux.setPnext(aux.getPnext().getPnext());
            }
            size--;
        }
    }

    /**
     * Obtiene el valor de un nodo en una posición específica.
     *
     * @param posicion La posición del nodo.
     * @return El valor del nodo o {@code null} si la posición es inválida.
     */
    public Object getValor(int posicion) {
        if (posicion >= 0 && posicion < size) {
            Nodo aux = pFirst;
            for (int i = 0; i < posicion; i++) {
                aux = aux.getPnext();
            }
            return aux.getDato();
        }
        return null;
    }

    /**
     * Busca un nodo en la lista por su valor.
     *
     * @param referencia El valor del nodo a buscar.
     * @return {@code true} si se encuentra el nodo, {@code false} en caso
     * contrario.
     */
    public boolean buscar(Object referencia) {
        Nodo aux = pFirst;
        while (aux != null) {
            if (referencia == aux.getDato()) {
                return true;
            }
            aux = aux.getPnext();
        }
        return false;
    }

    /**
     * Elimina todos los elementos de la lista, dejándola vacía.
     */
    public void eliminar() {
        pFirst = null;
        size = 0;
    }
}
