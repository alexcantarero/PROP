package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>Afinidad</b>.
 * <p>
 * Se encarga de calcular la afinidad entre dos productos, basándose en la relación entre los atributos
 * de tipo <b>Tipo</b> y <b>Contexto</b> que ambos comparten.
 */
public class Afinidad {
    /**
     * Valor numérico que representa la afinidad calculada entre dos productos.
     */
    private final int valor;

    /**
     * Matriz de afinidades, que se obtiene de la clase <b>Matriz</b>.
     */
    private int[][] matriz;

    /**
     * Calcula la afinidad entre los atributos de dos productos.
     * <p>
     * Precondición: Se reciben dos listas de <b>Tipo</b> (atrA, atrB) y dos listas de <b>Contexto</b> (contA, contB) 
     * para los dos productos.
     * Postcondición: Devuelve un entero que representa la afinidad basada en sus tipos y contextos.
     *
     * @param atrA  Lista de tipos del primer producto.
     * @param atrB  Lista de tipos del segundo producto.
     * @param contA Lista de contextos del primer producto.
     * @param contB Lista de contextos del segundo producto.
     * @return Entero que representa la afinidad entre ambos productos.
     */
    private int calAfinidad(ArrayList<Tipo> atrA, ArrayList<Tipo> atrB, ArrayList<Contexto> contA, ArrayList<Contexto> contB) {
        int v = 0;
        int count = 0;

        // Cálculo de similitud en los atributos Tipo
        for (Tipo tA : atrA) {
            for (Tipo tB : atrB) {
                int x = tA.getId();
                int y = tB.getId();
                v += matriz[x][y];
                ++count;
            }
        }
        v = v / count;  // Promedio de afinidades entre todos los pares de tipos

        // Cálculo de similitud en los atributos Contexto
        for (Contexto c : contA) {
            for (Contexto c2 : contB) {
                if (c2.getNombre().equals(c.getNombre())) {
                    v += 20;
                }
            }
        }

        if (v < 0) {
            throw new IllegalArgumentException("El valor de la afinidad debe ser al menos 0.");
        }
        return v;
    }

    /**
     * Calcula la afinidad entre dos listas de tipos y dos listas de contextos.
     * <p>
     * Precondición: Las listas atrA, atrB, contA y contB pueden ser vacías o contener atributos (ya que 
     *               un producto puede tener de 1 a 4 atributos, según la lógica del sistema).
     * Postcondición: Retorna el resultado entero de la afinidad calculada.
     *
     * @param atrA  Lista de tipos del primer producto.
     * @param atrB  Lista de tipos del segundo producto.
     * @param contA Lista de contextos del primer producto.
     * @param contB Lista de contextos del segundo producto.
     * @return Entero que representa la afinidad calculada.
     */
    public int calculoAfinidad(ArrayList<Tipo> atrA, ArrayList<Tipo> atrB, ArrayList<Contexto> contA, ArrayList<Contexto> contB) {
        return this.calAfinidad(atrA, atrB, contA, contB);
    }

    /**
     * Constructor de <b>Afinidad</b> para dos productos.
     * <p>
     * Precondición: Ambos productos existen en el sistema y la clase <b>Matriz</b> 
     *               ya tiene cargada la matriz de afinidad.
     * Postcondición: Se calcula y almacena el valor de afinidad entre ambos productos.
     *
     * @param a Primer producto.
     * @param b Segundo producto.
     */
    public Afinidad(Producto a, Producto b) {
        ArrayList<Tipo> atrA = a.getAtributosT();
        ArrayList<Tipo> atrB = b.getAtributosT();
        ArrayList<Contexto> contA = a.getAtributosC();
        ArrayList<Contexto> contB = b.getAtributosC();

        Matriz mat = Matriz.getInstance();
        this.matriz = mat.getMatriz();
        this.valor = calculoAfinidad(atrA, atrB, contA, contB);
    }

    /**
     * Obtiene el valor de afinidad calculado para los dos productos.
     *
     * @return Entero que representa la afinidad entre los productos.
     */
    public int getValor() {
        return valor;
    }
}
