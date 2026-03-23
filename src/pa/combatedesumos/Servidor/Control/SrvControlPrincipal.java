package pa.combatedesumos.Servidor.Control;

import java.net.Socket;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxProperties;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxServerSocket;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 *
 * @author Asus
 */
public class SrvControlPrincipal {

    private SrvControlVista sControlVista;
    private ControlDojo controlDojo;
    private ControlSocketServidor sControlSocket;
    private ControlLuchador controlLuchador;

    public SrvControlPrincipal() {
        controlLuchador = new ControlLuchador(this);
        controlDojo = new ControlDojo(this);
        sControlVista = new SrvControlVista(this);
        sControlSocket = new ControlSocketServidor(this);
    }
    
}
