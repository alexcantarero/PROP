package edu.upc.prop.clusterxx.presentacion.Controladores;

import edu.upc.prop.clusterxx.dominio.Atributo;
import edu.upc.prop.clusterxx.dominio.Contexto;
import edu.upc.prop.clusterxx.dominio.Controladores.CtrlDominio;
import edu.upc.prop.clusterxx.dominio.Producto;
import edu.upc.prop.clusterxx.dominio.Tipo;
import edu.upc.prop.clusterxx.presentacion.Vistas.*;

import java.util.ArrayList;

/**
 * Clase <b>CtrlPresentacion</b>.
 * <p>
 * Controlador de la capa de presentación que maneja la interacción entre las vistas y la lógica de dominio.
 * <p>
 * La clase contiene funciones para inicializar la presentación, activar diferentes vistas, gestionar productos,
 * distribuciones y estanterías, y sincronizar las vistas con la lógica de dominio.
 */
public class CtrlPresentacion {
    private CtrlDominio ctrlDominio;
    private VistaPrincipal vistaPrincipal;
    private VistaGestionarProductos vistaGestionarProductos;
    private VistaCrearDistribucion vistaCrearDistribucion;
    private VistaModificarDistribucion vistaModificarDistribucion;
    private VistaCrearEstanteria vistaCrearEstanteria;
    private VistaCargarEstanteria vistaCargarEstanteria;
    private VistaEliminarEstanteria vistaEliminarEstanteria;

    /**
     * Constructor de la clase <b>CtrlPresentacion</b>.
     *
     * <p><b>Pre:</b> Requiere que la inicialización de {@code CtrlDominio} y las vistas asociadas se realicen correctamente.</p>
     *
     * <p><b>Post:</b> Crea una nueva instancia de {@code CtrlPresentacion} y inicializa todas las vistas asociadas.</p>
     *
     * @throws Exception Si ocurre un error durante la inicialización de {@code CtrlDominio} o las vistas.
     */
    public CtrlPresentacion() throws Exception {
        this.ctrlDominio = new CtrlDominio();
        vistaPrincipal = new VistaPrincipal(this);
        vistaGestionarProductos = new VistaGestionarProductos(this);
        vistaCrearDistribucion = new VistaCrearDistribucion(this);
        vistaModificarDistribucion = new VistaModificarDistribucion(this);
        vistaCrearEstanteria = new VistaCrearEstanteria(this);
        vistaCargarEstanteria = new VistaCargarEstanteria(this);
        vistaEliminarEstanteria = new VistaEliminarEstanteria(this);
    }

    /**
     * Inicializa la presentación principal.
     *
     * <p><b>Pre:</b> Todas las vistas deben haber sido inicializadas correctamente.</p>
     *
     * <p><b>Post:</b> Hace visible la vista principal.</p>
     *
     * @throws Exception Si ocurre un error al hacer visible la vista principal.
     */
    public void inicializarPresentacion() throws Exception {
        vistaPrincipal.setVisible(true);
    }

    /**
     * Activa la vista para gestionar productos.
     *
     * <p><b>Pre:</b> La vista {@code VistaGestionarProductos} debe estar inicializada.</p>
     *
     * <p><b>Post:</b> Hace visible la vista para gestionar productos.</p>
     */
    public void activarVistaGestionarProductos() {
        vistaGestionarProductos.setVisible(true);
    }

    /**
     * Activa la vista para crear distribuciones.
     *
     * <p><b>Pre:</b> La vista {@code VistaCrearDistribucion} debe estar inicializada.</p>
     *
     * <p><b>Post:</b> Hace visible la vista para crear distribuciones.</p>
     */
    public void activarVistaCrearDistribucion() {
        vistaCrearDistribucion.setVisible(true);
    }

