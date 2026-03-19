package pa.combatedesumos.Servidor.Control;

import java.net.Socket;
import pa.combatedesumos.Servidor.Modelo.CnxProperties;
import pa.combatedesumos.Servidor.Modelo.CnxServerSocket;
import pa.combatedesumos.Servidor.Modelo.Luchador;

/**
 *
 * @author Asus
 */
public class SControlPrincipal {

    private SControlVista sControlVista;
    private ControlDojo controlDojo;
    private SContolSocket sControlSocket;
    private ControlLuchador controlLuchador;

    private int contadorLlegadas;

    public SControlPrincipal() {
        controlLuchador = new ControlLuchador(this);
        controlDojo = new ControlDojo(this);
        sControlVista = new SControlVista(this);
        sControlSocket = new SContolSocket(this);
        contadorLlegadas = 0;
        seleccionarProperties();
        sControlVista.mostrarVentana();
    }

    public void seleccionarProperties() {
        String ruta = sControlVista.seleccionar("Seleccione el archivo de configuracion del servidor:");
        if (ruta != null) {
            try {
                CnxProperties.cargaConfioguracion(ruta);
                CnxServerSocket.configurar(CnxProperties.getPuerto());
                Thread hiloServidor = new Thread(sControlSocket, "ServidorSockets");
                hiloServidor.start();
            } catch (Exception e) {
            }
        }
    }

    public void subirAlDojo(Luchador luchador) throws InterruptedException {
        controlDojo.subirLuchador(luchador);
    }

    public void ejecutarTurno(Luchador luchador) throws InterruptedException {
        controlDojo.ejecutarTurno(luchador);
    }

    public void crearYEjecutar(String nombre, float peso, int combatesGanados, String[] kimarites) throws InterruptedException {
        controlLuchador.crearLuchador(nombre, peso, combatesGanados, kimarites);
    }

    public void notificarLlegada(Luchador luchador) {
        contadorLlegadas++;
        if (contadorLlegadas == 1) {
            sControlVista.mostrarNombreLuchador1(luchador.getNombre());
        } else {
            sControlVista.mostrarNombreLuchador2(luchador.getNombre());
        }
    }

    public void notificarKimarite(Luchador luchador, String kimarite) {
        sControlVista.mostrarKimarite(luchador.getNombre(), kimarite);
        sControlVista.agregarEvento(luchador.getNombre() + " usó " + kimarite);
    }

    public void notificarGanador(Luchador ganador) {
        sControlVista.mostrarGanador(ganador.getNombre());
    }

    public boolean esGanador(String nombre) {
        Luchador ganador = controlDojo.determinarGanador();
        return ganador != null && ganador.getNombre().equals(nombre);
    }

    public void notificarResultado(Socket socket, String nombre) {
        sControlSocket.enviarResultado(socket, esGanador(nombre) ? "GANASTE" : "PERDISTE");
    }

}
