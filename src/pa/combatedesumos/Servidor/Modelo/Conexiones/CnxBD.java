package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 *
 * Permite cargar las credenciales desde un archivo {@code .properties}
 * y establecer una conexión utilizando {@link DriverManager}.
 *
 * Las credenciales se almacenan en atributos estáticos para que puedan
 * ser utilizadas en cualquier parte de la aplicación.
 *
 * @author Valen Aguilar
 */
public class CnxBD {

    /**Conexión actual con la base de datos.*/
    private static Connection cn = null;

    /**URL de conexión a la base de datos.*/
    private static String URLDB = null;

    /**Usuario de la base de datos.*/
    private static String usuario = null;

    /**Contraseña del usuario de la base de datos.*/
    private static String contrasena = null;

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
     * @param rutaProperties ruta del archivo {@code .properties} que contiene
     * las credenciales de la base de datos
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
     * Establece y retorna una conexión con la base de datos utilizando
     * las credenciales previamente cargadas.
     *
     * @return objeto {@link Connection} que representa la conexión con
     * la base de datos
     * @throws RuntimeException si ocurre un error al establecer la conexión
     */
    public static Connection getConexion() {
        try {
            cn = DriverManager.getConnection(URLDB, usuario, contrasena);
        } catch (SQLException ex) {
            throw new RuntimeException("No se puede establecer la conexión con la base de datos", ex);
        }
        return cn;
    }

    /**
     * Cierra la conexión actual con la base de datos si existe.
     *
     * Si la conexión está abierta, se invoca el método {@link Connection#close()}
     * para liberar los recursos asociados.
     */
    public static void desconectar() {
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException e) {
            // No se propaga la excepción porque el cierre es un proceso de liberación de recursos
        }
    }
}
