package edu.upc.prop.clusterxx.persistencia.controladores;

import edu.upc.prop.clusterxx.dominio.*;
import edu.upc.prop.clusterxx.persistencia.clases.ArchiusEstanterias;
import edu.upc.prop.clusterxx.persistencia.clases.ArchiusProductes;
import edu.upc.prop.clusterxx.persistencia.clases.Catalogo;
import edu.upc.prop.clusterxx.persistencia.clases.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que representa el controlador de persistencia (CtrlPersistencia).
 * 
 * Se encarga de recibir llamadas del controlador de dominio y comunicarse con las clases correspondientes
 * de la capa de persistencia. Principalmente realiza operaciones relacionadas con la lectura y escritura
 * de datos en ficheros (productos, estanterías, etc.).
 */
public class CtrlPersistencia {

    /**
     * Instancia encargada de gestionar los ficheros de estanterías.
     */
    private final ArchiusEstanterias archiusEstanterias;
    /**
     * Instancia encargada de gestionar los ficheros de productos.
     */
    private final ArchiusProductes archiusProductes;
    /**
     * Instancia que maneja el catálogo (productos, estanterías y atributos iniciales).
     */
    private final Catalogo catalogo;
    /**
     * Utilidad para gestión de matriz de afinidad u otras operaciones auxiliares.
     */
    private final Utils utils;

    /**
     * Única instancia de CtrlPersistencia (patrón singleton).
     */
    private static final CtrlPersistencia instance;

