package pa.combatedesumos.Cliente.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pa.combatedesumos.Cliente.Control.CControlVista;

/**
 * Panel que muestra las categorias y tecnicas kimarite con checkboxes para que
 * el luchador seleccione en cuales es experto.
 *
 * @author Asus
 */
public class PanelTecnicas extends JPanel {

    /**
     * ComboBox con las categorias disponibles
     */
    private JComboBox<String> comboCategorias;

    /**
     * Panel interno con los checkboxes
     */
    private JPanel panelCheckboxes;

    /**
     * Scroll para los checkboxes
     */
    private JScrollPane scroll;

    /**
     * Arreglo de checkboxes de la categoria actual
     */
    private JCheckBox[] checkboxes;

    /**
     * Boton confirmar seleccion
     */
    private JButton btnConfirmar;

    /**
     * Acumulado de tecnicas seleccionadas de todas las categorias
     */
    private ArrayList<String> tecnicasAcumuladas;

    /**
     * Constructor de PanelTecnicas.
     *
     * @param controlVista control de vista del cliente
     */
    public PanelTecnicas(CControlVista controlVista) {
        setOpaque(false);
        setLayout(new BorderLayout());
        tecnicasAcumuladas = new ArrayList<>();
        initComponentes(controlVista);
    }

    /**
     * Inicializa y organiza los componentes graficos del panel.
     *
     * @param controlVista control de vista para registrar los listeners
     */
    private void initComponentes(CControlVista controlVista) {
        comboCategorias = new JComboBox<>();
        comboCategorias.setFont(new Font("Arial", Font.BOLD, 14));
        comboCategorias.addActionListener(controlVista);
        comboCategorias.setActionCommand("SeleccionarCategoria");
        add(comboCategorias, BorderLayout.NORTH);

        panelCheckboxes = new JPanel();
        panelCheckboxes.setOpaque(false);
        scroll = new JScrollPane(panelCheckboxes);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll, BorderLayout.CENTER);

        btnConfirmar = new JButton("Confirmar selección");
        btnConfirmar.setBackground(new Color(255, 220, 100));
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmar.addActionListener(controlVista);
        btnConfirmar.setActionCommand("ConfirmarTecnicas");
        add(btnConfirmar, BorderLayout.SOUTH);
    }

    /**
     * Retorna el combobox de categorias.
     *
     * @return combobox de categorias
     */
    public JComboBox<String> getComboCategorias() {
        return comboCategorias;
    }

    /**
     * Retorna el panel de checkboxes.
     *
     * @return panel de checkboxes
     */
    public JPanel getPanelCheckboxes() {
        return panelCheckboxes;
    }

    /**
     * Retorna los checkboxes de la categoria actual.
     *
     * @return arreglo de checkboxes
     */
    public JCheckBox[] getCheckboxes() {
        return checkboxes;
    }

    /**
     * Establece los checkboxes de la categoria actual.
     *
     * @param checkboxes arreglo de checkboxes
     */
    public void setCheckboxes(JCheckBox[] checkboxes) {
        this.checkboxes = checkboxes;
    }

    /**
     * Retorna el acumulado de tecnicas seleccionadas.
     *
     * @return lista de tecnicas acumuladas
     */
    public ArrayList<String> getTecnicasAcumuladas() {
        return tecnicasAcumuladas;
    }

    /**
     * Retorna la categoria actualmente seleccionada en el combobox.
     *
     * @return id de la categoria seleccionada
     */
    public String getCategoriaSeleccionada() {
        return (String) comboCategorias.getSelectedItem();
    }

    /**
     * Retorna todas las tecnicas seleccionadas acumuladas.
     *
     * @return arreglo con todas las tecnicas marcadas
     */
    public String[] getTecnicasSeleccionadas() {
        return tecnicasAcumuladas.toArray(new String[0]);
    }
}
