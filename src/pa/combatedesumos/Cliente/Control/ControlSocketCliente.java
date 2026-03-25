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

    private ControlPrincipal ccp;
    /** Stream de entrada reutilizable para toda la sesion del socket. */
    private DataInputStream dis;

    /**
     * Constructor de ControlSocketCliente.
     *
     * @param ccp control principal del cliente
     */
    public ControlSocketCliente(ControlPrincipal ccp) {
        this.ccp = ccp;
    }

    /**
     * Envia los datos del luchador al servidor por el socket.
     * Protocolo: nombre, peso (float), cantidad de tecnicas, tecnicas.
     *
     * @param nombre    nombre del luchador
     * @param peso      peso del luchador (float 32 bits)
     * @param kimarites arreglo de tecnicas seleccionadas
     * @throws IOException si hay error de conexion
     */
    public void enviarLuchador(String nombre, float peso, String[] kimarites) throws IOException {
        Socket socket = CnxSocket.conexion();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());

        dos.writeUTF(nombre);
        dos.writeFloat(peso);
        dos.writeInt(kimarites.length);
        for (String kimarite : kimarites) {
            dos.writeUTF(kimarite);
        }
        dos.flush();
    }

    /**
     * Lee la confirmacion "RECIBIDO" enviada por el servidor tras registrar al
     * luchador. Debe llamarse justo despues de {@link #enviarLuchador}.
     *
     * @return mensaje de confirmacion del servidor ("RECIBIDO")
     * @throws IOException si hay error de conexion
     */
    public String leerConfirmacion() throws IOException {
        return dis.readUTF();
    }

    /**
     * Lee el resultado del combate enviado por el servidor.
     * Bloquea hasta recibir "GANASTE" o "PERDISTE".
     *
     * @return resultado del combate
     * @throws IOException si hay error de conexion
     */
    public String leerResultado() throws IOException {
        return dis.readUTF();
    }

}
