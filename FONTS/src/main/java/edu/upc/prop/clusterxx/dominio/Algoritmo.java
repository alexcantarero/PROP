package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Interfaz <b>Algoritmo</b>.
 * <p>
 * Representa el contrato para las clases que implementan diferentes estrategias
 * de ordenación de productos en una distribución.
 * Sus implementaciones concretas son:
 * <ul>
 *     <li>AlgoritmoAproximacion</li>
 *     <li>AlgoritmoBruto</li>
 *     <li>AlgoritmoGreedy</li>
 * </ul>
 */
public interface Algoritmo {

    /**
     * Ordena la lista de productos según la estrategia del algoritmo.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Una nueva lista de productos ordenada según la estrategia implementada.
     */
    ArrayList<Producto> ordenar(ArrayList<Producto> listaProductos);
}
