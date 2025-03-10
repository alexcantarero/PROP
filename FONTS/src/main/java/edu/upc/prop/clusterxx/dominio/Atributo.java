package edu.upc.prop.clusterxx.dominio;

/**
 * Clase <b>Atributo</b>.
 * <p>
 * Representa un atributo genérico que puede formar parte de un producto.
 * Existen dos subclases que heredan de este atributo:
 * <ul>
 *     <li>{@link Tipo}</li>
 *     <li>{@link Contexto}</li>
 * </ul>
 * Los atributos se usan para categorizar los productos dentro de la aplicación.
 */
public class Atributo {
    /**
     * Nombre del atributo. Debe ser único para cada tipo de atributo.
     */
    private String nombre;

    /**
     * Constructor de la clase <b>Atributo</b>.
     * <p>
     * Precondición: <b>nombre</b> no es null y representa el nombre único del atributo.
     * Postcondición: Se crea un objeto <b>Atributo</b> con el nombre indicado.
     *
     * @param nombre Nombre único que identifica el atributo.
     */
    public Atributo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del atributo.
     *
     * @return Cadena de texto con el nombre del atributo.
     */
    public String getNombre() {
        return nombre;
    }
}
