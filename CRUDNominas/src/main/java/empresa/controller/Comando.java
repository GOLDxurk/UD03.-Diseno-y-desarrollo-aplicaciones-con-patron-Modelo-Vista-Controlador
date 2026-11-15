package empresa.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Patrón <b>Command</b> (de comportamiento): cada acción que el usuario puede
 * pedir a la aplicación (listar, buscar, crear...) es un objeto que implementa
 * esta interfaz.
 *
 * <p>Sustituye al {@code switch} gigante del servlet de la unidad 2. Así el
 * {@link FrontController} no sabe qué hace cada acción: solo la ejecuta y
 * muestra la vista que le indica. Para añadir una acción nueva basta con crear
 * una clase que implemente {@code Comando} y registrarla en
 * {@link ComandoFactory} (principio abierto/cerrado), sin tocar el
 * controlador.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public interface Comando {

    /**
     * Ejecuta la acción: lee los parámetros de la petición, llama a la capa de
     * servicio y deja en la petición (con {@code setAttribute}) los datos que
     * necesita la vista (el Modelo que usará el JSP).
     *
     * @param req petición HTTP
     * @return nombre de la vista (JSP de /WEB-INF/views, sin la extensión)
     *         que debe mostrarse como resultado
     * @throws SQLException si ocurre un error de base de datos; lo captura el
     *                      {@link FrontController}
     */
    String ejecutar(HttpServletRequest req) throws SQLException;
}
