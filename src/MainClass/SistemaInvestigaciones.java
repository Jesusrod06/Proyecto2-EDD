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
     * Agrega un resumen al sistema usando la HashTable.Retorna true si se
     * insertó, false si el título ya existía.
     *
     * @param r
     * @return
     */
    public boolean agregarResumen(Resumen r) {
        return tablaResumenes.insertar(r);
    }

    /**
     * Sobrecarga: crea el Resumen internamente y lo inserta.
     *
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
     *
     * @param titulo
     * @return
     */
    public Resumen buscarResumenPorTitulo(String titulo) {
        return tablaResumenes.buscar(titulo);
    }

    /**
     * Verifica si existe un resumen con ese título.
     *
     * @param titulo
     * @return
     */
    public boolean existeResumen(String titulo) {
        return (tablaResumenes.buscar(titulo) != null);
    }

    /**
     * Devuelve cuántos resúmenes hay en todo el sistema.
     *
     * @return
     */
    public int cantidadResumenes() {
        return tablaResumenes.getNumElementos();
    }

    /**
     * Retorna un arreglo con TODOS los títulos de los resúmenes almacenados en
     * la HashTable (sin ordenar).
     *
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
     * Retorna una representación de texto simple de todos los resúmenes.(Útil
     * para debug o para pasar a una interfaz).
     *
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

    /**
     * Método privado de ayuda: cuenta cuántas veces aparece la palabraClave
     * como palabra completa (considerando letras) dentro del texto.
     */
    private int contarFrecuenciaPalabra(String texto, String palabraClave) {
        if (texto == null || palabraClave == null) {
            return 0;
        }

        String t = texto.toLowerCase();
        String p = palabraClave.toLowerCase();

        int n = t.length();
        int m = p.length();

        if (m == 0 || n < m) {
            return 0;
        }

        int contador = 0;

        for (int i = 0; i <= n - m; i++) {
            boolean coincide = true;

            for (int j = 0; j < m; j++) {
                if (t.charAt(i + j) != p.charAt(j)) {
                    coincide = false;
                    break;
                }
            }

            if (!coincide) {
                continue;
            }

            // Revisar borde anterior
            int antes = i - 1;
            boolean inicioOK = (antes < 0) || !Character.isLetter(t.charAt(antes));

            // Revisar borde posterior
            int despues = i + m;
            boolean finOK = (despues >= n) || !Character.isLetter(t.charAt(despues));

            if (inicioOK && finOK) {
                contador++;
            }
        }

        return contador;
    }

    /**
     * Analiza un Resumen concreto y devuelve el texto con: - Nombre del trabajo
     * - Autores - Cada palabra clave con su frecuencia en el cuerpo
     * @param r
     * @return 
     */
    public String analizarResumen(Resumen r) {
        if (r == null) {
            return "No se pudo analizar el resumen (objeto null).";
        }

        StringBuilder sb = new StringBuilder();

        // Nombre del trabajo
        sb.append("Nombre del trabajo:\n");
        sb.append(r.getTitulo()).append("\n\n");

        // Autores
        sb.append("Autores:\n");
        String[] autores = r.getAutores();
        if (autores != null && autores.length > 0) {
            for (int i = 0; i < autores.length; i++) {
                sb.append("  - ").append(autores[i]).append("\n");
            }
        } else {
            sb.append("  (No hay autores registrados)\n");
        }
        sb.append("\n");

        // Palabras clave y frecuencias
        sb.append("Palabras clave y frecuencias en el resumen:\n");

        String cuerpo = r.getCuerpo();
        String[] palabrasClave = r.getPalabrasClave();

        if (palabrasClave != null && palabrasClave.length > 0) {
            for (int i = 0; i < palabrasClave.length; i++) {
                String pc = palabrasClave[i];
                int frecuencia = contarFrecuenciaPalabra(cuerpo, pc);

                sb.append("  ")
                        .append(pc)
                        .append(": ")
                        .append(frecuencia)
                        .append(" vez");

                if (frecuencia != 1) {
                    sb.append("es");
                }

                sb.append(" que la palabra clave '")
                        .append(pc)
                        .append("' aparece en el resumen.\n");
            }
        } else {
            sb.append("  (No hay palabras clave registradas para este resumen)\n");
        }

        return sb.toString();
    }

    /**
     * Analiza un resumen a partir del título (usa la HashTable en O(1)
     * promedio).
     * @param titulo
     * @return 
     */
    public String analizarResumenPorTitulo(String titulo) {
        Resumen r = buscarResumenPorTitulo(titulo);

        if (r == null) {
            return "No se encontró ningún resumen con el título: " + titulo;
        }

        return analizarResumen(r);
    }
}
