package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Clase que representa la vista para eliminar una estantería existente.
 * Permite seleccionar la estantería que se desea eliminar y confirma la acción antes de realizarla.
 */
public class VistaEliminarEstanteria extends JFrame {
    /**
     * ComboBox para seleccionar la estantería a eliminar.
     */
    private JComboBox<String> comboEstanterias;
    /**
     * Botón para confirmar la eliminación de la estantería seleccionada.
     */
    private JButton btnEliminar;
    /**
     * Botón para cancelar la operación y cerrar la ventana.
     */
    private JButton btnCancelar;

    /**
     * Controlador de presentación que gestiona las operaciones sobre estanterías.
     */
    private CtrlPresentacion ctrlPresentacion;

    /**
     * Crea la vista para eliminar una estantería.
     *
     * Precondición: ctrlPresentacion no es null.
     * Postcondición: Se crea la vista y se inicializan sus componentes.
     *
     * @param ctrlPresentacion Controlador de presentación, no debe ser null.
     */
    public VistaEliminarEstanteria(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la ventana.
     *
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: La ventana queda preparada con un comboBox para seleccionar la estantería a eliminar
     * y botones para confirmar o cancelar la operación.
     */
    private void inicializarComponentes() {
        setTitle("Eliminar Estantería");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        panelCentral.add(new JLabel("Seleccionar Estantería a Eliminar:"));
        comboEstanterias = new JComboBox<>();
        panelCentral.add(comboEstanterias);

        JPanel panelBotones = new JPanel();
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnEliminar.addActionListener(new ActionListener() {
            /**
             * Acción al pulsar el botón "Eliminar".
             *
             * Precondición: Debe haber una estantería seleccionada en el comboBox.
             * Postcondición: Si el usuario confirma y la eliminación es exitosa, se actualiza la lista de estanterías.
             *                 Si hay algún error, se muestra un mensaje de error.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEstanteria = (String) comboEstanterias.getSelectedItem();
                int confirm = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro de que deseas eliminar la estantería: " + nombreEstanteria + "?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        ctrlPresentacion.eliminarEstanteria(nombreEstanteria);
                        JOptionPane.showMessageDialog(null, "Estantería eliminada correctamente.");
                        actualizarListaEstanterias();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
     * Establece la lista de estanterías en el comboBox.
     *
     * Precondición: estanterias no es null.
     * Postcondición: El comboBox se rellena con las estanterías proporcionadas.
     *
     * @param estanterias Lista de nombres de las estanterías disponibles.
     */
    public void setEstanterias(ArrayList<String> estanterias) {
        comboEstanterias.removeAllItems();
        for (String estanteria : estanterias) {
            comboEstanterias.addItem(estanteria);
        }
    }

    /**
     * Actualiza la lista de estanterías, obteniendo las estanterías actuales del controlador.
     *
     * Precondición: -
     * Postcondición: El comboBox muestra la lista actualizada de estanterías.
     */
    private void actualizarListaEstanterias() {
        setEstanterias(ctrlPresentacion.getEstanterias());
    }

}
