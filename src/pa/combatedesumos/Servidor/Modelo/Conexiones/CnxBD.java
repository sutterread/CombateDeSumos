package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 * Implementa el patrón Singleton con doble verificación para garantizar
 * una única conexión reutilizable a lo largo de toda la aplicación.
 *
 * @author Asus
 */
public class CnxBD {

    /** Conexión única con la base de datos (Singleton). */
    private static Connection cn = null;

    /** Lock para sincronización al crear la conexión. */
    private static final Object lock = new Object();

    /** URL de conexión a la base de datos. */
    private static String URLDB = null;

    /** Usuario de la base de datos. */
    private static String usuario = null;

    /** Contraseña del usuario de la base de datos. */
    private static String contrasena = null;

    /**
     * Constructor privado — esta clase no se instancia.
     */
    private CnxBD() {}

    /**
     * Carga las credenciales de conexión desde un archivo {@code .properties}.
     *
     * El archivo debe contener las siguientes propiedades:
     * <ul>
     * <li>{@code db.URLDB}</li>
     * <li>{@code db.usuario}</li>
     * <li>{@code db.contrasena}</li>
     * </ul>
     *
     * @param rutaProperties ruta del archivo {@code .properties}
     * @throws RuntimeException si ocurre un error al leer el archivo
     */
    public static void cargarCredenciales(String rutaProperties) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(rutaProperties)) {
            props.load(fis);
            URLDB = props.getProperty("db.URLDB");
            usuario = props.getProperty("db.usuario");
            contrasena = props.getProperty("db.contrasena");
        } catch (Exception e) {
            throw new RuntimeException("No se puede cargar el archivo properties", e);
        }
    }

    /**
     * Retorna la conexión Singleton con la base de datos.
     * Si la conexión no existe o está cerrada la crea usando doble verificación.
     *
     * @return objeto {@link Connection} listo para su uso
     * @throws RuntimeException si ocurre un error al establecer la conexión
     */
    public static Connection getConexion() {
        try {
            if (cn == null || cn.isClosed()) {
                synchronized (lock) {
                    if (cn == null || cn.isClosed()) {
                        cn = DriverManager.getConnection(URLDB, usuario, contrasena);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("No se puede establecer la conexión con la base de datos", ex);
        }
        return cn;
    }

    /**
     * Cierra la conexión actual con la base de datos si está abierta.
     */
    public static void desconectar() {
        synchronized (lock) {
            try {
                if (cn != null && !cn.isClosed()) {
                    cn.close();
                }
                cn = null;
            } catch (SQLException e) {
                // Liberación de recursos — no se propaga
            }
        }
    }
}
