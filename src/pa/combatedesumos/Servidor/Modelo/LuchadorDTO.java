package pa.combatedesumos.Servidor.Modelo;

/**
 *
 * @author Asus
 */
public class LuchadorDTO {
    
    private int idLuchador;
    private String nombre;
    private double peso;
    private int victorias;
    private String[] kimarites;

    // Para cuando se crea nuevo (sin ID, lo asigna la BD)
    public LuchadorDTO(String nombre, double peso, int victorias, String[] kimarites) {
    this.nombre = nombre;
    this.peso = peso;
    this.victorias=victorias;
    this.kimarites=kimarites;
}

    // Para cuando se trae de la BD (con ID ya asignado)
    public LuchadorDTO(int idLuchador, String nombre, double peso, int victorias, String[] kimarites) {
    this.idLuchador = idLuchador;
    this.nombre = nombre;
    this.peso = peso;
    this.victorias=victorias;
    this.kimarites=kimarites;
}

    public int getIdLuchador() {
        return idLuchador;
    }

    public void setIdLuchador(int idLuchador) {
        this.idLuchador = idLuchador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public String[] getKimarites() {
        return kimarites;
    }

    public void setKimarites(String[] kimarites) {
        this.kimarites = kimarites;
    }
    
    
    
}
