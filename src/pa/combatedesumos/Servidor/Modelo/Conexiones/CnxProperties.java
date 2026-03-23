package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase utilitaria estática que carga y expone el archivo de propiedades
 * del servidor. Patrón Singleton implícito mediante métodos estáticos.
 * La usan las clases CnxBD y CnxProperties
 *
 * @author Asus
 */
public class CnxProperties {
    
    private static final Properties props = new Properties();

    public CnxProperties() {
    }
    
    public static void cargar(String ruta) throws FileNotFoundException, IOException{
        try(FileInputStream fis = new FileInputStream(ruta)){
            props.load(fis);
        }
    }
    
    public static String getValor(String propiedad){
        return props.getProperty(propiedad);
    }
}
