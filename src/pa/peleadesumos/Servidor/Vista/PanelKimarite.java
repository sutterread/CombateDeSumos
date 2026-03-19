package pa.peleadesumos.Servidor.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Panel que muestra el nombre del kimarite ejecutado en letras grandes.
 * @author Asus
 */
public class PanelKimarite extends JPanel {

    /** Etiqueta que muestra el nombre del kimarite */
    private JLabel lblKimarite;

    /** Etiqueta que muestra quien ejecuto el kimarite */
    private JLabel lblLuchador;

    /** Timer para ocultar el kimarite */
    private Timer timer;

    /**
     * Constructor de PanelKimarite.
     */
    public PanelKimarite() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initComponentes();
    }

    /**
     * Inicializa y organiza los componentes graficos del panel.
     */
    private void initComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        lblLuchador = new JLabel("", SwingConstants.CENTER);
        lblLuchador.setFont(new Font("Impact", Font.BOLD, 20));
        lblLuchador.setForeground(Color.WHITE);
        lblLuchador.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblLuchador, gbc);

        lblKimarite = new JLabel("", SwingConstants.CENTER);
        lblKimarite.setFont(new Font("Impact", Font.BOLD, 48));
        lblKimarite.setForeground(new Color(255, 215, 0));
        lblKimarite.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblKimarite, gbc);
    }

    /**
     * Muestra el kimarite ejecutado y lo oculta despues de 1 segundo.
     * @param nombreLuchador nombre del luchador que ejecuto el kimarite
     * @param kimarite nombre del kimarite ejecutado
     */
    public void mostrarKimarite(String nombreLuchador, String kimarite) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        lblLuchador.setText(nombreLuchador);
        lblKimarite.setText("¡" + kimarite + "!");
        lblLuchador.setVisible(true);
        lblKimarite.setVisible(true);
        revalidate();
        repaint();

        timer = new Timer(500, e -> {
            lblLuchador.setVisible(false);
            lblKimarite.setVisible(false);
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}