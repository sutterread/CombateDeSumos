package pa.combatedesumos.Servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pa.combatedesumos.Servidor.Vista.PanelInscritos;
import pa.combatedesumos.Servidor.Vista.PanelDojo;
import pa.combatedesumos.Servidor.Vista.SVentana;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del servidor. Gestiona los eventos de la interfaz
 * gráfica y delega las operaciones al control principal.
 * NO CONTIENE LÓGICA DE NEGOCIO NI OBJETOS DEL MODELO.
 *
 * @author Asus
 */
public class SrvControlVista implements ActionListener {

    private SrvControlPrincipal controlPrincipal;
    private PanelInscritos panelInscritos;
    private PanelDojo panelDojo;
    private SVentana ventana;
    private final SeleccionarArchivo seleccionarArchivo;

    /**
     * Constructor de SrvControlVista.
     *
     * @param controlPrincipal control principal del servidor
     */
    public SrvControlVista(SrvControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.seleccionarArchivo = new SeleccionarArchivo();
        this.panelInscritos = new PanelInscritos(this);
        this.panelDojo = new PanelDojo(this);
        this.ventana = new SVentana(this);

        // Configurar botón
        panelInscritos.jButton1IrAlCombate.addActionListener(this);
        panelInscritos.jButton1IrAlCombate.setActionCommand("Ir al combate");

        // Mostrar ventana inicial
        mostrarVentana();
    }

    /**
     * Muestra la ventana principal con el panel de inscritos.
     */
    public void mostrarVentana() {
        cambiarPanel(panelInscritos);
        ventana.setVisible(true);
    }

    /**
     * Cambia el panel mostrado en la ventana.
     *
     * @param panel panel a mostrar
     */
    public void cambiarPanel(javax.swing.JPanel panel) {
        ventana.setContentPane(panel);
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Agrega un luchador a la lista de inscritos.
     * Solo recibe strings, SIN OBJETOS DEL MODELO.
     *
     * @param nombreLuchador nombre del luchador
     */
    public void agregarLuchadorALista(String nombreLuchador) {
        panelInscritos.agregarLuchador(nombreLuchador);
    }

    /**
     * Habilita el botón de combate.
     */
    public void habilitarBotonCombate() {
        panelInscritos.jButton1IrAlCombate.setEnabled(true);
    }

    /**
     * Actualiza el mensaje del combate en el panel dojo.
     *
     * @param mensaje mensaje a mostrar
     */
    public void actualizarCombate(String mensaje) {
        panelDojo.actualizarMensaje(mensaje);
    }

    /**
     * Cambia al panel de combate cuando inicia el torneo.
     */
    public void cambiarAlPanelCombate() {
        cambiarPanel(panelDojo);
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje mensaje de error
     */
    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(
            ventana,
            mensaje,
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
    
    
    /**
     * Retorna el panel dojo para que ControlDojo pueda usarlo.
     *
     * @return panel dojo
     */
    public PanelDojo getPanelDojo() {
        return panelDojo;
    }

    /**
     * Abre el selector de archivo properties.
     *
     * @param titulo título del diálogo
     * @return ruta del archivo seleccionado
     */
    String seleccionarArchivo(String titulo) {
        return seleccionarArchivo.seleccionarProperties(titulo);
    }

    /**
     * Gestiona los eventos de los componentes.
     *
     * @param e evento generado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Ir al combate")) {
            controlPrincipal.iniciarTorneo();
        }
    }

}