package pa.combatedesumos.Servidor.Modelo.DAO;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxRAF;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 *
 * @author Sergio Vanegas
 */
public class RAFDAO {
    
    private File archivo;
    
    /**
     * Añade un luchador al RAF es decir un registro
     * @param luchador
     * @param resultado
     */
    public void añadirLuchador(LuchadorDTO luchador, String resultado) {
        try {
            RandomAccessFile raf = CnxRAF.conexion();
            raf.seek(raf.length());
            raf.writeUTF(luchador.getNombre());
            raf.writeDouble(luchador.getPeso());
            raf.writeInt(luchador.getVictorias());
            raf.writeUTF(resultado); // "GANADOR" o "PERDEDOR" — lo decide el Control
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en RAF", e);
        }
    }
    
    /**
     * Separadorcito 
     */
    public void escribirSeparador() {
        try {
            RandomAccessFile raf = CnxRAF.conexion();
            raf.seek(raf.length());
            raf.writeUTF("--------------------------------");
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir separador", e);
        }
    }
    
  
}
