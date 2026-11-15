package empresa.conexion;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Gestiona la conexión con la base de datos MariaDB "empresa"
 * mediante un pool de conexiones (Apache DBCP2).
 *
 * <p>Sigue el patrón Singleton (ya presente en la unidad 2): solo existe un
 * pool compartido por todos los DAO. Como ahora todas las peticiones llegan al
 * mismo {@code FrontController} y pueden ser concurrentes, la creación del pool
 * está sincronizada.</p>
 *
 * @author Adrián Acevedo Reinoso
 */
public class conexionDB {

    /** Pool de conexiones compartido; se crea la primera vez que se necesita. */
    private static BasicDataSource dataSource = null;

    /**
     * Devuelve el pool de conexiones, creándolo y configurándolo
     * (driver, usuario, URL y tamaños del pool) si todavía no existe.
     *
     * @return el {@link DataSource} configurado
     */
    private static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("camael");

            dataSource.setUrl("jdbc:mariadb://localhost:3306/empresa?useTimezone=true&serverTimezone=UTC");

            dataSource.setInitialSize(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxTotal(20);
        }
        return dataSource;
    }

    /**
     * Obtiene una conexión del pool. Quien la pide debe cerrarla
     * (por ejemplo con try-with-resources) para devolverla al pool.
     *
     * @return una conexión abierta a la base de datos
     * @throws SQLException si no se puede obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

}
