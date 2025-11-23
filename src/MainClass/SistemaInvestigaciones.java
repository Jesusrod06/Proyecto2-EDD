/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import EDD.ArbolAVL;
import EDD.HashTable;
import EDD.Lista;
import EDD.Nodo;
import EDD.NodoA;

public class SistemaInvestigaciones {

    // Estructura principal (búsqueda O(1) promedio por título)
    private HashTable tablaResumenes;

    // Índices balanceados
    private ArbolAVL arbolAutores;        // clave = autor, valores = títulos
    private ArbolAVL arbolPalabrasClave;  // clave = palabra clave, valores = títulos

    public SistemaInvestigaciones(int capacidadHash) {
        this.tablaResumenes = new HashTable(capacidadHash);
        this.arbolAutores = new ArbolAVL();
        this.arbolPalabrasClave = new ArbolAVL();
    }

    public HashTable getTablaResumenes() {
        return tablaResumenes;
    }

    public void setTablaResumenes(HashTable tablaResumenes) {
        this.tablaResumenes = tablaResumenes;
    }

    public ArbolAVL getArbolAutores() {
        return arbolAutores;
    }

    public void setArbolAutores(ArbolAVL arbolAutores) {
        this.arbolAutores = arbolAutores;
    }

    public ArbolAVL getArbolPalabrasClave() {
        return arbolPalabrasClave;
    }

    public void setArbolPalabrasClave(ArbolAVL arbolPalabrasClave) {
        this.arbolPalabrasClave = arbolPalabrasClave;
    }

    /**
     * Agrega un resumen al sistema usando la HashTable.Retorna true si se
     * insertó, false si el título ya existía.Además, si se inserta
     * correctamente, actualiza los AVL de autores y de palabras clave.
     *
     * @param r
     * @return
     */
    public boolean agregarResumen(Resumen r) {
        boolean insertado = tablaResumenes.insertar(r);

        if (insertado) {
            cargarEnAVL(r);
        }

        return insertado;
    }

    /**
     * Carga autores y palabras clave del resumen en los árboles AVL. Ahora el
     * árbol almacena: - clave (autor/palabra) - lista de títulos asociados a
     * esa clave
     */
    private void cargarEnAVL(Resumen r) {
        String titulo = r.getTitulo();

        // Cargar AUTOR(ES) -> arbolAutores.insertar(autor, tituloResumen)
        String[] autores = r.getAutores();
        if (autores != null) {
            for (int i = 0; i < autores.length; i++) {
                if (autores[i] != null) {
                    String autor = autores[i].trim();
                    if (!autor.isEmpty()) {
                        arbolAutores.insertar(autor, titulo);
                    }
                }
            }
        }

        // Cargar PALABRAS CLAVE -> arbolPalabrasClave.insertar(pc, tituloResumen)
        String[] pcs = r.getPalabrasClave();
        if (pcs != null) {
            for (int i = 0; i < pcs.length; i++) {
                if (pcs[i] != null) {
                    String pc = pcs[i].trim();
                    if (!pc.isEmpty()) {
                        arbolPalabrasClave.insertar(pc, titulo);
                    }
                }
            }
        }
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
     * Retorna una representación de texto simple de todos los resúmenes.
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
     *
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
     *
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

    /**
     * Devuelve autores ordenados alfabéticamente (claves del AVL de autores).
     */
    public Lista listarAutoresOrdenados() {
        return arbolAutores.obtenerClavesEnOrden();
    }

    /**
     * Devuelve palabras clave ordenadas alfabéticamente (claves del AVL de
     * palabras clave).
     *
     * @return
     */
    public Lista listarPalabrasClaveOrdenadas() {
        return arbolPalabrasClave.obtenerClavesEnOrden();
    }

    /**
     * ?Buscar investigaciones por palabra clave: Dada una palabra clave, usa el
     * AVL para encontrar el nodo y retorna la lista de títulos asociados.
     *
     * @param palabraClave
     * @return
     */
    public Lista buscarInvestigacionesPorPalabraClave(String palabraClave) {
        NodoA nodo = arbolPalabrasClave.buscar(palabraClave);
        if (nodo == null) {
            return new Lista(); // lista vacía
        }
        return nodo.getListaTitulos();
    }

    /**
     * Versión cómoda para GUI: devuelve un arreglo de títulos a partir de una
     * palabra clave.
     *
     * @param palabraClave
     * @return
     */
    public String[] buscarInvestigacionesPorPalabraClaveArray(String palabraClave) {
        Lista listaTitulos = buscarInvestigacionesPorPalabraClave(palabraClave);

        int n = listaTitulos.getSize();
        String[] res = new String[n];

        Nodo aux = listaTitulos.getpFirst();
        int i = 0;
        while (aux != null) {
            res[i] = (String) aux.getDato();
            i++;
            aux = aux.getPnext();
        }

        return res;
    }
}
