/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

public class Nodo {

    private Object dato;
    private Nodo pnext;

    /**
     * Constructor por defecto. Inicializa el nodo con valor y enlace nulos.
     */
    public Nodo() {
        this.dato = null;
        this.pnext = null;
    }

    /**
     * Constructor que inicializa el nodo con un valor específico y sin enlace
     * al siguiente nodo.
     *
     * @param dato El valor que almacenará el nodo.
     */
    public Nodo(Object dato) {
        this.dato = dato;
        this.pnext = null;
    }

    /**
     * Obtiene el valor almacenado en el nodo.
     *
     * @return El valor del nodo.
     */
    public Object getDato() {
        return dato;
    }

    /**
     * Establece un nuevo valor para el nodo.
     *
     * @param dato El nuevo valor que se asignará al nodo.
     */
    public void setDato(Object dato) {
        this.dato = dato;
    }

    /**
     * Obtiene el nodo al que está enlazado este nodo (el siguiente nodo en la
     * lista).
     *
     * @return El siguiente nodo.
     */
    public Nodo getPnext() {
        return pnext;
    }

    /**
     * Establece el nodo al que se enlazará este nodo (el siguiente nodo en la
     * lista).
     *
     * @param pnext El nodo siguiente al que se enlazará este nodo.
     */
    public void setPnext(Nodo pnext) {
        this.pnext = pnext;
    }
}
