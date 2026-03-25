package pa.combatedesumos.Servidor.Control;

import java.io.IOException;
import java.util.List;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxBD;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxProperties;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxServerSocket;
import pa.combatedesumos.Servidor.Modelo.DAO.RAFDAO;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;
import pa.combatedesumos.Servidor.Vista.PanelDojo;

/**
 * Control principal del servidor. Coordina todos los controles secundarios.
 * Punto central de comunicación entre capas.
 *
 * @author Asus
 */
public class SrvControlPrincipal {

    private SrvControlVista srvControlVista;
    private ControlDojo controlDojo;
    private ControlSocketServidor controlSocketServidor;
    private ControlLuchador controlLuchador;

    /**
     * Constructor de SrvControlPrincipal.
     */
    public SrvControlPrincipal() {
        this.controlLuchador = new ControlLuchador(this);
        this.srvControlVista = new SrvControlVista(this);

        // Crear ControlDojo DESPUÉS de SrvControlVista para obtener PanelDojo
        PanelDojo panelDojo = obtenerPanelDojo();
        this.controlDojo = new ControlDojo(this, panelDojo);

        this.controlSocketServidor = new ControlSocketServidor(this);
        seleccionarProperties();
    }

    /**
     * Obtiene el panel dojo de la vista.
     *
     * @return panel dojo
     */
    private PanelDojo obtenerPanelDojo() {
        return srvControlVista.getPanelDojo();
    }

    /**
     * Carga el archivo de propiedades del servidor.
     */
    public void seleccionarProperties() {
        try {
            String ruta = srvControlVista.seleccionarArchivo("Seleccione servidor.properties");
            CnxProperties.cargaConfiguracion(ruta);
            CnxBD.cargarCredenciales(ruta);
            CnxServerSocket.configurar(CnxProperties.getPuerto());
            new Thread(controlSocketServidor).start();
        } catch (IOException e) {
            mostrarError("Error al cargar properties: " + e.getMessage());
        }
    }

    /**
     * Registra un luchador en el sistema.
     *
     * @param nombre nombre del luchador
     * @param peso peso del luchador
     * @param tecnicas técnicas del luchador
     * @param hilo hilo del luchador
     */
    public void registrarLuchador(String nombre, double peso, String[] tecnicas, HiloLuchador hilo) {
        controlLuchador.insertarLuchadorEnBD(nombre, peso, tecnicas);
        List<LuchadorDTO> luchadores = controlLuchador.getLuchadores();
        LuchadorDTO luchador = luchadores.get(luchadores.size() - 1);
        controlDojo.agregarHilo(luchador.getIdLuchador(), hilo);

        // SOLO STRINGS para la vista
        String nombreLuchador = nombre + " (" + peso + " kg)";
        srvControlVista.agregarLuchadorALista(nombreLuchador);

        // Habilitar botón si hay 6 o más
        if (luchadores.size() >= 6) {
            srvControlVista.habilitarBotonCombate();
        }
    }

    /**
     * Inicia el torneo en un hilo de fondo para no bloquear el EDT.
     */
    public void iniciarTorneo() {
        srvControlVista.cambiarAlPanelCombate();
        new Thread(() -> controlDojo.iniciarTorneo(controlLuchador.getLuchadores())).start();
    }

    /**
     * Suma una victoria al luchador.
     *
     * @param luchador luchador ganador
     */
    public void sumarVictoria(LuchadorDTO luchador) {
        controlLuchador.sumarVictoria(luchador);
    }

    /**
     * Consulta un luchador en BD.
     *
     * @param id ID del luchador
     * @return luchador con datos frescos
     */
    public LuchadorDTO consultarLuchador(int id) {
        return controlLuchador.consultarLuchadorEnBD(id);
    }

    /**
     * Finaliza el combate, escribe RAF y notifica a clientes.
     *
     * @param ganador ganador del combate
     * @param perdedor perdedor del combate
     */
    public void finalizarCombate(LuchadorDTO ganador, LuchadorDTO perdedor) {
        try {
            RAFDAO.escribirCombate(ganador, perdedor);

            HiloLuchador hiloGanador = controlDojo.getHilos().get(ganador.getIdLuchador());
            HiloLuchador hiloPerdedor = controlDojo.getHilos().get(perdedor.getIdLuchador());

            if (hiloGanador != null) {
                hiloGanador.enviarResultado("GANASTE");
            }
            if (hiloPerdedor != null) {
                hiloPerdedor.enviarResultado("PERDISTE");
            }

        } catch (IOException e) {
            mostrarError("Error al finalizar combate: " + e.getMessage());
        }
    }

    /**
     * Muestra el archivo final de resultados.
     */
    public void mostrarArchivoFinal() {
        try {
            RAFDAO.leerTodos();
        } catch (IOException e) {
            mostrarError("Error al leer archivo final: " + e.getMessage());
        }
    }

    /**
     * Actualiza el combate en la vista.
     *
     * @param mensaje mensaje
     */
    public void actualizarCombate(String mensaje) {
        srvControlVista.actualizarCombate(mensaje);
    }

    /**
     * Muestra un error en la vista (JOptionPane).
     *
     * @param mensaje mensaje de error
     */
    public void mostrarError(String mensaje) {
        srvControlVista.mostrarError(mensaje);
    }

    /**
     * Muestra el panel de combate.
     */
    public void mostrarPanelCombate() {
        srvControlVista.cambiarAlPanelCombate();
    }

    /**
     * Habilita el botón de combate cuando hay 6 o más luchadores.
     */
    public void habilitarCombate() {
        srvControlVista.habilitarBotonCombate();
    }
}
