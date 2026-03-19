package pa.combatedesumos.Servidor.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que muestra el titulo de la aplicacion en la ventana del servidor.
 * @author Asus
 */
public class PanelTitulo extends JPanel {

    //
    private JLabel lblTitulo;

    //Constructor
    public PanelTitulo() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initComponentes();
    }

    //Inicializa y organiza los componentes graficos del panel.
    private void initComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblTitulo = new JLabel("PELEA DE SUMOS");
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 48));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitulo, gbc);
    }
}