package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.dominio.Producto;
import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Clase que representa la vista principal de la aplicación.
 * Permite visualizar y modificar estanterías, distribuciones, productos y afinidades.
 */
public class VistaPrincipal {

    /**
     * Controlador de presentación que gestiona la lógica de la aplicación.
     */
    private CtrlPresentacion ctrlP;
    /**
     * Frame principal de la vista.
     */
    private JFrame frameVista = new JFrame();

    /**
     * Panel para los botones principales.
     */
    private JPanel panelBotonesP = new JPanel();
    /**
     * Panel para botones extra (afinidad, reiniciar, guardar y salir).
     */
    private JPanel panelBotonesEx = new JPanel();

    /**
     * Etiqueta para mostrar la imagen de la estantería.
     */
    private JLabel imagenEstanteria = new JLabel();

    /**
     * Panel con capas para superponer la imagen de la estantería y la distribución.
     */
    private JLayeredPane layeredPane = new JLayeredPane();

    /**
     * Etiqueta para mostrar la distribución de productos.
     */
    private JLabel distribucionLabel;
    /**
     * Etiqueta para mostrar la afinidad total de la distribución.
     */
    private JLabel afinidad;

    /**
     * Crea la vista principal.
     *
     * Precondición: ctrlP no es null.
     * Postcondición: Se crea la vista principal con sus componentes preparados.
     *
     * @param ctrlP Controlador de presentación, no debe ser null.
     * @throws Exception si ocurre algún error al inicializar los componentes.
     */
    public VistaPrincipal(CtrlPresentacion ctrlP) throws Exception {
        this.ctrlP = ctrlP;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la vista principal.
     *
     * Precondición: Existe un controlador válido.
     * Postcondición: La vista principal queda configurada con su imagen, título, paneles de botones y la distribución.
     *
     * @throws Exception si ocurre algún error al configurar la imagen de la estantería.
     */
    private void inicializarComponentes() throws Exception {
        configurarFrame();

        int numEstantes = ctrlP.getNumEstantes();
        String ficheroImagen = Integer.toString(numEstantes) + ".png";
        configurarImagenEstanteria(ficheroImagen);
        modificarTituloEstanteria();
        configurarPanelBotones();
        configurarPanelBotonesExtra();
        configurarPanelDistribucion();
    }

    /**
     * Configura el frame principal.
     *
     * Precondición: -
     * Postcondición: El frame se configura con tamaño, layout y cierre por defecto.
     */
    private void configurarFrame() {
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameVista.setSize(800, 800);
        frameVista.setLayout(new BorderLayout());
    }

    /**
     * Configura la imagen de la estantería según el número de estantes.
     *
     * Precondición: nombreImagen no es null.
     * Postcondición: Se muestra la imagen de la estantería en la parte superior de la vista.
     *
     * @param nombreImagen Nombre del fichero de la imagen (e.g. "3.png").
     */
    public void configurarImagenEstanteria(String nombreImagen) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(nombreImagen);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            imagenEstanteria.setIcon(icon);
        } else {
            System.err.println("Image not found: " + nombreImagen);
        }

