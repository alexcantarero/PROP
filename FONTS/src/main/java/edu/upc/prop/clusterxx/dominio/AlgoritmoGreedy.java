package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>AlgoritmoGreedy</b> que implementa la interfaz <b>Algoritmo</b>.
 * <p>
 * Ordena una lista de productos siguiendo un enfoque <i>greedy</i> para maximizar 
 * la afinidad entre ellos.
 */
public class AlgoritmoGreedy implements Algoritmo {

    /**
     * Aplica una estrategia <i>greedy</i> para ordenar la lista de productos.
     * 
     * Precondición: 
     *  - <b>listaProductos</b> no está vacía.
     * Postcondición: 
     *  - Devuelve una nueva lista de productos ordenada, en la que se elige en cada paso 
     *    el producto siguiente con la mayor afinidad respecto al producto actual.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Lista de productos ordenada utilizando el criterio <i>greedy</i>.
     */
    private ArrayList<Producto> greedy(ArrayList<Producto> listaProductos) {
        ArrayList<Producto> listaOrdenada = new ArrayList<>();
        ArrayList<Producto> listaAux = new ArrayList<>(listaProductos);
        // Suponiendo que getFirst() es un método definido en la estructura de listaAux
        // Ajusta según tu implementación real (por ejemplo, listaAux.get(0) en ArrayList).
        Producto p = listaAux.get(0);

        while (!listaAux.isEmpty()) {
            int afinidadMax = -1;
            int pos = 0;
            for (int j = 0; j < listaAux.size(); ++j) {
                Producto p2 = listaAux.get(j);
                Afinidad a = new Afinidad(p, p2);
                if (a.getValor() > afinidadMax && !p.equals(p2)) {
                    afinidadMax = a.getValor();
                    pos = j;
                }
            }
            listaOrdenada.add(p);
            Producto auxP = p;
            p = listaAux.get(pos);
            listaAux.remove(auxP);
        }
        // Añadir afinidad entre el primer y el último producto
        // Ajusta si necesitas usar get(0) y get(size()-1) en lugar de getFirst()/getLast()
        Producto primero = listaOrdenada.get(0);
        Producto ultimo = listaOrdenada.get(listaOrdenada.size() - 1);
        Afinidad a = new Afinidad(primero, ultimo);

        return listaOrdenada;
    }

    /**
     * Método que implementa la operación <b>ordenar</b> de la interfaz <b>Algoritmo</b> 
     * utilizando el criterio <i>greedy</i>.
     * 
     * Precondición: 
     *  - <b>listaProductos</b> no está vacía.
     * Postcondición: 
     *  - Devuelve una lista de productos ordenada según un criterio <i>greedy</i> 
     *    para maximizar la afinidad.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Lista de productos ordenada tras aplicar el algoritmo <i>greedy</i>.
     */
    @Override
    public ArrayList<Producto> ordenar(ArrayList<Producto> listaProductos) {
        return greedy(listaProductos);
    }
}
