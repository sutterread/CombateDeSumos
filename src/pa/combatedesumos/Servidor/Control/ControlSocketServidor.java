package pa.combatedesumos.Servidor.Control;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxServerSocket;

/**
 * Clase encargada de gestionar el ServerSocket del servidor. Acepta exactamente
 * dos conexiones y lanza un hilo por cada luchador.
 *
 * @author Asus
 */
public class ControlSocketServidor implements Runnable {

    private SrvControlPrincipal sControlPrincipal;

    private ServerSocket servidor;
    private boolean servidorActivo;

    public ControlSocketServidor(SrvControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
        this.servidorActivo = true;
    }

    @Override
    public void run() {
        //PENDIENTE PARA RECIBIR COMO MINIMO 6 CONEXIONES
//        try {
//            servidor = CnxServerSocket.conexion();
//            int contador = 0;
//            while (contador < 2) {
//                Socket sc = servidor.accept();
//                SHiloLuchador hilo = new SHiloLuchador(sc, sControlPrincipal);
//                new Thread(hilo, "Luchador-" + (contador + 1)).start();
//                contador++;
//            }
//        } catch (IOException ex) {
//            if (servidorActivo) {
//            }
//        } finally {
//            detenerServidor(); //cierre del serversocket
//        }
    }

    /**
     * Envia el resultado del combate al cliente por el socket
     *
     * @param socket socket del cliente
     * @param resultado "GANASTE" o "PERDISTE"
     */
    public void enviarResultado(Socket socket, String resultado) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(resultado);
            dos.flush();
        } catch (IOException e) {}
    }

    private void detenerServidor() {
        servidorActivo = false;
        CnxServerSocket.cerrar();
    }

}
