/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import MainClass.Resumen;

public class HashTable {

    private Lista[] tabla;
    private int capacidad;
    private int numElementos; // NUEVO: para saber cuántos resúmenes hay

    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        tabla = new Lista[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new Lista();
        }
        this.numElementos = 0;
    }

    private int hash(String clave) {
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (h * 31 + clave.charAt(i)) % capacidad;
        }
        return h;
    }

    public Lista[] getTabla() {
        return tabla;
    }

    public void setTabla(Lista[] tabla) {
        this.tabla = tabla;
    }

    /**
     * Inserta un resumen en la tabla.Valida que no exista previamente (misma
     * clave: título).
     *
     * @param r
     * @return
     */
    public boolean insertar(Resumen r) {
        int indice = hash(r.getTitulo());
        Lista lista = tabla[indice];

        for (int i = 0; i < lista.getSize(); i++) {
            Resumen existente = (Resumen) lista.getValor(i);
            if (existente.getTitulo().equalsIgnoreCase(r.getTitulo())) {
                return false; // YA EXISTE
            }
        }

        lista.insertarInicio(r);
        numElementos++;
        return true;
    }

    /**
     * Busca un resumen por título.
     */
    public Resumen buscar(String titulo) {
        int indice = hash(titulo);
        Lista lista = tabla[indice];

        for (int i = 0; i < lista.getSize(); i++) {
            Resumen r = (Resumen) lista.getValor(i);
            if (r.getTitulo().equalsIgnoreCase(titulo)) {
                return r;
            }
        }
        return null;
    }

    public int getNumElementos() {
        return numElementos;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Lista getListaEnIndice(int indice) {
        if (indice < 0 || indice >= capacidad) {
            return null;
        }
        return tabla[indice];
    }

    public void mostrarTabla() {
        for (int i = 0; i < capacidad; i++) {
            System.out.println("Índice " + i + ": ");
            tabla[i].mostrar();
        }
    }

}
