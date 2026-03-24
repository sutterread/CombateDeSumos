package pa.combatedesumos.Cliente.Modelo;

import java.io.IOException;
import java.net.Socket;

/**
 *  Realiza la conexion al serverSocket
 * @author Asus
 */
public class CnxSocket {

    private static Socket socket = null;

    private static String ipServidor;
    private static int puertoServidor;

    /**
     * Asigna los valores para iniciar la conexion con el servidor
     *
     * @param ip Ip del servidor
     * @param puerto Puerto del servidor
     */
    public static void configurarSocket(String ip, int puerto) {
        CnxSocket.ipServidor = ip;
        CnxSocket.puertoServidor = puerto;
    }

    /**
     * Conectarse al servidor
     *
     * @return socket
     */
    public static Socket conexion() {
        if (socket != null && socket.isConnected() && !socket.isClosed()) {
            return socket;
        }
        if (ipServidor == null || ipServidor.isEmpty() || puertoServidor <= 0) {
            throw new IllegalStateException("IP/PUERTO del socket no configurados.");
        }
        try {
            socket = new Socket(ipServidor, puertoServidor);
            return socket;
        } catch (IOException ioe) {
            throw new RuntimeException("No fue posible conectar al servidor socket", ioe);
        }
    }

    /**
     * Cierra el socket si esta abierto
     */
    public static void cerrarSocket() {
        if (socket != null && !socket.isClosed())
            try {
            socket.close();
        } catch (IOException ioe) {
            throw new RuntimeException("Error al cerrar el socket.", ioe);
            //liveracion de recursos por eso no se extiende
        }
    }

    /**
     * Verifica la conexion al socket
     * @return
     */
    public static boolean estaConectado(){
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
    
    /**
     * Obtiene la ip del servidor
     *
     * @return Ip del servidoe
     */
    public static String getIpServidor() {
        return ipServidor;
    }

    /**
     * Obtiene el puerto del servidor
     *
     * @return Puerto del servidor
     */
    public static int getPuertoServidor() {
        return puertoServidor;
    }
}
