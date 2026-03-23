package pa.combatedesumos.Cliente.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import pa.combatedesumos.Cliente.Modelo.CnxSocket;

/**
 * Control encargado de manejar la comunicacion del cliente con el socket.
 * Envia los datos del luchador y espera el resultado del combate.
 * 
 * @author Asus
 */
public class ControlSocketCliente {

    private CnxSocket cnxSocket;
    private CControlPrincipal ccp;

    /**
     * Constructor de ControlSocketCliente
     *
     * @param ccp
     */
    public ControlSocketCliente(CControlPrincipal ccp) {
        this.ccp = ccp;
        this.cnxSocket = new CnxSocket();
    }

    /**
     * Envia los datos del luchador al servidor por el socket
     *
     * @param nombre
     * @param peso
     * @param kimarites
     * @throws java.io.IOException
     */
    public void enviarLuchador(String nombre, float peso, String[] kimarites) throws IOException {

        Socket socket = cnxSocket.conexion();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeUTF(nombre);
        dos.writeFloat(peso);
        dos.writeInt(kimarites.length);

        for (String kimarite : kimarites) {
            dos.writeUTF(kimarite);
        }

        dos.flush();

    }

    /**
     * Retorna el input stream del socket
     *
     * @return DataInputStream del socket
     * @throws IOException si hay error
     */
    public DataInputStream getInputStream() throws IOException {
        return new DataInputStream(cnxSocket.conexion().getInputStream());
    }

}
