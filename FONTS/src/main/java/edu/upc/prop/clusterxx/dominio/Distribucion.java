package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>Distribucion</b>.
 * <p>
 * Una distribución se identifica por un ID y está formada por una lista de productos.
 * Estos productos pueden estar ordenados según un algoritmo específico, y la lista ordenada
 * tiene una afinidad total asociada.
 * <p>
 * La clase contiene funciones básicas para gestionar las distribuciones, incluyendo:
 * <ul>
 *     <li>Añadir y eliminar productos de la distribución.</li>
 *     <li>Ordenar la lista de productos utilizando diferentes algoritmos.</li>
 *     <li>Modificar el orden de la lista ordenada.</li>
 *     <li>Calcular la afinidad total de la distribución.</li>
 * </ul>
 */
public class Distribucion {
    private final String id;
    private int afinidadTotal;
    private final ArrayList<Producto> listaProductos;
    private ArrayList<Producto> listaOrdenada;
    private transient Algoritmo estrategia;

    /**
     * Coloca un producto en una posición específica de la lista ordenada.
     *
     * <p><b>Pre:</b> Un entero {@code i} que representa la posición donde mover un producto y el producto {@code p} en cuestión.</p>
     * <p><b>Post:</b> La listaOrdenada tendrá el producto {@code p} en la posición {@code i} indicada.</p>
     *
     * @param i La posición donde se moverá el producto (0-based index).
     * @param p El producto que se colocará en la posición indicada.
     */
    private void colocarProd(int i, Producto p) {
        listaOrdenada.add(i, p);
    }

    /**
     * Calcula la afinidad total de los productos en la lista ordenada.
     *
     * <p><b>Pre:</b> La listaOrdenada no está vacía.</p>
     * <p><b>Post:</b> Calcula y actualiza el valor de afinidad total basado en las relaciones entre productos.</p>
     */
    private void calAfinidadTotal() {
        Afinidad a;
        for (int i = 0; i < listaOrdenada.size() - 1; ++i) {
            Producto p1 = listaOrdenada.get(i);
            Producto p2 = listaOrdenada.get(i + 1);
            a = new Afinidad(p1, p2);
            this.afinidadTotal += a.getValor();
        }
        a = new Afinidad(this.listaOrdenada.get(0), this.listaOrdenada.get(listaOrdenada.size() - 1));
        this.afinidadTotal += a.getValor();
    }

    /**
     * Determina el tipo de algoritmo asociado a la distribución.
     *
     * <p><b>Pre:</b> Ninguna.</p>
     * <p><b>Post:</b> Devuelve un entero diferente (1, 2, 3) en función del algoritmo asociado a la distribución.
     * Si no se ha definido un algoritmo, devuelve 0.</p>
     *
     * @return Un entero que representa el algoritmo:
     *         <ul>
     *             <li>1: AlgoritmoBruto</li>
     *             <li>2: AlgoritmoAproximacion</li>
     *             <li>3: AlgoritmoGreedy</li>
     *         </ul>
     */
    private int esAlgoritmo() {
        if (estrategia instanceof AlgoritmoBruto) return 1;
        else if (estrategia instanceof AlgoritmoAproximacion) return 2;
        else if (estrategia instanceof AlgoritmoGreedy) return 3;
        else return 0;
    }

