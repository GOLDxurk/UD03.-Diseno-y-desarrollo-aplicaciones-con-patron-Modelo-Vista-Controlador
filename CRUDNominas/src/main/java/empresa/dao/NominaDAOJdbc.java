package empresa.dao;

import empresa.conexion.conexionDB;
import empresa.model.Empleado;
import empresa.nomina.CalculadoraSueldo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementación JDBC (MariaDB) de {@link NominaDAO}: lee y guarda los sueldos
 * en la tabla Nominas.
 *
 * <p>El cálculo del sueldo no se hace aquí: se delega en una
 * {@link CalculadoraSueldo} (patrón Strategy) que se recibe por el constructor
 * (inyección de dependencias).</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public class NominaDAOJdbc implements NominaDAO {

    /** Estrategia de cálculo de sueldos, inyectada desde fuera. */
    private final CalculadoraSueldo calculadora;

    /**
     * Crea el DAO con la estrategia de cálculo que debe usar.
     *
     * @param calculadora estrategia que calcula el sueldo de un empleado
     */
    public NominaDAOJdbc(CalculadoraSueldo calculadora) {
        this.calculadora = calculadora;
    }

    /** {@inheritDoc} */
    @Override
    public Double obtenerSueldo(String dni) throws SQLException {
        String sql = "SELECT sueldo FROM Nominas WHERE dni = ?";
        try (Connection con = conexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("sueldo") : 0.0;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void asignarSueldo(Empleado emp) throws SQLException {
        try (Connection con = conexionDB.getConnection()) {
            con.setAutoCommit(false);
            try {
                asignarSueldo(con, emp);
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    /**
     * Calcula e inserta la nómina de un empleado usando una conexión ya abierta.
     * Si el empleado ya tenía nómina, se actualiza su sueldo.
     * No hace commit ni rollback: lo gestiona quien llama, para poder incluirla
     * en su misma transacción (alta o modificación del empleado en
     * {@link EmpleadoDAOJdbc}).
     *
     * @param con conexión abierta en la que se ejecuta la inserción
     * @param emp empleado cuyo sueldo se calcula y se guarda
     * @throws SQLException si ocurre un error de base de datos
     */
    void asignarSueldo(Connection con, Empleado emp) throws SQLException {
        // Tabla Nominas: ID autonumérico, SUELDO y dni. Se actualiza la fila del
        // empleado si existe y, si no, se inserta.
        String sqlUpdate = "UPDATE Nominas SET sueldo = ? WHERE dni = ?";
        String sqlInsert = "INSERT INTO Nominas(dni,sueldo) VALUES (?,?)";
        double sueldo = calculadora.sueldo(emp);

        try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setDouble(1, sueldo);
            ps.setString(2, emp.dni);
            if (ps.executeUpdate() > 0) {
                return;
            }
        }
        try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
            ps.setString(1, emp.dni);
            ps.setDouble(2, sueldo);
            ps.executeUpdate();
        }
    }
}
