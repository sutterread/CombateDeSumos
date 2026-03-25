package pa.combatedesumos.Cliente.Control;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import pa.combatedesumos.Cliente.Vista.CVentana;
import pa.combatedesumos.Cliente.Vista.Emergente;
import pa.combatedesumos.Cliente.Vista.PanelCombatiendo;
import pa.combatedesumos.Cliente.Vista.PanelEspera;
import pa.combatedesumos.Cliente.Vista.PanelPrincipal;
import pa.combatedesumos.Cliente.Vista.PanelRegistro;
import pa.combatedesumos.Cliente.Vista.PanelTecnicas;
import pa.combatedesumos.Servidor.Vista.SeleccionarArchivo;

/**
 * Controlador de vista del cliente. Gestiona los eventos de la interfaz grafica
 * y delega las operaciones al control principal.
 *
 * @author Asus
 */
public class ControlVista implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private PanelTecnicas panelTecnicas;
    private PanelRegistro panelRegistro;
    private PanelPrincipal panelPrincipal;
    private Emergente emergente;
    private CVentana ventana;
    private PanelEspera panelEspera;
    private PanelCombatiendo panelCombatiendo;
    private String rutaProperties;
    private SeleccionarArchivo seleccionarArchivo;

    /**
     * Constructor de CControlVista.
     *
     * @param controlPrincipal control principal del cliente
     */
    public ControlVista(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.controlPrincipal = controlPrincipal;
        panelTecnicas = new PanelTecnicas(this);
        panelRegistro = new PanelRegistro(this);
        panelPrincipal = new PanelPrincipal(this);
        panelEspera = new PanelEspera(this);
        panelCombatiendo = new PanelCombatiendo(this);
        emergente = new Emergente();
        seleccionarArchivo = new SeleccionarArchivo();
        ventana = new CVentana(this);

        //Botones Panel Principal
        panelPrincipal.jButton1IrRegistarse.addActionListener(this);
        panelPrincipal.jButton1IrRegistarse.setActionCommand("Ir a PanelRegistrarse");
        panelPrincipal.jButton2Salir.addActionListener(this);
        panelPrincipal.jButton2Salir.setActionCommand("Salir");

        //Botones Panel Registro
        panelRegistro.jButton1SeleccionarTecnicas.addActionListener(this);
        panelRegistro.jButton1SeleccionarTecnicas.setActionCommand("Ir A PanelTecnicas");
        panelRegistro.jButton2EnviarDatos.addActionListener(this);
        panelRegistro.jButton2EnviarDatos.setActionCommand("Enviar Datos");

        //Botón Panel Ténicas
        panelTecnicas.jButton1ConfirmarSeleccion.addActionListener(this);
        panelTecnicas.jButton1ConfirmarSeleccion.setActionCommand("Confirmar Seleccion");
        panelTecnicas.comboCategorias.addActionListener(this);
        panelTecnicas.comboCategorias.setActionCommand("SeleccionarCategoria");
        
        //Botón Panel Espera
        panelEspera.jButton1Salir.addActionListener(this);
        panelEspera.jButton1Salir.setActionCommand("Salir");
    }

    /**
     * Hace visible la ventana principal mostrando el panel principal.
     */
    public void mostrarVentana() {
        cambiarPanel(panelPrincipal);
        ventana.setVisible(true);
    }

    /**
     * Cambia el panel que se muestra actualmente en la ventana principal.
     *
     * @param panel panel que se desea mostrar en la ventana.
     */
    public void cambiarPanel(javax.swing.JPanel panel) {
        ventana.setContentPane(panel);
        ventana.revalidate();
        ventana.repaint();
    }

    /**
     * Carga las categorias en el combobox y muestra el panel de tecnicas.
     *
     * @param categorias arreglo con los ids de las categorias
     */
    public void cargarCategorias(String[] categorias) {
        panelTecnicas.getComboCategorias().removeAllItems();
        for (String cat : categorias) {
            panelTecnicas.getComboCategorias().addItem(cat);
        }
        cambiarPanel(panelTecnicas);
    }

    /**
     * Reconstruye los checkboxes con los kimarites de la categoria elegida.
     * Preserva las selecciones previas de otras categorias.
     *
     * @param kimarites arreglo de kimarites de la categoria
     */
    public void cargarKimaritesPorCategoria(String[] kimarites) {
        // Guarda el estado de los checkboxes de la categoria anterior
        if (panelTecnicas.getCheckboxes() != null) {
            for (JCheckBox cb : panelTecnicas.getCheckboxes()) {
                if (cb.isSelected() && !panelTecnicas.getTecnicasAcumuladas().contains(cb.getText())) {
                    panelTecnicas.getTecnicasAcumuladas().add(cb.getText());
                } else if (!cb.isSelected()) {
                    panelTecnicas.getTecnicasAcumuladas().remove(cb.getText());
                }
            }
        }
        // Reconstruye los checkboxes para la nueva categoria
        panelTecnicas.getPanelCheckboxes().removeAll();
        panelTecnicas.getPanelCheckboxes().setLayout(new GridLayout(kimarites.length, 1));
        JCheckBox[] nuevos = new JCheckBox[kimarites.length];
        for (int i = 0; i < kimarites.length; i++) {
            nuevos[i] = new JCheckBox(kimarites[i].trim());
            nuevos[i].setOpaque(false);
            nuevos[i].setFont(new Font("Colonna MT", Font.PLAIN, 14));
            // Restaura el check si ya habia sido seleccionado antes
            if (panelTecnicas.getTecnicasAcumuladas().contains(kimarites[i].trim())) {
                nuevos[i].setSelected(true);
            }
            panelTecnicas.getPanelCheckboxes().add(nuevos[i]);
        }
        panelTecnicas.setCheckboxes(nuevos);
        panelTecnicas.getPanelCheckboxes().revalidate();
        panelTecnicas.getPanelCheckboxes().repaint();

        java.awt.Container padre = panelTecnicas.getPanelCheckboxes().getParent();
        while (padre != null) {
            if (padre instanceof javax.swing.JScrollPane) {
                padre.revalidate();
                padre.repaint();
                break;
            }
            padre = padre.getParent();
        }
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
     * Cambia la vista al panel de espera (esperando inicio del combate).
     */
    public void mostrarPanelEspera() {
        cambiarPanel(panelEspera);
    }

    /**
     * Cambia la vista al panel de combatiendo (cuando el combate esta activo).
     */
    public void mostrarPanelCombatiendo() {
        cambiarPanel(panelCombatiendo);
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
     * Abre el selector de archivo properties.
     *
     * @param titulo titulo del dialogo
     * @return ruta del archivo seleccionado, o null si se cancela
     */
    public String seleccionar(String titulo) {
        return seleccionarArchivo.seleccionarProperties(titulo);
    }

    /**
     * Establece la ruta del archivo properties cargado.
     *
     * @param ruta ruta del archivo properties
     */
    public void setRutaProperties(String ruta) {
        this.rutaProperties = ruta;
    }

    
    /**
     * Gestiona los eventos generados por los componentes de la interfaz
     * grafica.
     *
     * @param e evento generado por un componente de la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Ir a PanelRegistrarse")) {
            cambiarPanel(panelRegistro);

        } else if (e.getActionCommand().equalsIgnoreCase("Salir")) {
            emergente.confirmacionSalir();

        } else if (e.getActionCommand().equalsIgnoreCase("Ir A PanelTecnicas")) {
            //cambiarPanel(panelTecnicas); //No sé si esto esté bien el el flujo o qué
            controlPrincipal.seleccionarProperties();

        } else if (e.getActionCommand().equalsIgnoreCase("Enviar Datos")) {
            String n = panelRegistro.jTextField1Nombre.getText();
            String p = panelRegistro.jTextField2Peso.getText();
            controlPrincipal.conectarYPelear(panelRegistro.jTextField1Nombre.getText(),
                    panelRegistro.jTextField2Peso.getText());
        } else if (e.getActionCommand().equalsIgnoreCase("Confirmar Seleccion")) {
            String[] tecnicas = panelTecnicas.getTecnicasSeleccionadas();
            if (tecnicas.length == 0) {
                emergente.mostrarAdvertencia("Debe seleccionar al menos una tecnica.");
            } else {
                controlPrincipal.confirmarTecnicas(tecnicas);
                cambiarPanel(panelRegistro); // vuelve al registro tras confirmar
            }

        } else if (e.getActionCommand().equalsIgnoreCase("SeleccionarCategoria")) {
            // Dispara cuando el usuario cambia de categoria en el combo
            if (rutaProperties != null) {
                String categoriaId = panelTecnicas.getCategoriaSeleccionada();
                if (categoriaId != null) {
                    controlPrincipal.cargarKimaritesPorCategoria(rutaProperties, categoriaId);
                }
            }

        } else if (e.getActionCommand().equalsIgnoreCase("..")) {

        } else if (e.getActionCommand().equalsIgnoreCase("...")) {

        } else if (e.getActionCommand().equalsIgnoreCase("....")) {

        }
    }
}
