package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.dominio.Contexto;
import edu.upc.prop.clusterxx.dominio.Producto;
import edu.upc.prop.clusterxx.dominio.Tipo;
import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que representa la vista para gestionar productos del catálogo.
 * Permite crear, modificar, eliminar y visualizar información de los productos.
 */
public class VistaGestionarProductos {

    /**
     * Controlador de presentación asociado a esta vista.
     */
    private CtrlPresentacion ctrlP;
    /**
     * Frame principal de la vista.
     */
    private JFrame frameVista = new JFrame("Gestionar Productos");
    /**
     * Panel que contiene los botones de acciones sobre los productos.
     */
    private JPanel panelBotonesP = new JPanel();
    /**
     * Panel que muestra la información del producto seleccionado.
     */
    private JPanel panelInfoProducto = new JPanel();
    /**
     * Lista de productos.
     */
    private JList<String> lista;
    /**
     * Etiqueta que muestra información del producto seleccionado.
     */
    private JLabel labelInfo;
    /**
     * Lista de tipos disponibles en el catálogo.
     */
    private ArrayList<Tipo> TiposCatalogo;
    /**
     * Lista de contextos disponibles en el catálogo.
     */
    private ArrayList<Contexto> ContextosCatalogo;

    /**
     * Crea la vista para gestionar productos.
     *
     * Precondición: ctrlP no es null.
     * Postcondición: Se crea la vista y se inicializan los componentes visuales.
     *
     * @param ctrlP Controlador de presentación que gestiona las operaciones de productos.
     */
    public VistaGestionarProductos(CtrlPresentacion ctrlP) {
        this.ctrlP = ctrlP;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la vista.
     *
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: Se han configurado el frame, la lista de productos, el panel de información y el panel de botones.
     */
    private void inicializarComponentes() {
        TiposCatalogo = ctrlP.getListaTipos();
        ContextosCatalogo = ctrlP.getListaContextos();
        configurarFrame();
        configurarPanelBotones();
        configurarListaProductos();
        configurarPanelInfoProducto();
    }

    /**
     * Configura la ventana principal.
     *
     * Precondición: -
     * Postcondición: La ventana principal se ha configurado con dimensiones, layout y comportamiento por defecto.
     */
    private void configurarFrame() {
        frameVista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVista.setSize(800, 800);
        frameVista.setLayout(new BorderLayout());
    }

    /**
     * Configura el panel de botones (crear, modificar, eliminar y atrás).
     *
     * Precondición: -
     * Postcondición: El panel de botones está preparado, con los listeners correspondientes.
     */
    private void configurarPanelBotones() {
        panelBotonesP.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton CP = new JButton("Crear producto");
        CP.setPreferredSize(new Dimension(150, 30));
        CP.addActionListener(e -> crearProducto());
        panelBotonesP.add(CP);

        JButton MP = new JButton("Modificar producto");
        MP.setPreferredSize(new Dimension(150, 30));
        MP.addActionListener(e -> modificarProducto());
        panelBotonesP.add(MP);

        JButton EP = new JButton("Eliminar producto");
        EP.setPreferredSize(new Dimension(150, 30));
        EP.addActionListener(e -> {
            try {
                eliminarProducto();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        panelBotonesP.add(EP);

        JButton A = new JButton("Atrás");
        A.setPreferredSize(new Dimension(150, 30));
        A.addActionListener(e -> this.setVisible(false));
        panelBotonesP.add(A);

        frameVista.add(panelBotonesP, BorderLayout.SOUTH);
    }

    /**
     * Configura la lista de productos que se muestran en la vista.
     *
     * Precondición: -
     * Postcondición: La lista de productos se ha llenado con los nombres de los productos existentes.
     */
    private void configurarListaProductos() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ArrayList<Producto> productos = ctrlP.getProductosCatalogo();
        for (Producto producto : productos) {
            listModel.addElement(producto.getNombre());
        }

        lista = new JList<>(listModel);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.addListSelectionListener(e -> mostrarInfoProducto(lista.getSelectedValue()));
        JScrollPane listScrollPane = new JScrollPane(lista);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        listScrollPane.setPreferredSize(new Dimension(300, 800));

        frameVista.add(listScrollPane, BorderLayout.CENTER);
    }

    /**
     * Configura el panel que muestra la información del producto seleccionado.
     *
     * Precondición: -
     * Postcondición: El panel de información muestra un mensaje por defecto hasta que se seleccione un producto.
     */
    private void configurarPanelInfoProducto() {
        panelInfoProducto.setLayout(new BorderLayout());
        labelInfo = new JLabel("Selecciona un producto para ver la información");
        panelInfoProducto.add(labelInfo, BorderLayout.CENTER);
        panelInfoProducto.setPreferredSize(new Dimension(300, 800));
        frameVista.add(panelInfoProducto, BorderLayout.EAST);
    }

    /**
     * Muestra la información del producto seleccionado en la interfaz.
     *
     * Precondición: nombreProducto puede ser null; si es null no se mostrará información.
     * Postcondición: Si el producto existe, se muestra su información; si no, se indica que no se encontró.
     *
     * @param nombreProducto Nombre del producto seleccionado.
     */
    private void mostrarInfoProducto(String nombreProducto) {
        if (nombreProducto != null) {
            Producto producto = ctrlP.getProducto(nombreProducto);
            if (producto != null) {
                StringBuilder texto = new StringBuilder("<html>Producto: " + producto.getNombre() + "<br/>Atributos Tipo:<br/>");
                for (Tipo atributo : producto.getAtributosT()) {
                    texto.append("- ").append(atributo.getNombre()).append("<br/>");
                }
                texto.append("Atributos Contexto:<br/>");
                for (Contexto atributo : producto.getAtributosC()) {
                    texto.append("- ").append(atributo.getNombre()).append("<br/>");
                }
                texto.append("</html>");
                labelInfo.setText(texto.toString());
            } else {
                labelInfo.setText("Producto no encontrado");
            }
        } else {
            labelInfo.setText("Selecciona un producto para ver la información");
        }
    }

    /**
     * Inicia el proceso de modificación de un producto seleccionado.
     *
     * Precondición: Debe existir un producto seleccionado en la lista.
     * Postcondición: Si hay producto seleccionado, se abre el diálogo para modificarlo.
     *                Si no, se muestra un mensaje de error.
     */
    private void modificarProducto() {
        String selectedProduct = lista.getSelectedValue();
        if (selectedProduct != null) {
            Producto producto = ctrlP.getProducto(selectedProduct);
            if (producto != null) {

                mostrarDialogoModificarProducto(producto);
            }
        } else {
            JOptionPane.showMessageDialog(frameVista, "Por favor, selecciona un producto para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * Muestra el cuadro de diálogo para modificar un producto.
     *
     * Precondición: producto no es null.
     * Postcondición: Se muestra una ventana con campos editables para nombre y atributos, y un botón de guardar.
     *
     * @param producto Producto a modificar.
     */
    private void mostrarDialogoModificarProducto(Producto producto) {
        JDialog pestañaMP = new JDialog(frameVista, "Modificar Producto", true);
        pestañaMP.setSize(800, 800);
        pestañaMP.setLayout(new BorderLayout());

        // Campo nombre del producto
        JPanel panelNombre = new JPanel(new GridLayout(0, 1));
        JTextField campoNombre = new JTextField(producto.getNombre());
        panelNombre.add(new JLabel("Nombre:"));
        panelNombre.add(campoNombre);

        // Checkbox de atributos
        JPanel panelAtributos = new JPanel();
        panelAtributos.setLayout(new BoxLayout(panelAtributos, BoxLayout.Y_AXIS));
        ArrayList<JCheckBox> checkboxes = new ArrayList<>();

        for (Tipo tipo : TiposCatalogo) {
            JCheckBox checkbox = new JCheckBox(tipo.getNombre());
            if (producto.getAtributosT().stream().anyMatch(t -> t.getNombre().equals(tipo.getNombre()))) {
                checkbox.setSelected(true);
            }
            checkboxes.add(checkbox);
            panelAtributos.add(checkbox);
        }
        for (Contexto contexto : ContextosCatalogo) {
            JCheckBox checkbox = new JCheckBox(contexto.getNombre());
            if (producto.getAtributosC().stream().anyMatch(c -> c.getNombre().equals(contexto.getNombre()))) {
                checkbox.setSelected(true);
            }
            checkboxes.add(checkbox);
            panelAtributos.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panelAtributos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        panelNombre.add(new JLabel("Atributos:"));
        panelNombre.add(scrollPane);

        pestañaMP.add(panelNombre, BorderLayout.CENTER);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(e -> pestañaMP.dispose());

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> {
            try {
                String nuevoNombre = campoNombre.getText();

                ctrlP.modificarNombreProducto(producto.getNombre(), nuevoNombre);
                ArrayList<String> nuevosAtributos = new ArrayList<>();
                for (JCheckBox checkbox : checkboxes) {
                    if (checkbox.isSelected()) {
                        nuevosAtributos.add(checkbox.getText());
                    }
                }
                ctrlP.modificarAtrProducto(nuevoNombre, nuevosAtributos);
                actualizarListaProductos();
                pestañaMP.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(pestañaMP, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonCancelar);

        pestañaMP.add(panelBotones, BorderLayout.SOUTH);
        pestañaMP.setVisible(true);
    }

    /**
     * Elimina el producto seleccionado.
     *
     * Precondición: Debe existir un producto seleccionado.
     * Postcondición: Si el usuario confirma, el producto se elimina del catálogo y la lista se actualiza.
     *
     * @throws Exception si ocurre un error durante la eliminación.
     */
    private void eliminarProducto() throws Exception {
        String selectedProduct = lista.getSelectedValue();
        if (selectedProduct != null) {
            int respuesta = JOptionPane.showConfirmDialog(frameVista, "¿Estás seguro de que quieres eliminar el producto " + selectedProduct + " ?", "Eliminar producto", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    ctrlP.eliminarProducto(selectedProduct);
                    actualizarListaProductos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frameVista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frameVista, "Por favor, selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Abre un diálogo para crear un nuevo producto.
     *
     * Precondición: -
     * Postcondición: Se muestra un formulario con campo de nombre y selección de atributos.
     *                Si se guarda, se crea el nuevo producto en el catálogo.
     */
    private void crearProducto() {
        JDialog ventanaCP = new JDialog(frameVista, "Crear Producto", true);
        ventanaCP.setSize(800, 800);
        ventanaCP.setLayout(new BorderLayout());

        JPanel panelNombreNuevo = new JPanel(new GridLayout(0, 1));
        JTextField campoNombreNuevo = new JTextField();
        panelNombreNuevo.add(new JLabel("Nombre:"));
        panelNombreNuevo.add(campoNombreNuevo);

        JPanel panelAtributosNuevos = new JPanel();
        panelAtributosNuevos.setLayout(new BoxLayout(panelAtributosNuevos, BoxLayout.Y_AXIS));
        ArrayList<JCheckBox> checkboxes = new ArrayList<>();

        for (Tipo tipo : TiposCatalogo) {
            JCheckBox checkbox = new JCheckBox(tipo.getNombre());
            checkboxes.add(checkbox);
            panelAtributosNuevos.add(checkbox);
        }
        for (Contexto contexto : ContextosCatalogo) {
            JCheckBox checkbox = new JCheckBox(contexto.getNombre());
            checkboxes.add(checkbox);
            panelAtributosNuevos.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panelAtributosNuevos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        panelNombreNuevo.add(new JLabel("Atributos:"));
        panelNombreNuevo.add(scrollPane);

        ventanaCP.add(panelNombreNuevo, BorderLayout.CENTER);

        JButton botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(e -> {
            try {
                String nombreNuevo = campoNombreNuevo.getText();

                ArrayList<String> nuevosAtributos = new ArrayList<>();
                for (JCheckBox checkbox : checkboxes) {
                    if (checkbox.isSelected()) {
                        nuevosAtributos.add(checkbox.getText());
                    }
                }
                ctrlP.crearProducto(nombreNuevo, nuevosAtributos);
                actualizarListaProductos();
                ventanaCP.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ventanaCP, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton botonAtras = new JButton("Atrás");
        botonAtras.addActionListener(e -> ventanaCP.dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(botonGuardar);
        panelBotones.add(botonAtras);

        ventanaCP.add(panelBotones, BorderLayout.SOUTH);
        ventanaCP.setVisible(true);
    }

    /**
     * Actualiza la lista de productos de la vista con los datos del catálogo.
     *
     * Precondición: -
     * Postcondición: La lista de productos visible en la vista se actualiza con el estado actual del catálogo.
     */
    private void actualizarListaProductos() {
        DefaultListModel<String> listModel = (DefaultListModel<String>) lista.getModel();
        listModel.clear();
        ArrayList<Producto> productos = ctrlP.getProductosCatalogo();
        for (Producto producto : productos) {
            listModel.addElement(producto.getNombre());
        }
    }

    /**
     * Ajusta la visibilidad de la vista y actualiza la lista de productos.
     *
     * Precondición: -
     * Postcondición: Si b es true, la vista se muestra y la lista de productos se actualiza; si es false, se oculta.
     *
     * @param b true para mostrar la vista, false para ocultarla.
     */
    public void setVisible(boolean b) {
        frameVista.setVisible(b);
        actualizarListaProductos();
    }
}
