package pa.combatedesumos.Servidor.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hilo encargado de manejar la comunicación con un cliente luchador.
 * Recibe datos del luchador, lo registra y espera el resultado del combate.
 *
 * @author VALEN
 */
public class HiloLuchador implements Runnable {

    private Socket socket;
    private SrvControlPrincipal srvControlPrincipal;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean activo = true;

    /**
     * Constructor de HiloLuchador.
     *
     * @param socket socket del cliente conectado
     * @param srvControlPrincipal referencia al control principal del servidor
     */
    public HiloLuchador(Socket socket, SrvControlPrincipal srvControlPrincipal) {
        this.socket = socket;
        this.srvControlPrincipal = srvControlPrincipal;
    }

    /**
     * Ejecuta el hilo — recibe datos del cliente y espera resultado.
     */
    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            // Fase 1 — recibir datos del cliente
            String nombre = input.readUTF();
            double peso = input.readDouble();
            int cantidadTecnicas = input.readInt();
            String[] tecnicas = new String[cantidadTecnicas];
            
            for (int i = 0; i < cantidadTecnicas; i++) {
                tecnicas[i] = input.readUTF();
            }

            // Registrar luchador (SIN System.out)
            srvControlPrincipal.registrarLuchador(nombre, peso, tecnicas, this);

            // Fase 2 — esperar resultado
            synchronized (this) {
                while (activo) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

        } catch (IOException e) {
            // Manejar excepción sin System.out
            srvControlPrincipal.mostrarError("Error en comunicación con cliente: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            cerrarConexion();
        }
    }

    /**
     * Envía el resultado del combate al cliente y despierta el hilo.
     *
     * @param resultado "GANASTE" o "PERDISTE"
     */
    public void enviarResultado(String resultado) {
        try {
            output.writeUTF(resultado);
            output.flush();

            synchronized (this) {
                activo = false;
                notifyAll();
            }
        } catch (IOException e) {
            srvControlPrincipal.mostrarError("Error al enviar resultado: " + e.getMessage());
        }
    }

    /**
     * Cierra los streams y el socket del cliente.
     */
    private void cerrarConexion() {
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            // Silenciar error de cierre
        }
    }
}