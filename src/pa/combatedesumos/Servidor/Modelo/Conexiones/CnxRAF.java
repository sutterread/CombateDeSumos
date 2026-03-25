package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Asus
 */
public class CnxRAF {
    
    private static RandomAccessFile raf;
    private static String ruta;
    
    public static void configurar(String ruta){
        CnxRAF.ruta = ruta;
    }
    
    public static RandomAccessFile conexion(){
        if(ruta == null || ruta.isEmpty()){
            throw new IllegalStateException("Ruta del archivo RAF no configurada");
        }
        if(raf != null){
            return raf;
        }
        try{
            raf = new RandomAccessFile(ruta, "rw");
            return raf;
        }catch(IOException e){
            throw new RuntimeException("No se pudo abrir el archivo de acceso aleatorio", e);
        }
    }
    
    public static void cerrar(){
        if(raf != null){
            try{
                raf.close();
                raf = null;
            } catch(IOException e){}
        }
    }

    
}
