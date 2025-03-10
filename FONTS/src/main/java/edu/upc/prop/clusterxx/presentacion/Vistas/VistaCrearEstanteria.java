package edu.upc.prop.clusterxx.presentacion.Vistas;

import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que representa la vista para crear una nueva estantería.
 * Permite al usuario introducir un nombre para la estantería y crearla.
 */
public class VistaCrearEstanteria extends JFrame {
    /**
     * Campo de texto donde se introduce el nombre de la nueva estantería.
     */
    private JTextField textNombreEstanteria;
    /**
     * Botón para aceptar y crear la estantería.
     */
    private JButton btnAceptar;
    /**
     * Botón para cancelar la operación y cerrar la ventana.
     */
    private JButton btnCancelar;

    /**
     * Controlador de presentación para gestionar la creación de la estantería.
     */
    private CtrlPresentacion ctrlPresentacion;

    /**
     * Crea la vista para la creación de una estantería.
     *
     * Precondición: ctrlPresentacion no es null.
     * Postcondición: Se crea la vista y se inicializan los componentes gráficos.
     *
     * @param ctrlPresentacion Controlador de presentación que gestiona las operaciones sobre estanterías.
     */
    public VistaCrearEstanteria(CtrlPresentacion ctrlPresentacion) {
        this.ctrlPresentacion = ctrlPresentacion;
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes de la ventana.
     *
     * Precondición: Existe un controlador de presentación válido.
     * Postcondición: La ventana está preparada con campos para introducir el nombre de la estantería
     *                y botones de aceptar/cancelar.
     */
    private void inicializarComponentes() {
        setTitle("Crear Estantería");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel(new GridLayout(2, 2));
        panelCentral.add(new JLabel("Introducir Nombre Estantería:"));
        textNombreEstanteria = new JTextField();
        panelCentral.add(textNombreEstanteria);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAceptar.addActionListener(new ActionListener() {
            /**
             * Acción al pulsar el botón "Aceptar".
             *
             * Precondición: El campo de texto puede estar vacío o no.
             * Postcondición: Si el nombre es válido, se crea la estantería y se muestra un mensaje de confirmación.
             *                En caso de error, se muestra un mensaje de error.
             *                Finalmente, se cierra la ventana.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreEstanteria = textNombreEstanteria.getText();
                try {
                    ctrlPresentacion.crearEstanteria(nombreEstanteria);
                    JOptionPane.showMessageDialog(null, "Estantería creada correctamente.");
                    dispose();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            /**
             * Acción al pulsar el botón "Cancelar".
             *
             * Precondición: -
             * Postcondición: Se cierra la ventana sin crear la estantería.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