        imagenEstanteria.setBounds(0, 0, 800, 600);
        layeredPane.setPreferredSize(new Dimension(800, 600));
        layeredPane.add(imagenEstanteria, JLayeredPane.DEFAULT_LAYER, 0);
        frameVista.add(layeredPane, BorderLayout.NORTH);
    }

    /**
     * Configura la visualización de la distribución sobre la imagen de la estantería.
     *
     * Precondición: -
     * Postcondición: Si existe una distribución, se muestra la misma sobre la imagen de la estantería.
     *                La afinidad se actualiza según la distribución actual.
     */
    public void configurarPanelDistribucion() {
        if (distribucionLabel == null) {
            distribucionLabel = new JLabel();
            distribucionLabel.setBounds(0, 0, 800, 600);
            distribucionLabel.setOpaque(false);
            distribucionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            distribucionLabel.setForeground(Color.BLACK);
            layeredPane.add(distribucionLabel, JLayeredPane.PALETTE_LAYER);
        }

        // Limpiar el texto del label cuando se crea o modifica la distribución
        distribucionLabel.setText("");

        if (ctrlP.existeDistribucion()) {
            ArrayList<Producto> productos = ctrlP.getDistribucionOrdenada();
            int numEstantes = ctrlP.getNumEstantes();
            int div = productos.size() / numEstantes;
            int resto = (productos.size() % numEstantes);
            int extra = 1;
            int count = 0;
            StringBuilder distribucionText = new StringBuilder("<html><div style='text-align: center;'>");
            for (int i = 0; i < productos.size(); i += div + extra) {
                if(resto == 0) extra = 0;
                if (resto > 0) --resto;

                for(int j = i; j < i + div + extra; j++) {
                    distribucionText.append(productos.get(j).getNombre());
                    if (j < i + div + extra - 1) {
                        distribucionText.append(" - ");
                    }
                }
                distribucionText.append("<div style='margin-top: 22px;'></div>");
                ++count;
            }
            for(int k = count; k < 10; ++k){
                distribucionText.append(" ");
                distribucionText.append("<div style='margin-top: 35px;'></div>");
            }
            distribucionText.append("</div></html>");
            distribucionLabel.setText(distribucionText.toString());
        }
        afinidad.setText("Afinidad: " + ctrlP.getAfinidadTotal());
    }

    /**
     * Configura el panel con los botones principales para gestionar la estantería, la distribución y los productos.
     *
     * Precondición: -
     * Postcondición: Se añaden todos los botones con sus respectivas acciones.
     */
    private void configurarPanelBotones() {
        panelBotonesP.setLayout(new GridLayout(4, 2, 10, 10));

        JButton mas = new JButton("+");
        mas.addActionListener(e -> {
            try {
                ctrlP.modificarNumeroEstantes(ctrlP.getNumEstantes() + 1);
                String ficheroImagen = ctrlP.getNumEstantes() + ".png";
                configurarImagenEstanteria(ficheroImagen);
                configurarPanelDistribucion();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameVista, "Error: "+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBotonesP.add(mas);

        JButton menos = new JButton("-");
        menos.addActionListener(e -> {
            try {
                ctrlP.modificarNumeroEstantes(ctrlP.getNumEstantes() - 1);
                String ficheroImagen = ctrlP.getNumEstantes() + ".png";
                configurarImagenEstanteria(ficheroImagen);
                configurarPanelDistribucion();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frameVista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBotonesP.add(menos);

        JButton CD = new JButton("Crear distribución");
        CD.addActionListener(e -> {
            // Precondición: Se puede crear una nueva distribución.
            // Postcondición: Se abre la vista para crear distribución y se actualiza la visualización.
            ctrlP.activarVistaCrearDistribucion();
            configurarPanelDistribucion();
        });
        panelBotonesP.add(CD);

        JButton CE = new JButton("Crear estantería");
        CE.addActionListener(e -> ctrlP.activarVistaCrearEstanteria());
        panelBotonesP.add(CE);

        JButton MD = new JButton("Modificar distribución");
        MD.addActionListener(e -> {
            // Precondición: Debe existir una distribución para modificar.
            // Postcondición: Si existe, se abre la vista para modificar distribución; si no, se muestra error.
            if (ctrlP.existeDistribucion()) {
                ctrlP.activarVistaModificarDistribucion();
                configurarPanelDistribucion();
            } else {
                JOptionPane.showMessageDialog(frameVista, "No hay ninguna distribución creada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelBotonesP.add(MD);

        JButton LE = new JButton("Cargar estantería");
        LE.addActionListener(e -> {
            // Precondición: Debe existir alguna estantería guardada para cargar.
            // Postcondición: Se abre la vista de cargar estantería y se actualiza la distribución.
            ctrlP.activarVistaCargarEstanteria();
            configurarPanelDistribucion();
        });
        panelBotonesP.add(LE);

        JButton GP = new JButton("Gestionar productos");
        GP.addActionListener(e -> ctrlP.activarVistaGestionarProductos());
        panelBotonesP.add(GP);

        JButton EE = new JButton("Eliminar estantería");
        EE.addActionListener(e -> ctrlP.activarVistaEliminarEstanteria());
        panelBotonesP.add(EE);

        frameVista.add(panelBotonesP, BorderLayout.CENTER);
    }

    /**
     * Configura el panel con botones extra, como el de afinidad, reiniciar y guardar/salir.
     *
     * Precondición: -
     * Postcondición: Se añaden las etiquetas de afinidad y los botones para reiniciar y guardar/salir.
     *
     * @throws Exception Si se produce algún error durante la reconfiguración.
     */
    private void configurarPanelBotonesExtra() throws Exception {
        panelBotonesEx.setLayout(new FlowLayout(FlowLayout.RIGHT));

        afinidad = new JLabel("Afinidad: ");

        JButton exitButton = new JButton("Guardar y salir");
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            // Precondición: -
            // Postcondición: Se guardan las estanterías y productos, y se sale del programa.
            try {
                ctrlP.peristenciaEstanterias();
                ctrlP.peristenciaProductos();
                System.exit(0);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        
        JButton resetButton = new JButton("Reiniciar");
        resetButton.addActionListener(e -> {
            // Precondición: -
            // Postcondición: Se resetea el controlador de dominio, la vista de crear distribución y se actualiza la vista principal.
            try {
                ctrlP.resetearCtrlDominio();
                ctrlP.actualizarVistaCrearDistribucion();
                String ficheroImagen = ctrlP.getNumEstantes() + ".png" + " ";
                configurarImagenEstanteria(ficheroImagen);
                configurarPanelDistribucion();
                modificarTituloEstanteria();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        panelBotonesEx.add(afinidad);
        panelBotonesEx.add(resetButton);
        panelBotonesEx.add(exitButton);

        frameVista.add(panelBotonesEx, BorderLayout.SOUTH);
    }

    /**
     * Modifica el título del frame según el nombre de la estantería actual.
     *
     * Precondición: -
     * Postcondición: El título del frame refleja el nombre de la estantería actual.
     */
    public void modificarTituloEstanteria(){
        frameVista.setTitle("Inicio - Estantería: " + ctrlP.getNombreEstanteria());
    }

    /**
     * Cambia la visibilidad de la vista principal.
     *
     * Precondición: -
     * Postcondición: La vista se muestra u oculta según el parámetro b.
     *
     * @param b true para mostrar la vista, false para ocultarla.
     */
    public void setVisible(boolean b) {
        frameVista.setVisible(b);
    }
}
