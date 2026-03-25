package pa.combatedesumos.Servidor.Vista;

/**
 * Interfaz para sincronizar la reproducción de GIFs con el combate.
 * Permite que el GIF se reproduzca completamente antes de continuar.
 * 
 * @author VALEN
 */
public interface IGifSincronizado {
    /**
     * Reproduce un GIF y espera a que termine
     * @param rutaGif ruta del GIF a reproducir
     * @return true cuando el GIF ha terminado completamente
     */
    void reproducirGifYEsperar(String rutaGif, Runnable alTerminar);
    
    /**
     * Detiene la reproducción del GIF actual
     */
    void detenerGif();
}
