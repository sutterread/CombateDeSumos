package pa.combatedesumos.Servidor.Control;

import java.util.concurrent.ThreadLocalRandom;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Control encargado de gestionar la logica del combate en el dojo. Sincroniza
 * los dos hilos de los luchadores y determina el ganador.
 *
 * @author Sergio, Asus
 */
public class ControlDojo {

    private SControlPrincipal sControlPrincipal;
    
    /**
     * Constructor de ControlDojo.
     *
     * @param sControlPrincipal control principal del servidor
     */
    public ControlDojo(SControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
    }
    
}
