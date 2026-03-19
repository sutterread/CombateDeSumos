package pa.combatedesumos.Servidor.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hilo encargado de atender a un luchador conectado via socket. Recibe los
 * datos del luchador y delega al control principal.
 *
 * @author Asus
 */
public class SHiloLuchador implements Runnable {

    /**
     * Socket de conexion con el cliente
     */
    private Socket socket;
    /**
     * Control principal del servidor
     */
    private SControlPrincipal cControlPrincipal;

    private DataInputStream input;
    private DataOutputStream output;

    /**
     * Constructor del hilo luchador
     * @param socket
     * @param cControlPrincipal
     */
    public SHiloLuchador(Socket socket, SControlPrincipal cControlPrincipal) {
        this.socket = socket;
        this.cControlPrincipal = cControlPrincipal;
    }

    /**
     * Flujo principal del hilo. Recibe los datos del luchador, delega al
     * control principal y notifica el resultado al cliente.
     */
    @Override
    public void run() {
    }

    private void cerrarConexion() {
        try{
            if(input != null){
                input.close();
            }
            if(output != null){
                output.close();
            }
            if(socket != null && !socket.isClosed()){
                socket.close();
            }
        } catch(IOException e){}
    }
    
}
