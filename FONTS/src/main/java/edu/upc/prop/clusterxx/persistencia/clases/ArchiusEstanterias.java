package edu.upc.prop.clusterxx.persistencia.clases;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import edu.upc.prop.clusterxx.dominio.Estanteria;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase encargada de gestionar los archivos JSON de las estanterías (creación, eliminación y lectura)
 * para asegurar la persistencia de las mismas.
 * <p>
 * La clase interactúa con dos carpetas: <b>estanterias</b> y <b>estanteriasIniciales</b>.
 * <ul>
 *   <li>
 *       En <b>estanterias</b> se guardan los JSON de las estanterías que estarán disponibles
 *       para el usuario al ejecutar la aplicación.
 *   </li>
 *   <li>
 *       En <b>estanteriasIniciales</b> se guarda una lista de estanterías básicas que se cargarán
 *       al resetear la aplicación.
 *   </li>
 * </ul>
 * Según la operación requerida, esta clase se comunicará con la carpeta necesaria
 * para crear, eliminar o leer los JSON de estanterías.
 */
public class ArchiusEstanterias {

    /**
     * Ruta a la carpeta que contiene los archivos JSON de las estanterías.
     */
    static String folderPath = System.getProperty("user.dir") + File.separator + "FONTS" + File.separator
            + "src" + File.separator + "main" + File.separator + "java" + File.separator
            + "edu" + File.separator + "upc" + File.separator + "prop" + File.separator
            + "clusterxx" + File.separator + "persistencia" + File.separator + "estanterias";

    /**
     * Instancia única de la clase ArchiusEstanterias (patrón singleton).
     */
    private static final ArchiusEstanterias instance;

    static {
        try {
            instance = new ArchiusEstanterias();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carga una estantería desde un archivo JSON específico.
     *
     * Precondición: archivo es un fichero JSON válido, gson está inicializado.
     * Postcondición: Se devuelve la estantería leída del archivo, o null si no es un archivo JSON.
     *
     * @param archivo Fichero que contiene la estantería en formato JSON.
     * @param gson Objeto Gson para la deserialización.
     * @return La estantería cargada, o null si el archivo no es un fichero JSON.
     * @throws Exception Si hay un error de lectura o el formato JSON es incorrecto.
     */
    private static Estanteria cargarEstanteria(File archivo, Gson gson) throws Exception {
        if (archivo.isFile() && archivo.getName().endsWith(".json")) {
            try (FileReader reader = new FileReader(archivo)) {
                return gson.fromJson(reader, Estanteria.class);
            } catch (IOException e) {
                throw new Exception("Error leyendo los productos: " + archivo.getName());
            } catch (JsonSyntaxException e) {
                throw new Exception("Error leyendo los productos: Formato JSON incorrecto en el archivo: " + archivo.getName());
            }
        }
        return null;
    }

    /**
     * Elimina todos los archivos JSON de la carpeta <b>estanterias</b>.
     *
     * Precondición: -
     * Postcondición: La carpeta <b>estanterias</b> queda vacía (se eliminan todos sus archivos JSON).
     *
     * @throws Exception Si ocurre algún error al eliminar archivos o no se encuentra la carpeta.
     */
    public static void eliminarTodasEstanterias() throws Exception {
        File carpeta = new File(folderPath);
        if (carpeta.exists() && carpeta.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpeta.listFiles())) {
                if(!archivo.delete()) {
                    throw new Exception("Error leyendo los productos: " + archivo.getName());
                }
            }
        } else {
            throw new Exception("Error al resetear: No se encontró la carpeta.");
        }
    }

