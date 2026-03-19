package pa.combatedesumos.Cliente.Control;


public class CControlPrincipal {

    private CControlVista cControlVista;
    private ControlCliente controlCliente;

    public CControlPrincipal() {
        cControlVista = new CControlVista(this);
        controlCliente = new ControlCliente(this);
    }
}