    /**
     * Busca un producto en la lista ordenada por su nombre.
     *
     * <p><b>Pre:</b> Un string que pertenece a un producto de la listaOrdenada de la distribución.</p>
     * <p><b>Post:</b> Devuelve el producto con el nombre indicado de la listaOrdenada. Si este no se encuentra, devuelve {@code null}.</p>
     *
     * @param nombre El nombre del producto a buscar.
     * @return El objeto {@code Producto} si se encuentra en la lista ordenada; de lo contrario, {@code null}.
     */
    private Producto buscarEnLista(String nombre) {
        for (Producto p : this.listaOrdenada) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Añade un producto a la distribución.
     *
     * <p><b>Pre:</b> Recibe un producto {@code p}.</p>
     * <p><b>Post:</b> Añade el producto a la distribución si no está ya presente.</p>
     *
     * @param p El producto a añadir.
     */
    public void anadirProductoDist(Producto p) {
        if (!this.listaProductos.contains(p)) {
            this.listaProductos.add(p);
        } else {
            System.out.println("El producto ya está en la distribución");
        }
    }

    /**
     * Elimina un producto de la distribución.
     *
     * <p><b>Pre:</b> Recibe un producto {@code p}.</p>
     * <p><b>Post:</b> Elimina el producto de la distribución si está presente.</p>
     *
     * @param p El producto a eliminar.
     */
    public void eliminarProductoDist(Producto p) {
        if (this.listaProductos.contains(p)) {
            this.listaProductos.remove(p);
        } else {
            System.out.println("El producto no está en la distribución");
        }
    }

    /**
     * Elimina un producto de la lista ordenada de la distribución.
     *
     * <p><b>Pre:</b> Un producto {@code p}.</p>
     * <p><b>Post:</b> Elimina el producto indicado de la listaOrdenada.</p>
     *
     * @param p El producto a eliminar de la lista ordenada.
     */
    public void eliminarProductoDistOrdenada(Producto p) {
        this.listaOrdenada.remove(p);
    }

    /**
     * Ordena la distribución utilizando el algoritmo especificado.
     *
     * <p><b>Pre:</b> Un entero {@code algoritmo} que representa el algoritmo: 1, 2 o 3.</p>
     * <p><b>Post:</b> Cambia el algoritmo asociado a la distribución por el indicado por el entero
     * y ordena la distribución según este.</p>
     *
     * @param algoritmo Un entero que representa el algoritmo a utilizar:
     *                  <ul>
     *                      <li>1: AlgoritmoBruto</li>
     *                      <li>2: AlgoritmoAproximacion</li>
     *                      <li>3: AlgoritmoGreedy</li>
     *                  </ul>
     */
    public void ordenar(int algoritmo) {
        if (esAlgoritmo() != algoritmo) setEstrategia(algoritmo);
        if (estrategia != null) {
            listaOrdenada = estrategia.ordenar(listaProductos);
            calAfinidadTotal();
        }
    }

    /**
     * Coloca un producto en una posición específica de la lista ordenada basado en su nombre.
     *
     * <p><b>Pre:</b> Recibe un entero {@code i} y un string {@code nombreProducto} que pertenece al nombre de un producto.</p>
     * <p><b>Post:</b> Devuelve {@code true} si el producto indicado por el nombre existe; de lo contrario, {@code false}.
     *         Si el producto existe, lo coloca en la posición {@code i} de la lista ordenada, donde 1 es el primero.</p>
     *
     * @param nombreProducto El nombre del producto a mover.
     * @param i               La posición (1-based index) donde se colocará el producto.
     * @return {@code true} si el producto fue colocado exitosamente; {@code false} si el producto no existe en la lista ordenada.
     */
    public boolean colocarProdPosicio(String nombreProducto, int i) {
        Producto p = buscarEnLista(nombreProducto);
        if (p != null) {
            eliminarProductoDistOrdenada(p);
            colocarProd(i - 1, p); // Convertir a índice 0-based
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula y actualiza la afinidad total de la distribución.
     *
     * <p><b>Pre:</b> La listaOrdenada de la distribución no se encuentra vacía.</p>
     * <p><b>Post:</b> Calcula la afinidad total de la listaOrdenada de la distribución.</p>
     */
    public void calculaAfinidadTotal() {
        this.afinidadTotal = 0;
        calAfinidadTotal();
    }

    /**
     * Constructor de la clase <b>Distribucion</b>.
     *
     * <p><b>Pre:</b> Requiere un ID {@code id} y una lista de productos {@code lp} sin repetidos que estarán en la distribución.</p>
     * <p><b>Post:</b> Crea una nueva distribución con el ID y la lista de productos especificados.</p>
     *
     * @param id El identificador único de la distribución.
     * @param lp La lista de productos que formarán parte de la distribución. No debe contener productos duplicados.
     */
    public Distribucion(String id, ArrayList<Producto> lp) {
        this.id = id;
        this.listaProductos = lp;
        this.listaOrdenada = new ArrayList<>();
        this.afinidadTotal = 0;
    }

    /**
     * Establece la estrategia de ordenación basada en el algoritmo especificado.
     *
     * <p><b>Pre:</b> Un entero {@code algoritmo} que representa el algoritmo a asociar a la estrategia.</p>
     * <p><b>Post:</b> Cambia el algoritmo asociado a la distribución por el indicado por el entero.</p>
     *
     * @param algoritmo Un entero que representa el algoritmo a establecer:
     *                  <ul>
     *                      <li>1: AlgoritmoBruto</li>
     *                      <li>2: AlgoritmoAproximacion</li>
     *                      <li>3: AlgoritmoGreedy</li>
     *                  </ul>
     */
    public void setEstrategia(int algoritmo) {
        if (algoritmo == 1) this.estrategia = new AlgoritmoBruto();
        else if (algoritmo == 2) this.estrategia = new AlgoritmoAproximacion();
        else this.estrategia = new AlgoritmoGreedy();
    }

    /**
     * Obtiene el ID de la distribución.
     *
     * @return El identificador único de la distribución.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Obtiene la afinidad total de la distribución.
     *
     * <p><b>Pre:</b> La distribución debe contener más de un producto.</p>
     * <p><b>Post:</b> Devuelve el valor de afinidad total si hay más de un producto en la distribución;
     * de lo contrario, devuelve {@code 0}.</p>
     *
     * @return El valor de afinidad total o {@code 0} si la distribución contiene uno o ningún producto.
     */
    public int getAfinidadTotal() {
        if (listaProductos.size() > 1) return this.afinidadTotal;
        else return 0;
    }

    /**
     * Obtiene la lista de productos de la distribución.
     *
     * @return La lista de productos que forman parte de la distribución.
     */
    public ArrayList<Producto> getListaProductos() {
        return this.listaProductos;
    }

    /**
     * Obtiene la lista ordenada de productos de la distribución.
     *
     * @return La lista de productos ordenados según el algoritmo aplicado.
     */
    public ArrayList<Producto> getListaOrdenada() {
        return this.listaOrdenada;
    }
}
