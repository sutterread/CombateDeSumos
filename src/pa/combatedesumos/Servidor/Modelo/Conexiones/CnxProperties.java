package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Conecta con el archivo de propiedades
 * @author Asus
 * PENDIENTE DETERMINAR SI SOLO DEJAMOS UNO O DOS PROPERTIES
 */
public class CnxProperties {
    
    private static int puerto;
    
    /**
     * Carga el archivo properties desde la ruta indicada.
     * @param ruta ruta del archivo properties
     * @return objeto Properties cargado
     * @throws IOException si el archivo no se puede leer
     */
    private static Properties cargarProps(String ruta) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(ruta);
        props.load(fis);
        fis.close();
        return props;
    }
    
    public static void cargaConfioguracion(String ruta) throws IOException{
        Properties props = cargarProps(ruta);
        puerto = Integer.parseInt(props.getProperty("PUERTO_SERVIDOR"));
    }
    
    public static int getPuerto(){
        return puerto;
    }
}
