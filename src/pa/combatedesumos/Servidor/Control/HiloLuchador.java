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
public class HiloLuchador implements Runnable {

    /**
     * Socket de conexion con el cliente
     */
    private Socket socket;
    /**
     * Control principal del servidor
     */
    private SrvControlPrincipal srvControlPrincipal;

    private DataInputStream input;
    private DataOutputStream output;

    /**
     * Constructor del hilo luchador
     * @param socket
     * @param srvControlPrincipal
     */
    public HiloLuchador(Socket socket, SrvControlPrincipal srvControlPrincipal) {
        this.socket = socket;
        this.srvControlPrincipal = srvControlPrincipal;
    }

    /**
     * Flujo principal del hilo. Recibe los datos del luchador, delega al
     * control principal y notifica el resultado al cliente.
     */
    @Override
    public void run() {
        try {
            //incializar flujos
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            
            //recibir los datos del luchador
            String nombre = input.readUTF();
            double peso = input.readDouble();
            int cantidadKimarites = input.readInt();
            String[] kimarites = new String[cantidadKimarites];
            
            //meter los kimarites extraides en su array
            for(int i = 0; i<cantidadKimarites; i++){
                kimarites[i] = input.readUTF();
            }
            // pasar los datos al controlprincipal junto con la referencia porque esta vez ya no son 2 hilos si no varios
            srvControlPrincipal.registrarLuchador(nombre, peso, kimarites, this);
            
            //esperar el resultado
            synchronized (this) {
                wait();
            }
            
        } catch (IOException  | InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            cerrarConexion();
        }
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
