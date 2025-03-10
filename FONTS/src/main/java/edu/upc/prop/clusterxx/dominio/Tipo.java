package edu.upc.prop.clusterxx.dominio;

/**
 * Clase <b>Tipo</b> que es una subclase de {@link Atributo}.
 * <p>
 * Un atributo tipo viene identificado por un nombre y un ID.
 * La clase contiene las funciones básicas para el manejo de los atributos {@code Tipo}.
 */
public class Tipo extends Atributo {
    private final int id;

    /**
     * Constructor de la clase <b>Tipo</b>.
     *
     * <p><b>Pre:</b> Requiere el nombre del atributo de la superclase {@code Atributo} y un entero {@code id} único.</p>
     *
     * <p><b>Post:</b> Crea un nuevo objeto {@code Tipo} con el nombre y el ID especificados.</p>
     *
     * @param nombre El nombre del atributo. Debe ser único y no vacío.
     * @param id     El identificador único del atributo tipo.
     */
    public Tipo(String nombre, int id) {
        super(nombre);
        this.id = id;
    }

    /// GETTERS

    /**
     * Obtiene el nombre del atributo tipo.
     *
     * <p><b>Pre:</b> El objeto {@code Tipo} debe estar correctamente inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve el nombre del atributo tipo.</p>
     *
     * @return El nombre del atributo tipo.
     */
    @Override
    public String getNombre() {
        return super.getNombre();
    }

    /**
     * Obtiene el ID del atributo tipo.
     *
     * <p><b>Pre:</b> El objeto {@code Tipo} debe estar correctamente inicializado.</p>
     *
     * <p><b>Post:</b> Devuelve el ID del atributo tipo.</p>
     *
     * @return El identificador único del atributo tipo.
     */
    public int getId() {
        return this.id;
    }
}