    /**
     * Carga todas las estanterías desde la carpeta especificada.
     *
     * Precondición: -
     * Postcondición: Devuelve un ArrayList con todas las estanterías leídas de los archivos JSON en la carpeta.
     *                Si la carpeta está vacía, se lanza una excepción.
     *
     * @param folderpath Ruta a la carpeta que contiene los ficheros JSON de las estanterías.
     * @return Lista de estanterías cargadas.
     * @throws Exception Si no se encuentran ficheros JSON o hay error en la lectura.
     */
    public static ArrayList<Estanteria> cargarEstanterias(String folderpath) throws Exception {
        ArrayList<Estanteria> estanterias = new ArrayList<>();
        Gson gson = new Gson();

        File carpeta = new File(folderpath);
        if (carpeta.exists() && carpeta.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpeta.listFiles())) {
                Estanteria estanteria = cargarEstanteria(archivo, gson);
                estanterias.add(estanteria);
            }
        } else {
            throw new Exception("Error leyendo los productos: No se encontraron productos guardados.");
        }
        return estanterias;
    }

    /**
     * Elimina los JSON de la carpeta <b>estanterias</b> y la llena con los JSON
     * de la carpeta <b>estanteriasIniciales</b>. Devuelve la lista de estanterías así cargadas.
     *
     * Precondición: La carpeta <b>estanteriasIniciales</b> no está vacía.
     * Postcondición: La carpeta <b>estanterias</b> se vacía y se rellena con las estanterías iniciales,
     *                y se devuelve la lista de dichas estanterías.
     *
     * @param folderpath Ruta a la carpeta que contiene los archivos JSON de las estanterías iniciales.
     * @return Lista de estanterías iniciales.
     * @throws Exception Si no se encuentra la carpeta de origen o destino, o falla la lectura/escritura.
     */
    public static ArrayList<Estanteria> cargarestanteriasInicio(String folderpath) throws Exception {
        ArrayList<Estanteria> est = new ArrayList<>();
        Gson gson = new Gson();

        File carpetaOrigen = new File(folderpath);
        if (carpetaOrigen.exists() && carpetaOrigen.isDirectory()) {
            for (File archivo : Objects.requireNonNull(carpetaOrigen.listFiles())) {
                Estanteria estanteria = cargarEstanteria(archivo, gson);
                est.add(estanteria);
            }
            File carpetaDest = new File(folderPath);
            if (carpetaDest.exists() && carpetaDest.isDirectory()) {
                eliminarTodasEstanterias();
                for (Estanteria estanteria : est) {
                    guardarEstanteria(estanteria);
                }
            } else {
                throw new Exception("Error leyendo los productos: No se encontró la carpeta destino estanterias.");
            }
        } else {
            throw new Exception("Error leyendo los productos: No se encontró la carpeta original estanterias.");
        }
        return est;
    }

    /**
     * Crea un archivo JSON para la estantería dada en la carpeta <b>estanterias</b>.
     *
     * Precondición: est no es null.
     * Postcondición: Se crea el archivo JSON correspondiente a la estantería.
     *
     * @param est Estantería a guardar.
     * @throws Exception Si ocurre algún error al escribir el archivo.
     */
    public static void guardarEstanteria(Estanteria est) throws Exception {
        Gson gson = new Gson();
        String filename = folderPath + File.separator + est.getId() + ".json";
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(est, writer);
        } catch (IOException e) {
            throw new Exception("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Elimina el fichero JSON de la carpeta <b>estanterias</b> que corresponde a la estantería con el id indicado.
     *
     * Precondición: id corresponde a una estantería existente.
     * Postcondición: Se elimina el archivo JSON relacionado.
     *
     * @param id Identificador de la estantería a eliminar.
     * @throws Exception Si ocurre algún error durante la eliminación.
     */
    public void eliminarEstanteria(String id) throws Exception {
        String filename = folderPath + File.separator + id + ".json";
        File file = new File(filename);
        if (file.exists()) {
            if(!file.delete()) {
                throw new Exception("Error al eliminar: " + filename);
            }
        } else {
            throw new Exception("Error al eliminar el producto: el archivo no existe");
        }
    }

    /**
     * Constructor que verifica la existencia de la carpeta <b>estanterias</b>.
     * Si no existe, la crea.
     *
     * @throws IOException Si hay un problema al crear la carpeta.
     */
    public ArchiusEstanterias() throws IOException {
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
     * Devuelve la instancia única (singleton) de ArchiusEstanterias.
     *
     * @return Instancia de ArchiusEstanterias.
     */
    public static ArchiusEstanterias getInstance() {
        return instance;
    }
}
