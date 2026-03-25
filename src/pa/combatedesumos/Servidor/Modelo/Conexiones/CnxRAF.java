package pa.combatedesumos.Servidor.Modelo.Conexiones;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase utilitaria estática que gestiona la conexión con el archivo
 * de acceso aleatorio donde se registran los resultados de los combates.
 *
 * @author
 */
public class CnxRAF {
    
    private static RandomAccessFile raf;
    private static String ruta;
    
    /**
     * Asigna la ruta del archivo de acceso aleatorio
     * @param ruta
     */
    public static void configurar(String ruta){
        CnxRAF.ruta = ruta;
    }
    
    /**
     * Abre y retorna el archivo de acceso aleatorio en modo lectura/escritura.
     * Si ya está abierto, retorna la instancia existente.
     * @return RAF abierto
     */
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
    
    /**
     * Cierra el raf si esta abierto
     */
    public static void cerrar(){
        if(raf != null){
            try{
                raf.close();
                raf = null;
            } catch(IOException e){}
        }
    }

    
}
