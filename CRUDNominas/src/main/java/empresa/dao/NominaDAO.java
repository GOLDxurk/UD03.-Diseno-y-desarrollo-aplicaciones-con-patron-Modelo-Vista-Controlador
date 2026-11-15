package empresa.dao;

import empresa.model.Empleado;

import java.sql.SQLException;

/**
 * Contrato de acceso a datos de las nóminas (patrón DAO).
 * Igual que {@link EmpleadoDAO}, es la abstracción de la que depende la capa
 * de servicio.
 *
 * @author Adrián Acevedo Reinoso
 */
public interface NominaDAO {

    /**
     * Consulta el sueldo guardado de un empleado.
     *
     * @param dni DNI del empleado
     * @return el sueldo guardado, o 0 si el empleado no tiene nómina
     * @throws SQLException si ocurre un error de base de datos
     */
    Double obtenerSueldo(String dni) throws SQLException;

    /**
     * Calcula el sueldo del empleado y lo guarda (lo crea o lo actualiza).
     * Abre su propia conexión y transacción.
     *
     * @param emp empleado cuyo sueldo se calcula y se guarda
     * @throws SQLException si ocurre un error de base de datos
     */
    void asignarSueldo(Empleado emp) throws SQLException;
}
