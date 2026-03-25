package pa.combatedesumos.Servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del servidor. Gestiona los eventos de la interfaz
 * grafica y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class SrvControlVista implements ActionListener {

    /**
     * Control principal del servidor
     */
    private SrvControlPrincipal controlPrincipal;
    
    private final SeleccionarArchivo seleccionarArchivo;

    /**
     * Constructor de SControlVista.
     *
     * @param controlPrincipal control principal del servidor
     */
    public SrvControlVista(SrvControlPrincipal controlPrincipal) {
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

    String seleccionarArchivo(String seleccione_servidorproperties) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void actualizarLuchadores(List<LuchadorDTO> luchadores) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void habilitarBotonCombate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void actualizarCombate(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void mostrarError(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
