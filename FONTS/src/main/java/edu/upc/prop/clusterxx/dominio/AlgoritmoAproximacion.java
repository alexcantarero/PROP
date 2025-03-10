package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase <b>AlgoritmoAproximacion</b> que implementa la interfaz <b>Algoritmo</b>.
 * <p>
 * Su objetivo es ordenar una lista de productos siguiendo un algoritmo de 
 * <i>hill climbing</i> para maximizar la afinidad total de la distribución.
 */
public class AlgoritmoAproximacion implements Algoritmo {

    /**
     * Aplica el enfoque de <i>hill climbing</i> a la lista de productos.
     * <p>
     * Precondición: <b>listaProductos</b> no está vacía. 
     * Postcondición: Devuelve una lista ordenada de productos con una afinidad total 
     * elevada, resultado de los intentos y mejoras locales del algoritmo.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Una nueva lista de productos ordenada tras aplicar el algoritmo de <i>hill climbing</i>.
     */
    private ArrayList<Producto> hillclimbing(ArrayList<Producto> listaProductos) {
        Random random = new Random();
        int numIntentos;
        if (listaProductos.size() < 14) {
            numIntentos = listaProductos.size() * 3;
        } else {
            numIntentos = listaProductos.size() * 5;
        }

        ArrayList<Producto> mejorIntento = listaProductos;
        int afinidadMejorIntento = 0;

        for (int iIntentos = 0; iIntentos < numIntentos; ++iIntentos) {
            // Creación de una distribución inicial aleatoria
            ArrayList<Producto> listaOrdenada = new ArrayList<>();
            for (int i = 0; i < listaProductos.size(); ++i) {
                int pos = random.nextInt(listaOrdenada.size() + 1);
                listaOrdenada.add(pos, listaProductos.get(i));
            }

            // Cálculo de la afinidad total de la distribución inicial
            int afinidadTotalAux = 0;
            for (int i = 0; i < listaOrdenada.size() - 1; ++i) {
                Producto p1 = listaOrdenada.get(i);
                Producto p2 = listaOrdenada.get(i + 1);
                Afinidad a = new Afinidad(p1, p2);
                afinidadTotalAux += a.getValor();
            }
            // Añadir afinidad entre el primer y el último producto (distribución circular)
            Producto primero = listaOrdenada.get(0);  // Ajusta si utilizas otro tipo de lista
            Producto ultimo = listaOrdenada.get(listaOrdenada.size() - 1);
            Afinidad a = new Afinidad(primero, ultimo);
            afinidadTotalAux += a.getValor();

            // Búsqueda de mejoras locales (intercambios de productos)
            boolean mejora = true;
            ArrayList<Producto> mejor_solucion = new ArrayList<>(listaOrdenada);
            while (mejora) {
                mejora = false;
                for (int i = 0; i < listaOrdenada.size(); ++i) {
                    for (int j = i; j < listaOrdenada.size(); ++j) {
                        ArrayList<Producto> listaAux = new ArrayList<>(listaOrdenada);
                        if (i != j) {
                            // Intercambiamos los productos
                            Producto auxP1 = listaAux.get(i);
                            Producto auxP2 = listaAux.get(j);
                            listaAux.set(i, auxP2);
                            listaAux.set(j, auxP1);

                            int afinidadTotalAux2 = 0;
                            for (int k = 0; k < listaAux.size() - 1; ++k) {
                                Producto p3 = listaAux.get(k);
                                Producto p4 = listaAux.get(k + 1);
                                Afinidad a2 = new Afinidad(p3, p4);
                                afinidadTotalAux2 += a2.getValor();
                            }
                            // Añadimos la afinidad entre el primer y el último producto
                            Producto primero2 = listaAux.get(0);
                            Producto ultimo2 = listaAux.get(listaAux.size() - 1);
                            Afinidad a2 = new Afinidad(primero2, ultimo2);
                            afinidadTotalAux2 += a2.getValor();

                            // Si esta permutación mejora la afinidad, actualizamos
                            if (afinidadTotalAux2 > afinidadTotalAux) {
                                afinidadTotalAux = afinidadTotalAux2;
                                mejora = true;
                                mejor_solucion = listaAux;
                            }
                        }
                    }
                }
                listaOrdenada = mejor_solucion;
            }

            // Guardamos la mejor solución conseguida en todos los intentos
            if (afinidadTotalAux > afinidadMejorIntento) {
                afinidadMejorIntento = afinidadTotalAux;
                mejorIntento = listaOrdenada;
            }
        }
        return mejorIntento;
    }

    /**
     * Ordena la lista de productos aplicando el algoritmo de <i>hill climbing</i>.
     * <p>
     * Precondición: <b>listaProductos</b> no está vacía.
     * Postcondición: Devuelve una lista de productos ordenada según la afinidad.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Lista de productos ordenada que maximiza la afinidad.
     */
    @Override
    public ArrayList<Producto> ordenar(ArrayList<Producto> listaProductos) {
        return hillclimbing(listaProductos);
    }
}
