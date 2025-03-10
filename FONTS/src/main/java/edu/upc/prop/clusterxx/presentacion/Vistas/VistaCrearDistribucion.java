package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.dominio.Producto;
import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * Clase que representa la vista para crear una nueva distribución de productos.
 * Permite seleccionar productos a incluir, elegir el algoritmo a aplicar y generar la distribución.
 */
public class VistaCrearDistribucion {

    /**
     * Controlador de presentación para gestionar las operaciones sobre la distribución.
     */
    private CtrlPresentacion ctrlP;
    /**
     * Frame principal de la vista.
     */
    private JFrame frameVista = new JFrame("Crear Distribución");
    /**
     * Panel para los botones y el comboBox de selección de algoritmo.
     */
    private JPanel panelBotones = new JPanel();
    /**
     * ComboBox para seleccionar el tipo de algoritmo a emplear.
     */
    private JComboBox<String> algoritmos;

    /**
     * Botón para volver atrás.
     */
    private JButton atras;
    /**
     * Botón para aceptar y crear la distribución.
     */
    private JButton aceptar;

    /**
     * Lista de checkboxes, una por producto, para seleccionar qué productos incluir en la distribución.
     */
    private ArrayList<JCheckBox> checkboxes = new ArrayList<>();
    /**
     * Área de texto que muestra los productos actualmente seleccionados.
     */
    private JTextArea visualizacion = new JTextArea();

    /**
     * Lista con los nombres de los productos seleccionados actualmente.
     */
    private ArrayList<String> productosActuales = new ArrayList<>();

    /**
     * Panel para la visualización previa de los productos seleccionados.
     */
    public JPanel panelVistaPrevia = new JPanel();

