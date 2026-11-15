package empresa.controller.comandos;

import empresa.controller.Comando;
import empresa.exceptions.DatosNoCorrectosException;
import empresa.exceptions.ReglaNegocioException;
import empresa.model.Empleado;
import empresa.service.EmpleadoService;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

/**
 * Comando "actualizar" (POST): modifica un empleado con los datos del
 * formulario y muestra la vista {@code mensaje}.
 *
 * @author Adrián Acevedo Reinoso
 */
public class ActualizarCommand implements Comando {

    /** Fachada con las operaciones de negocio. */
    private final EmpleadoService servicio;

    /**
     * Crea el comando.
     *
     * @param servicio servicio que se usa para modificar al empleado
     */
    public ActualizarCommand(EmpleadoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Valida los datos (al crear el {@link Empleado}), modifica al empleado,
     * se recalcula su sueldo y deja en la petición el atributo "mensaje".
     *
     * @param req petición HTTP; parámetros: nombre, dni, sexo, categoria y anyos
     * @return la vista "mensaje"
     * @throws SQLException              si ocurre un error de base de datos
     * @throws DatosNoCorrectosException si los datos no son válidos
     * @throws ReglaNegocioException     si no existe el empleado
     */
    @Override
    public String ejecutar(HttpServletRequest req) throws SQLException {
        servicio.actualizar(Peticion.empleadoDe(req));
        req.setAttribute("mensaje", "Empleado actualizado correctamente. Su sueldo se ha recalculado.");
        return "mensaje";
    }
}
