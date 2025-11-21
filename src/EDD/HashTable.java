/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import MainClass.Resumen;

public class HashTable {

    private Lista[] tabla;
    private int capacidad;

    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        tabla = new Lista[capacidad];

        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new Lista(); // Cada posición inicia con una lista vacía
        }
    }

    private int hash(String clave) {
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (h * 31 + clave.charAt(i)) % capacidad;
        }
        return h;
    }

    /**
     * Inserta un resumen en la tabla. Valida que no exista previamente.
     */
    public boolean insertar(Resumen r) {
        int indice = hash(r.getTitulo());
        Lista lista = tabla[indice];

        // verificar si ya existe (comparar por título)
        for (int i = 0; i < lista.getSize(); i++) {
            Resumen existente = (Resumen) lista.getValor(i);
            if (existente.getTitulo().equalsIgnoreCase(r.getTitulo())) {
                return false; // YA EXISTE
            }
        }

        lista.insertarInicio(r);
        return true;
    }

    /**
     * Busca un resumen por título en O(1) promedio.
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
        return null; // no encontrado
    }

    /**
     * Muestra TODA la tabla (solo para depuración).
     */
    public void mostrarTabla() {
        for (int i = 0; i < capacidad; i++) {
            System.out.println("Índice " + i + ": ");
            tabla[i].mostrar();
        }
    }

}