    /**
     * Crea la vista para crear una distribución.
     *
     * Precondición: ctrlP no es null.
     * Postcondición: Se crea la vista y se inicializan todos sus componentes.
     *
     * @param ctrlP Controlador de presentación asociado.
     */
    public VistaCrearDistribucion(CtrlPresentacion ctrlP) {
        this.ctrlP = ctrlP;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la vista.
     *
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: El frame, el panel de botones, el comboBox de algoritmos, el panel de productos y la visualización previa quedan configurados.
     */
    private void inicializarComponentes() {
        configurarFrame();
        configurarPanelBotonesyAlgoritmo();
        configurarDesplegableProductos();
        configurarVisualizacionPrevia();
    }

    /**
     * Configura el frame principal.
     *
     * Precondición: -
     * Postcondición: El frame se configura con tamaño, layout y comportamiento por defecto.
     */
    private void configurarFrame() {
        frameVista.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameVista.setSize(800, 800);
        frameVista.setLayout(new BorderLayout());
    }

    /**
     * Configura el panel de botones y el comboBox de selección de algoritmo.
     *
     * Precondición: -
     * Postcondición: Se añaden el comboBox con los algoritmos y los botones de "Atrás" y "Aceptar".
     */
    private void configurarPanelBotonesyAlgoritmo() {
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        JPanel panelAlgoritmos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        algoritmos = new JComboBox<>();
        algoritmos.addItem("Fuerza bruta");
        algoritmos.addItem("Aproximación");
        algoritmos.addItem("Greedy");
        panelAlgoritmos.add(algoritmos);

        panelBotones.add(panelAlgoritmos);

        JPanel panelBotonesInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        atras = new JButton("Atrás");
        aceptar = new JButton("Aceptar");
        aceptar.setBackground(Color.GREEN);

        // Acción para crear la distribución.
        aceptar.addActionListener(e -> {
            try {
                crearDistribucion();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción para volver atrás (ocultar la vista).
        atras.addActionListener(e -> frameVista.setVisible(false));

        panelBotonesInferior.add(atras);
        panelBotonesInferior.add(aceptar);
        panelBotones.add(panelBotonesInferior);

        frameVista.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Configura el panel con checkboxes para la selección de productos.
     *
     * Precondición: -
     * Postcondición: Se muestra una lista de productos, cada uno con su checkbox.
     *                Al marcar o desmarcar un producto, se actualiza la lista de seleccionados.
     */
    private void configurarDesplegableProductos() {
        panelVistaPrevia.setLayout(new BoxLayout(panelVistaPrevia, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Seleccione los productos que quiere incluir en la distribución: ");
        panelVistaPrevia.add(titulo);

        ArrayList<Producto> productos = ctrlP.getProductosCatalogo();
        for (Producto producto : productos) {
            JCheckBox checkbox = new JCheckBox(producto.getNombre());
            checkbox.addItemListener(e -> {
                // Precondición: El producto existe en el catálogo.
                // Postcondición: Si se marca el checkbox, el producto se añade a productosActuales; si se desmarca, se elimina.
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    productosActuales.add(producto.getNombre());
                } else {
                    productosActuales.remove(producto.getNombre());
                }
                actualizarVisualizacionPrevia();
            });
            checkboxes.add(checkbox);
            panelVistaPrevia.add(checkbox);
        }

        JScrollPane scrollPane = new JScrollPane(panelVistaPrevia);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 600));

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        frameVista.add(panelCentral, BorderLayout.CENTER);
    }

    /**
     * Configura la visualización previa, un área de texto que muestra los productos seleccionados.
     *
     * Precondición: -
     * Postcondición: Se crea un panel a la derecha con un título y un JTextArea donde se muestran los productos escogidos.
     */
    private void configurarVisualizacionPrevia() {
        JPanel panelVisualizacion = new JPanel();
        panelVisualizacion.setLayout(new BoxLayout(panelVisualizacion, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Productos escogidos para la distribución: ");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVisualizacion.add(titulo);

        visualizacion.setEditable(false);
        panelVisualizacion.add(visualizacion);

        JScrollPane scrollPane = new JScrollPane(panelVisualizacion);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(300, 600));

        JPanel panelDerecha = new JPanel(new BorderLayout());
        panelDerecha.add(scrollPane, BorderLayout.CENTER);
        frameVista.add(panelDerecha, BorderLayout.EAST);
    }

    /**
     * Actualiza las checkboxes marcándolas si ya existe una distribución creada.
     *
     * Precondición: -
     * Postcondición: Si existe una distribución, se marcan los productos ya asignados a ella.
     *                También se actualiza la visualización previa con la lista actual.
     */
    private void actualizarCheckBoxes() {
        if (ctrlP.existeDistribucion()) {
            StringBuilder seleccionados = new StringBuilder();
            ArrayList<Producto> productos = ctrlP.getDistribucionOrdenada();
            for (Producto p : productos) {
                seleccionados.append(p.getNombre()).append("\n");
            }
            for (JCheckBox checkbox : checkboxes) {
                String productoABuscar = checkbox.getText();
                productoABuscar = productoABuscar.substring(productoABuscar.indexOf(".") + 1);
                String finalProductoABuscar = productoABuscar;
                // Marcar el checkbox si el producto forma parte de la distribución actual.
                checkbox.setSelected(productos.stream().anyMatch(prod -> prod.getNombre().equals(finalProductoABuscar)));
            }
            visualizacion.setText(seleccionados.toString());
        }
    }

    /**
     * Actualiza el área de visualización previa con los productos seleccionados actualmente.
     *
     * Precondición: -
     * Postcondición: El JTextArea muestra los nombres de los productos actualmente escogidos.
     */
    private void actualizarVisualizacionPrevia() {
        StringBuilder seleccionados = new StringBuilder();
        for (String pa : productosActuales) {
            seleccionados.append(pa).append("\n");
        }
        visualizacion.setText(seleccionados.toString());
    }

    /**
     * Crea la distribución usando el algoritmo seleccionado y los productos marcados.
     *
     * Precondición: Debe haberse seleccionado un algoritmo y al menos un producto.
     * Postcondición: Se crea la distribución con los productos seleccionados y el algoritmo elegido.
     *                Se muestra un mensaje de confirmación y se cierra la ventana.
     *
     * @throws Exception Si se produce algún error durante la creación de la distribución.
     */
    private void crearDistribucion() throws Exception {
        String algoritmo = (String) algoritmos.getSelectedItem();
        ArrayList<Producto> productos = ctrlP.getProductosCatalogo();
        
        ArrayList<Producto> productosSeleccionados = new ArrayList<>();

        // Filtramos los productos seleccionados a partir de la lista total de productos.
        for (String pa : productosActuales) {
            for (Producto p : productos) {
                if (p.getNombre().equals(pa)) {
                    productosSeleccionados.add(p);
                    break;
                }
            }
        }

        // Aplicar el algoritmo seleccionado.
        if (algoritmo.equals("Fuerza bruta")) {
            ctrlP.crearDistribucion(productosSeleccionados, 1);
        } else if(algoritmo.equals("Aproximación")) {
            ctrlP.crearDistribucion(productosSeleccionados, 2);
        } else {
            ctrlP.crearDistribucion(productosSeleccionados, 3);
        }

        JOptionPane.showMessageDialog(null, "Distribución creada correctamente.");
        frameVista.setVisible(false);
    }

    /**
     * Actualiza la vista conforme los productos modificados, eliminados o creados.
     *
     * Precondición: -
     * Postcondición: La vista se actualiza con las modificaciones correspondientes.
     */
    public void actualizarVista() {
            panelVistaPrevia.removeAll();
            checkboxes.clear();
            productosActuales.clear();

            ArrayList<Producto> productos = ctrlP.getProductosCatalogo();
            for (Producto producto : productos) {
                JCheckBox checkbox = new JCheckBox(producto.getNombre());
                checkbox.addItemListener(e -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        productosActuales.add(producto.getNombre());
                    } else {
                        productosActuales.remove(producto.getNombre());
                    }
                    actualizarVisualizacionPrevia();
                });
                checkboxes.add(checkbox);
                panelVistaPrevia.add(checkbox);
            }

            panelVistaPrevia.revalidate();
            panelVistaPrevia.repaint();
            actualizarVisualizacionPrevia();

    }

    /**
     * Cambia la visibilidad de la vista.
     *
     * Precondición: -
     * Postcondición: Si se hace visible, se actualizan las checkboxes con la distribución actual (si existe).
     *
     * @param b true para mostrar la vista, false para ocultarla.
     */
    public void setVisible(boolean b) {
        if (b) {
            actualizarCheckBoxes();
        }
        frameVista.setVisible(b);
    }
}
