package edu.upc.prop.clusterxx.presentacion;

import edu.upc.prop.clusterxx.presentacion.Controladores.CtrlPresentacion;
import edu.upc.prop.clusterxx.presentacion.Vistas.VistaPrincipal;


/**
 * Clase Main que contiene el método principal de la aplicación.
 * Se encarga de inicializar el controlador de presentación e iniciar la interfaz gráfica.
 */
public class Main {

    
    /**
     * Método principal de la aplicación.
     * Precondición: -
     * Postcondición: Se crea un CtrlPresentacion, se inicializa la presentación y se inicia la interfaz gráfica.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    /**
                     * Ejecuta la inicialización del controlador de presentación.
                     *
                     * Precondición: -
                     * Postcondición: Se crea un CtrlPresentacion y se llama a inicializarPresentacion()
                     *                para mostrar la interfaz gráfica al usuario.
                     */
                    public void run() {
                        CtrlPresentacion ctrlPresentacion = null;
                        try {
                            ctrlPresentacion = new CtrlPresentacion();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            ctrlPresentacion.inicializarPresentacion();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
    }
}
