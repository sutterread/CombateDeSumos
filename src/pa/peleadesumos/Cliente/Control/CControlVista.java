package pa.peleadesumos.Cliente.Control;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import pa.peleadesumos.Cliente.Vista.CVentana;
import pa.peleadesumos.Cliente.Vista.Emergente;
import pa.peleadesumos.Cliente.Vista.PanelFormulario;
import pa.peleadesumos.Cliente.Vista.PanelTecnicas;
import pa.peleadesumos.Cliente.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del cliente. Gestiona los eventos de la interfaz grafica
 * y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class CControlVista implements ActionListener {

    /**
     * Control principal del cliente
     */
    private CControlPrincipal controlPrincipal;

    /**
     * Ventana principal del cliente
     */
    private CVentana ventana;

    /**
     * Panel del formulario
     */
    private PanelFormulario panelFormulario;

    /**
     * Panel de tecnicas
     */
    private PanelTecnicas panelTecnicas;

    /**
     * Selector de archivos
     */
    private SeleccionarArchivo seleccionarArchivo;

    /**
     * Ventanas emergentes
     */
    private Emergente emergente;

    /**
     * Ruta del properties cargado
     */
    private String rutaProperties;

    /**
     * Constructor de CControlVista.
     *
     * @param controlPrincipal control principal del cliente
     */
    public CControlVista(CControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.seleccionarArchivo = new SeleccionarArchivo();
        this.emergente = new Emergente();
        this.panelFormulario = new PanelFormulario(this);
        this.panelTecnicas = new PanelTecnicas(this);
        this.ventana = new CVentana(this, panelFormulario, panelTecnicas);
    }

    /**
     * Gestiona los eventos generados por los componentes de la interfaz
     * grafica.
     *
     * @param e evento generado por un componente de la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("CargarTecnicas")) {
            controlPrincipal.seleccionarProperties();

        } else if (e.getActionCommand().equalsIgnoreCase("SeleccionarCategoria")) {
            if (rutaProperties != null) {
                String categoriaId = panelTecnicas.getCategoriaSeleccionada();
                if (categoriaId != null) {
                    controlPrincipal.cargarKimaritesPorCategoria(rutaProperties, categoriaId);
                }
            }

        } else if (e.getActionCommand().equalsIgnoreCase("ConfirmarTecnicas")) {
            String[] tecnicas = panelTecnicas.getTecnicasSeleccionadas();
            if (tecnicas.length == 0) {
                emergente.mostrarAdvertencia("Debe seleccionar al menos una tecnica.");
            } else {
                controlPrincipal.confirmarTecnicas(tecnicas);
                ventana.ocultarPanelTecnicas();
            }

        } else if (e.getActionCommand().equalsIgnoreCase("Pelear")) {
            controlPrincipal.conectarYPelear(
                    panelFormulario.getNombre(),
                    panelFormulario.getPeso(),
                    panelFormulario.getCombates()
            );
        } else if(e.getActionCommand().equalsIgnoreCase("Salir")){
            emergente.confirmacionSalir();
        } 
        
    }

    /**
     * Carga las categorias en el combobox del panel de tecnicas.
     *
     * @param categorias arreglo con los ids de las categorias
     */
    public void cargarCategorias(String[] categorias) {
        panelTecnicas.getComboCategorias().removeAllItems();
        for (String categoria : categorias) {
            panelTecnicas.getComboCategorias().addItem(categoria);
        }
        ventana.mostrarPanelTecnicas();
    }

    /**
     * Carga los kimarites de una categoria en los checkboxes.
     *
     * @param kimarites arreglo de kimarites de la categoria
     */
    public void cargarKimaritesPorCategoria(String[] kimarites) {
        if (panelTecnicas.getCheckboxes() != null) {
            for (JCheckBox cb : panelTecnicas.getCheckboxes()) {
                if (cb.isSelected() && !panelTecnicas.getTecnicasAcumuladas().contains(cb.getText())) {
                    panelTecnicas.getTecnicasAcumuladas().add(cb.getText());
                } else if (!cb.isSelected()) {
                    panelTecnicas.getTecnicasAcumuladas().remove(cb.getText());
                }
            }
        }
        panelTecnicas.getPanelCheckboxes().removeAll();
        panelTecnicas.getPanelCheckboxes().setLayout(new GridLayout(kimarites.length, 1));
        JCheckBox[] nuevos = new JCheckBox[kimarites.length];
        for (int i = 0; i < kimarites.length; i++) {
            nuevos[i] = new JCheckBox(kimarites[i].trim());
            nuevos[i].setOpaque(false);
            nuevos[i].setFont(new Font("Arial", Font.PLAIN, 13));
            if (panelTecnicas.getTecnicasAcumuladas().contains(kimarites[i].trim())) {
                nuevos[i].setSelected(true);
            }
            panelTecnicas.getPanelCheckboxes().add(nuevos[i]);
        }
        panelTecnicas.setCheckboxes(nuevos);
        panelTecnicas.getPanelCheckboxes().revalidate();
        panelTecnicas.getPanelCheckboxes().repaint();
    }

    /**
     * Muestra el resultado del combate al usuario.
     *
     * @param resultado "GANASTE" o "PERDISTE"
     */
    public void mostrarResultado(String resultado) {
        emergente.mostrarResultado(resultado);
    }

    /**
     * Muestra un mensaje de advertencia al usuario.
     *
     * @param mensaje texto de advertencia
     */
    public void mostrarAdvertencia(String mensaje) {
        emergente.mostrarAdvertencia(mensaje);
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
     * Hace visible la ventana principal del cliente.
     */
    public void mostrarVentana() {
        ventana.setVisible(true);
    }

    /**
     * Establece la ruta del archivo properties cargado.
     *
     * @param ruta ruta del archivo properties
     */
    public void setRutaProperties(String ruta) {
        this.rutaProperties = ruta;
    }
}
