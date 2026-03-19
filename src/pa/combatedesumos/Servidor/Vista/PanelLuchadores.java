package pa.combatedesumos.Servidor.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel que muestra los dos luchadores del combate con sus imagenes y nombres.
 *
 * @author Asus
 */
public class PanelLuchadores extends JPanel {

    //label luchador 2
    private JLabel lblNombreLuchador1;

    //label luchador 2
    private JLabel lblNombreLuchador2;

    private JPanel panelL1;
    private JPanel panelL2;

    //Constructor 
    public PanelLuchadores() {
        setOpaque(false);
        setLayout(new GridLayout(1, 2, 10, 0));
        initComponentes();
    }

    //Inicializa y organiza los componentes graficos del panel.
    private void initComponentes() {
        // Panel luchador 1
        panelL1 = new JPanel(new BorderLayout());
        panelL1.setOpaque(false);
        PanelImagenLuchador imagenL1 = new PanelImagenLuchador("Imgs/luchador1.jpg");
        lblNombreLuchador1 = new JLabel("Esperando...", SwingConstants.CENTER);
        lblNombreLuchador1.setForeground(Color.WHITE);
        lblNombreLuchador1.setFont(new Font("Impact", Font.BOLD, 18));
        imagenL1.setPreferredSize(new java.awt.Dimension(200, 200));
        imagenL1.setMaximumSize(new java.awt.Dimension(200, 200));
        JPanel wrapL1 = new JPanel();
        wrapL1.setOpaque(false);
        wrapL1.add(imagenL1);
        panelL1.add(wrapL1, BorderLayout.CENTER);
        panelL1.add(lblNombreLuchador1, BorderLayout.SOUTH);
        add(panelL1);

        // Panel luchador 2
        panelL2 = new JPanel(new BorderLayout());
        panelL2.setOpaque(false);
        PanelImagenLuchador imagenL2 = new PanelImagenLuchador("Imgs/luchador2.png");
        lblNombreLuchador2 = new JLabel("Esperando...", SwingConstants.CENTER);
        lblNombreLuchador2.setForeground(Color.WHITE);
        lblNombreLuchador2.setFont(new Font("Impact", Font.BOLD, 18));
        imagenL2.setPreferredSize(new java.awt.Dimension(200, 200));
        imagenL2.setMaximumSize(new java.awt.Dimension(200, 200));
        JPanel wrapL2 = new JPanel();
        wrapL2.setOpaque(false);
        wrapL2.add(imagenL2);
        panelL2.add(wrapL2, BorderLayout.CENTER);
        panelL2.add(lblNombreLuchador2, BorderLayout.SOUTH);
        add(panelL2);
    }

    /**
     * Muestra el nombre del luchador 1.
     *
     * @param nombre nombre del luchador 1
     */
    public void mostrarNombreLuchador1(String nombre) {
        lblNombreLuchador1.setText(nombre);
    }

    /**
     * Muestra el nombre del luchador 2.
     *
     * @param nombre nombre del luchador 2
     */
    public void mostrarNombreLuchador2(String nombre) {
        lblNombreLuchador2.setText(nombre);
    }

    /**
     * Retorna el panel del luchador 1.
     *
     * @return panel del luchador 1
     */
    public JPanel getPanelLuchador1() {
        return panelL1;
    }

    /**
     * Retorna el panel del luchador 2.
     *
     * @return panel del luchador 2
     */
    public JPanel getPanelLuchador2() {
        return panelL2;
    }

    /**
     * Panel interno que muestra la imagen de un luchador.
     */
    private class PanelImagenLuchador extends JPanel {

        /**
         * Imagen del luchador
         */
        private Image imagen;

        /**
         * Constructor de PanelImagenLuchador.
         *
         * @param ruta ruta de la imagen del luchador
         */
        public PanelImagenLuchador(String ruta) {
            setOpaque(false);
            setPreferredSize(new java.awt.Dimension(200, 200));
            imagen = new ImageIcon(ruta).getImage();
        }

        /**
         * Dibuja la imagen del luchador.
         *
         * @param g objeto Graphics
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
