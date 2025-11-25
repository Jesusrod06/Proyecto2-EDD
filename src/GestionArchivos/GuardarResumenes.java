/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.HashTable;
import EDD.Lista;
import EDD.Nodo;
import MainClass.Resumen;
import MainClass.SistemaInvestigaciones;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GuardarResumenes {

    private SistemaInvestigaciones sistema;

    public GuardarResumenes(SistemaInvestigaciones sistema) {
        this.sistema = sistema;
    }

    public SistemaInvestigaciones getSistema() {
        return sistema;
    }

    public void setSistema(SistemaInvestigaciones sistema) {
        this.sistema = sistema;
    }

    /**
     * Guarda TODOS los resúmenes en un archivo de texto, con el formato:
     *
     * Titulo 1
     *
     * Autores
     * Autor 1
     * Autor 2
     *
     * Resumen
     * (cuerpo del resumen, puede ser varias líneas)
     *
     * Palabras claves: pc1, pc2, pc3.
     *
     * (línea en blanco)
     * Titulo 2
     * ...
     *
     * @param nombreArchivo por ejemplo "resumenes.txt"
     */
    public void guardarEnTest(String nombreArchivo) {
        if (sistema == null || sistema.cantidadResumenes() == 0) {
            JOptionPane.showMessageDialog(
                null,
                "No hay resúmenes en el sistema para guardar."
            );
            return;
        }

        HashTable tabla = sistema.getTablaResumenes();

        StringBuilder sb = new StringBuilder();

        int capacidad = tabla.getCapacidad();

        // Recorremos todos los buckets de la HashTable
        for (int i = 0; i < capacidad; i++) {
            Lista lista = tabla.getListaEnIndice(i);

            if (lista == null || lista.isEmpty()) {
                continue;
            }

            Nodo aux = lista.getpFirst();
            while (aux != null) {
                Resumen r = (Resumen) aux.getDato();

                // Escribir un resumen en el StringBuilder
                escribirResumen(sb, r);

                // Separador entre trabajos (dos líneas en blanco)
                sb.append("\n\n");

                aux = aux.getPnext();
            }
        }

        // Archivo en carpeta src/test (ajusta si tu ruta es otra)
        File file = new File("src/test/" + nombreArchivo);

        // Aseguramos que el directorio exista
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.flush();

            JOptionPane.showMessageDialog(
                null,
                "Resúmenes guardados correctamente en:\n" + file.getAbsolutePath()
            );

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al guardar los resúmenes: " + e.getMessage()
            );
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // ignorar
                }
            }
        }
    }

    /**
     * Escribe UN resumen en el StringBuilder, usando el formato
     * compatible con la carga:
     *
     * Título
     *
     * Autores
     * Autor 1
     * Autor 2
     *
     * Resumen
     * (cuerpo)
     *
     * Palabras claves: pc1, pc2, pc3.
     */
    private void escribirResumen(StringBuilder sb, Resumen r) {
        // 1) Título
        sb.append(r.getTitulo() != null ? r.getTitulo() : "").append("\n\n");

        // 2) Autores
        sb.append("Autores\n");
        String[] autores = r.getAutores();
        if (autores != null) {
            for (int i = 0; i < autores.length; i++) {
                if (autores[i] != null) {
                    String a = autores[i].trim();
                    if (!a.isEmpty()) {
                        sb.append(a).append("\n");
                    }
                }
            }
        }
        sb.append("\n");

        // 3) Resumen
        sb.append("Resumen\n");
        String cuerpo = r.getCuerpo();
        if (cuerpo != null) {
            sb.append(cuerpo.trim()).append("\n");
        }
        sb.append("\n");

        // 4) Palabras claves
        sb.append("Palabras claves: ");
        String[] pcs = r.getPalabrasClave();
        boolean primera = true;

        if (pcs != null) {
            for (int i = 0; i < pcs.length; i++) {
                if (pcs[i] != null) {
                    String pc = pcs[i].trim();
                    if (!pc.isEmpty()) {
                        if (!primera) {
                            sb.append(", ");
                        }
                        sb.append(pc);
                        primera = false;
                    }
                }
            }
        }

        sb.append("."); // punto final de la línea de palabras clave
    }
}
