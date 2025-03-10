package edu.upc.prop.clusterxx.dominio;

/**
 * Clase <b>Matriz</b>.
 * <p>
 * Se trata de una clase singleton que contiene una matriz.
 * La matriz que se guarda en esta clase es la misma que la de Utils, para así ahorrar consultas innecesarias a la persistencia.
 * La clase contiene las funciones para cargar y leer la matriz.
 */
public class Matriz {
    private int[][] matriz;

    /**
     * Declaración de instancia de matriz.
     */
    private static Matriz instance;

    /**
     * Obtiene la instancia única de la clase {@code Matriz}.
     *
     * @return La instancia única de {@code Matriz}.
     */
    public static Matriz getInstance() {
        if (instance == null) {
            instance = new Matriz();
        }
        return instance;
    }

    /**
     * Establece la matriz de afinidades.
     *
     * <p><b>Pre:</b> Recibe una matriz de enteros {@code matriz}.</p>
     * <p><b>Post:</b> La matriz de la clase es igual a la matriz entrante.</p>
     *
     * @param matriz La matriz de enteros a establecer.
     */
    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    /**
     * Obtiene la matriz de afinidades.
     *
     * @return La matriz de enteros almacenada en la clase.
     */
    public int[][] getMatriz() {
        return this.matriz;
    }
}
