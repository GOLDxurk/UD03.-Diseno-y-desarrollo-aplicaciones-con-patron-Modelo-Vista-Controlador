package empresa.controller.comandos;

import empresa.controller.Comando;
import empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Comando "buscar": busca empleados con los filtros del formulario (todos
 * opcionales) y muestra la vista {@code resultados}.
 *
 * @author Adrián Acevedo Reinoso
 */
public class BuscarCommand implements Comando {

    /** Fachada con las operaciones de negocio. */
    private final EmpleadoService servicio;

    /**
     * Crea el comando.
     *
     * @param servicio servicio que se usa para buscar empleados
     */
    public BuscarCommand(EmpleadoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Deja en la petición el atributo "empleados" (List&lt;Empleado&gt;, puede
     * estar vacía). Parámetros leídos: nombre, dni, sexo, categoria y anyos.
     *
     * @param req petición HTTP
     * @return la vista "resultados"
     * @throws SQLException          si ocurre un error de base de datos
     * @throws NumberFormatException si categoría o años no son números
     */
    @Override
    public String ejecutar(HttpServletRequest req) throws SQLException {
        req.setAttribute("empleados", servicio.buscar(
                req.getParameter("nombre"),
                req.getParameter("dni"),
                req.getParameter("sexo"),
                Peticion.aEntero(req.getParameter("categoria")),
                Peticion.aEntero(req.getParameter("anyos"))));
        return "resultados";
    }
}
