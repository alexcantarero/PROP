package edu.upc.prop.clusterxx.persistencia.clases;

import edu.upc.prop.clusterxx.dominio.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Clase que se encarga de la creación y gestión de los atributos disponibles en el sistema (tipos, contextos, etc.).
 * También se ocupa de los reseteos del sistema, llamando a las clases <b>ArchiusProductes</b> y <b>ArchiusEstanterias</b>
 * para vaciar las carpetas correspondientes y cargar los objetos básicos (productos y estanterías iniciales).
 */
public class Catalogo {

    /**
     * Lista de productos actualmente en el sistema.
     */
    private ArrayList<Producto> productos;
    /**
     * Lista de atributos generales (incluyendo tipos y contextos).
     */
    private ArrayList<Atributo> atributos;
    /**
     * Lista de tipos disponibles.
     */
    private ArrayList<Tipo> tipos;
    /**
     * Lista de contextos disponibles.
     */
    private ArrayList<Contexto> contextos;
    /**
     * Lista de estanterías disponibles.
     */
    private ArrayList<Estanteria> estanterias;

    /**
     * Única instancia de <b>Catalogo</b> (patrón singleton).
     */
    private static final Catalogo instance;

    static {
        try {
            instance = new Catalogo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un atributo y lo añade a la lista de atributos generales.
     * 
     * Precondición: a no es null.
     * Postcondición: El atributo se añade a la lista <b>atributos</b>.
     * 
     * @param a Atributo a crear.
     */
    private void crearAtributo(Atributo a) {
        atributos.add(a);
    }

    /**
     * Crea un tipo con el nombre y el id indicados, lo añade a la lista de tipos
     * y también lo registra como atributo.
     * 
     * Precondición: nombre != null, id >= 0.
     * Postcondición: Se crea el tipo y se añade a <b>tipos</b> y a <b>atributos</b>.
     * 
     * @param nombre Nombre del tipo.
     * @param id     Identificador del tipo.
     */
    private void crearTipo(String nombre, int id) {
        Tipo t = new Tipo(nombre, id);
        tipos.add(t);
        crearAtributo(t);
    }

    /**
     * Crea un contexto con el nombre dado, lo añade a la lista de contextos
     * y también lo registra como atributo.
     * 
     * Precondición: nombre != null.
     * Postcondición: Se crea el contexto y se añade a <b>contextos</b> y a <b>atributos</b>.
     * 
     * @param nombre Nombre del contexto.
     */
    private void crearContexto(String nombre) {
        Contexto a = new Contexto(nombre);
        contextos.add(a);
        crearAtributo(a);
    }

    /**
     * Inicializa la lista de atributos del sistema, creando los tipos y contextos por defecto.
     * 
     * Precondición: -
     * Postcondición: <b>atributos</b>, <b>tipos</b> y <b>contextos</b> quedan rellenos con los valores iniciales.
     */
    private void cargarAtributos() {
        this.atributos = new ArrayList<>();
        this.tipos = new ArrayList<>();
        this.contextos = new ArrayList<>();

        //CREACIÓN DE TIPOS (IDs de ejemplo)
        this.crearTipo("Carne", 0);
        this.crearTipo("Pescado", 1);
        this.crearTipo("Fruta", 2);
        this.crearTipo("Verdura&Hortaliza", 3);
        this.crearTipo("Cereal", 4);
        this.crearTipo("Lácteo", 5);
        this.crearTipo("Alcohol", 6);
        this.crearTipo("Legumbre", 7);
        this.crearTipo("Condimento", 8);
        this.crearTipo("Dulce", 9);
        this.crearTipo("Bebida", 10);

        //CREACIÓN DE CONTEXTOS
        this.crearContexto("Desayuno+Merienda");
        this.crearContexto("Aperitivo");
        this.crearContexto("Fresco");
    }

    /**
     * Elimina los JSON de productos y estanterías en las carpetas correspondientes,
     * y los carga con los JSON de <b>productosIniciales</b> y <b>estanteriasIniciales</b>.
     * 
     * Precondición: Las carpetas <b>productosIniciales</b> y <b>estanteriasIniciales</b> no están vacías.
     * Postcondición: Las carpetas <b>productos</b> y <b>estanterias</b> se vacían y se rellenan con los datos iniciales.
     *                Se actualizan las listas <b>estanterias</b> y <b>productos</b> con los objetos básicos.
     * 
     * @throws Exception Si ocurre algún error en el proceso de lectura/escritura de JSON.
     */
    public void cargarCatalogo() throws Exception {
        String folderpathP = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator 
                + "src" + File.separator + "main" + File.separator + "java" + File.separator
                + "edu" + File.separator + "upc" + File.separator + "prop" + File.separator
                + "clusterxx" + File.separator + "persistencia" + File.separator + "productosIniciales";
        String folderpathE = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator
                + "src" + File.separator + "main" + File.separator + "java" + File.separator
                + "edu" + File.separator + "upc" + File.separator + "prop" + File.separator
                + "clusterxx" + File.separator + "persistencia" + File.separator + "estanteriasIniciales";

        estanterias = ArchiusEstanterias.cargarestanteriasInicio(folderpathE);
        productos = ArchiusProductes.cargarProductosInicio(folderpathP);
    }

    /**
     * Constructor privado de Catalogo.
     * 
     * Inicializa la lista de atributos del sistema llamando a <b>cargarAtributos()</b>.
     */
    private Catalogo() {
        cargarAtributos();
    }

    /* GETTERS */

    /**
     * Obtiene la lista de productos.
     * 
     * @return Lista de productos almacenada en el catálogo.
     */
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    /**
     * Obtiene la lista de atributos (tipos y contextos).
     * 
     * @return Lista de todos los atributos del sistema.
     */
    public ArrayList<Atributo> getAtributos() {
        return atributos;
    }

    /**
     * Obtiene la lista de tipos disponibles.
     * 
     * @return Lista de tipos.
     */
    public ArrayList<Tipo> getTipos() {
        return tipos;
    }

    /**
     * Obtiene la lista de contextos disponibles.
     * 
     * @return Lista de contextos.
     */
    public ArrayList<Contexto> getContextos() {
        return contextos;
    }

    /**
     * Obtiene la lista de estanterías disponibles.
     * 
     * @return Lista de estanterías.
     */
    public ArrayList<Estanteria> getEstanterias() {
        return estanterias;
    }

    /**
     * Devuelve la instancia única (singleton) de Catalogo.
     * 
     * @return Instancia de Catalogo.
     */
    public static Catalogo getInstance() {
        return instance;
    }

}
