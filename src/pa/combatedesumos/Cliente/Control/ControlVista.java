package pa.combatedesumos.Cliente.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador de vista del cliente. Gestiona los eventos de la interfaz grafica
 * y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class ControlVista implements ActionListener {

    private ControlPrincipal controlPrincipal;

    /**
     * Constructor de CControlVista.
     *
     * @param controlPrincipal control principal del cliente
     */
    public ControlVista(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
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
