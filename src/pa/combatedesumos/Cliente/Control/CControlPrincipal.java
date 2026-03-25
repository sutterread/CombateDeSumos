package pa.combatedesumos.Cliente.Control;

import java.io.IOException;
import pa.combatedesumos.Cliente.Modelo.CnxProperties;
import pa.combatedesumos.Cliente.Modelo.CnxSocket;


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
     * Solicita al usuario seleccionar el properties, carga la configuracion
     * del socket y las categorias disponibles.
     */
    public void seleccionarProperties() {
        rutaProperties = cControlVista.seleccionar("Seleccione el archivo de configuracion del cliente: ");
        if (rutaProperties != null) {
            try {
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
     * Carga las categorias disponibles y las muestra en la vista.
     *
     * @param rutaProperties ruta del archivo properties
     */
    public void cargarCategorias(String rutaProperties) {
        try {
            String[] categorias = CnxProperties.cargarCategorias(rutaProperties);
            cControlVista.cargarCategorias(categorias);
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error al cargar las categorias.");
        }
    }
 
    /**
     * Carga los kimarites de una categoria y los muestra en la vista.
     *
     * @param rutaProperties ruta del archivo properties
     * @param categoriaId    id de la categoria seleccionada
     */
    public void cargarKimaritesPorCategoria(String rutaProperties, String categoriaId) {
        try {
            CnxProperties.cargarKimarites(rutaProperties, categoriaId);
            cControlVista.cargarKimaritesPorCategoria(CnxProperties.getKimarites());
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error al cargar los kimarites.");
        }
    }
 
    /**
     * Guarda las tecnicas seleccionadas por el luchador.
     *
     * @param tecnicas arreglo de tecnicas confirmadas
     */
    public void confirmarTecnicas(String[] tecnicas) {
        this.tecnicasSeleccionadas = tecnicas;
    }
 
    /**
     * Valida los datos, conecta al servidor y envia al luchador.
     * Nota: V2 no tiene campo combatesGanados, se omite en el envio.
     *
     * @param nombre nombre del luchador
     * @param peso   peso del luchador (String a convertir)
     */
    public void conectarYPelear(String nombre, String peso) {
        if (tecnicasSeleccionadas == null || tecnicasSeleccionadas.length == 0) {
            cControlVista.mostrarAdvertencia("Debe confirmar sus tecnicas antes de pelear.");
            return;
        }
        if (nombre.trim().isEmpty() || peso.trim().isEmpty()) {
            cControlVista.mostrarAdvertencia("Debe completar todos los campos.");
            return;
        }
        try {
            float pesoFloat = Float.parseFloat(peso);
            controlCliente.enviarLuchador(nombre, pesoFloat, tecnicasSeleccionadas);
            String resultado = esperarResultado();
            cControlVista.mostrarResultado(resultado);
        } catch (NumberFormatException e) {
            cControlVista.mostrarAdvertencia("El peso debe ser un numero decimal (ej: 95.5).");
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error de conexion con el servidor.");
        }
    }
 
    /**
     * Espera y retorna el resultado del combate enviado por el servidor.
     *
     * @return "GANASTE" o "PERDISTE"
     * @throws IOException si hay error de conexion
     */
    public String esperarResultado() throws IOException {
        return controlCliente.getInputStream().readUTF();
    }
}

