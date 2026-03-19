package pa.combatedesumos.Servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;
import pa.combatedesumos.Servidor.Vista.SVentana;

/**
 * Controlador de vista del servidor. Gestiona los eventos de la interfaz
 * grafica y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class SControlVista implements ActionListener {

    /**
     * Control principal del servidor
     */
    private SControlPrincipal controlPrincipal;

    /**
     * Ventana principal del servidor
     */
    private SVentana ventana;
    private SeleccionarArchivo seleccionarArchivo;

    /**
     * Constructor de SControlVista.
     *
     * @param controlPrincipal control principal del servidor
     */
    public SControlVista(SControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventana = new SVentana(this);
        this.seleccionarArchivo = new SeleccionarArchivo();
    }

    /**
     * Gestiona los eventos generados por los componentes de la interfaz
     * grafica.
     *
     * @param e evento generado por un componente de la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("SalirServidor")) {
            System.exit(0);
        }
    }

    /**
     * Permite seleccionar un archivo properties.
     *
     * @param titulo titulo del dialogo
     * @return ruta del archivo seleccionado
     */
    public String seleccionar(String titulo) {
        return seleccionarArchivo.seleccionarProperties(titulo);
    }

    /**
     * Muestra el nombre del luchador 1 en la ventana.
     *
     * @param nombre nombre del luchador 1
     */
    public void mostrarNombreLuchador1(String nombre) {
        SwingUtilities.invokeLater(() -> ventana.mostrarNombreLuchador1(nombre));
    }

    /**
     * Muestra el nombre del luchador 2 en la ventana.
     *
     * @param nombre nombre del luchador 2
     */
    public void mostrarNombreLuchador2(String nombre) {
        SwingUtilities.invokeLater(() -> ventana.mostrarNombreLuchador2(nombre));
    }

    /**
     * Muestra el kimarite ejecutado en la ventana.
     *
     * @param nombreLuchador nombre del luchador
     * @param kimarite nombre del kimarite
     */
    public void mostrarKimarite(String nombreLuchador, String kimarite) {
        ventana.mostrarKimarite(nombreLuchador, kimarite);
    }

    /**
     * Agrega un evento al log del combate.
     *
     * @param evento texto del evento a mostrar
     */
    public void agregarEvento(String evento) {
        SwingUtilities.invokeLater(() -> ventana.agregarEvento(evento));
    }

    /**
     * Muestra el ganador del combate en la ventana.
     *
     * @param nombre nombre del luchador ganador
     */
    public void mostrarGanador(String nombre) {
        SwingUtilities.invokeLater(() -> ventana.mostrarGanador(nombre));
    }

    /**
     * Hace visible la ventana principal del servidor.
     */
    public void mostrarVentana() {
        ventana.setVisible(true);
    }
}
