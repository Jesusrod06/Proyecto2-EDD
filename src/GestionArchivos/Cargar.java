/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.HashTable;
import MainClass.Resumen;
import MainClass.SistemaInvestigaciones;
import javax.swing.JOptionPane;

public class Cargar {

    private SistemaInvestigaciones sistema;
    private String txt;

    public Cargar(SistemaInvestigaciones sistema) {
        this.sistema = sistema;
        this.txt = null;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public SistemaInvestigaciones getSistema() {
        return sistema;
    }

    public void setSistema(SistemaInvestigaciones sistema) {
        this.sistema = sistema;
    }

    /**
     * Método principal para cargar un resumen desde el String txt.
     * Usa SistemaInvestigaciones para insertar en la HashTable
     * y actualizar los árboles AVL (autores y palabras clave).
     */
    public void cargarResumen() {
        try {
            if (txt == null || txt.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: el texto está vacío.");
                return;
            }

            String[] lineas = txt.split("\\r?\\n");
            if (lineas.length < 5) {
                JOptionPane.showMessageDialog(null,
                        "Error: Formato de resumen inválido. Muy pocas líneas.");
                return;
            }

            int indice = 0;

            // Saltar líneas vacías iniciales
            while (indice < lineas.length && lineas[indice].trim().isEmpty()) {
                indice++;
            }

            if (indice >= lineas.length) {
                JOptionPane.showMessageDialog(null,
                        "Error: no se encontró el título del trabajo.");
                return;
            }

            // TÍTULO = primera línea NO vacía
            String titulo = lineas[indice].trim();
            indice++;

            // Buscar la línea "Autores", saltando vacíos
            while (indice < lineas.length && lineas[indice].trim().isEmpty()) {
                indice++;
            }

            if (indice >= lineas.length
                    || !lineas[indice].trim().equalsIgnoreCase("Autores")) {
                JOptionPane.showMessageDialog(null,
                        "Error: No se encontró la sección 'Autores'.");
                return;
            }

            indice++; // saltar la palabra "Autores"

            // LECTURA DE AUTORES hasta encontrar "Resumen"
            // ignorando líneas vacías
            int inicioAutores = indice;
            int contadorAutores = 0;
            int j = indice;

            // PRIMER PASO: contar cuántos autores hay
            while (j < lineas.length
                    && !lineas[j].trim().equalsIgnoreCase("Resumen")) {

                String linea = lineas[j].trim();
                if (!linea.isEmpty()) {
                    contadorAutores++;
                }
                j++;
            }

            if (j >= lineas.length) {
                JOptionPane.showMessageDialog(null,
                        "Error: No se encontró la sección 'Resumen'.");
                return;
            }

            // SEGUNDO PASO: crear el arreglo de autores y llenarlo
            String[] autores = new String[contadorAutores];
            int k = 0;
            j = inicioAutores;

            while (j < lineas.length
                    && !lineas[j].trim().equalsIgnoreCase("Resumen")) {

                String linea = lineas[j].trim();
                if (!linea.isEmpty()) {
                    autores[k] = linea;
                    k++;
                }
                j++;
            }

            // j está en la línea "Resumen"
            indice = j + 1; // saltar la palabra "Resumen"

            // LECTURA DEL CUERPO DEL RESUMEN
            // Saltar líneas vacías justo después de "Resumen"
            while (indice < lineas.length && lineas[indice].trim().isEmpty()) {
                indice++;
            }

            StringBuilder cuerpoSB = new StringBuilder();

            // Leer hasta encontrar una línea que empiece con "Palabras claves"
            while (indice < lineas.length
                    && !lineas[indice].trim().toLowerCase().startsWith("palabras claves")) {

                String linea = lineas[indice].trim();
                if (!linea.isEmpty()) {
                    cuerpoSB.append(linea).append(" ");
                }
                indice++;
            }

            if (indice >= lineas.length) {
                JOptionPane.showMessageDialog(null,
                        "Error: No se encontró la sección 'Palabras claves'.");
                return;
            }

            String cuerpo = cuerpoSB.toString().trim();

            // LECTURA DE PALABRAS CLAVES
            // En la forma: "Palabras claves: a, b, c"
            String lineaPC = lineas[indice].trim();
            if (!lineaPC.toLowerCase().startsWith("palabras claves")) {
                JOptionPane.showMessageDialog(null,
                        "Error: línea de palabras clave no válida.");
                return;
            }

            int posDosPuntos = lineaPC.indexOf(':');
            if (posDosPuntos == -1 || posDosPuntos == lineaPC.length() - 1) {
                JOptionPane.showMessageDialog(null,
                        "Error: Formato de 'Palabras claves' inválido (falta ':').");
                return;
            }

            String listaPC = lineaPC.substring(posDosPuntos + 1).trim();
            String[] palabrasClaveTemp = listaPC.split(",");

            // Limpiar espacios en cada palabra clave
            for (int i2 = 0; i2 < palabrasClaveTemp.length; i2++) {
                palabrasClaveTemp[i2] = palabrasClaveTemp[i2].trim();
            }

            String[] palabrasClave = palabrasClaveTemp;

            // CREAR EL OBJETO RESUMEN
            Resumen resumen = new Resumen(titulo, autores, cuerpo, palabrasClave);

            // Insertar en el sistema:
            // - HashTable interna
            // - Árbol AVL de autores
            // - Árbol AVL de palabras clave
            boolean insertado = sistema.agregarResumen(resumen);

            if (!insertado) {
                JOptionPane.showMessageDialog(null,
                        "Error: este resumen ya existe en el sistema (título repetido).");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Resumen cargado correctamente:\n\n" + titulo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error inesperado al cargar el resumen: " + e.getMessage());
        }
    }

}