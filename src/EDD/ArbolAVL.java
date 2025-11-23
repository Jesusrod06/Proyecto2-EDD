/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 * Árbol AVL que almacena cadenas de texto (autores o palabras clave) ordenadas
 * lexicográficamente (ignorando mayúsculas/minúsculas).
 *
 * Cada nodo (NodoA) contiene:
 *  - la clave (autor/palabra clave)
 *  - una Lista de títulos de resúmenes asociados a esa clave
 */
public class ArbolAVL {

    private NodoA raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public NodoA getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Inserta una clave en el árbol y asocia un título de resumen a esa clave.
     * Si la clave ya existe, NO se crea un nodo nuevo, solo se agrega el título
     * a la lista de títulos del nodo correspondiente.
     *
     * @param clave          Autor o palabra clave.
     * @param tituloResumen  Título del resumen asociado.
     */
    public void insertar(String clave, String tituloResumen) {
        raiz = insertarRec(raiz, clave, tituloResumen);
    }

    /**
     * Inserción recursiva típica de BST + actualización de altura + balanceo.
     */
    private NodoA insertarRec(NodoA nodo, String clave, String tituloResumen) {
        if (nodo == null) {
            NodoA nuevo = new NodoA(clave);
            nuevo.agregarTitulo(tituloResumen);
            return nuevo;
        }

        int cmp = clave.compareToIgnoreCase(nodo.getClave());

        if (cmp < 0) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), clave, tituloResumen));
        } else if (cmp > 0) {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), clave, tituloResumen));
        } else {
            // clave repetida -> no insertamos nodo nuevo, sólo agregamos el título
            nodo.agregarTitulo(tituloResumen);
            return nodo;
        }

        // 1) Actualizar altura del nodo actual
        actualizarAltura(nodo);

        // 2) Balancear a partir de este nodo hacia arriba
        return balancear(nodo);
    }

    /**
     * Verifica si una clave existe en el árbol.
     *
     * @param clave
     * @return true si la clave existe, false en caso contrario.
     */
    public boolean contiene(String clave) {
        return buscar(clave) != null;
    }

    /**
     * Devuelve el nodo donde está la clave o null si no existe.
     *
     * @param clave
     * @return NodoA con esa clave o null.
     */
    public NodoA buscar(String clave) {
        return buscarRec(raiz, clave);
    }

    private NodoA buscarRec(NodoA nodo, String clave) {
        if (nodo == null) {
            return null;
        }

        int cmp = clave.compareToIgnoreCase(nodo.getClave());

        if (cmp == 0) {
            return nodo;
        } else if (cmp < 0) {
            return buscarRec(nodo.getIzquierdo(), clave);
        } else {
            return buscarRec(nodo.getDerecho(), clave);
        }
    }

    /**
     * Devuelve una Lista (tu lista enlazada) con todas las claves
     * en orden alfabético.
     *
     * @return Lista con las claves ordenadas.
     */
    public Lista obtenerClavesEnOrden() {
        Lista resultado = new Lista();
        inOrdenRec(raiz, resultado);
        return resultado;
    }

    private void inOrdenRec(NodoA nodo, Lista lista) {
        if (nodo == null) {
            return;
        }
        inOrdenRec(nodo.getIzquierdo(), lista);
        lista.insertarFinal(nodo.getClave());
        inOrdenRec(nodo.getDerecho(), lista);
    }

    /**
     * Muestra las claves por consola en orden (para pruebas rápidas).
     */
    public void mostrarInOrden() {
        Lista l = obtenerClavesEnOrden();
        l.mostrar();
    }

    /* ==================  LÓGICA AVL (ALTURA, BALANCEO, ROTACIONES)  ================== */

    private int altura(NodoA nodo) {
        return (nodo == null) ? 0 : nodo.getAltura();
    }

    private void actualizarAltura(NodoA nodo) {
        int alturaIzq = altura(nodo.getIzquierdo());
        int alturaDer = altura(nodo.getDerecho());
        nodo.setAltura(1 + (alturaIzq > alturaDer ? alturaIzq : alturaDer));
    }

    private int factorBalance(NodoA nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.getIzquierdo()) - altura(nodo.getDerecho());
    }

    private NodoA balancear(NodoA nodo) {
        int fb = factorBalance(nodo);

        // Caso Izquierda-Izquierda (LL)
        if (fb > 1 && factorBalance(nodo.getIzquierdo()) >= 0) {
            return rotacionDerecha(nodo);
        }

        // Caso Izquierda-Derecha (LR)
        if (fb > 1 && factorBalance(nodo.getIzquierdo()) < 0) {
            nodo.setIzquierdo(rotacionIzquierda(nodo.getIzquierdo()));
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Derecha (RR)
        if (fb < -1 && factorBalance(nodo.getDerecho()) <= 0) {
            return rotacionIzquierda(nodo);
        }

        // Caso Derecha-Izquierda (RL)
        if (fb < -1 && factorBalance(nodo.getDerecho()) > 0) {
            nodo.setDerecho(rotacionDerecha(nodo.getDerecho()));
            return rotacionIzquierda(nodo);
        }

        // Ya balanceado
        return nodo;
    }

    private NodoA rotacionDerecha(NodoA y) {
        NodoA x = y.getIzquierdo();
        NodoA T2 = x.getDerecho();

        // Rotación
        x.setDerecho(y);
        y.setIzquierdo(T2);

        // Actualizar alturas
        actualizarAltura(y);
        actualizarAltura(x);

        // Nueva raíz del subárbol
        return x;
    }

    private NodoA rotacionIzquierda(NodoA x) {
        NodoA y = x.getDerecho();
        NodoA T2 = y.getIzquierdo();

        // Rotación
        y.setIzquierdo(x);
        x.setDerecho(T2);

        // Actualizar alturas
        actualizarAltura(x);
        actualizarAltura(y);

        // Nueva raíz del subárbol
        return y;
    }
}