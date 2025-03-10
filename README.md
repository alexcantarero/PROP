# PROP - ENTREGA 3 - GRUP 22.2

## Integrantes

- Marc Perpiña (marc.perpina-robert@estudiantat.upc.edu)
- Alejandro Comadran (alejandro.comadran@estudiantat.upc.edu)
- Sergio Utrilla (sergio.utrilla@estudiantat.upc.edu)
- Alex Cantarero (alex.cantarero@estudiantat.upc.edu)


## Descripción

Este proyecto es una aplicación que se usa para la gestión de productos y estanterías. Permite crear, modificar y consultar productos, así como gestionar distribuciones en estanterías, ordenándolas a través de algunos algoritmos.

## Directorios más importantes

- `build/`: Directorio de salida de los archivos compilados.
- `FONTS/`: Código fuente de la aplicación, donde se encuentran los archivos `.java`. de las clases, controladores y imágenes utilizadas.
- `DOCS/`: Documentación del proyecto, tales como el diagrama de casos de uso, diagrama UML, documentación de la primera entrega y ficheros de los juegos de prueba.
- `EXE/`: Ejecutable del programa y juegos de prueba.
- `build.gradle`: Fichero de configuración de Gradle, donde se definen las tareas de compilación, ejecución y testeo del programa.

## Ejecución del programa

1. Ir al directorio raíz del proyecto:
    ```sh
    subgrup-prop22.2/
    ```
2. Compila el proyecto:
    ```sh
    gradle build
    ```
   o bien:
    ```sh
    ./gradlew build
    ```
3. Ejecuta el programa:
    ```sh
    gradle run
    ```
   o también:
    ```sh
    ./gradlew run
    ```

## Estructura
El proyecto está formado siguiendo una arquitectura en tres capas: una capa de dominio con un CtrlDominio, una capa de presentacion con un CtrlPresentacion y una capa de persistencia con un CtrlPersistencia. Cada capa contiene más clases que son necesarias para el funcionamiento de la aplicación.

En la capa de presentación existe un fichero Main que inicializa la aplicación y se encarga de gestionar las ventanas y los controladores de la capa de presentación. Esta capa de presentación comunica las acciones del usuario con la capa de dominio, que se encarga de gestionar los datos y la lógica de la aplicación. La capa de dominio se comunica con la capa de persistencia para guardar y cargar los datos de la aplicación. 

Es por ello que en la capa de presentación se incluyen varias carpetas con los productos del catálogo inicial, las estanterias iniciales, los productos actuales en la sesión y las estanterías actuales de la sesión. A través del botón Guardar y Salir se pueden manterner los cambios hechos en cada sesión, y si se requiere volver a la configuración inicial, simplemente se puede hacer con el boton de Reiniciar.



