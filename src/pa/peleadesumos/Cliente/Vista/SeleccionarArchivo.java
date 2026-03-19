package pa.peleadesumos.Cliente.Vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Clase utilitaria que permite al usuario seleccionar archivos del sistema
 * mediante un cuadro de dialogo.
 * @author Asus
 */
public class SeleccionarArchivo {

    /**
     * Abre un cuadro de dialogo para que el usuario seleccione el archivo
     * de propiedades con las tecnicas kimarite.
     * @param titulo titulo que se mostrara en la ventana del selector
     * @return ruta absoluta del archivo seleccionado, o null si se cancela
     */
    public String seleccionarProperties(String titulo) {
        
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.setDialogTitle(titulo);
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos Properties (*.properties)", "properties");
        
        fileChooser.setFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".properties")) {
                return null;
            }
            return archivo.getAbsolutePath();
        }
        return null;
    }
}