package pa.combatedesumos.Servidor.Modelo.DAO;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxRAF;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 *
 * @author Sergio Vanegas
 */
public class RAFDAO {
    /**
     * Añade un luchador al RAF con sus datos y si ganó o perdió
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
     * Añade una línea al RAF con el fin de separar cada par de luchadores por combate
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
    /**
     * Pone en una lista el contenido del RAF dejándolo preparado para mostrarse por consola
     * @return lista con 
     */
    public List<String> leerRAF() {
        List<String> lista = new ArrayList<>();
        try {
            RandomAccessFile raf = CnxRAF.conexion();
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                String nombre = raf.readUTF();
                if (nombre.startsWith("---")) {
                    lista.add(nombre); // lo agrega como línea separadora y sigue
                    continue;
                }
            int peso = raf.readInt();
            int victorias = raf.readInt();
            String resultado = raf.readUTF();
            lista.add(nombre + " | " + peso + "kg | Victorias: " + victorias + " | " + resultado);
}
        } catch (IOException e) {
            throw new RuntimeException("Error al leer RAF", e);
    }
        return lista;
}
}
