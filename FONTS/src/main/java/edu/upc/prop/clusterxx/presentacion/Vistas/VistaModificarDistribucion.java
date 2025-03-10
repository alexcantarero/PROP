package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.dominio.Producto;
import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que representa la vista para modificar una distribución existente.
 * Permite visualizar la distribución actual, modificar el orden de los productos y ver la afinidad total.
 */
public class VistaModificarDistribucion {

    /**
     * Controlador de presentación para gestionar las operaciones sobre la distribución.
     */
    private CtrlPresentacion ctrlP;
    /**
     * Frame principal de la vista.
     */
    private JFrame frameVista = new JFrame("Modificar Distribución");
    /**
     * Etiqueta que muestra la afinidad total de la distribución.
     */
    private JLabel afinidad;

    /**
     * Lista de productos actuales en la distribución.
     */
    private ArrayList<Producto> productosDistribucion;

    /**
     * Lista gráfica de productos para mostrar la distribución actual.
     */
    private JList<String> listaProductos;

    /**
     * Crea la vista para modificar una distribución.
     *
     * Precondición: ctrlP no es null.
     * Postcondición: Se crea la vista y se inicializan sus componentes.
     *
     * @param ctrlP Controlador de presentación, no debe ser null.
     */
    public VistaModificarDistribucion(CtrlPresentacion ctrlP) {
        this.ctrlP = ctrlP;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la vista.
     *
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: El frame, la lista de productos y el panel con la afinidad y el botón de salir quedan configurados.
     */
    private void inicializarComponentes() {
        configurarFrame();
        configurarBotonSaliryAfinidad();
        configurarListaProductos();
    }

    /**
     * Configura el frame principal.
     *
     * Precondición: -
     * Postcondición: El frame se configura con tamaño, layout y comportamiento por defecto al cerrar.
     */
    private void configurarFrame() {
        frameVista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVista.setSize(800, 800);
        frameVista.setLayout(new BorderLayout());
    }

    /**
     * Configura el panel inferior con el botón de salir y la etiqueta de afinidad.
     *
     * Precondición: -
     * Postcondición: Se agrega un panel en la parte inferior con la afinidad total y un botón para cerrar la vista.
     */
    private void configurarBotonSaliryAfinidad(){
        JPanel panelBotonSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonSalir = new JButton("Salir");
        afinidad = new JLabel("Afinidad: " + ctrlP.getAfinidadTotal());
        botonSalir.addActionListener(e -> frameVista.setVisible(false));
        panelBotonSalir.add(afinidad);
        panelBotonSalir.add(botonSalir);
        frameVista.add(panelBotonSalir, BorderLayout.SOUTH);
    }

    
    /**
     * Actualiza la afinidad mostrada en la etiqueta inferior.
     *
     * Precondición: -
     * Postcondición: La etiqueta de afinidad muestra el valor actualizado de la afinidad total.
     */
    private void actualizarAfinidad(){
        afinidad.setText("Afinidad: " + ctrlP.getAfinidadTotal());
    }

    /**
     * Configura la lista de productos, mostrando los productos de la distribución actual si existe.
     *
     * Precondición: -
     * Postcondición: Si existe distribución, se muestra una lista con los productos ordenados.
     *                Al seleccionar un producto, se permite modificar su posición.
     */
    private void configurarListaProductos() {
        DefaultListModel<String> listaVisual = new DefaultListModel<>();
        if (ctrlP.existeDistribucion()) {
            productosDistribucion = ctrlP.getDistribucionOrdenada();
            int orden = 1;
            for (Producto producto : productosDistribucion) {
                listaVisual.addElement(orden + ". " + producto.getNombre());
                ++orden;
            }

            listaProductos = new JList<>(listaVisual);
            listaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listaProductos.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String selectedValue = listaProductos.getSelectedValue();
                    if (selectedValue != null) {
                        modificarPosicionProducto(selectedValue);
                    }
                }
            });
            JScrollPane listScrollPane = new JScrollPane(listaProductos);
            listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            listScrollPane.setPreferredSize(new Dimension(300, 600));

            JLabel titulo = new JLabel("Seleccione el producto a reorganizar en la distribución: ");
            frameVista.add(titulo, BorderLayout.NORTH);
            frameVista.add(listScrollPane, BorderLayout.CENTER);
        }
    }

    /**
     * Abre un diálogo para modificar la posición de un producto en la distribución.
     *
     * Precondición: nombreProducto no es null y pertenece a la distribución actual.
     * Postcondición: Si se introduce una posición válida y se guarda, la posición del producto se modifica.
     *                 Se actualiza la lista de productos y la afinidad mostrada.
     *
     * @param nombreProducto Nombre del producto seleccionado con su numeración inicial.
     */
    private void modificarPosicionProducto(String nombreProducto) {
        String nombreProductoSinOrden = nombreProducto.substring(nombreProducto.indexOf(".") + 2);

        JDialog ventanaPosicionNueva = new JDialog(frameVista, "Modificar posición de " + nombreProductoSinOrden, true);
        ventanaPosicionNueva.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        ventanaPosicionNueva.setSize(400, 300);
        ventanaPosicionNueva.setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        JLabel texto = new JLabel("Introduce la nueva posición del producto " + nombreProductoSinOrden + ": ");
        JTextField campoDeTexto = new JTextField();

        panelCentral.add(texto);
        panelCentral.add(campoDeTexto);

        JButton botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(e -> {
            // Precondición: campoDeTexto contiene el valor de la nueva posición.
            // Postcondición: Si es un entero válido, se modifica la posición del producto.
            if(campoDeTexto.getText().isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPosicionNueva, "Introduce una posición válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String nuevaPosicion = campoDeTexto.getText();
            int nuevaPosicionInt;
            try {
                nuevaPosicionInt = Integer.parseInt(nuevaPosicion);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventanaPosicionNueva, "Introduce una posición válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                ctrlP.modificarPosicionProducto(nombreProductoSinOrden, nuevaPosicionInt);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventanaPosicionNueva.setVisible(false);
            actualizarListaProductos();
            actualizarAfinidad();
        });

        ventanaPosicionNueva.add(panelCentral, BorderLayout.CENTER);
        ventanaPosicionNueva.add(botonAceptar, BorderLayout.SOUTH);
        ventanaPosicionNueva.setVisible(true);
    }

    /**
     * Actualiza la lista de productos de la distribución mostrada en la vista.
     *
     * Precondición: -
     * Postcondición: Se carga el estado actual de la distribución desde el controlador y se refleja en la lista.
     */
    public void actualizarListaProductos() {
        if (listaProductos == null) {
            configurarListaProductos();
        }
        DefaultListModel<String> listaVisual = (DefaultListModel<String>) listaProductos.getModel();
        listaVisual.clear();
        productosDistribucion = ctrlP.getDistribucionOrdenada();
        int orden = 1;
        for (Producto producto : productosDistribucion) {
            listaVisual.addElement(orden + ". " + producto.getNombre());
            ++orden;
        }
    }

    /**
     * Cambia la visibilidad de la vista.
     *
     * Precondición: -
     * Postcondición: Si b es true, se muestran la vista, se actualizan la lista de productos y la afinidad;
     *                si b es false, se oculta.
     *
     * @param b true para mostrar la vista, false para ocultarla.
     */
    public void setVisible(boolean b) {
        frameVista.setVisible(b);
        if (b) {
            actualizarListaProductos();
            actualizarAfinidad();
        }
    }
}
