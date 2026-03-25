package pa.combatedesumos.Cliente.Vista;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Asus
 */
public class PanelFondo extends JPanel {

    /**
     * Imagen utilizada como fondo del panel.
     */
    private Image imagen;

    /**
     * Constructor vacío del panel.
     *
     * Es necesario para permitir que el diseñador gráfico de NetBeans pueda
     * instanciar el panel.
     */
    public PanelFondo() {
    }

    /**
     * Constructor que permite crear el panel con una imagen de fondo.
     *
     * @param ruta ruta de la imagen que se utilizará como fondo del panel.
     */
    public PanelFondo(String ruta) {
        setImagen(ruta);
    }

    /**
     * Establece la imagen que se utilizará como fondo del panel.
     *
     * Si la imagen no se encuentra en la ruta especificada, se lanza una
     * excepción en tiempo de ejecución.
     *
     * @param ruta ruta del archivo de imagen que se desea mostrar como fondo.
     */
    public void setImagen(String ruta) {
        ImageIcon icono = new ImageIcon(ruta);
        if (icono.getIconWidth() == -1) {
            throw new RuntimeException(
                    "No se encontró la imagen: " + ruta
            );
        }
        imagen = icono.getImage();
        repaint();
    }

    /**
     * Dibuja la imagen de fondo dentro del panel con alta calidad.
     *
     * Este método sobrescribe el comportamiento de JPanel para renderizar la
     * imagen escalada al tamaño actual del panel. Se utilizan RenderingHints
     * para garantizar interpolación bicúbica, evitando pérdida de calidad
     * al reducir imágenes más grandes que el panel.
     *
     * @param g objeto Graphics utilizado para realizar el dibujo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            g2d.setRenderingHint(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY
            );
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2d.drawImage(
                    imagen,
                    0, 0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }
}