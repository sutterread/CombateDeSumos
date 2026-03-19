package pa.peleadesumos.Cliente.Control;

import java.io.IOException;
import pa.peleadesumos.Cliente.Modelo.CnxProperties;
import pa.peleadesumos.Cliente.Modelo.CnxSocket;

public class CControlPrincipal {

    private CControlVista cControlVista;
    private ControlCliente controlCliente;

    private String[] tecnicasSeleccionadas;
    private String rutaProperties;

    public CControlPrincipal() {
        cControlVista = new CControlVista(this);
        controlCliente = new ControlCliente(this);
        cControlVista.mostrarVentana();
    }

    /**
     * Solicita al usuario seleccionar el properties carga la configuracion del
     * socket y las categorias disponibles
     */
    public void seleccionarProperties() {

        rutaProperties = cControlVista.seleccionar("Seleccione el archivo de configuracion del cliente: ");

        if (rutaProperties != null) {
            try {
                //le carga los datos a todo
                CnxProperties.cargaConfiguracion(rutaProperties);
                CnxSocket.configurarSocket(CnxProperties.getIp(), CnxProperties.getPuerto());
                cargarCategorias(rutaProperties);
                cControlVista.setRutaProperties(rutaProperties);
            } catch (IOException e) {
                cControlVista.mostrarAdvertencia("Error al cargar el archivo properties.");
            }
        }

    }

    /**
     * Carga las categorias disponibles y las muestra en la vista
     *
     * @param rutaProperties
     */
    public void cargarCategorias(String rutaProperties) {
        try {
            String[] categorias = CnxProperties.cargarCategorias(rutaProperties);
            cControlVista.cargarCategorias(categorias);
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error al cargar las categorias");
        }
    }

    /**
     * Carga los kimarites de una categoria y los muestra en la vista
     *
     * @param rutaProperties
     * @param categoriaId
     */
    public void cargarKimaritesPorCategoria(String rutaProperties, String categoriaId) {
        try {
            CnxProperties.cargarKimarites(rutaProperties, categoriaId);
            cControlVista.cargarKimaritesPorCategoria(CnxProperties.getKimarites());
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error al cargar los kimarites");
        }

    }

    /**
     * Guarda las tecnicas seleccioandas por el luchador
     *
     * @param tecnicas
     */
    public void confirmarTecnicas(String[] tecnicas) {
        this.tecnicasSeleccionadas = tecnicas;
    }

    public void conectarYPelear(String nombre, String peso, String combates) {
        if (tecnicasSeleccionadas == null || tecnicasSeleccionadas.length == 0) {
            cControlVista.mostrarAdvertencia("Debe confirmar sus tecnicas antes de pelear.");
            return;
        }
        if (nombre.trim().isEmpty() || peso.trim().isEmpty() || combates.trim().isEmpty()) {
            cControlVista.mostrarAdvertencia("Debe completar todos los campos.");
            return;
        }
        try {
            float pesoFloat = Float.parseFloat(peso);
            int combatesInt = Integer.parseInt(combates);
            controlCliente.enviarLuchador(nombre, pesoFloat, combatesInt, tecnicasSeleccionadas);
            String resultado = esperarResultado();
            cControlVista.mostrarResultado(resultado);
        } catch (NumberFormatException e) {
            cControlVista.mostrarAdvertencia("Peso y combates deben ser numericos flotante y entero respectivamente");
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error de conexion con el servidor");
        }
    }
    
    /**
     * Espera y retorna el resultado del combate enviado por el servidor
     *
     * @return "GANASTE O PERDISTE" obviamente este mensaje se manda por vista
     * @throws IOException
     */
    public String esperarResultado() throws IOException {
        return controlCliente.getInputStream().readUTF();
    }

}
