package pa.combatedesumos.Cliente.Control;

import java.io.IOException;
import javax.swing.SwingUtilities;
import pa.combatedesumos.Cliente.Modelo.CnxProperties;
import pa.combatedesumos.Cliente.Modelo.CnxSocket;

/**
 * Control principal del cliente. Coordina la comunicacion con el servidor y
 * la interaccion con la vista.
 *
 * @author Asus
 */
public class ControlPrincipal {

    private ControlVista cControlVista;
    private ControlSocketCliente controlCliente;

    private String[] tecnicasSeleccionadas;
    private String rutaProperties;

    /**
     * Constructor de ControlPrincipal. Inicializa controles de vista y socket.
     */
    public ControlPrincipal() {
        cControlVista = new ControlVista(this);
        controlCliente = new ControlSocketCliente(this);
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
     * La comunicacion con el socket se ejecuta en un hilo de fondo para
     * no bloquear el EDT (Event Dispatch Thread).
     * Protocolo:
     * <ol>
     *   <li>Enviar datos del luchador</li>
     *   <li>Esperar confirmacion "RECIBIDO" del servidor</li>
     *   <li>Cambiar a PanelEspera</li>
     *   <li>Esperar resultado final ("GANASTE" o "PERDISTE")</li>
     *   <li>Mostrar resultado</li>
     * </ol>
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
        float pesoFloat;
        try {
            pesoFloat = Float.parseFloat(peso);
        } catch (NumberFormatException e) {
            cControlVista.mostrarAdvertencia("El peso debe ser un numero decimal (ej: 95.5).");
            return;
        }

        final float pesoFinal = pesoFloat;
        final String nombreFinal = nombre;

        // Ejecutar comunicacion de red en hilo de fondo para no bloquear el EDT
        new Thread(() -> {
            try {
                controlCliente.enviarLuchador(nombreFinal, pesoFinal, tecnicasSeleccionadas);

                // Esperar confirmacion "RECIBIDO" del servidor
                String confirmacion = controlCliente.leerConfirmacion();
                if ("RECIBIDO".equals(confirmacion)) {
                    // Cambiar al panel de espera en el EDT
                    SwingUtilities.invokeLater(() -> cControlVista.mostrarPanelEspera());
                }

                // Esperar resultado final del combate (puede tardar varios minutos)
                String resultado = controlCliente.leerResultado();

                // Mostrar resultado en el EDT
                SwingUtilities.invokeLater(() -> cControlVista.mostrarResultado(resultado));

            } catch (IOException e) {
                SwingUtilities.invokeLater(() ->
                    cControlVista.mostrarAdvertencia("Error de conexion con el servidor."));
            }
        }).start();
    }
}
