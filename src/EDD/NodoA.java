/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 * NodoA representa un nodo de un Árbol AVL.
 *
 * Cada nodo almacena: - clave: texto usado para ordenar (autor o palabra
 * clave). - altura: necesaria para mantener el árbol balanceado. - enlaces al
 * hijo izquierdo y derecho.
 */
public class NodoA {

    private String clave;   // autor o palabra clave
    private int altura;     // altura del nodo en el AVL
    private NodoA izquierdo;
    private NodoA derecho;

    public NodoA(String clave) {
        this.clave = clave;
        this.altura = 1;   // una hoja se considera altura 1
        this.izquierdo = null;
        this.derecho = null;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoA getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoA izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoA getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoA derecho) {
        this.derecho = derecho;
    }
}
