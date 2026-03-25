package pa.combatedesumos.Servidor.Control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hilo encargado de manejar la comunicación con un cliente luchador. Recibe los
 * datos del luchador y espera el resultado del combate.
 *
 * @author
 */
public class HiloLuchador implements Runnable {

    /**
     * Socket del cliente.
     */
    private Socket socket;
    /**
     * Referencia al control principal del servidor.
     */
    private SrvControlPrincipal srvControlPrincipal;
    /**
     * Stream de entrada del cliente.
     */
    private DataInputStream input;
    /**
     * Stream de salida al cliente.
     */
    private DataOutputStream output;
    
    /**
     * Bandera para controlar si el hilo está activo
     */
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

            System.out.println("Luchador registrado: " + nombre + " (" + peso + " kg)");
            
            // Pasar datos al SrvControlPrincipal
            srvControlPrincipal.registrarLuchador(nombre, peso, tecnicas, this);

            // Fase 2 — esperar resultado (será desbloqueado por enviarResultado())
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
            System.out.println("Resultado enviado: " + resultado);
            
            // Marcar como inactivo y despertar el wait()
            synchronized (this) {
                activo = false;
                notifyAll();  // ← CRÍTICO: Despierta el wait() del hilo
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al enviar resultado al cliente", e);
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
            System.out.println("Conexión cerrada");
        } catch (IOException e) {
        }
    }
}