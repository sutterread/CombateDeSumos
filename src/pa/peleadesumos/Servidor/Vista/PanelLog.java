package pa.peleadesumos.Servidor.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Panel que muestra el log de eventos del combate.
 * @author Asus
 */
public class PanelLog extends JPanel {

    /** Area de texto con los eventos del combate */
    private JTextArea txtLog;

    /**
     * Constructor de PanelLog.
     */
    public PanelLog() {
        setOpaque(false);
        setLayout(new BorderLayout());
        initComponentes();
    }

    /**
     * Inicializa y organiza los componentes graficos del panel.
     */
    private void initComponentes() {
        // Titulo
        JLabel lblTitulo = new JLabel("¡Log del combate!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Impact", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Area de texto
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtLog.setForeground(Color.WHITE);
        txtLog.setBackground(new Color(0, 0, 0, 150));
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        txtLog.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JScrollPane scroll = new JScrollPane(txtLog);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * Agrega un evento al log del combate.
     * @param evento texto del evento a mostrar
     */
    public void agregarEvento(String evento) {
        txtLog.append(" -> " + evento + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
}