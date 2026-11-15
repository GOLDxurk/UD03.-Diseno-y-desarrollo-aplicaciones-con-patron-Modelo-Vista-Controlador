package empresa.controller.comandos;

import empresa.controller.Comando;
import empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Comando "listar": carga todos los empleados y muestra la vista
 * {@code listado}.
 *
 * @author Adrián Acevedo Reinoso
 */
public class ListarCommand implements Comando {

    /** Fachada con las operaciones de negocio. */
    private final EmpleadoService servicio;

    /**
     * Crea el comando.
     *
     * @param servicio servicio que se usa para consultar los empleados
     */
    public ListarCommand(EmpleadoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Deja en la petición el atributo "empleados" (List&lt;Empleado&gt;).
     *
     * @param req petición HTTP
     * @return la vista "listado"
     * @throws SQLException si ocurre un error de base de datos
     */
    @Override
    public String ejecutar(HttpServletRequest req) throws SQLException {
        req.setAttribute("empleados", servicio.listar());
        return "listado";
    }
}
