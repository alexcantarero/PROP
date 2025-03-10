package edu.upc.prop.clusterxx.dominio;

import java.util.ArrayList;

/**
 * Clase <b>Producto</b>.
 * <p>
 * Un producto viene definido por un nombre y contiene una lista de atributos {@link Tipo} y {@link Contexto}.
 * La clase contiene las funciones básicas para el manejo de los productos, así como funciones para
 * modificar estas listas de atributos.
 */
public class Producto {
    private String nombre;
    private final ArrayList<Tipo> atributosT;
    private final ArrayList<Contexto> atributosC;

    /**
     * Establece los atributos del producto a partir de una lista de atributos proporcionada.
     *
     * <p><b>Pre:</b> Recibe una lista de atributos no vacía, sin duplicados y pertenecientes a la lista de atributos posibles.</p>
     *
     * <p><b>Post:</b> Añade los atributos de la lista {@code AtributosProducto} a la lista de tipos {@code atributosT}
     * o a la lista de contextos {@code atributosC} del producto según corresponda.</p>
     *
     * @param AtributosProducto La lista de atributos a asignar al producto.
     */
    private void setAtr(ArrayList<Atributo> AtributosProducto) {
        for (Atributo atributoSeleccionado : AtributosProducto) {
            // Si el atributo es de tipo "Tipo"
            if (atributoSeleccionado instanceof Tipo) {
                this.atributosT.add((Tipo) atributoSeleccionado);
            }
            // Si el atributo es de tipo "Contexto"
            else if (atributoSeleccionado instanceof Contexto) {
                this.atributosC.add((Contexto) atributoSeleccionado);
            }
        }
    }

    /**
     * Constructor de la clase <b>Producto</b>.
     *
     * <p><b>Pre:</b> Requiere un {@code String} {@code nombre} no vacío y una lista de atributos {@code AtributosProducto}
     * que no contenga duplicados y que pertenezcan a la lista de atributos posibles.</p>
     *
     * <p><b>Post:</b> Crea un objeto {@code Producto} con el nombre y los atributos especificados.</p>
     *
     * @param nombre            El nombre del producto. Debe ser único y no vacío.
     * @param AtributosProducto La lista de atributos a asignar al producto.
     * @throws Exception Si el {@code nombre} es vacío.
     */
    public Producto(String nombre, ArrayList<Atributo> AtributosProducto) throws Exception {
        if (nombre.isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío.");
        }
        this.nombre = nombre;

        // Inicialización de listas
        this.atributosT = new ArrayList<>();
        this.atributosC = new ArrayList<>();

        // Asignación de atributos
        this.setAtr(AtributosProducto);
    }

    /// GETTERS

    /**
     * Obtiene el nombre del producto.
     *
     * @return El nombre del producto.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Obtiene la lista de atributos de tipo {@link Tipo} del producto.
     *
     * @return La lista de atributos de tipo {@code Tipo}.
     */
    public ArrayList<Tipo> getAtributosT() {
        return this.atributosT;
    }

    /**
     * Obtiene la lista de atributos de tipo {@link Contexto} del producto.
     *
     * @return La lista de atributos de tipo {@code Contexto}.
     */
    public ArrayList<Contexto> getAtributosC() {
        return this.atributosC;
    }

    // SETTERS

    /**
     * Cambia el nombre del producto.
     *
     * <p><b>Pre:</b> Recibe un {@code String} {@code name} no vacío.</p>
     *
     * <p><b>Post:</b> Cambia el nombre del producto por el {@code String} recibido.</p>
     *
     * @param name El nuevo nombre para el producto.
     * @throws Exception Si el {@code name} es vacío.
     */
    public void cambiarNombre(String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío.");
        }
        this.nombre = name;
    }

    /**
     * Cambia los atributos del producto por una nueva lista de atributos.
     *
     * <p><b>Pre:</b> Recibe una lista de atributos no vacía, sin duplicados y existentes en la lista de atributos posibles.</p>
     *
     * <p><b>Post:</b> Cambia los atributos del producto por los de la lista {@code AtributosProducto}.
     * Si los atributos son los mismos que los actuales, no realiza ningún cambio.</p>
     *
     * @param AtributosProducto La nueva lista de atributos a asignar al producto.
     */
    public void changeAtr(ArrayList<Atributo> AtributosProducto) {
        // Si los atributos son los mismos, no hacemos nada
        if (AtributosProducto.equals(this.atributosC) && AtributosProducto.equals(this.atributosT)) return;
        this.atributosC.clear();
        this.atributosT.clear();
        setAtr(AtributosProducto);
    }
}