    /**
     * Activa la vista para crear estanterías.
     *
     * <p><b>Pre:</b> La vista {@code VistaCrearEstanteria} y la lista de estanterías deben estar actualizadas.</p>
     *
     * <p><b>Post:</b> Actualiza las estanterías en las vistas correspondientes y hace visible la vista para crear estanterías.</p>
     */
    public void activarVistaCrearEstanteria() {
        actualizarEstanteriasEnVistas();
        vistaCrearEstanteria.setVisible(true);
    }

    /**
     * Activa la vista para eliminar estanterías.
     *
     * <p><b>Pre:</b> La vista {@code VistaEliminarEstanteria} y la lista de estanterías deben estar actualizadas.</p>
     *
     * <p><b>Post:</b> Actualiza las estanterías en las vistas correspondientes y hace visible la vista para eliminar estanterías.</p>
     */
    public void activarVistaEliminarEstanteria() {
        actualizarEstanteriasEnVistas();
        vistaEliminarEstanteria.setVisible(true);
    }

    /**
     * Activa la vista para cargar estanterías.
     *
     * <p><b>Pre:</b> La vista {@code VistaCargarEstanteria} y la lista de estanterías deben estar actualizadas.</p>
     *
     * <p><b>Post:</b> Actualiza las estanterías en las vistas correspondientes y hace visible la vista para cargar estanterías.</p>
     */
    public void activarVistaCargarEstanteria() {
        actualizarEstanteriasEnVistas();
        vistaCargarEstanteria.setVisible(true);
    }

    /**
     * Activa la vista para modificar distribuciones.
     *
     * <p><b>Pre:</b> La vista {@code VistaModificarDistribucion} debe estar inicializada.</p>
     *
     * <p><b>Post:</b> Hace visible la vista para modificar distribuciones.</p>
     */
    public void activarVistaModificarDistribucion() {
        vistaModificarDistribucion.setVisible(true);
    }

    /**
     * Actualiza la vista para crear distribuciones.
     *
     * <p><b>Pre:</b> La vista {@code VistaCrearDistribucion} debe estar inicializada.</p>
     *
     * <p><b>Post:</b> Actualiza la vista para reflejar los cambios recientes en la creación de distribuciones.</p>
     */
    public void actualizarVistaCrearDistribucion() {
        vistaCrearDistribucion.actualizarVista();
    }

    /**
     * Resetea el controlador de dominio.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Restablece el estado del controlador de dominio.</p>
     *
     * @throws Exception Si ocurre un error al resetear el controlador de dominio.
     */
    public void resetearCtrlDominio() throws Exception {
        ctrlDominio.resetear();
    }

    /**
     * Obtiene la lista completa de atributos.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de atributos gestionados por el controlador de dominio.</p>
     *
     * @return Una lista de objetos {@code Atributo}.
     */
    public ArrayList<Atributo> getListaAtributos() {
        return ctrlDominio.getListaAtributos();
    }

    /**
     * Obtiene la lista completa de tipos.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de tipos gestionados por el controlador de dominio.</p>
     *
     * @return Una lista de objetos {@code Tipo}.
     */
    public ArrayList<Tipo> getListaTipos() {
        return ctrlDominio.getListaTipos();
    }

    /**
     * Obtiene la lista completa de contextos.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de contextos gestionados por el controlador de dominio.</p>
     *
     * @return Una lista de objetos {@code Contexto}.
     */
    public ArrayList<Contexto> getListaContextos() {
        return ctrlDominio.getListaContextos();
    }

    /**
     * Obtiene un producto específico por su nombre.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado y contener el producto.</p>
     *
     * <p><b>Post:</b> Devuelve el producto con el nombre especificado si existe.</p>
     *
     * @param nombre El nombre del producto a obtener.
     * @return El objeto {@code Producto} correspondiente al nombre proporcionado.
     */
    public Producto getProducto(String nombre) {
        return ctrlDominio.obtenerProducto(nombre);
    }

