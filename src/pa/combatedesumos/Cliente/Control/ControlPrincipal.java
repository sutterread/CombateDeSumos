package pa.combatedesumos.Cliente.Control;


public class ControlPrincipal {

    private ControlVista controlVista;
    private ControlSocketCliente controlSocketCliente;

    public ControlPrincipal() {
        controlVista = new ControlVista(this);
        controlSocketCliente = new ControlSocketCliente(this);
    }
}
