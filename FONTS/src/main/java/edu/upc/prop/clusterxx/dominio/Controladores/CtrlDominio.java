package edu.upc.prop.clusterxx.dominio.Controladores;

import edu.upc.prop.clusterxx.dominio.*;
import edu.upc.prop.clusterxx.persistencia.controladores.CtrlPersistencia;

import java.util.ArrayList;
import java.util.AbstractMap;

/**
 * Clase <b>CtrlDominio</b> que actúa como controlador de la lógica de dominio.
 * Se encarga de recibir las solicitudes del <b>CtrlPresentacion</b>, implementar
 * las operaciones principales, y comunicarse con las clases de dominio y el
 * <b>CtrlPersistencia</b>.
 */
public class CtrlDominio {
    /**
     * Controlador de persistencia para leer/guardar datos.
     */
    private final CtrlPersistencia persistencia;
    /**
     * Lista de todas las estanterías del sistema.
     */
    private ArrayList<Estanteria> estanterias;
    /**
     * La estantería actual que se encuentra cargada en el sistema.
     */
    private Estanteria estanteria_actual;
    /**
     * Lista de todos los productos del sistema.
     */
    private ArrayList<Producto> productos;
    /**
     * Lista de todos los atributos (tipos y contextos) disponibles.
     */
    private final ArrayList<Atributo> listaAtributos;
    /**
     * Lista de todos los tipos disponibles.
     */
    private final ArrayList<Tipo> listaTipos;
    /**
     * Lista de todos los contextos disponibles.
     */
    private final ArrayList<Contexto> listaContextos;
    /**
     * Lista de (Producto, Boolean) para saber qué productos han sido añadidos o eliminados
     * y así gestionar su persistencia (true para guardar, false para eliminar).
     */
    private final ArrayList<AbstractMap.SimpleEntry<Producto, Boolean>> ProductosModificados;
    /**
     * Lista de (Estanteria, Boolean) para saber qué estanterías han sido añadidas o eliminadas
     * y así gestionar su persistencia (true para guardar, false para eliminar).
     */
    private final ArrayList<AbstractMap.SimpleEntry<Estanteria, Boolean>> EstanteriasModificadas;

    /**
     * Instancia única de <b>CtrlDominio</b> (patrón singleton).
     */
    public static final CtrlDominio instance;

