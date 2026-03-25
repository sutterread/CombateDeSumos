package pa.combatedesumos.Servidor.Control;

import java.io.IOException;
import java.util.List;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxBD;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxProperties;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxServerSocket;
import pa.combatedesumos.Servidor.Modelo.DAO.RAFDAO;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Control principal del servidor. Coordina todos los controles secundarios
 * y actúa como punto central de comunicación entre ellos.
 *
 * @author Asus
 */
public class SrvControlPrincipal {

    /** Control de la vista del servidor. */
    private SrvControlVista srvControlVista;

    /** Control del dojo. */
    private ControlDojo controlDojo;

    /** Control del servidor socket. */
    private ControlSocketServidor controlSocketServidor;

    /** Control de luchadores. */
    private ControlLuchador controlLuchador;

    /**
     * Constructor de SrvControlPrincipal.
     * Crea todos los controles secundarios e inicializa la vista.
     */
    public SrvControlPrincipal() {
        this.controlLuchador = new ControlLuchador(this);
        this.controlDojo = new ControlDojo(this);
        this.srvControlVista = new SrvControlVista(this);
        this.controlSocketServidor = new ControlSocketServidor(this);
        seleccionarProperties();
    }

    /**
     * Carga el archivo de propiedades del servidor y configura las conexiones.
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
     * Registra un luchador — delega a ControlLuchador y ControlDojo.
     *
     * @param nombre   nombre del luchador
     * @param peso     peso del luchador
     * @param tecnicas técnicas del luchador
     * @param hilo     hilo del luchador
     */
    public void registrarLuchador(String nombre, double peso, String[] tecnicas, HiloLuchador hilo) {
        controlLuchador.insertarLuchadorEnBD(nombre, peso, tecnicas);
        List<LuchadorDTO> luchadores = controlLuchador.getLuchadores();
        LuchadorDTO luchador = luchadores.get(luchadores.size() - 1);
        controlDojo.agregarHilo(luchador.getIdLuchador(), hilo);
        srvControlVista.actualizarLuchadores(luchadores);
    }

    /**
     * Habilita el botón de combate en la vista.
     */
    public void habilitarCombate() {
        srvControlVista.habilitarBotonCombate();
    }

    /**
     * Inicia el torneo delegando a ControlDojo.
     */
    public void iniciarTorneo() {
        controlDojo.iniciarTorneo(controlLuchador.getLuchadores());
    }

    /**
     * Suma una victoria al luchador — delega a ControlLuchador.
     *
     * @param luchador luchador ganador
     */
    public void sumarVictoria(LuchadorDTO luchador) {
        controlLuchador.sumarVictoria(luchador);
    }

    /**
     * Consulta un luchador en BD — delega a ControlLuchador.
     *
     * @param id ID del luchador
     * @return LuchadorDTO con datos frescos de BD
     */
    public LuchadorDTO consultarLuchador(int id) {
        return controlLuchador.consultarLuchadorEnBD(id);
    }

    /**
     * Finaliza el combate — escribe RAF y notifica a los clientes.
     *
     * @param ganador  luchador ganador con datos frescos de BD
     * @param perdedor luchador perdedor con datos frescos de BD
     */
    public void finalizarCombate(LuchadorDTO ganador, LuchadorDTO perdedor) {
        try {
            RAFDAO.escribirResultado(ganador, "GANO");
            RAFDAO.escribirResultado(perdedor, "PERDIO");
            controlDojo.getHilos().get(ganador.getIdLuchador()).enviarResultado("GANASTE");
            controlDojo.getHilos().get(perdedor.getIdLuchador()).enviarResultado("PERDISTE");
        } catch (IOException e) {
            mostrarError("Error al finalizar combate: " + e.getMessage());
        }
    }

    /**
     * Lee el archivo RAF y muestra los resultados por consola.
     */
    public void mostrarArchivoFinal() {
        try {
            RAFDAO.leerTodos();
        } catch (IOException e) {
            mostrarError("Error al leer archivo final: " + e.getMessage());
        }
    }

    /**
     * Actualiza el panel del dojo en la vista.
     *
     * @param mensaje mensaje a mostrar
     */
    public void actualizarCombate(String mensaje) {
        srvControlVista.actualizarCombate(mensaje);
    }

    /**
     * Muestra un mensaje de error en la vista.
     *
     * @param mensaje mensaje de error
     */
    public void mostrarError(String mensaje) {
        srvControlVista.mostrarError(mensaje);
    }
}