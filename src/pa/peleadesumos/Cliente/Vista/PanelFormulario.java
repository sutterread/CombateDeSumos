package pa.peleadesumos.Cliente.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pa.peleadesumos.Cliente.Control.CControlVista;

/**
 * Panel que contiene el formulario de registro del luchador. Fondo
 * transparente, se muestra encima del PanelFondo.
 *
 * @author Asus
 */
public class PanelFormulario extends JPanel {

    /**
     * Etiquetas
     */
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblPeso;
    private JLabel lblCombates;

    /**
     * Campos de texto
     */
    private JTextField txtNombre;
    private JTextField txtPeso;
    private JTextField txtCombates;

    /**
     * Botones
     */
    private JButton btnCargarTecnicas;
    private JButton btnPelear;
    private JButton btnSalir;

    /**
     * Constructor de PanelFormulario.
     *
     * @param controlVista control de vista del cliente
     */
    public PanelFormulario(CControlVista controlVista) {
        setOpaque(false);
        setLayout(new GridBagLayout());
        initComponentes(controlVista);
    }

    /**
     * Inicializa y organiza los componentes graficos del formulario.
     *
     * @param controlVista control de vista para registrar los listeners
     */
    private void initComponentes(CControlVista controlVista) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titulo
        lblTitulo = new JLabel("PELEA DE SUMOS");
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Nombre
        gbc.gridwidth = 1;
        lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtNombre, gbc);

        // Peso
        lblPeso = new JLabel("Peso:");
        lblPeso.setForeground(Color.WHITE);
        lblPeso.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPeso, gbc);

        txtPeso = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPeso, gbc);

        // Combates ganados
        lblCombates = new JLabel("Combates ganados:");
        lblCombates.setForeground(Color.WHITE);
        lblCombates.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblCombates, gbc);

        txtCombates = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(txtCombates, gbc);

        // Boton cargar tecnicas
        btnCargarTecnicas = new JButton("Cargar técnicas");
        btnCargarTecnicas.setBackground(new Color(255, 220, 100));
        btnCargarTecnicas.setFont(new Font("Arial", Font.BOLD, 14));
        btnCargarTecnicas.addActionListener(controlVista);
        btnCargarTecnicas.setActionCommand("CargarTecnicas");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnCargarTecnicas, gbc);

        // Boton pelear
        btnPelear = new JButton("¡A pelear!");
        btnPelear.setBackground(new Color(255, 100, 100));
        btnPelear.setForeground(Color.WHITE);
        btnPelear.setFont(new Font("Arial", Font.BOLD, 16));
        btnPelear.addActionListener(controlVista);
        btnPelear.setActionCommand("Pelear");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btnPelear, gbc);
        
        //Boton salir
        btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(180, 0, 0));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.addActionListener(controlVista);
        btnSalir.setActionCommand("Salir");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(btnSalir, gbc);
    }

    /**
     * Retorna el nombre ingresado.
     *
     * @return nombre del luchador
     */
    public String getNombre() {
        return txtNombre.getText();
    }

    /**
     * Retorna el peso ingresado.
     *
     * @return peso del luchador
     */
    public String getPeso() {
        return txtPeso.getText();
    }

    /**
     * Retorna los combates ganados ingresados.
     *
     * @return combates ganados
     */
    public String getCombates() {
        return txtCombates.getText();
    }
}
