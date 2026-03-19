package pa.combatedesumos.Servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del servidor. Gestiona los eventos de la interfaz
 * grafica y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class SControlVista implements ActionListener {

    /**
     * Control principal del servidor
     */
    private SControlPrincipal controlPrincipal;
    
    private final SeleccionarArchivo seleccionarArchivo;

    /**
     * Constructor de SControlVista.
     *
     * @param controlPrincipal control principal del servidor
     */
    public SControlVista(SControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.seleccionarArchivo = new SeleccionarArchivo();
    }

    /**
     * Gestiona los eventos generados por los componentes de la interfaz
     * grafica.
     *
     * @param e evento generado por un componente de la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
