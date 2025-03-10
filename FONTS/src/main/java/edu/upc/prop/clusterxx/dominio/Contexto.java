package edu.upc.prop.clusterxx.dominio;

/**
 * Clase <b>Contexto</b> que extiende de {@link Atributo}.
 * <p>
 * Representa un atributo de tipo "Contexto" dentro del sistema, identificado por un nombre.
 * Forma parte de la clasificación de los productos junto con los tipos.
 */
public class Contexto extends Atributo {

    /**
     * Crea un contexto con el nombre indicado.
     * <p>
     * Precondición: <b>nombre</b> no es null y es único.
     * Postcondición: Se crea un contexto con el nombre especificado.
     *
     * @param nombre Nombre único que identifica el contexto.
     */
    public Contexto(String nombre){
        super(nombre);
    }
}
