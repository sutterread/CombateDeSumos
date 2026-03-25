package pa.combatedesumos.Servidor.Control;

import pa.combatedesumos.Servidor.Modelo.DAO.LuchadorDAO;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 *
 * @author Sergio Vanegas
 * @author Asus
 */
public class ControlLuchador {
    
    private SrvControlPrincipal sControlPrincipal;
    private LuchadorDTO luchador;
    private LuchadorDAO ldao;
    
    public ControlLuchador(SrvControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
        this.ldao=new LuchadorDAO(this);
    }
    //Este método finalmente será absorbido por el de consultar, parece que no se puede dejar un solo método de crear
    //(ni falta que hace) por lo que hay sobrecarga de constructores en DTO y los únicos métodos del DAO que los usan
    //usan uno diferente cada uno
    public LuchadorDTO crearLuchador(int idLuchador,String nombre, double peso, int victorias, String[] kimarites){
        luchador= new LuchadorDTO(idLuchador,nombre, peso, victorias, kimarites);
        return luchador;
    }
    
    public void insertarLuchadorEnBD(String nombre, double peso, int victorias, String[] kimarites){
        luchador = new LuchadorDTO(nombre, peso, victorias, kimarites);
        ldao.insertarLuchador(luchador);
    }
    
    public void sumarVictoria(LuchadorDTO luchador){
        luchador.setVictorias(luchador.getVictorias()+1);
        ldao.modificarVictorias(luchador);
    }
    
    
}
