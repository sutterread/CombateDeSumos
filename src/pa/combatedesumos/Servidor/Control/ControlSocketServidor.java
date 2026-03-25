package pa.combatedesumos.Servidor.Control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxServerSocket;


/**
 * Clase encargada de gestionar el ServerSocket del servidor. Acepta 
 * conexiones y lanza un hilo por cada luchador.
 *
 * @author Asus
 */
public class ControlSocketServidor implements Runnable {

    private SrvControlPrincipal srvControlPrincipal;
    private boolean servidorActivo;
    private Socket socket;
    
    public ControlSocketServidor(SrvControlPrincipal SrvControlPrincipal) {
        this.srvControlPrincipal = SrvControlPrincipal;
        this.servidorActivo = true;
    }
    
    /**
     * Loop principal  acepta clientes y genera un hilo luchador por cada uno
     */
    @Override
    public void run() {
        try{
            ServerSocket serverSocket = CnxServerSocket.conexion();
            while(servidorActivo){
                socket = serverSocket.accept();
                HiloLuchador hilo = new HiloLuchador(socket, srvControlPrincipal);
                new Thread(hilo).start();
            }
        }catch (IOException e){
            if(servidorActivo){
                srvControlPrincipal.mostrarError("Error en el servidor: "+e.getMessage());
            }
        }
        
    }
    
    /**
     *Detiene el servidor y cierre el ss
     */
    public void detenerServidor(){
        servidorActivo = false;
        CnxServerSocket.cerrar();
    }

}