    static {
        try {
            instance = new CtrlDominio();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Devuelve la instancia única de <b>CtrlDominio</b>.
     * @return Instancia singleton de CtrlDominio.
     */
    public static CtrlDominio getInstance() {
        return instance;
    }

    /**
     * Constructor de <b>CtrlDominio</b>.
     * Inicializa el controlador de persistencia, carga la lista de estanterías,
     * la estantería actual, la lista de productos y los atributos (tipos y contextos).
     * También configura la matriz de afinidad en la clase <b>Matriz</b>.
     * 
     * @throws Exception si ocurre un error al obtener los datos de persistencia.
     */
    public CtrlDominio() throws Exception {
        this.persistencia = CtrlPersistencia.getInstance();
        this.estanterias = persistencia.getEstanterias();
        // Asumimos que getFirst() es un método de ArrayList en tu código real, 
        // pero en Java no existe. Quizá uses LinkedList o un método personalizado. 
        // Ajusta según tu implementación.
        this.estanteria_actual = estanterias.get(0);
        this.productos = persistencia.getProductos();
        this.listaAtributos = persistencia.getAtributos();
        this.listaTipos = persistencia.getTipos();
        this.listaContextos = persistencia.getContextos();
        int[][] matriz = persistencia.getMatriz();
        ProductosModificados = new ArrayList<>();
        EstanteriasModificadas = new ArrayList<>();
        Matriz mat = Matriz.getInstance();
        mat.setMatriz(matriz);
    }

    /**
     * Obtiene el atributo correspondiente a un nombre dado.
     * 
     * Precondición: <b>nombre</b> no es null.
     * Postcondición: Si existe un atributo con ese nombre, se devuelve; en caso contrario, null.
     * 
     * @param nombre Nombre del atributo.
     * @return Objeto <b>Atributo</b> con el nombre dado, o null si no existe.
     */
    private Atributo obtenerAtributo(String nombre) {
        for (Atributo atributo : listaAtributos) {
            if (atributo.getNombre().equals(nombre)) {
                return atributo;
            }
        }
        return null;
    }

    /**
     * Obtiene el producto correspondiente a un nombre dado.
     * 
     * Precondición: <b>nombre</b> no es null.
     * Postcondición: Si existe un producto con ese nombre, se devuelve; en caso contrario, null.
     * 
     * @param nombre Nombre del producto.
     * @return Objeto <b>Producto</b> con el nombre dado, o null si no existe.
     */
    public Producto obtenerProducto(String nombre) {
        for (Producto producto : this.productos) {
            if (producto.getNombre().equals(nombre)) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Genera una lista de atributos a partir de sus nombres.
     * 
     * Precondición: <b>atributosProducto</b> no está vacía.
     * Postcondición: Devuelve una lista de <b>Atributo</b> correspondientes a los nombres dados.
     * 
     * @param atributosProducto Lista de nombres de atributos.
     * @return Lista de objetos <b>Atributo</b>.
     * @throws Exception si la lista está vacía.
     */
    private ArrayList<Atributo> seleccionarAtributos(ArrayList<String> atributosProducto) throws Exception {
        ArrayList<Atributo> new_atr = new ArrayList<>();
        if (!atributosProducto.isEmpty()) {
            for (String Atr : atributosProducto) {
                Atributo a = obtenerAtributo(Atr);
                new_atr.add(a);
            }
            return new_atr;
        } else {
            throw new Exception("Error: el producto debe tener como mínimo un atributo.");
        }
    }

    /**
     * Mueve un producto a una nueva posición en la lista ordenada de la estantería actual.
     * 
     * Precondición: <b>productoMovido</b> debe existir en la estantería actual,
     *               <b>posicion</b> debe estar en el rango [1, tamaño de la lista de productos].
     * Postcondición: El producto se mueve a la posición indicada (posicion - 1 en índice base 0).
     * 
     * @param productoMovido Nombre del producto que se desea mover.
     * @param posicion Nueva posición (1-based) a la que se desea mover el producto.
     * @throws Exception Si la posición es inválida o el producto ya está en esa posición.
     */
    public void MoverProd(String productoMovido, int posicion) throws Exception {
        int size = this.estanteria_actual.getListaProductos().size();
        if (posicion - 1 < 0 || posicion - 1 > size - 1) {
            throw new Exception("La posición indicada no es correcta. Debe estar entre 1 y " + size);
        } else {
            if (estanteria_actual.getListaOrdenada().get(posicion - 1).getNombre().equals(productoMovido)) {
                throw new Exception("El producto ya se encuentra en la posición " + posicion);
            }
            this.estanteria_actual.moverProducto(productoMovido, posicion - 1);
            EstanteriasModificadas.add(new AbstractMap.SimpleEntry<>(estanteria_actual, true));
        }
    }

    /**
     * Cambia el número de estantes de la estantería actual.
     * 
     * Precondición: <b>filas</b> es el nuevo número de filas deseado (>0 y <=10).
     * Postcondición: La estantería actual cambia su número de filas, y se marca para persistencia.
     * 
     * @param filas Número de filas deseado.
     * @throws Exception Si <b>filas</b> es menor que 1 o mayor que 10.
     */
    public void modificarEstanteria(int filas) throws Exception {
        if (filas < 1) {
            throw new Exception("El número de filas debe ser mayor que 0.");
        } else if (filas > 10) {
            throw new Exception("El número de filas no puede ser mayor que 10.");
        } else {
            this.estanteria_actual.setNumPrestatges(filas);
            EstanteriasModificadas.add(new AbstractMap.SimpleEntry<>(estanteria_actual, true));
        }
    }

    /**
     * Carga la estantería con el id especificado como la estantería actual.
     * 
     * Precondición: <b>id</b> corresponde a una estantería existente.
     * Postcondición: La variable <b>estanteria_actual</b> pasa a apuntar a la estantería seleccionada.
     * 
     * @param id Identificador de la estantería a cargar.
     */
    public void cargarEstanteria(String id) {
        for (Estanteria estanteria : this.estanterias) {
            if (estanteria.getId().equals(id)) {
                this.estanteria_actual = estanteria;
            }
        }
    }

    /**
     * Crea una estantería con el id indicado, si no existe ya una con ese id.
     * 
     * Precondición: <b>id_new</b> no es null.
     * Postcondición: Se crea la nueva estantería y se añade a la lista de estanterías.
     * 
     * @param id_new Id de la nueva estantería.
     * @throws Exception Si ya existe una estantería con <b>id_new</b>.
     */
    public void crearEstanteria(String id_new) throws Exception {
        boolean found = false;
        for (Estanteria estanteria : this.estanterias) {
            if (estanteria.getId().equals(id_new)) {
                found = true;
                break;
            }
        }
        if (found) {
            throw new Exception("Error: ya hay una estantería con ese nombre.");
        } else {
            Estanteria estanteria_new = new Estanteria(id_new, 1);
            this.estanterias.add(estanteria_new);
            EstanteriasModificadas.add(new AbstractMap.SimpleEntry<>(estanteria_new, true));
        }
    }

    /**
     * Elimina la estantería con el id indicado, si existe.
     * 
     * Precondición: <b>id_new</b> es el id de una estantería existente y no es la única estantería en el sistema.
     * Postcondición: Se elimina la estantería de la lista y se marca para persistencia.
     * 
     * @param id_new Id de la estantería a eliminar.
     * @throws Exception Si la estantería no existe, si es la estantería actual o si solo queda una.
     */
    public void eliminarEstanteria(String id_new) throws Exception {
        if (this.estanterias.size() > 1) {
            if (!this.estanteria_actual.getId().equals(id_new)) {
                Estanteria estEliminar = null;
                for (Estanteria estanteria : this.estanterias) {
                    if (estanteria.getId().equals(id_new)) {
                        estEliminar = estanteria;
                        break;
                    }
                }
                if (estEliminar != null) {
                    this.estanterias.remove(estEliminar);
                    EstanteriasModificadas.add(new AbstractMap.SimpleEntry<>(estEliminar, false));
                } else {
                    throw new Exception("No existe ninguna estantería con id " + id_new);
                }
            } else {
                throw new Exception("Error: no puedes eliminar la estantería actualmente cargada.");
            }
        } else {
            throw new Exception("Error: solo queda una estantería, no se puede eliminar.");
        }
    }

    /**
     * Crea una nueva distribución en la estantería actual con los productos dados,
     * utilizando el algoritmo de ordenación indicado.
     * 
     * Precondición: <b>listaDistribucion</b> no está vacía, <b>algoritmo</b> es un identificador válido.
     * Postcondición: Se genera una distribución en la estantería actual con dichos productos.
     * 
     * @param listaDistribucion Lista de productos seleccionados para la distribución.
     * @param algoritmo Identificador del algoritmo a usar (1, 2, 3, ...).
     * @throws Exception Si la lista está vacía o hay algún error en la creación de la distribución.
     */
    public void crearDistribucion(ArrayList<Producto> listaDistribucion, int algoritmo) throws Exception {
        if (!listaDistribucion.isEmpty()) {
            this.estanteria_actual.crearDistribucion(listaDistribucion, algoritmo);
            EstanteriasModificadas.add(new AbstractMap.SimpleEntry<>(estanteria_actual, true));
        } else {
            throw new Exception("La lista de productos está vacía.");
        }
    }

    /**
     * Crea un producto con el nombre y atributos indicados.
     * 
     * Precondición: <b>nombre</b> no es vacío, <b>listaAtr</b> no vacía ni mayor a 4.
     * Postcondición: Se crea y añade el producto a la lista de productos, marcándolo para persistencia.
     * 
     * @param nombre Nombre del nuevo producto.
     * @param listaAtr Lista de nombres de atributos para el nuevo producto.
     * @throws Exception Si ya existe un producto con <b>nombre</b>, 
     *                   o si la lista de atributos es vacía o excede 4.
     */
    public void crearProducto(String nombre, ArrayList<String> listaAtr) throws Exception {
        if (this.buscarProducto(nombre)) {
            throw new Exception("Ya existe un producto con el nombre " + nombre + ".");
        } else if (listaAtr.isEmpty()) {
            throw new Exception("El producto debe tener como mínimo un atributo.");
        } else if (listaAtr.size() > 4) {
            throw new Exception("El producto no puede tener más de 4 atributos.");
        } else {
            ArrayList<Atributo> atributosProducto = this.seleccionarAtributos(listaAtr);
            Producto p = new Producto(nombre, atributosProducto);
            this.productos.add(p);
            ProductosModificados.add(new AbstractMap.SimpleEntry<>(p, true));
        }
    }

    /**
     * Elimina el producto con el nombre indicado.
     * 
     * Precondición: No es el único producto existente; dicho producto no está en ninguna distribución actual.
     * Postcondición: Se elimina el producto de la lista y se marca para persistencia.
     * 
     * @param nombre Nombre del producto a eliminar.
     * @throws Exception Si solo queda un producto o si el producto está en una distribución.
     */
    public void eliminarProd(String nombre) throws Exception {
        if (this.productos.size() > 1) {
            // Comprobamos si el producto está en alguna distribución
            for (Estanteria estanteria : this.estanterias) {
                if (estanteria.existeDistribucion()) {
                    if (estanteria.getListaProductos().contains(this.obtenerProducto(nombre))) {
                        throw new Exception("No se puede eliminar el producto " + nombre
                                + " mientras esté en la distribución de la estantería " + estanteria.getId() + ".");
                    }
                }
            }
            Producto p = this.obtenerProducto(nombre);
            this.productos.remove(p);
            ProductosModificados.add(new AbstractMap.SimpleEntry<>(p, false));
        } else {
            throw new Exception("Solo queda un producto en el sistema, no se puede eliminar.");
        }
    }

    /**
     * Aplica la persistencia a los productos modificados.
     * <p>Guarda o elimina el JSON de los productos indicados en <b>ProductosModificados</b>.
     * 
     * Precondición: -
     * Postcondición: Se persiste la información de los productos creados o eliminados.
     * 
     * @throws Exception Si ocurre un error en la persistencia.
     */
    public void persistenciaProductos() throws Exception {
        for (AbstractMap.SimpleEntry<Producto, Boolean> entry : ProductosModificados) {
            if (entry.getValue()) {
                persistencia.guardarProducto(entry.getKey());
            } else {
                persistencia.eliminarProducto(entry.getKey().getNombre());
            }
        }
    }

    /**
     * Aplica la persistencia a las estanterías modificadas.
     * <p>Guarda o elimina el JSON de las estanterías indicadas en <b>EstanteriasModificadas</b>.
     * 
     * Precondición: -
     * Postcondición: Se persiste la información de las estanterías creadas o eliminadas.
     * 
     * @throws Exception Si ocurre un error en la persistencia.
     */
    public void persistenciaEstanterias() throws Exception {
        for (AbstractMap.SimpleEntry<Estanteria, Boolean> entry : EstanteriasModificadas) {
            if (entry.getValue()) {
                persistencia.guardarEstanteria(entry.getKey());
            } else {
                persistencia.eliminarEstanteria(entry.getKey().getId());
            }
        }
    }

    /**
     * Modifica el nombre de un producto si no se encuentra en ninguna distribución
     * y no existe ya un producto con el nuevo nombre.
     * 
     * Precondición: <b>prodACambiar</b> corresponde a un producto existente,
     *               <b>newName</b> no está en uso, y el producto no está en una distribución.
     * Postcondición: El producto cambia su nombre a <b>newName</b> y se marca para persistencia.
     * 
     * @param prodACambiar Nombre del producto a cambiar.
     * @param newName Nuevo nombre para el producto.
     * @throws Exception Si el producto ya existe con ese nombre, si <b>newName</b> es vacío
     *                   o si está en una distribución.
     */
    public void modificarNombreProducto(String prodACambiar, String newName) throws Exception {
        Producto producto = this.obtenerProducto(prodACambiar);
        if (this.buscarProducto(newName) && !newName.equals(prodACambiar)) {
            throw new Exception("Ya existe un producto con el nombre " + newName + ".");
        } else {
            // Verificamos si el producto está en alguna distribución
            for (Estanteria estanteria : this.estanterias) {
                if (estanteria.existeDistribucion()) {
                    if (estanteria.getListaProductos().contains(producto)) {
                        throw new Exception("No se puede modificar el producto " + prodACambiar
                                + " mientras esté en la distribución de la estantería " + estanteria.getId() + ".");
                    }
                }
            }
            producto.cambiarNombre(newName);
            // Añadimos el producto con el nuevo nombre a la lista de modificaciones
            ProductosModificados.add(new AbstractMap.SimpleEntry<>(obtenerProducto(newName), true));
            // Creamos un objeto temporal para representar la eliminación del producto antiguo
            Producto p = new Producto(prodACambiar, new ArrayList<>());
            ProductosModificados.add(new AbstractMap.SimpleEntry<>(p, false));
        }
    }

    /**
     * Modifica la lista de atributos de un producto si no se encuentra en ninguna distribución.
     * 
     * Precondición: <b>listaAtr</b> no está vacía ni excede las 4 entradas,
     *               <b>prodACambiar</b> no está en una distribución.
     * Postcondición: El producto cambia sus atributos a la nueva lista <b>nuevosAtributos</b>.
     * 
     * @param prodACambiar Nombre del producto a cambiar.
     * @param listaAtr Lista de nombres de los nuevos atributos.
     * @throws Exception Si la lista está vacía, excede 4 o el producto está en una distribución.
     */
    public void modificarAtrProducto(String prodACambiar, ArrayList<String> listaAtr) throws Exception {
        if (listaAtr.isEmpty()) {
            throw new Exception("El producto debe tener como mínimo un atributo.");
        } else if (listaAtr.size() > 4) {
            throw new Exception("El producto no puede tener más de 4 atributos.");
        }
        Producto producto = this.obtenerProducto(prodACambiar);
        ArrayList<Atributo> nuevosAtributos = this.seleccionarAtributos(listaAtr);

        // Verificar si el producto está en alguna distribución
        for (Estanteria estanteria : this.estanterias) {
            if (estanteria.existeDistribucion()) {
                if (estanteria.getListaProductos().contains(producto)) {
                    throw new Exception("No se puede modificar el producto " + prodACambiar
                            + " mientras esté en la distribución de la estantería " + estanteria.getId() + ".");
                }
            }
        }
        producto.changeAtr(nuevosAtributos);
    }

    /**
     * Devuelve el id de la estantería cargada actualmente.
     * 
     * Precondición: -
     * Postcondición: Retorna el id de la estantería actual o null si no hay ninguna.
     * 
     * @return Id de la estantería actual.
     */
    public String getEstanteriaCargada() {
        return estanteria_actual != null ? estanteria_actual.getId() : null;
    }

    /**
     * Retorna la lista de todos los productos del sistema.
     * 
     * @return Lista de <b>Producto</b>.
     */
    public ArrayList<Producto> getProductos() {
        return this.productos;
    }

    /**
     * Devuelve la lista de ids de todas las estanterías.
     * 
     * @return Lista de <b>String</b> con los ids de las estanterías.
     */
    public ArrayList<String> getEstanteriasId() {
        ArrayList<String> idsEstanterias = new ArrayList<>();
        for (Estanteria estanteria : this.estanterias) {
            idsEstanterias.add(estanteria.getId());
        }
        return idsEstanterias;
    }

    /**
     * Devuelve la afinidad total de la distribución en la estantería actual.
     * 
     * @return Valor numérico de la afinidad total.
     */
    public int getAfinidadTotal() {
        return this.estanteria_actual.getAfinidadTotal();
    }

    /**
     * Devuelve el número de estantes de la estantería actual.
     * 
     * @return Número de filas/estantes.
     */
    public int getNumEstantes() {
        return this.estanteria_actual.getNumPrestatges();
    }

    /**
     * Devuelve el id (nombre) de la estantería actual.
     * 
     * @return Id de la estantería actual.
     */
    public String getNombreEstanteria() {
        return this.estanteria_actual.getId();
    }

    /**
     * Retorna la lista global de atributos (tipos y contextos).
     * 
     * @return Lista de <b>Atributo</b>.
     */
    public ArrayList<Atributo> getListaAtributos() {
        return this.listaAtributos;
    }

    /**
     * Retorna la lista de tipos disponibles.
     * 
     * @return Lista de <b>Tipo</b>.
     */
    public ArrayList<Tipo> getListaTipos() {
        return this.listaTipos;
    }

    /**
     * Retorna la lista de contextos disponibles.
     * 
     * @return Lista de <b>Contexto</b>.
     */
    public ArrayList<Contexto> getListaContextos() {
        return this.listaContextos;
    }

    /**
     * Obtiene la lista ordenada de productos en la estantería actual.
     * 
     * @return Lista de <b>Producto</b> que representa la distribución ordenada.
     */
    public ArrayList<Producto> getListaOrdenada() {
        return this.estanteria_actual.getListaOrdenada();
    }

    /**
     * Indica si existe una distribución en la estantería actual.
     * 
     * @return true si hay distribución, false en caso contrario.
     */
    public boolean existeDistribucion() {
        return this.estanteria_actual.existeDistribucion();
    }

    /**
     * Verifica si existe un producto con el nombre indicado.
     * 
     * @param nombre Nombre a buscar.
     * @return true si existe, false si no.
     */
    private boolean buscarProducto(String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resetea el sistema a su estado inicial.
     * <p>Elimina los JSON de la carpeta productos y estanterías, y carga los de las
     * carpetas productosIniciales y estanteriasIniciales. También borra los registros
     * de modificaciones para persistencia.
     * 
     * Precondición: -
     * Postcondición: La lista de productos, estanterías y la estantería actual 
     *                se establecen en sus valores iniciales.
     * 
     * @throws Exception Si ocurre algún error durante el proceso de reseteo.
     */
    public void resetear() throws Exception {
        this.persistencia.reset();
        this.productos = persistencia.getProductosIniciales();
        this.estanterias = persistencia.getEstanteriasIniciales();
        // Ajustar si usas LinkedList u otro método para getFirst().
        this.estanteria_actual = estanterias.get(0);
        ProductosModificados.clear();
        EstanteriasModificadas.clear();
    }
}
