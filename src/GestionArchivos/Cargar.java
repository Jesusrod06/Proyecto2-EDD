/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.HashTable;
import MainClass.Resumen;
import javax.swing.JOptionPane;

public class Cargar {

    private HashTable tabla;
    private String txt;

    public Cargar(HashTable tabla) {
        this.tabla = tabla;
        this.txt = null;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public HashTable getTabla() {
        return tabla;
    }

    public void setTabla(HashTable tabla) {
        this.tabla = tabla;
    }

    /**
     * Método principal para cargar un resumen desde el String txt.
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
            
            // TÍTULO
            String titulo = lineas[indice].trim();
            indice++;

            //LECTURA DE AUTORES
            if (!lineas[indice].trim().equalsIgnoreCase("Autores")) {
                JOptionPane.showMessageDialog(null, 
                    "Error: No se encontró la sección 'Autores'.");
                return;
            }
            indice++;

            // leer autores hasta la palabra "Resumen"
            java.util.List<String> autoresTemp = new java.util.ArrayList<>();

            while (indice < lineas.length &&
                   !lineas[indice].trim().equalsIgnoreCase("Resumen")) {

                String lineaAutor = lineas[indice].trim();
                if (!lineaAutor.isEmpty()) {
                    autoresTemp.add(lineaAutor);
                }
                indice++;
            }

            if (indice >= lineas.length) {
                JOptionPane.showMessageDialog(null,
                    "Error: No se encontró la sección 'Resumen'.");
                return;
            }

            // saltar la palabra "Resumen"
            indice++;

            // LECTURA DEL CUERPO DEL RESUMEN
            StringBuilder cuerpoSB = new StringBuilder();

            // Leer hasta encontrar "Palabras claves:"
            while (indice < lineas.length &&
                   !lineas[indice].toLowerCase().startsWith("palabras claves")) {

                cuerpoSB.append(lineas[indice]).append(" ");
                indice++;
            }

            String cuerpo = cuerpoSB.toString().trim();

            if (indice >= lineas.length) {
                JOptionPane.showMessageDialog(null, 
                    "Error: No se encontró la sección 'Palabras claves'.");
                return;
            }

            // LECTURA DE PALABRAS CLAVES
            String lineaPC = lineas[indice].trim();
            if (!lineaPC.toLowerCase().startsWith("palabras claves")) {
                JOptionPane.showMessageDialog(null, 
                    "Error: línea de palabras clave no válida.");
                return;
            }

            String listaPC = lineaPC.substring(lineaPC.indexOf(":") + 1).trim();

            String[] palabrasClave = listaPC.split(",");

            for (int i = 0; i < palabrasClave.length; i++) {
                palabrasClave[i] = palabrasClave[i].trim();
            }

            // convertir a arreglo los autores
            String[] autores = autoresTemp.toArray(
                    new String[autoresTemp.size()]
            );

            //CREACIÓN DEL OBJETO RESUMEN
            Resumen resumen = new Resumen(titulo, autores, cuerpo, palabrasClave);

            //INSERTAR EN LA HASH TABLE
            boolean insertado = tabla.insertar(resumen);

            if (!insertado) {
                JOptionPane.showMessageDialog(null, 
                    "Error: este resumen ya existe en el sistema.");
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