    static {
        try {
            instance = new CtrlPersistencia();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reinicia la persistencia, recargando el catálogo inicial.
     * 
     * Precondición: -
     * Postcondición: Se vacían las carpetas de productos y estanterías, y se vuelven a cargar los iniciales.
     *
     * @throws Exception Si ocurre algún error al recargar el catálogo.
     */
    public void reset() throws Exception {
        catalogo.cargarCatalogo();
    }

    /**
     * Guarda un producto p como un fichero JSON en la carpeta de productos.
     * 
     * Precondición: p no es null.
     * Postcondición: p se guarda en la carpeta de productos en formato JSON.
     *
     * @param p Producto a guardar.
     * @throws Exception Si ocurre algún error durante la escritura.
     */
    public void guardarProducto(Producto p) throws Exception {
        ArchiusProductes.guardarProd(p);
    }

    /**
     * Elimina el fichero JSON que corresponde al producto con el nombre indicado.
     * 
     * Precondición: nombre corresponde a un producto existente.
     * Postcondición: El fichero JSON del producto se elimina de la carpeta de productos.
     *
     * @param nombre Nombre del producto que se desea eliminar.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    public void eliminarProducto(String nombre) throws Exception {
        archiusProductes.eliminarProducto(nombre);
    }

    /**
     * Guarda una estantería como un fichero JSON en la carpeta de estanterías.
     * 
     * Precondición: e no es null.
     * Postcondición: Se guarda la estantería en formato JSON en la carpeta de estanterías.
     *
     * @param e Estantería a guardar.
     * @throws Exception Si ocurre algún error durante la escritura.
     */
    public void guardarEstanteria(Estanteria e) throws Exception {
        ArchiusEstanterias.guardarEstanteria(e);
    }

    /**
     * Elimina el fichero JSON que corresponde a la estantería con el id indicado.
     * 
     * Precondición: id corresponde a una estantería existente.
     * Postcondición: El fichero JSON de la estantería se elimina de la carpeta de estanterías.
     *
     * @param id Identificador de la estantería a eliminar.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    public void eliminarEstanteria(String id) throws Exception {
        archiusEstanterias.eliminarEstanteria(id);
    }

    /**
     * Constructor privado del CtrlPersistencia (singleton).
     *
     * @throws IOException Si ocurre algún problema de E/S al instanciar los objetos de persistencia.
     */
    private CtrlPersistencia() throws IOException {
        archiusEstanterias = ArchiusEstanterias.getInstance();
        archiusProductes = ArchiusProductes.getInstance();
        catalogo = Catalogo.getInstance();
        utils = Utils.getInstance();
    }

    /**
     * Obtiene la lista de estanterías iniciales definidas en el catálogo.
     * 
     * Precondición: -
     * Postcondición: Devuelve la lista de estanterías iniciales del catálogo.
     *
     * @return Lista de estanterías iniciales.
     */
    public ArrayList<Estanteria> getEstanteriasIniciales() {
        return catalogo.getEstanterias();
    }

    /**
     * Carga las estanterías desde la carpeta de persistencia correspondiente.
     * 
     * Precondición: -
     * Postcondición: Devuelve una lista con todas las estanterías almacenadas en ficheros JSON.
     *
     * @return Lista de estanterías.
     * @throws Exception Si ocurre algún error en la lectura de los ficheros.
     */
    public ArrayList<Estanteria> getEstanterias() throws Exception {
        String folderpath = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator +
                            "src" + File.separator + "main" + File.separator + "java" + File.separator +
                            "edu" + File.separator + "upc" + File.separator + "prop" + File.separator +
                            "clusterxx" + File.separator + "persistencia" + File.separator + "estanterias";
        return ArchiusEstanterias.cargarEstanterias(folderpath);
    }

    /**
     * Obtiene la lista de productos iniciales definidos en el catálogo.
     * 
     * Precondición: -
     * Postcondición: Devuelve la lista de productos iniciales del catálogo.
     *
     * @return Lista de productos iniciales.
     */
    public ArrayList<Producto> getProductosIniciales() {
        return catalogo.getProductos();
    }

    /**
     * Carga los productos desde la carpeta de persistencia correspondiente.
     * 
     * Precondición: -
     * Postcondición: Devuelve una lista con todos los productos almacenados en ficheros JSON.
     *
     * @return Lista de productos.
     * @throws Exception Si ocurre algún error en la lectura de los ficheros.
     */
    public ArrayList<Producto> getProductos() throws Exception {
        String folderpath = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator +
                            "src" + File.separator + "main" + File.separator + "java" + File.separator +
                            "edu" + File.separator + "upc" + File.separator + "prop" + File.separator +
                            "clusterxx" + File.separator + "persistencia" + File.separator + "productos";
        return ArchiusProductes.cargarProductos(folderpath);
    }

    /**
     * Devuelve la lista de tipos definida en el catálogo.
     * 
     * Precondición: -
     * Postcondición: Retorna la lista de objetos Tipo del catálogo.
     *
     * @return Lista de tipos.
     */
    public ArrayList<Tipo> getTipos() {
        return catalogo.getTipos();
    }

    /**
     * Devuelve la lista de atributos definida en el catálogo.
     * 
     * Precondición: -
     * Postcondición: Retorna la lista de objetos Atributo del catálogo.
     *
     * @return Lista de atributos.
     */
    public ArrayList<Atributo> getAtributos() {
        return catalogo.getAtributos();
    }

    /**
     * Devuelve la lista de contextos definida en el catálogo.
     * 
     * Precondición: -
     * Postcondición: Retorna la lista de objetos Contexto del catálogo.
     *
     * @return Lista de contextos.
     */
    public ArrayList<Contexto> getContextos() {
        return catalogo.getContextos();
    }

    /**
     * Devuelve la matriz de afinidad u otra matriz definida en Utils.
     * 
     * Precondición: -
     * Postcondición: Retorna la matriz de enteros gestionada por Utils.
     *
     * @return Matriz de afinidad (o similar).
     */
    public int[][] getMatriz() {
        return utils.getMatriz();
    }

    /**
     * Devuelve la instancia única de CtrlPersistencia.
     * 
     * Precondición: -
     * Postcondición: Retorna la misma instancia singleton en cada llamada.
     *
     * @return Instancia singleton de CtrlPersistencia.
     */
    public static CtrlPersistencia getInstance() {
        return instance;
    }
}
