package empresa.service;

import empresa.dao.EmpleadoDAO;
import empresa.dao.NominaDAO;
import empresa.exceptions.ReglaNegocioException;
import empresa.model.Empleado;

import java.sql.SQLException;
import java.util.List;

/**
 * Patrón <b>Facade</b> (estructural) / capa de servicio: ofrece a los comandos
 * una interfaz sencilla con las operaciones de la aplicación y esconde la
 * colaboración entre {@link EmpleadoDAO} y {@link NominaDAO}.
 *
 * <p>Por qué existe:</p>
 * <ul>
 *   <li>Los comandos (controlador) no conocen los DAO ni SQL: menos acoplamiento.</li>
 *   <li>Las reglas de negocio (DNI repetido, empleado inexistente) están aquí y
 *       no en el servlet: cada clase tiene una única responsabilidad (SRP).</li>
 *   <li>Depende de las interfaces DAO, que le llegan por el constructor
 *       (inversión de dependencias): se puede probar con DAO falsos.</li>
 * </ul>
 *
 * @author Adrián Acevedo Reinoso
 */
public class EmpleadoService {

    /** Acceso a los datos de los empleados. */
    private final EmpleadoDAO empleadoDAO;

    /** Acceso a los datos de las nóminas. */
    private final NominaDAO nominaDAO;

    /**
     * Crea el servicio con sus dependencias (inyección por constructor).
     *
     * @param empleadoDAO acceso a la tabla de empleados
     * @param nominaDAO   acceso a la tabla de nóminas
     */
    public EmpleadoService(EmpleadoDAO empleadoDAO, NominaDAO nominaDAO) {
        this.empleadoDAO = empleadoDAO;
        this.nominaDAO = nominaDAO;
    }

    /**
     * Devuelve todos los empleados, ordenados por DNI.
     *
     * @return lista de empleados (vacía si no hay ninguno)
     * @throws SQLException si ocurre un error de base de datos
     */
    public List<Empleado> listar() throws SQLException {
        return empleadoDAO.listarEmpleados();
    }

    /**
     * Busca un empleado por su DNI exacto.
     *
     * @param dni DNI del empleado
     * @return el empleado, o {@code null} si no existe
     * @throws SQLException si ocurre un error de base de datos
     */
    public Empleado buscarPorDni(String dni) throws SQLException {
        return empleadoDAO.buscarEmpleado(dni);
    }

    /**
     * Busca empleados combinando filtros opcionales (los {@code null} o vacíos
     * no se aplican).
     *
     * @param nombre    parte del nombre (opcional)
     * @param dni       parte del DNI (opcional)
     * @param sexo      'M' o 'F' (opcional)
     * @param categoria categoría exacta (opcional)
     * @param anyos     años exactos (opcional)
     * @return empleados que cumplen todos los filtros
     * @throws SQLException si ocurre un error de base de datos
     */
    public List<Empleado> buscar(String nombre, String dni, String sexo,
                                 Integer categoria, Integer anyos) throws SQLException {
        return empleadoDAO.buscarEmpleados(nombre, dni, sexo, categoria, anyos);
    }

    /**
     * Consulta el sueldo guardado de un empleado.
     *
     * @param dni DNI del empleado
     * @return el sueldo guardado (0 si no tiene nómina)
     * @throws SQLException si ocurre un error de base de datos
     */
    public Double sueldoDe(String dni) throws SQLException {
        return nominaDAO.obtenerSueldo(dni);
    }

    /**
     * Da de alta un empleado y su nómina. Regla de negocio: el DNI no puede
     * estar repetido.
     *
     * @param emp empleado ya validado
     * @throws ReglaNegocioException si ya existe un empleado con ese DNI
     * @throws SQLException          si ocurre un error de base de datos
     */
    public void crear(Empleado emp) throws SQLException {
        if (empleadoDAO.buscarEmpleado(emp.getDni()) != null) {
            throw new ReglaNegocioException("Ya existe un empleado con el DNI " + emp.getDni() + ".");
        }
        empleadoDAO.altaEmpleado(emp);
    }

    /**
     * Modifica un empleado y recalcula su sueldo. Regla de negocio: el
     * empleado tiene que existir.
     *
     * @param emp empleado con los datos nuevos (su DNI lo identifica)
     * @throws ReglaNegocioException si no existe ningún empleado con ese DNI
     * @throws SQLException          si ocurre un error de base de datos
     */
    public void actualizar(Empleado emp) throws SQLException {
        if (empleadoDAO.buscarEmpleado(emp.getDni()) == null) {
            throw new ReglaNegocioException("No existe ningún empleado con el DNI " + emp.getDni() + ".");
        }
        empleadoDAO.actualizarEmpleado(emp);
    }
}
