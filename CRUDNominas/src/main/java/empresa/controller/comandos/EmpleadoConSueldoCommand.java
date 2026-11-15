package empresa.controller.comandos;

import empresa.controller.Comando;
import empresa.model.Empleado;
import empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Comando que busca un empleado por DNI y su sueldo y los entrega a una vista.
 * Se reutiliza para dos opciones que hacían exactamente lo mismo en la unidad 2
 * y solo se diferenciaban en la vista: "salario" (vista {@code salario}) y
 * "editar" (vista {@code editar}).
 *
 * @author Adrián Acevedo Reinoso
 */
public class EmpleadoConSueldoCommand implements Comando {

    /** Fachada con las operaciones de negocio. */
    private final EmpleadoService servicio;

    /** Vista a la que se entregan los datos. */
    private final String vista;

    /**
     * Crea el comando.
     *
     * @param servicio servicio que se usa para consultar el empleado y su sueldo
     * @param vista    nombre de la vista que muestra los datos, sin extensión
     */
    public EmpleadoConSueldoCommand(EmpleadoService servicio, String vista) {
        this.servicio = servicio;
        this.vista = vista;
    }

    /**
     * Deja en la petición el atributo "empleado" (o {@code null} si no existe)
     * y, si existe, el atributo "sueldo" (Double). Parámetro leído: dni.
     *
     * @param req petición HTTP
     * @return la vista indicada en el constructor
     * @throws SQLException si ocurre un error de base de datos
     */
    @Override
    public String ejecutar(HttpServletRequest req) throws SQLException {
        String dni = req.getParameter("dni");
        Empleado emp = servicio.buscarPorDni(dni);
        req.setAttribute("empleado", emp);
        if (emp != null) {
            req.setAttribute("sueldo", servicio.sueldoDe(dni));
        }
        return vista;
    }
}
