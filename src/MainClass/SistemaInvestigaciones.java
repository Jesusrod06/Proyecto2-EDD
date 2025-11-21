/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import EDD.HashTable;
import EDD.Lista;
import EDD.Nodo;

public class SistemaInvestigaciones {

    // Estructura principal
    private HashTable tablaResumenes;


    public SistemaInvestigaciones(int capacidadHash) {
        this.tablaResumenes = new HashTable(capacidadHash);
    }
    
    public HashTable getTablaResumenes() {
        return tablaResumenes;
    }

    public void setTablaResumenes(HashTable tablaResumenes) {
        this.tablaResumenes = tablaResumenes;
    }

    /**
     * Agrega un resumen al sistema usando la HashTable.Retorna true si se insertó, false si el título ya existía.
     * @param r
     * @return 
     */
    public boolean agregarResumen(Resumen r) {
        return tablaResumenes.insertar(r);
    }

    /**
     * Sobrecarga: crea el Resumen internamente y lo inserta.
     * @param titulo
     * @param autores
     * @param cuerpo
     * @param palabrasClave
     * @return 
     */
    public boolean agregarResumen(String titulo, String[] autores,
                                  String cuerpo, String[] palabrasClave) {

        Resumen r = new Resumen(titulo, autores, cuerpo, palabrasClave);
        return agregarResumen(r);
    }

    /**
     * Busca un resumen por título (usa directamente la HashTable).
     * @param titulo
     * @return 
     */
    public Resumen buscarResumenPorTitulo(String titulo) {
        return tablaResumenes.buscar(titulo);
    }

    /**
     * Verifica si existe un resumen con ese título.
     * @param titulo
     * @return 
     */
    public boolean existeResumen(String titulo) {
        return (tablaResumenes.buscar(titulo) != null);
    }

    /**
     * Devuelve cuántos resúmenes hay en todo el sistema.
     * @return 
     */
    public int cantidadResumenes() {
        return tablaResumenes.getNumElementos();
    }

    /**
     * Retorna un arreglo con TODOS los títulos de los resúmenes
     * almacenados en la HashTable (sin ordenar).
     * @return 
     */
    public String[] listarTitulos() {
        int n = tablaResumenes.getNumElementos();
        String[] titulos = new String[n];

        int indexGlobal = 0;
        int capacidad = tablaResumenes.getCapacidad();

        // Recorremos cada bucket de la tabla
        for (int i = 0; i < capacidad; i++) {
            Lista lista = tablaResumenes.getListaEnIndice(i);
            if (lista == null || lista.isEmpty()) {
                continue;
            }

            Nodo aux = lista.getpFirst();
            while (aux != null) {
                Resumen r = (Resumen) aux.getDato();
                titulos[indexGlobal] = r.getTitulo();
                indexGlobal++;
                aux = aux.getPnext();
            }
        }

        return titulos;
    }

    /**
     * Retorna una representación de texto simple de todos los resúmenes.(Útil para debug o para pasar a una interfaz).
     * @return
     */
    public String listarResumenesComoTexto() {
        StringBuilder sb = new StringBuilder();

        int capacidad = tablaResumenes.getCapacidad();
        for (int i = 0; i < capacidad; i++) {
            Lista lista = tablaResumenes.getListaEnIndice(i);
            if (lista == null || lista.isEmpty()) {
                continue;
            }

            Nodo aux = lista.getpFirst();
            while (aux != null) {
                Resumen r = (Resumen) aux.getDato();
                sb.append(r.toString()).append("\n------------------------\n");
                aux = aux.getPnext();
            }
        }

        return sb.toString();
    }
}

