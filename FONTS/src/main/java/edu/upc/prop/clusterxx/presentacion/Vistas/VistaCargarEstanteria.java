package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Clase que representa la vista para cargar una estantería existente.
 * Permite seleccionar una estantería de una lista desplegable y cargarla en el sistema.
 */
public class VistaCargarEstanteria extends JFrame {
    /**
     * ComboBox para seleccionar la estantería a cargar.
     */
    private JComboBox<String> comboEstanterias;
    /**
     * Botón para confirmar la carga de la estantería seleccionada.
     */
    private JButton btnCargar;
    /**
     * Botón para cancelar la operación y cerrar la ventana.
     */
    private JButton btnCancelar;

    /**
     * Controlador de presentación que gestiona las operaciones sobre las estanterías.
     */
    private CtrlPresentacion ctrlPresentacion;

    /**
     * Crea la vista para cargar una estantería.
     * 
     * Precondición: ctrlPresentacion no es null.
     * Postcondición: Se crea la vista y se inicializan sus componentes.
     * 
     * @param ctrlPresentacion Controlador de presentación para interactuar con la lógica de la aplicación.
     */
    public VistaCargarEstanteria(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la ventana, incluyendo layout, botones, comboBox y listeners.
     * Deja la ventana lista para que el usuario seleccione una estantería y la cargue.
     * 
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: La ventana queda configurada con su comboBox, botones y listeners.
     */
    private void inicializarComponentes() {
        setTitle("Cargar Estantería");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        panelCentral.add(new JLabel("Seleccionar Estantería a Cargar:"));
        comboEstanterias = new JComboBox<>();
        panelCentral.add(comboEstanterias);

        JPanel panelBotones = new JPanel();
        btnCargar = new JButton("Cargar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnCargar);
        panelBotones.add(btnCancelar);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnCargar.addActionListener(new ActionListener() {
            /**
             * Acción al pulsar el botón "Cargar".
             * 
             * Precondición: Debe haberse seleccionado una estantería en el comboBox (no null).
             * Postcondición: Si la estantería existe, se carga y se muestra un mensaje de confirmación.
             * Además, se actualiza el título de la estantería y se cierra la ventana.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEstanteria = (String) comboEstanterias.getSelectedItem();
                if (nombreEstanteria != null) {
                    ctrlPresentacion.cargarEstanteria(nombreEstanteria);
                    JOptionPane.showMessageDialog(null, "Estantería cargada correctamente.");
                    dispose(); // Cerrar el pop-up tras cargar
                }
                ctrlPresentacion.modificarTituloEstanteria();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            /**
             * Acción al pulsar el botón "Cancelar".
             * 
             * Precondición: -
             * Postcondición: Se cierra la ventana sin realizar cambios.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                
            }
        });
    }

    /**
     * Establece la lista de estanterías en el comboBox y selecciona la estantería actualmente cargada, si la hay.
     * 
     * Precondición: estanterias no es null.
     * Postcondición: El comboBox se llena con las estanterías proporcionadas.
     *                Si estanteriaCargada no es null, se selecciona automáticamente.
     * 
     * @param estanterias Lista con los nombres de las estanterías disponibles.
     * @param estanteriaCargada Nombre de la estantería que ya está cargada, o null si no hay ninguna.
     */
    public void setEstanterias(ArrayList<String> estanterias, String estanteriaCargada) {
        comboEstanterias.removeAllItems();
        for (String estanteria : estanterias) {
            comboEstanterias.addItem(estanteria);
        }
        if (estanteriaCargada != null) {
            comboEstanterias.setSelectedItem(estanteriaCargada);
        }
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     * 
     * Precondición: mensaje no es null.
     * Postcondición: Se muestra una ventana de diálogo informando del error.
     * 
     * @param mensaje Mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
