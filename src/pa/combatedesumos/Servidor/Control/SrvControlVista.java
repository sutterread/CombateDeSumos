package pa.combatedesumos.Servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pa.combatedesumos.Servidor.Vista.PanelInscritos;
import pa.combatedesumos.Servidor.Vista.PanelDojo;
import pa.combatedesumos.Servidor.Vista.SVentana;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del servidor. Gestiona los eventos de la interfaz
 * grafica y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class SrvControlVista implements ActionListener {

    /**
     * Control principal del servidor
     */
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

        // Configurar botón del panel de inscritos
        panelInscritos.jButton1IrAlCombate.addActionListener(this);
        panelInscritos.jButton1IrAlCombate.setActionCommand("Ir al combate");

        // Mostrar ventana con panel de inscritos
        mostrarVentana();
    }

    /**
     * Cambia al panel de combate cuando inicia el torneo
     */
    public void cambiarAlPanelCombate() {
        cambiarPanel(panelDojo);
    }

    /**
     * Hace visible la ventana principal
     */
    public void mostrarVentana() {
        cambiarPanel(panelInscritos);
        ventana.setVisible(true);
    }

    /**
     * Cambia el panel que se muestra actualmente
     *
     * @param panel panel a mostrar
     */
    public void cambiarPanel(javax.swing.JPanel panel) {
        ventana.setContentPane(panel);
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Agrega un luchador a la lista de inscritos Solo recibe strings, sin DTOs
     *
     * @param nombreLuchador nombre del luchador a agregar
     */
    public void agregarLuchadorALista(String nombreLuchador) {
        panelInscritos.agregarLuchador(nombreLuchador);
    }

    /**
     * Habilita el botón de combate
     */
    public void habilitarBotonCombate() {
        panelInscritos.jButton1IrAlCombate.setEnabled(true);
    }

    /**
     * Actualiza el panel del dojo con un mensaje
     *
     * @param mensaje mensaje a mostrar
     */
    public void actualizarCombate(String mensaje) {
        panelDojo.actualizarMensaje(mensaje);
    }

    /**
     * Muestra un mensaje de error en la vista.
     *
     * @param mensaje mensaje de error
     */
    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(ventana, mensaje, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    String seleccionarArchivo(String seleccione_servidorproperties) {
        return seleccionarArchivo.seleccionarProperties(seleccione_servidorproperties);
    }

    /**
     * Gestiona los eventos generados por los componentes de la interfaz
     * grafica.
     *
     * @param e evento generado por un componente de la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Ir al combate")) {
            cambiarPanel(panelDojo);
        }
    }

}
