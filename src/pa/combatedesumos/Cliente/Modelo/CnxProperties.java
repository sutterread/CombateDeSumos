package pa.combatedesumos.Cliente.Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase encargada de cargar la configuracion del cliente desde el archivo
 * properties. Maneja tanto la configuracion de red como las tecnicas kimarite
 * por categoria.
*
* @author Asus, Sergio
 */
public class CnxProperties {

    private static String ip;
    private static int puerto;
    private static String[] kimarites;
    private static String nombreCategoria;

    /**
     * Metodo privado para la clase, para cargar el properties
     *
     * @param ruta
     * @return el properties
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static Properties cargarProps(String ruta) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(ruta);
        props.load(fis);
        fis.close();
        return props;
    }

    /**
     * Carga la ip y el puerto del servidor desde el properties
     *
     * @param ruta
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void cargaConfiguracion(String ruta) throws FileNotFoundException, IOException {
        Properties props = cargarProps(ruta);
        ip = props.getProperty("IP_SOCKET");
        puerto = Integer.parseInt(props.getProperty("PUERTO_SOCKET"));
    }

    /**
     * Carga las categorias disponibles desde el properties
     *
     * @param ruta
     * @return arreglo con los ids de las categorias
     * @throws IOException
     */
    public static String[] cargarCategorias(String ruta) throws IOException {
        return cargarProps(ruta).getProperty("categorias").split(",");
    }

    /**
     * Carga los kimarites para una categoria especifica
     *
     * @param ruta
     * @param categoriaId
     * @throws IOException
     */
    public static void cargarKimarites(String ruta, String categoriaId) throws IOException {
        Properties props = cargarProps(ruta);
        String km = props.getProperty("categoria." + categoriaId + ".kimarites");
        kimarites = km.split(",");
        nombreCategoria = props.getProperty("categoria." + categoriaId + ".nombre");
    }

    public static String getIp() {
        return ip;
    }

    public static int getPuerto() {
        return puerto;
    }

    public static String[] getKimarites() {
        return kimarites;
    }

    public static String getNombreCategoria() {
        return nombreCategoria;
    }

}