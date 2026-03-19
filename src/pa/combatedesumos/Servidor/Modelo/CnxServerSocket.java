package pa.combatedesumos.Servidor.Modelo;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * Metodos y atributos estaticos
 * @author Asus
 */
public class CnxServerSocket {
    
    private static ServerSocket serverSocket;
    private static int puerto;
    
    /**
     * Le asigna el numero del puerto
     * @param entrada
     */
    public static void configurar(int entrada){
        puerto = entrada;
    }
    
    /**
     * Establece el serverSocket
     * @return serverSocket
     */
    public static ServerSocket conexion(){
        if(puerto <= 0){
            throw new IllegalStateException("PUERTO_SERVIDOR no configurado");
        }
        if(serverSocket != null && !serverSocket.isClosed()){
            return serverSocket;
        }
        try{
            //crea el serverSocket en el puerto PREVIAMENTE configurado
            serverSocket = new ServerSocket(puerto);
            return serverSocket;
        } catch(IOException ioe){
            throw new RuntimeException("No fue posible iniciar el ServerSocket", ioe);
        }
    }
    
    /**
     * Cierra el serverSocket si esta abierto
     */
    public static void cerrar(){
        if(serverSocket != null && !serverSocket.isClosed()){
            try {
                serverSocket.close();
            } catch (IOException ignored) {
                
            }
        }
    }
    
    /**
     * comprueba si el serversocket esta activo o cerrado
     * @return true o false
     */
    public static boolean activo(){
        return serverSocket != null && !serverSocket.isClosed();
    }
    
    /**
     * Metodo para obtener el puerto en el que corre el ss
     * @return puerto
     */
    public static int getPuerto(){
        return puerto;
    }
    
}
