package pa.combatedesumos.Cliente.Control;


public class ControlPrincipal {

    private ControlVista cControlVista;
    private ControlSocketCliente controlCliente;

    public ControlPrincipal() {
        cControlVista = new ControlVista(this);
        controlCliente = new ControlSocketCliente(this);
    }
}