    /**
     * Obtiene la lista completa de productos en el catálogo.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de todos los productos gestionados por el controlador de dominio.</p>
     *
     * @return Una lista de objetos {@code Producto}.
     */
    public ArrayList<Producto> getProductosCatalogo() {
        return ctrlDominio.getProductos();
    }

    /**
     * Obtiene la lista ordenada de productos en la distribución.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado y contener una distribución ordenada.</p>
     *
     * <p><b>Post:</b> Devuelve la lista de productos ordenados según el algoritmo aplicado.</p>
     *
     * @return Una lista de objetos {@code Producto} ordenados.
     */
    public ArrayList<Producto> getDistribucionOrdenada() {
        return ctrlDominio.getListaOrdenada();
    }

    /**
     * Obtiene la afinidad total de la distribución actual.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve el valor de afinidad total de la distribución.</p>
     *
     * @return El valor de afinidad total.
     */
    public int getAfinidadTotal() {
        return ctrlDominio.getAfinidadTotal();
    }

    /**
     * Verifica si existe una distribución inicializada.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve {@code true} si existe una distribución inicializada; de lo contrario, {@code false}.</p>
     *
     * @return {@code true} si existe una distribución; {@code false} en caso contrario.
     */
    public Boolean existeDistribucion() {
        return ctrlDominio.existeDistribucion();
    }

    /**
     * Crea un nuevo producto con los atributos especificados.
     *
     * <p><b>Pre:</b> Requiere un {@code String} {@code nombre} no vacío y una lista de {@code String} {@code atributos}
     * que representan los atributos del producto.</p>
     *
     * <p><b>Post:</b> Crea y añade el nuevo producto al catálogo. Actualiza la vista para crear distribuciones.</p>
     *
     * @param nombre    El nombre del nuevo producto.
     * @param atributos La lista de atributos asociados al nuevo producto.
     * @throws Exception Si ocurre un error durante la creación del producto.
     */
    public void crearProducto(String nombre, ArrayList<String> atributos) throws Exception {
        ctrlDominio.crearProducto(nombre, atributos);
        actualizarVistaCrearDistribucion();
    }

    /**
     * Persiste la lista de productos.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Guarda la lista de productos en la persistencia.</p>
     *
     * @throws Exception Si ocurre un error durante la persistencia de los productos.
     */
    public void peristenciaProductos() throws Exception {
        ctrlDominio.persistenciaProductos();
    }

    /**
     * Elimina un producto específico por su nombre.
     *
     * <p><b>Pre:</b> Requiere que el producto con el {@code nombre} especificado exista en la distribución.</p>
     *
     * <p><b>Post:</b> Elimina el producto de la distribución y actualiza la vista para crear distribuciones.</p>
     *
     * @param nombre El nombre del producto a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación del producto.
     */
    public void eliminarProducto(String nombre) throws Exception {
        ctrlDominio.eliminarProd(nombre);
        actualizarVistaCrearDistribucion();
    }

    /**
     * Modifica el nombre de un producto existente.
     *
     * <p><b>Pre:</b> Requiere que el producto con el {@code nombre} especificado exista.</p>
     *
     * <p><b>Post:</b> Cambia el nombre del producto por {@code nuevoNombre}, configura el panel de distribución en la vista principal
     * y actualiza la vista para crear distribuciones.</p>
     *
     * @param nombre      El nombre actual del producto.
     * @param nuevoNombre El nuevo nombre para el producto.
     * @throws Exception Si ocurre un error durante la modificación del nombre del producto.
     */
    public void modificarNombreProducto(String nombre, String nuevoNombre) throws Exception {
        ctrlDominio.modificarNombreProducto(nombre, nuevoNombre);
        vistaPrincipal.configurarPanelDistribucion();
        actualizarVistaCrearDistribucion();
    }

