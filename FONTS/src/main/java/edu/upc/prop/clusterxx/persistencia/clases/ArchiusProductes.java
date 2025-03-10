package edu.upc.prop.clusterxx.persistencia.clases;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import edu.upc.prop.clusterxx.dominio.Producto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase que gestiona los archivos JSON de los productos (creación, eliminación y lectura)
 * para garantizar la persistencia de los mismos.
 * <p>
 * La clase interactúa con dos carpetas: <b>productos</b> y <b>productosIniciales</b>.
 * <ul>
 *   <li>
 *       En <b>productos</b> se guardan los JSON de los productos que el usuario podrá gestionar
 *       durante la ejecución de la aplicación.
 *   </li>
 *   <li>
 *       En <b>productosIniciales</b> se guarda una lista de productos básicos que se cargarán
 *       al resetear la aplicación.
 *   </li>
 * </ul>
 * Según la operación, esta clase se comunicará con la carpeta necesaria para
 * crear, eliminar o leer los JSON de productos.
 */
public class ArchiusProductes {

    /**
     * Ruta a la carpeta que contiene los archivos JSON de los productos.
     */
    static String folderPath = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator
            + "src" + File.separator + "main" + File.separator + "java" + File.separator
            + "edu" + File.separator + "upc" + File.separator + "prop" + File.separator
            + "clusterxx" + File.separator + "persistencia" + File.separator + "productos";

    /**
     * Instancia única de la clase ArchiusProductes (patrón singleton).
     */
    private static final ArchiusProductes instance;

    static {
        try {
            instance = new ArchiusProductes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga un producto desde un archivo JSON específico.
     * <p>
     * El archivo debe ser un fichero JSON válido, y gson debe estar inicializado.
     * Devuelve el producto leído del archivo, o null si no es un archivo JSON.
     *
     * @param archivo Fichero que contiene el producto en formato JSON.
     * @param gson    Objeto Gson para la deserialización.
     * @return El producto cargado o null si el archivo no es un fichero JSON.
     * @throws Exception Si hay un error de lectura o el formato JSON es incorrecto.
     */
    private static Producto cargarProd(File archivo, Gson gson) throws Exception {
        if (archivo.isFile() && archivo.getName().endsWith(".json")) {
            try (FileReader reader = new FileReader(archivo)) {
                return gson.fromJson(reader, Producto.class);
            } catch (IOException e) {
                throw new Exception("Error leyendo los productos: " + archivo.getName());
            } catch (JsonSyntaxException e) {
                throw new Exception("Error leyendo los productos: Formato JSON incorrecto en el archivo: " + archivo.getName());
            }
        }
        return null;
    }

    /**
     * Elimina todos los archivos JSON de la carpeta <b>productos</b>.
     * <p>
     * Si la carpeta no existe o no se pueden eliminar los ficheros,
     * se lanza una excepción.
     *
     * @throws Exception Si ocurre algún error al eliminar archivos o no se encuentra la carpeta.
     */
    public static void eliminarTodosProductos() throws Exception {
        File carpeta = new File(folderPath);
        if (carpeta.exists() && carpeta.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpeta.listFiles())) {
                if (!archivo.delete()) {
                    throw new Exception("Error leyendo los productos: " + archivo.getName());
                }
            }
        } else {
            throw new Exception("Error al resetear: No se encontró la carpeta.");
        }
    }

    /**
     * Carga todos los productos desde la carpeta especificada.
     * <p>
     * Lee y carga todos los archivos JSON guardados en la carpeta <b>folderpath</b>,
     * y los devuelve como un ArrayList de productos. Si la carpeta está vacía,
     * se lanza una excepción.
     *
     * @param folderpath Ruta a la carpeta que contiene los ficheros JSON de productos.
     * @return Lista de productos cargados.
     * @throws Exception Si no se encuentran ficheros JSON o hay error en la lectura.
     */
    public static ArrayList<Producto> cargarProductos(String folderpath) throws Exception {
        ArrayList<Producto> productos = new ArrayList<>();
        Gson gson = new Gson();

        File carpeta = new File(folderpath);
        if (carpeta.exists() && carpeta.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpeta.listFiles())) {
                Producto producto = cargarProd(archivo, gson);
                productos.add(producto);
            }
        } else {
            throw new Exception("Error leyendo los productos: No se encontraron productos guardados.");
        }
        return productos;
    }

    /**
     * Elimina todos los JSON de la carpeta <b>productos</b> y la llena con los JSON
     * de la carpeta <b>productosIniciales</b>. Devuelve la lista de productos así cargados.
     * <p>
     * Si la carpeta <b>productosIniciales</b> está vacía, se lanza una excepción.
     *
     * @param folderpath Ruta a la carpeta que contiene los archivos JSON de los productos iniciales.
     * @return Lista de productos iniciales.
     * @throws Exception Si no se encuentra la carpeta de origen o destino, o falla la lectura/escritura.
     */
    public static ArrayList<Producto> cargarProductosInicio(String folderpath) throws Exception {
        ArrayList<Producto> prods = new ArrayList<>();
        Gson gson = new Gson();

        File carpetaOrigen = new File(folderpath);
        if (carpetaOrigen.exists() && carpetaOrigen.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpetaOrigen.listFiles())) {
                Producto producto = cargarProd(archivo, gson);
                prods.add(producto);
            }
            File carpetaDest = new File(folderPath);
            if (carpetaDest.exists() && carpetaDest.isDirectory()) {
                eliminarTodosProductos();
                for (Producto producto : prods) {
                    guardarProd(producto);
                }
            } else {
                throw new Exception("Error leyendo los productos: No se encontró la carpeta destino.");
            }
        } else {
            throw new Exception("Error leyendo los productos: No se encontró la carpeta original.");
        }
        return prods;
    }

    /**
     * Crea un archivo JSON para el producto dado en la carpeta <b>productos</b>.
     *
     * @param producto Producto a guardar.
     * @throws Exception Si ocurre algún error al escribir el archivo.
     */
    public static void guardarProd(Producto producto) throws Exception {
        Gson gson = new Gson();
        String filename = folderPath + File.separator + producto.getNombre() + ".json";
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(producto, writer);
        } catch (IOException e) {
            throw new Exception("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Elimina el fichero JSON que corresponde al producto con el nombre indicado
     * en la carpeta <b>productos</b>.
     *
     * @param nombre Nombre del producto a eliminar.
     * @throws Exception Si ocurre algún error durante la eliminación o el archivo no existe.
     */
    public void eliminarProducto(String nombre) throws Exception {
        String filename = folderPath + File.separator + nombre + ".json";
        File file = new File(filename);
        if (file.exists()) {
            if (!file.delete()) {
                throw new Exception("Error al eliminar: " + filename);
            }
        } else {
            throw new Exception("Error al eliminar el producto:" + nombre + " el archivo no existe");
        }
    }

    /**
     * Constructor que verifica la existencia de la carpeta <b>productos</b>.
     * Si no existe, la crea.
     *
     * @throws IOException Si hay un problema al crear la carpeta.
     */
    public ArchiusProductes() throws IOException {
        File carpeta = new File(folderPath);
        if (!carpeta.exists()) {
            try {
                carpeta.mkdirs();
            } catch (SecurityException se) {
                throw new IOException("Error al crear carpeta");
            }
        }
    }

    /**
     * Devuelve la instancia única (singleton) de ArchiusProductes.
     *
     * @return Instancia de ArchiusProductes.
     */
    public static ArchiusProductes getInstance() {
        return instance;
    }
}
