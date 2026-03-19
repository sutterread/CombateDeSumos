package pa.combatedesumos.Servidor.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import pa.combatedesumos.Servidor.Control.SControlVista;

/**
 * Panel que muestra el ganador del combate al final.
 * @author Asus
 */
public class PanelGanador extends JPanel {

    /** Etiqueta del titulo ganador */
    private JLabel lblTituloGanador;

    /** Etiqueta con el nombre del ganador */
    private JLabel lblNombreGanador;

    /** Boton salir */
    private JButton btnSalir;

    /**
     * Constructor de PanelGanador.
     * @param controlVista control de vista del servidor
     */
    public PanelGanador(SControlVista controlVista) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initComponentes(controlVista);
    }

    /**
     * Inicializa y organiza los componentes graficos del panel.
     * @param controlVista control de vista para registrar los listeners
     */
    private void initComponentes(SControlVista controlVista) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titulo ganador
        lblTituloGanador = new JLabel("¡GANADOR!", SwingConstants.CENTER);
        lblTituloGanador.setFont(new Font("Impact", Font.BOLD, 48));
        lblTituloGanador.setForeground(new Color(255, 215, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTituloGanador, gbc);

        // Nombre del ganador
        lblNombreGanador = new JLabel("", SwingConstants.CENTER);
        lblNombreGanador.setFont(new Font("Impact", Font.BOLD, 36));
        lblNombreGanador.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(lblNombreGanador, gbc);

        // Boton salir
        btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(180, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalir.addActionListener(controlVista);
        btnSalir.setActionCommand("SalirServidor");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnSalir, gbc);
    }

    /**
     * Muestra el nombre del ganador del combate.
     * @param nombre nombre del luchador ganador
     */
    public void mostrarGanador(String nombre) {
        lblNombreGanador.setText(nombre);
        setVisible(true);
        revalidate();
        repaint();
    }
}