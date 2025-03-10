package edu.upc.prop.clusterxx.persistencia.clases;

/**
 * Clase Utils.
 * <p>
 * Guarda una matriz de afinidades que se cargará en la capa de dominio 
 * para representar relaciones o compatibilidades entre distintos tipos de productos o atributos.
 */
public class Utils {

    /**
     * Instancia única de la clase Utils (patrón singleton).
     */
    private static final Utils instance = new Utils();

    /**
     * Matriz de afinidades/compatibilidades. 
     * Cada posición (i, j) indica un valor de compatibilidad o afinidad 
     * entre el atributo o tipo i y el atributo o tipo j.
     */
    private final int[][] matriz = {
            {100, 20,  5, 85, 80,  0, 50, 65, 95,  0,  5},
            { 20,100,  5, 70, 20,  0, 25, 10, 95,  0,  5},
            {  5,  5,100, 90, 75, 85, 20, 60,  5, 85, 25},
            { 85, 70, 90,100, 75, 50,  5, 90, 80,  0,  5},
            { 80, 20, 75, 75,100, 75, 50, 75, 50, 60,  5},
            {  0,  0, 85, 50, 75,100, 20,  5, 20, 90,  5},
            { 50, 25, 20,  5, 50, 20,100,  5, 10, 20, 95},
            { 65, 10, 60, 90, 75,  5,  5,100, 90, 10,  5},
            { 95, 95,  5, 80, 50, 20, 10, 90,100, 25,  5},
            {  0,  0, 85,  0, 60, 90, 20, 10, 25,100,  5},
            {  5,  5, 25,  5,  5,  5, 95,  5,  5,  5,100}
    };

    /**
     * Devuelve la instancia única (singleton) de la clase Utils.
     * 
     * @return Instancia de Utils.
     */
    public static Utils getInstance() {
        return instance;
    }

    /**
     * Devuelve la matriz de afinidades.
     * 
     * Precondición: -
     * Postcondición: Se retorna la matriz que representa las compatibilidades 
     *                entre distintos tipos/atributos del sistema.
     *
     * @return La matriz de afinidades.
     */
    public int[][] getMatriz() {
        return matriz;
    }
}
