/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

/**
 * Representa un resumen científico que será almacenado dentro de la HashTable.
 */
public class Resumen {

    private String titulo;          // Clave principal
    private String[] autores;       // Lista de autores
    private String cuerpo;          // Texto completo del resumen
    private String[] palabrasClave; // Palabras clave del artículo

    public Resumen(String titulo, String[] autores, String cuerpo, String[] palabrasClave) {
        this.titulo = titulo;
        this.autores = autores;
        this.cuerpo = cuerpo;
        this.palabrasClave = palabrasClave;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String[] getAutores() {
        return autores;
    }

    public void setAutores(String[] autores) {
        this.autores = autores;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String[] getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String[] palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autores: ");
        for (String a : autores) {
            sb.append(a).append(", ");
        }
        sb.append("\n");
        sb.append("Palabras clave: ");
        for (String p : palabrasClave) {
            sb.append(p).append(", ");
        }
        sb.append("\n\n");
        sb.append("Resumen: ").append(cuerpo).append("\n");

        return sb.toString();
    }
}
