package pa.combatedesumos.Servidor.Control;


/**
 * Clase encargada de gestionar el ServerSocket del servidor. Acepta minimo 6
 * conexiones y lanza un hilo por cada luchador.
 *
 * @author Asus
 */
public class ControlSocketServidor implements Runnable {

    private SrvControlPrincipal SrvControlPrincipal;

    public ControlSocketServidor(SrvControlPrincipal SrvControlPrincipal) {
        this.SrvControlPrincipal = SrvControlPrincipal;
    }
    
    
    
    @Override
    public void run() {
        
    }

}
