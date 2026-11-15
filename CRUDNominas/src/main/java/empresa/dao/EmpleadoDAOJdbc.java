package empresa.dao;

import empresa.conexion.conexionDB;
import empresa.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC (MariaDB) de {@link EmpleadoDAO}: altas, consultas y
 * modificaciones sobre la tabla Empleados.
 *
 * <p>Ya no tiene métodos estáticos: es un objeto que se crea una sola vez en
 * {@code FrontController} y se inyecta en la capa de servicio. El sueldo de
 * cada empleado se guarda en la tabla Nominas a través de {@link NominaDAOJdbc},
 * que se recibe por el constructor para poder compartir la misma transacción.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public class EmpleadoDAOJdbc implements EmpleadoDAO {

    /** Colaborador que guarda el sueldo dentro de la transacción del empleado. */
    private final NominaDAOJdbc nominaDAO;

    /**
     * Crea el DAO con el colaborador que guarda las nóminas.
     *
     * @param nominaDAO DAO de nóminas que se usa en las altas y modificaciones
     */
    public EmpleadoDAOJdbc(NominaDAOJdbc nominaDAO) {
        this.nominaDAO = nominaDAO;
    }

    /** {@inheritDoc} */
    @Override
    public void altaEmpleado(Empleado emp) throws SQLException {
        String sqlEmp = "INSERT INTO Empleados(dni,nombre,sexo,categoria,anyos) VALUES (?,?,?,?,?)";

        try (Connection con = conexionDB.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlEmp)) {
                ps.setString(1, emp.dni);
                ps.setString(2, emp.nombre);
                ps.setString(3, String.valueOf(emp.sexo));
                ps.setInt(4, emp.getCategoria());
                ps.setInt(5, emp.getAnyos());
                ps.executeUpdate();

                // La nómina la guarda NominaDAOJdbc, en la misma transacción que el empleado
                nominaDAO.asignarSueldo(con, emp);

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Empleado> listarEmpleados() throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados ORDER BY dni";

        try (Connection con = conexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(crearEmpleado(rs));
            }
        }
        return lista;
    }

    /** {@inheritDoc} */
    @Override
    public Empleado buscarEmpleado(String dni) throws SQLException {
        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados WHERE dni = ?";
        try (Connection con = conexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? crearEmpleado(rs) : null;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Empleado> buscarEmpleados(String nombre, String dni, String sexo,
                                          Integer categoria, Integer anyos) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            sql.append(" AND nombre LIKE ?");
            params.add("%" + nombre.trim() + "%");
        }
        if (dni != null && !dni.isEmpty()) {
            sql.append(" AND dni LIKE ?");
            params.add("%" + dni.trim() + "%");
        }
        if (sexo != null && !sexo.isEmpty()) {
            sql.append(" AND sexo = ?");
            params.add(sexo.trim());
        }
        if (categoria != null) {
            sql.append(" AND categoria = ?");
            params.add(categoria);
        }
        if (anyos != null) {
            sql.append(" AND anyos = ?");
            params.add(anyos);
        }
        sql.append(" ORDER BY dni");

        List<Empleado> lista = new ArrayList<>();
        try (Connection con = conexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearEmpleado(rs));
                }
            }
        }
        return lista;
    }

    /** {@inheritDoc} */
    @Override
    public void actualizarEmpleado(Empleado emp) throws SQLException {
        String sql = "UPDATE Empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";

        try (Connection con = conexionDB.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, emp.nombre);
                ps.setString(2, String.valueOf(emp.sexo));
                ps.setInt(3, emp.getCategoria());
                ps.setInt(4, emp.getAnyos());
                ps.setString(5, emp.dni);
                ps.executeUpdate();

                // El sueldo se recalcula en la misma transacción que la modificación
                nominaDAO.asignarSueldo(con, emp);

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    /**
     * Construye un {@link Empleado} con la fila actual del {@link ResultSet}.
     * Evita repetir este código en todas las consultas (principio DRY).
     *
     * @param rs resultado de una consulta, posicionado en la fila a leer
     *           (debe tener las columnas nombre, dni, sexo, categoria y anyos)
     * @return el empleado creado con los datos de la fila
     * @throws SQLException si no se puede leer alguna columna
     */
    private static Empleado crearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getString("nombre"),
                rs.getString("dni"),
                rs.getString("sexo").charAt(0),
                rs.getInt("categoria"),
                rs.getInt("anyos"));
    }
}
