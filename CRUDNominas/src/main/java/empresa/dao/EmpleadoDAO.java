package empresa.dao;

import empresa.model.Empleado;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acceso a datos de los empleados (patrón DAO).
 *
 * <p>Es la abstracción de la que depende la capa de servicio (principio de
 * inversión de dependencias): {@code EmpleadoService} no sabe si los datos
 * están en MariaDB, en un fichero o en memoria. Para cambiar de almacén solo
 * hay que escribir otra implementación de esta interfaz.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public interface EmpleadoDAO {

    /**
     * Alta de un empleado junto con su nómina (sueldo calculado), de forma
     * atómica: si falla una de las dos inserciones no se guarda ninguna.
     *
     * @param emp empleado que se da de alta
     * @throws SQLException si ocurre un error de base de datos
     *                      (por ejemplo, DNI duplicado)
     */
    void altaEmpleado(Empleado emp) throws SQLException;

    /**
     * Obtiene todos los empleados, ordenados por DNI.
     *
     * @return lista con todos los empleados (vacía si no hay ninguno)
     * @throws SQLException si ocurre un error de base de datos
     */
    List<Empleado> listarEmpleados() throws SQLException;

    /**
     * Busca un empleado por su DNI exacto.
     *
     * @param dni DNI del empleado que se busca
     * @return el empleado encontrado, o {@code null} si no existe ninguno con ese DNI
     * @throws SQLException si ocurre un error de base de datos
     */
    Empleado buscarEmpleado(String dni) throws SQLException;

    /**
     * Búsqueda de empleados combinando filtros opcionales. Solo se aplican los
     * filtros que no son {@code null} ni están vacíos. El nombre y el DNI se
     * buscan por coincidencia parcial; el sexo, la categoría y los años, por
     * coincidencia exacta. El resultado se ordena por DNI.
     *
     * @param nombre    texto que debe contener el nombre (opcional)
     * @param dni       texto que debe contener el DNI (opcional)
     * @param sexo      sexo exacto, 'M' o 'F' (opcional)
     * @param categoria categoría exacta (opcional)
     * @param anyos     años de antigüedad exactos (opcional)
     * @return lista de empleados que cumplen todos los filtros (vacía si ninguno)
     * @throws SQLException si ocurre un error de base de datos
     */
    List<Empleado> buscarEmpleados(String nombre, String dni, String sexo,
                                   Integer categoria, Integer anyos) throws SQLException;

    /**
     * Actualiza el nombre, sexo, categoría y años de un empleado (identificado
     * por su DNI) y recalcula su sueldo, de forma atómica.
     *
     * @param emp empleado con los datos nuevos; su DNI identifica el registro a modificar
     * @throws SQLException si ocurre un error de base de datos
     */
    void actualizarEmpleado(Empleado emp) throws SQLException;
}
