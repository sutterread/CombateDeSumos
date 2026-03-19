package pa.combatedesumos.Servidor.Modelo;

/**
 *
 * @author Asus
 */
public class LuchadorDTO {
    
    private String nombre;
    private float peso;
    private int combatesGanados;
    private String[] kimarites;

    /**
     * Constructor del luchador
     * @param nombre
     * @param peso
     * @param combatesGanados
     * @param kimarites
     */
    public LuchadorDTO(String nombre, float peso, int combatesGanados, String[] kimarites) {
        this.nombre = nombre;
        this.peso = peso;
        this.combatesGanados = combatesGanados;
        this.kimarites = kimarites;
    }

    //getters y setters normales
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getCombatesGanados() {
        return combatesGanados;
    }

    public void setCombatesGanados(int combatesGanados) {
        this.combatesGanados = combatesGanados;
    }

    public String[] getKimarites() {
        return kimarites;
    }

    public void setKimarites(String[] kimarites) {
        this.kimarites = kimarites;
    }
    
    
    
}
