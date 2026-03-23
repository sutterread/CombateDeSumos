package pa.combatedesumos.Cliente.Control;


public class CControlPrincipal {

    private CControlVista cControlVista;
    private ControlSocketCliente controlCliente;

    public CControlPrincipal() {
        cControlVista = new CControlVista(this);
        controlCliente = new ControlSocketCliente(this);
    }
}
