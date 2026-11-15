package empresa.controller.comandos;

import empresa.controller.Comando;
import empresa.exceptions.DatosNoCorrectosException;
import empresa.exceptions.ReglaNegocioException;
import empresa.model.Empleado;
import empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Comando "crear" (POST): da de alta un empleado con los datos del formulario
 * y muestra la vista {@code mensaje}.
 *
 * @author Adrián Acevedo Reinoso
 */
public class CrearCommand implements Comando {

    /** Fachada con las operaciones de negocio. */
    private final EmpleadoService servicio;

    /**
     * Crea el comando.
     *
     * @param servicio servicio que se usa para dar de alta al empleado
     */
    public CrearCommand(EmpleadoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Valida los datos (al crear el {@link Empleado}), da de alta al empleado
     * y deja en la petición el atributo "mensaje".
     *
     * @param req petición HTTP; parámetros: nombre, dni, sexo, categoria y anyos
     * @return la vista "mensaje"
     * @throws SQLException              si ocurre un error de base de datos
     * @throws DatosNoCorrectosException si los datos no son válidos
     * @throws ReglaNegocioException     si ya existe un empleado con ese DNI
     */
    @Override
    public String ejecutar(HttpServletRequest req) throws SQLException {
        servicio.crear(Peticion.empleadoDe(req));
        req.setAttribute("mensaje", "Empleado creado correctamente. Su sueldo se ha calculado automáticamente.");
        return "mensaje";
    }
}
