package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>Estanteria</b>.
 * <p>
 * Una estantería se identifica por un ID, tiene un número de estantes y una distribución asociada.
 * La clase contiene las funciones básicas para gestionar las estanterías, así como funciones para
 * modificar el número de estantes. También se encarga de comunicar la distribución asociada con
 * el controlador de dominio, haciendo las llamadas necesarias a esta cuando {@code CtrlDominio} lo
 * solicite.
 */
public class Estanteria {
    private final String id;
    private int numPrestatges;
    private Distribucion distribucion; // Lista para almacenar productos en la estantería

    /**
     * Mueve un producto a una posición específica dentro de la distribución.
     *
     * <p><b>Pre:</b> Recibe un {@code String} {@code productoMovido} que identifica al producto
     * y un entero {@code posicion} que representa la posición donde se desea mover el producto.</p>
     *
     * <p><b>Post:</b> Si el producto existe en la distribución, se mueve a la posición {@code posicion}
     * indicada y se recalcula la afinidad total. Si el producto no existe, se imprime un mensaje de error.</p>
     *
     * @param productoMovido El nombre del producto a mover.
     * @param posicion       La posición a la que se moverá el producto.
     */
    public void moverProducto(String productoMovido, int posicion) {
        boolean found = distribucion.colocarProdPosicio(productoMovido, posicion);
        if (found) {
            calcularAfinidad();
        } else {
            System.out.println("Error: el producto no existe");
        }
    }

    /**
     * Calcula la afinidad total de la distribución asociada.
     *
     * <p><b>Pre:</b> La distribución asociada no es {@code null}.</p>
     *
     * <p><b>Post:</b> Calcula y actualiza la afinidad total de la distribución.</p>
     */
    public void calcularAfinidad() {
        distribucion.calculaAfinidadTotal();
    }

    /**
     * Constructor de la clase <b>Estanteria</b>.
     *
     * <p><b>Pre:</b> Requiere un identificador único {@code id} y el número de estantes {@code numPrestatges}.
     * Si {@code id} es vacío, lanza una excepción.</p>
     *
     * <p><b>Post:</b> Crea una nueva instancia de {@code Estanteria} con el ID y número de estantes especificados.</p>
     *
     * @param id           El identificador único de la estantería.
     * @param numPrestatges El número de estantes que tendrá la estantería.
     * @throws Exception Si el {@code id} es vacío.
     */
    public Estanteria(String id, int numPrestatges) throws Exception {
        if(id.isEmpty()) throw new Exception("Error: el nombre de la estantería no puede estar vacío.");
        this.id = id;
        this.numPrestatges = numPrestatges;
    }

    //// GETTERS

    /**
     * Obtiene el ID de la estantería.
     *
     * @return El identificador único de la estantería.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el número de estantes de la estantería.
     *
     * @return El número de estantes.
     */
    public int getNumPrestatges() {
        return numPrestatges;
    }

    /**
     * Obtiene la afinidad total de la distribución asociada.
     *
     * <p><b>Pre:</b> La distribución asociada puede ser {@code null}.</p>
     *
     * <p><b>Post:</b> Devuelve la afinidad total si la distribución existe; de lo contrario, devuelve {@code 0}.</p>
     *
     * @return La afinidad total de la distribución o {@code 0} si no existe una distribución.
     */
    public int getAfinidadTotal() {
        if (distribucion == null) {
            return 0;
        }
        else return distribucion.getAfinidadTotal();
    }

    /**
     * Obtiene la lista de productos de la distribución asociada.
     *
     * <p><b>Pre:</b> La distribución asociada no es {@code null}.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de productos que forman parte de la distribución.</p>
     *
     * @return La lista de productos de la distribución.
     */
    public ArrayList<Producto> getListaProductos() {
        return distribucion.getListaProductos();
    }

    /**
     * Obtiene la distribución asociada a la estantería.
     *
     * @return La distribución asociada.
     */
    public Distribucion getDistribucion() {
        return distribucion;
    }

    /**
     * Obtiene la lista ordenada de productos de la distribución.
     *
     * <p><b>Pre:</b> La distribución asociada debe tener una lista ordenada.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de productos ordenados según el algoritmo aplicado.</p>
     *
     * @return La lista ordenada de productos.
     */
    public ArrayList<Producto> getListaOrdenada(){
        return distribucion.getListaOrdenada();
    }

    //// SETTERS

    /**
     * Modifica el número de estantes de la estantería.
     *
     * <p><b>Pre:</b> Recibe un entero {@code numPrestatges} que representa el nuevo número de estantes.
     * Este número debe estar entre 1 y 10.</p>
     *
     * <p><b>Post:</b> Actualiza el número de estantes de la estantería.</p>
     *
     * @param numPrestatges El nuevo número de estantes.
     */
    public void setNumPrestatges(int numPrestatges) {
        this.numPrestatges = numPrestatges;
    }

    /**
     * Crea una distribución con el ID y lista de productos proporcionados, utilizando el algoritmo especificado.
     *
     * <p><b>Pre:</b> Recibe una lista de productos {@code listaProductos}, un entero {@code algoritmo} que representa el algoritmo a usar
     * (1: AlgoritmoBruto, 2: AlgoritmoAproximacion, 3: AlgoritmoGreedy), y el ID de la estantería {@code this.id} que ya debe existir.</p>
     *
     * <p><b>Post:</b> Crea una nueva distribución con el ID y la lista de productos dados mediante el algoritmo especificado.
     * Si el algoritmo no es válido (no está entre 1 y 3), lanza una excepción.</p>
     *
     * @param listaProductos La lista de productos a incluir en la distribución.
     * @param algortimo       El algoritmo a utilizar para ordenar la distribución (1: Bruto, 2: Aproximación, 3: Greedy).
     * @throws Exception Si el algoritmo no es 1, 2 o 3.
     */
    public void crearDistribucion(ArrayList<Producto> listaProductos, int algortimo) throws Exception {
        distribucion = new Distribucion(this.id, listaProductos);
        if(algortimo < 1 || algortimo > 3) {
            throw new Exception("Error: el algoritmo ha de ser 1, 2 o 3.");
        }
        else {
            this.distribucion.ordenar(algortimo);
        }
    }

    //// CONSULTORAS

    /**
     * Verifica si la estantería tiene una distribución inicializada.
     *
     * <p><b>Pre:</b> Ninguna.</p>
     *
     * <p><b>Post:</b> Devuelve {@code true} si la estantería ha inicializado una distribución; de lo contrario, {@code false}.</p>
     *
     * @return {@code true} si existe una distribución; {@code false} en caso contrario.
     */
    public boolean existeDistribucion() {
        return distribucion != null;
    }
}
