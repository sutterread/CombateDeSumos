package pa.peleadesumos.Servidor.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            //  incializando streamsss
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            //recibir datos del luchador
            String nombre = input.readUTF();
            float peso = input.readFloat();
            int combatesGanados = input.readInt();
            int cantKimarites = input.readInt();
            String[] kimarites = new String[cantKimarites];
            //Meter los kimatires extraidos en su array
            for (int i = 0; i < cantKimarites; i++) {
                kimarites[i] = input.readUTF();
            }

            try {
                // Delegar creacion y combate al control principal
                cControlPrincipal.crearYEjecutar(nombre, peso, combatesGanados, kimarites);
            } catch (InterruptedException ex) {}

            String resultado = cControlPrincipal.esGanador(nombre) ? "GANASTE" : " PERDISTE";
            output.writeUTF(resultado);
            output.flush();

        } catch (IOException e) {
        } finally {
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
