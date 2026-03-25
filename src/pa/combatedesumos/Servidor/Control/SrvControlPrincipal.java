package pa.combatedesumos.Servidor.Control;


/**
 *
 * @author Asus
 */
public class SrvControlPrincipal {

    private SrvControlVista srvControlVista;
    private ControlDojo controlDojo;
    private ControlSocketServidor controlSocketServidor;
    private ControlLuchador controlLuchador;

    public SrvControlPrincipal() {
        controlLuchador = new ControlLuchador(this);
        controlDojo = new ControlDojo(this);
        srvControlVista = new SrvControlVista(this);
        controlSocketServidor = new ControlSocketServidor(this);
    }

    void registrarLuchador(String nombre, double peso, String[] kimarites, HiloLuchador aThis) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void mostrarError(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