    /**
     * Modifica los atributos de un producto existente.
     *
     * <p><b>Pre:</b> Requiere que el producto con el {@code nuevoNombre} especificado exista.</p>
     *
     * <p><b>Post:</b> Cambia los atributos del producto por {@code nuevosAtributos} y actualiza la vista para crear distribuciones.</p>
     *
     * @param nuevoNombre     El nombre del producto cuyos atributos serán modificados.
     * @param nuevosAtributos La nueva lista de atributos para el producto.
     * @throws Exception Si ocurre un error durante la modificación de los atributos del producto.
     */
    public void modificarAtrProducto(String nuevoNombre, ArrayList<String> nuevosAtributos) throws Exception {
        ctrlDominio.modificarAtrProducto(nuevoNombre, nuevosAtributos);
        actualizarVistaCrearDistribucion();
    }

    /**
     * Modifica el título de la estantería en la vista principal.
     *
     * <p><b>Pre:</b> La vista principal debe estar inicializada.</p>
     *
     * <p><b>Post:</b> Actualiza el título de la estantería en la vista principal.</p>
     */
    public void modificarTituloEstanteria(){
        vistaPrincipal.modificarTituloEstanteria();
    }

    /**
     * Crea una nueva distribución con los productos y algoritmo especificados.
     *
     * <p><b>Pre:</b> Requiere una lista de productos {@code productos} y un {@code int} {@code algoritmo} válido (1, 2 o 3).</p>
     *
     * <p><b>Post:</b> Crea una nueva distribución, la ordena según el algoritmo especificado y configura el panel de distribución en la vista principal.</p>
     *
     * @param productos El listado de productos a incluir en la distribución.
     * @param algoritmo El algoritmo a utilizar para ordenar la distribución (1: Bruto, 2: Aproximación, 3: Greedy).
     * @throws Exception Si ocurre un error durante la creación de la distribución.
     */
    public void crearDistribucion(ArrayList<Producto> productos, int algoritmo) throws Exception {
        ctrlDominio.crearDistribucion(productos, algoritmo);
        vistaPrincipal.configurarPanelDistribucion();
    }

    /**
     * Modifica la posición de un producto en la distribución.
     *
     * <p><b>Pre:</b> Requiere que el producto con el {@code nombreProducto} especificado exista en la distribución
     * y que {@code i} sea una posición válida.</p>
     *
     * <p><b>Post:</b> Mueve el producto a la posición {@code i} en la distribución y configura el panel de distribución
     * en la vista principal.</p>
     *
     * @param nombreProducto El nombre del producto a mover.
     * @param i              La nueva posición para el producto.
     * @throws Exception Si ocurre un error durante el movimiento del producto.
     */
    public void modificarPosicionProducto(String nombreProducto, int i) throws Exception {
        ctrlDominio.MoverProd(nombreProducto, i);
        vistaPrincipal.configurarPanelDistribucion();
    }

    /**
     * Crea una nueva estantería con el nombre especificado.
     *
     * <p><b>Pre:</b> Requiere un {@code String} {@code nombre} no vacío.</p>
     *
     * <p><b>Post:</b> Crea una nueva estantería, actualiza las estanterías en las vistas correspondientes y configura el panel de distribución en la vista principal.</p>
     *
     * @param nombre El nombre de la nueva estantería.
     * @throws Exception Si ocurre un error durante la creación de la estantería.
     */
    public void crearEstanteria(String nombre) throws Exception {
        ctrlDominio.crearEstanteria(nombre);
        actualizarEstanteriasEnVistas();
        vistaPrincipal.configurarPanelDistribucion();
    }

    /**
     * Obtiene el nombre de la estantería cargada.
     *
     * <p><b>Pre:</b> Debe existir una estantería cargada en el controlador de dominio.</p>
     *
     * <p><b>Post:</b> Devuelve el nombre de la estantería actualmente cargada.</p>
     *
     * @return El nombre de la estantería cargada.
     */
    public String getNombreEstanteria() {
        return ctrlDominio.getNombreEstanteria();
    }

