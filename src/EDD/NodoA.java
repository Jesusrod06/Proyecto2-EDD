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
    private Lista listaTitulos;  // Lista de String (títulos de resúmenes)

    public NodoA(String clave) {
        this.clave = clave;
        this.altura = 1;   // una hoja se considera altura 1
        this.izquierdo = null;
        this.derecho = null;
        this.listaTitulos = new Lista();
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

    public Lista getListaTitulos() {
        return listaTitulos;
    }

    public void setListaTitulos(Lista listaTitulos) {
        this.listaTitulos = listaTitulos;
    }
    
    /**
     * Agrega un título de resumen a la lista de este nodo,
     * evitando duplicados (comparación ignorando mayúsculas/minúsculas).
     *
     * @param tituloResumen Título del resumen a asociar con esta clave.
     */
    public void agregarTitulo(String tituloResumen) {
        if (tituloResumen == null) {
            return;
        }

        String tNuevo = tituloResumen.trim();
        if (tNuevo.isEmpty()) {
            return;
        }

        // Evitar títulos repetidos en la lista
        Nodo aux = listaTitulos.getpFirst();
        while (aux != null) {
            String existente = (String) aux.getDato();
            if (existente != null && existente.equalsIgnoreCase(tNuevo)) {
                // Ya está registrado este título para esta clave
                return;
            }
            aux = aux.getPnext();
        }

        // Si no estaba, lo insertamos al final
        listaTitulos.insertarFinal(tNuevo);
    }
    
}
