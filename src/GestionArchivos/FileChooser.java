/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileChooser {
    private JFrame ventana;
    private String ruta = null;
    private String contenido = null;

    public FileChooser(JFrame ventana) {
        this.ventana = ventana;
    }

    public JFrame getVentana() {
        return ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    public String getRuta() {
        return ruta;
    }

    public String getContenido() {
        return contenido;
    }

    /**
     * Abre un JFileChooser, guarda la ruta y el contenido del archivo .txt
     * en los atributos internos.Si el usuario cancela, muestra un mensaje.
     * @throws java.io.IOException
     */
    public void fileChooser() throws IOException {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos TXT (*.txt)", "txt"));
        fc.setAcceptAllFileFilterUsed(false);

        int seleccion = fc.showOpenDialog(ventana);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();

            ruta = fichero.getAbsolutePath();
            contenido = Files.readString(fichero.toPath(), StandardCharsets.UTF_8);

        } else {
            JOptionPane.showMessageDialog(ventana, "No se ha seleccionado ningún archivo.");
            ruta = null;
            contenido = null;
        }
    }
}
