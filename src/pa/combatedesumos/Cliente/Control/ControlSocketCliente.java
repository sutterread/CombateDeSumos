package pa.combatedesumos.Cliente.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import pa.combatedesumos.Cliente.Modelo.CnxSocket;

/**
 * Control encargado de manejar la comunicacion del cliente con el socket.
 * Envia los datos del luchador y espera el resultado del combate.
 *
 * @author Asus
 */
public class ControlSocketCliente {

    private ControlPrincipal ccp;
    /** Stream de entrada reutilizable para el mismo socket. */
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
     * Envia los datos del luchador al servidor por el socket y espera la
     * confirmación "RECIBIDO" antes de retornar.
     *
     * @param nombre    nombre del luchador
     * @param peso      peso del luchador (float)
     * @param kimarites técnicas seleccionadas
     * @throws IOException si hay error de conexion o confirmación inesperada
     */
    public void enviarLuchador(String nombre, float peso, String[] kimarites) throws IOException {
        DataOutputStream dos = new DataOutputStream(CnxSocket.conexion().getOutputStream());

        dos.writeUTF(nombre);
        dos.writeFloat(peso);
        dos.writeInt(kimarites.length);

        for (String kimarite : kimarites) {
            dos.writeUTF(kimarite);
        }

        dos.flush();

        // Esperar confirmación del servidor
        String confirmación = getInputStream().readUTF();
        if (!"RECIBIDO".equals(confirmación)) {
            throw new IOException("Confirmación inesperada del servidor: " + confirmación);
        }
    }

    /**
     * Retorna el DataInputStream del socket, reutilizando la misma instancia.
     *
     * @return DataInputStream del socket
     * @throws IOException si hay error al obtener el stream
     */
    public DataInputStream getInputStream() throws IOException {
        if (dis == null) {
            dis = new DataInputStream(CnxSocket.conexion().getInputStream());
        }
        return dis;
    }

}
