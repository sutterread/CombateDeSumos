package pa.combatedesumos.Cliente.Control;

import java.io.IOException;
import javax.swing.SwingUtilities;
import pa.combatedesumos.Cliente.Modelo.CnxProperties;
import pa.combatedesumos.Cliente.Modelo.CnxSocket;

/**
 * Control principal del cliente. Coordina la vista y el control de socket.
 *
 * @author Asus
 */
public class ControlPrincipal {

    private ControlVista cControlVista;
    private ControlSocketCliente controlCliente;

    private String[] tecnicasSeleccionadas;
    private String rutaProperties;

    /**
     * Constructor de ControlPrincipal. Inicializa la vista y el control de socket.
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
     * Valida los datos, envia al luchador al servidor y luego espera el
     * resultado del combate en un hilo de fondo para no bloquear la UI.
     * Cambia al PanelEspera tras recibir la confirmacion y al
     * PanelCombatiendo cuando llega el resultado final.
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
        try {
            // Enviar datos y recibir confirmacion "RECIBIDO" (sincronico)
            controlCliente.enviarLuchador(nombre, pesoFloat, tecnicasSeleccionadas);
            // Datos recibidos correctamente — mostrar pantalla de espera
            cControlVista.mostrarPanelEspera();
        } catch (IOException e) {
            cControlVista.mostrarAdvertencia("Error de conexion con el servidor.");
            return;
        }
        // Esperar resultado del combate en hilo de fondo (no bloquea la UI)
        new Thread(() -> {
            try {
                String resultado = controlCliente.getInputStream().readUTF();
                SwingUtilities.invokeLater(() -> {
                    cControlVista.mostrarPanelCombatiendo();
                    cControlVista.mostrarResultado(resultado);
                });
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() ->
                    cControlVista.mostrarAdvertencia("Se perdio la conexion con el servidor.")
                );
            }
        }, "HiloEsperaResultado").start();
    }
}