    /**
     * Obtiene el número de estantes de la estantería cargada.
     *
     * <p><b>Pre:</b> Debe existir una estantería cargada en el controlador de dominio.</p>
     *
     * <p><b>Post:</b> Devuelve el número de estantes de la estantería cargada.</p>
     *
     * @return El número de estantes de la estantería cargada.
     */
    public int getNumEstantes() {
        return ctrlDominio.getNumEstantes();
    }

    /**
     * Elimina una estantería específica por su nombre.
     *
     * <p><b>Pre:</b> Requiere que la estantería con el {@code nombre} especificado exista.</p>
     *
     * <p><b>Post:</b> Elimina la estantería de la lista de estanterías y actualiza las estanterías en las vistas correspondientes.</p>
     *
     * @param nombre El nombre de la estantería a eliminar.
     * @throws Exception Si ocurre un error durante la eliminación de la estantería.
     */
    public void eliminarEstanteria(String nombre) throws Exception {
        ctrlDominio.eliminarEstanteria(nombre);
        actualizarEstanteriasEnVistas();
    }

    /**
     * Persiste la lista de estanterías.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Guarda la lista de estanterías en la persistencia.</p>
     *
     * @throws Exception Si ocurre un error durante la persistencia de las estanterías.
     */
    public void peristenciaEstanterias() throws Exception {
        ctrlDominio.persistenciaEstanterias();
    }

    /**
     * Obtiene la lista actual de estanterías.
     *
     * <p><b>Pre:</b> El controlador de dominio debe estar inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve la lista actual de estanterías gestionadas por el controlador de dominio.</p>
     *
     * @return Una lista de {@code String} que representan los IDs de las estanterías.
     */
    public ArrayList<String> getEstanterias() {
        return ctrlDominio.getEstanteriasId(); // Devuelve la lista actual de estanterías
    }

    /**
     * Carga una estantería específica por su nombre.
     *
     * <p><b>Pre:</b> Requiere que la estantería con el {@code nombre} especificado exista.</p>
     *
     * <p><b>Post:</b> Carga la estantería, configura la imagen correspondiente y actualiza el panel de distribución en la vista principal.</p>
     *
     * @param nombre El nombre de la estantería a cargar.
     */
    public void cargarEstanteria(String nombre) {
        ctrlDominio.cargarEstanteria(nombre);
        int numEstantes = ctrlDominio.getNumEstantes();
        vistaPrincipal.configurarImagenEstanteria(numEstantes + ".png");
        vistaPrincipal.configurarPanelDistribucion();
    }

    /**
     * Actualiza todas las vistas que utilizan la lista de estanterías.
     *
     * <p><b>Pre:</b> Requiere que el controlador de dominio esté inicializado y que las vistas estén configuradas para recibir las estanterías.</p>
     *
     * <p><b>Post:</b> Actualiza la lista de estanterías en las vistas {@code VistaEliminarEstanteria} y {@code VistaCargarEstanteria}.</p>
     */
    private void actualizarEstanteriasEnVistas() {
        ArrayList<String> estanterias = ctrlDominio.getEstanteriasId();
        String estanteriaCargada = ctrlDominio.getEstanteriaCargada(); // Obtener estantería cargada

        vistaEliminarEstanteria.setEstanterias(estanterias); // Solo necesita lista
        vistaCargarEstanteria.setEstanterias(estanterias, estanteriaCargada); // Pasa también la estantería cargada
    }

    /**
     * Modifica el número de estantes de la estantería.
     *
     * <p><b>Pre:</b> Requiere un entero {@code n} que representa el nuevo número de estantes.</p>
     *
     * <p><b>Post:</b> Actualiza el número de estantes de la estantería en el controlador de dominio.</p>
     *
     * @param n El nuevo número de estantes.
     * @throws Exception Si ocurre un error durante la modificación del número de estantes.
     */
    public void modificarNumeroEstantes(int n) throws Exception {
        ctrlDominio.modificarEstanteria(n);
    }
}
