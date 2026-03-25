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
    
    /**
     * Constructor que recibe una inyección de dependencia y envía otra
     * @param sControlPrincipal 
     */
    public ControlLuchador(SrvControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
        this.ldao=new LuchadorDAO(this);
    }
    /**
     * Instancia un luchador
     * @param idLuchador
     * @param nombre
     * @param peso
     * @param victorias
     * @param kimarites
     * @return el luchador recién instanciado
     */
    public LuchadorDTO crearLuchador(int idLuchador,String nombre, double peso, int victorias, String[] kimarites){
        luchador= new LuchadorDTO(idLuchador,nombre, peso, victorias, kimarites);
        return luchador;
    }
    /**
     * Llama el método de la clase DAO correspondiente para consultar un luchador por su id en la base de datos
     * @param idLuchador
     * @return el luchador consultado
     */
    public LuchadorDTO consultarLuchadorEnBD(int idLuchador){
        return ldao.consultarLuchador(idLuchador);
    }
    /**
     * A partir de los datos de un nuevo luchador llama al método del DAO para crearlo en la base de datos
     * @param nombre
     * @param peso
     * @param victorias
     * @param kimarites 
     */
    public void insertarLuchadorEnBD(String nombre, double peso, int victorias, String[] kimarites){
        luchador= new LuchadorDTO(nombre, peso, victorias, kimarites);
        ldao.insertarLuchador(luchador);
    }
    /**
     * Suma una victoria en la base de datos a un luchador dado
     * @param luchador 
     */
    public void sumarVictoria(LuchadorDTO luchador){
        luchador.setVictorias(luchador.getVictorias()+1);
        ldao.modificarVictorias(luchador);
    }
    
    
    
    
}
