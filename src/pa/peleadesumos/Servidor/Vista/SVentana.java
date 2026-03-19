package pa.peleadesumos.Servidor.Vista;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import pa.peleadesumos.Servidor.Control.SControlVista;

/**
 * Ventana principal del servidor. Muestra el desarrollo del combate de sumo
 * visualmente.
 *
 * @author Asus
 */
public class SVentana extends JFrame {

    /**
     * Panel de fondo
     */
    private PanelFondo panelFondo;

    /**
     * Panel del titulo
     */
    private PanelTitulo panelTitulo;

    /**
     * Panel del luchador 1
     */
    private PanelLuchadores panelLuchadores;

    private PanelKimarite panelKimarite;

    /**
     * Panel del log
     */
    private PanelLog panelLog;

    /**
     * Panel del ganador
     */
    private PanelGanador panelGanador;

    /**
     * Constructor de SVentana.
     *
     * @param controlVista control de vista del servidor
     */
    public SVentana(SControlVista controlVista) {
        initComponentes(controlVista);
    }

    /**
     * Inicializa y organiza los componentes de la ventana.
     *
     * @param controlVista control de vista del servidor
     */
    private void initComponentes(SControlVista controlVista) {
        setTitle("PeleaDeSumos - Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel fondo
        panelFondo = new PanelFondo();
        panelFondo.setImagen("Imgs/fondoservidor.jpg");
        panelFondo.setBounds(0, 0, 1000, 700);
        panelFondo.setLayout(new BorderLayout());

        // Panel titulo - NORTH
        panelTitulo = new PanelTitulo();
        panelFondo.add(panelTitulo, BorderLayout.NORTH);

        // Panel centro - luchadores a los lados, combate y log en el centro
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setOpaque(false);

        panelLuchadores = new PanelLuchadores();
        panelLuchadores.getPanelLuchador1().setPreferredSize(new java.awt.Dimension(200, 0));
        panelLuchadores.getPanelLuchador2().setPreferredSize(new java.awt.Dimension(200, 0));
        panelCentro.add(panelLuchadores.getPanelLuchador1(), BorderLayout.WEST);
        panelCentro.add(panelLuchadores.getPanelLuchador2(), BorderLayout.EAST);

        // Combate y log en el centro
        JPanel panelMedio = new JPanel(new BorderLayout());
        panelMedio.setOpaque(false);
        panelKimarite = new PanelKimarite();
        panelLog = new PanelLog();
        panelLog.setPreferredSize(new java.awt.Dimension(0, 150));
        panelMedio.add(panelKimarite, BorderLayout.CENTER);
        panelMedio.add(panelLog, BorderLayout.SOUTH);
        panelCentro.add(panelMedio, BorderLayout.CENTER);

        panelFondo.add(panelCentro, BorderLayout.CENTER);

        // Panel ganador - SOUTH
        panelGanador = new PanelGanador(controlVista);
        panelGanador.setVisible(false);
        panelFondo.add(panelGanador, BorderLayout.SOUTH);

        add(panelFondo);
    }

    /**
     * Muestra el nombre del luchador 1.
     *
     * @param nombre nombre del luchador 1
     */
    public void mostrarNombreLuchador1(String nombre) {
        panelLuchadores.mostrarNombreLuchador1(nombre);
    }

    /**
     * Muestra el kimarite ejecutado.
     *
     * @param nombreLuchador nombre del luchador
     * @param kimarite nombre del kimarite
     */
    public void mostrarKimarite(String nombreLuchador, String kimarite) {
        SwingUtilities.invokeLater(() -> panelKimarite.mostrarKimarite(nombreLuchador, kimarite));
    }

    /**
     * Muestra el nombre del luchador 2.
     *
     * @param nombre nombre del luchador 2
     */
    public void mostrarNombreLuchador2(String nombre) {
        panelLuchadores.mostrarNombreLuchador2(nombre);
    }

    /**
     * Agrega un evento al log del combate.
     *
     * @param evento texto del evento a mostrar
     */
    public void agregarEvento(String evento) {
        panelLog.agregarEvento(evento);
    }

    /**
     * Muestra el ganador del combate.
     *
     * @param nombre nombre del luchador ganador
     */
    public void mostrarGanador(String nombre) {
        panelGanador.setVisible(true);
        panelGanador.mostrarGanador(nombre);
    }
}
