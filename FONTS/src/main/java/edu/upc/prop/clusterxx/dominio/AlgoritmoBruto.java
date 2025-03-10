package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>AlgoritmoBruto</b> que implementa la interfaz <b>Algoritmo</b>.
 * <p>
 * Ordena una lista de productos siguiendo un algoritmo de <i>backtracking</i> (fuerza bruta), 
 * explorando todas las permutaciones posibles y eligiendo aquella que maximize la afinidad total.
 */
public class AlgoritmoBruto implements Algoritmo {

    /**
     * Lista de productos en el orden que maximiza la afinidad.
     */
    private ArrayList<Producto> listaOrdenada;
    /**
     * Valor máximo de afinidad encontrado.
     */
    private int afinidadTotal;

    /**
     * Función recursiva de <i>backtracking</i> que explora todas las permutaciones de la lista de productos
     * en búsqueda de la que maximice la afinidad total.
     * 
     * Precondición: <b>listaAux</b> contiene los productos a permutar,
     *               <b>repes</b> es una lista booleana para marcar qué productos ya se han añadido,
     *               <b>listaAux2</b> mantiene la permutación actual,
     *               <b>i</b> es el nivel de profundidad en la recursión.
     * Postcondición: Al completar la exploración, <b>listaOrdenada</b> contiene la permutación con la afinidad total más alta.
     *
     * @param listaAux  Lista de productos original.
     * @param repes     Lista booleana para indicar si un producto ya está en la permutación actual.
     * @param listaAux2 Permutación de productos en construcción.
     * @param i         Nivel de profundidad de la recursión (índice actual).
     */
    private void backtracking(ArrayList<Producto> listaAux, 
                              ArrayList<Boolean> repes, 
                              ArrayList<Producto> listaAux2, 
                              int i) {

        if (i == listaAux.size()) {
            // Se ha construido una permutación completa, calculamos la afinidad
            int afinidadTotalMax = 0;
            for (int j = 0; j < listaAux2.size() - 1; ++j) {
                Producto p = listaAux2.get(j);
                Producto p2 = listaAux2.get(j + 1);
                Afinidad a = new Afinidad(p, p2);
                afinidadTotalMax += a.getValor();
            }
            // Añadimos la afinidad entre el primer y el último producto (distribución circular)
            Producto primero = listaAux2.get(0);
            Producto ultimo = listaAux2.get(listaAux2.size() - 1);
            Afinidad a = new Afinidad(primero, ultimo);
            afinidadTotalMax += a.getValor();

            // Actualizamos la mejor afinidad y la mejor permutación si es necesario
            if (afinidadTotalMax > afinidadTotal || afinidadTotal == 0) {
                afinidadTotal = afinidadTotalMax;
                listaOrdenada = new ArrayList<>(listaAux2);
            }
        } else {
            // Probamos a colocar cada producto en la posición i
            for (int j = 0; j < listaAux.size(); ++j) {
                if (!repes.get(j)) {
                    listaAux2.add(listaAux.get(j));
                    repes.set(j, true);

                    backtracking(listaAux, repes, listaAux2, i + 1);

                    // Deshacemos los cambios para volver al estado anterior
                    repes.set(j, false);
                    listaAux2.remove(listaAux.get(j));
                }
            }
        }
    }

    /**
     * Ordena la lista de productos según la afinidad total usando <i>backtracking</i>.
     * 
     * Precondición: <b>listaProductos</b> no está vacía.
     * Postcondición: Se devuelve una lista de productos ordenada de modo que la afinidad total se maximice.
     *
     * @param listaProductos Lista de productos a ordenar.
     * @return Lista de productos en el orden que maximiza la afinidad total.
     */
    @Override
    public ArrayList<Producto> ordenar(ArrayList<Producto> listaProductos) {
        this.listaOrdenada = new ArrayList<>();
        this.afinidadTotal = 0;

        // Copia de la lista de productos
        ArrayList<Producto> listaAux = new ArrayList<>(listaProductos);
        // Lista en construcción para la permutación
        ArrayList<Producto> listaAux2 = new ArrayList<>();
        // Lista booleana para marcar los productos ya usados
        ArrayList<Boolean> repes = new ArrayList<>();

        // Inicializamos la lista de marcados a false
        for (int i = 0; i < listaAux.size(); ++i) {
            repes.add(false);
        }

        // Llamamos a la función recursiva de backtracking
        backtracking(listaAux, repes, listaAux2, 0);

        return this.listaOrdenada;
    }
}
