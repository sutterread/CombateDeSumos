package pa.combatedesumos.Servidor.Modelo.DAO;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import pa.combatedesumos.Servidor.Control.ControlLuchador;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxBD;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Realiza todas las operaciones de la base de datos para LuchadorDTO
 * @author Sergio Vanegas
 */
public class LuchadorDAO implements ILuchadorDAO{
    
    private ControlLuchador cl;
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private LuchadorDTO luchador;

    /**
     * Constructor que recibe la inyección de dependencia
     * @param cl controlador de luchador utilizado para crear instancias de LuchadorDTO
     */
    public LuchadorDAO(ControlLuchador cl) {
        this.cl=cl;
        this.luchador=null;
    }
    /**
     * Inserta un luchador en la tabla de la base de datos, convirtiendo un String[] del DTO
     * en String
     * @param luchador 
     */
    @Override
    public void insertarLuchador(LuchadorDTO luchador){
        try {
            con = CnxBD.getConexion();
            pst = con.prepareStatement("INSERT INTO luchador (nombre,peso,victorias,kimarites) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, luchador.getNombre());
            pst.setDouble(2,luchador.getPeso());
            pst.setInt(3, luchador.getVictorias());
            pst.setString(4, String.join(",",luchador.getKimarites()));
            pst.executeUpdate();
            
            rs=pst.getGeneratedKeys();
            if(rs.next()){
                luchador.setIdLuchador(rs.getInt("id_luchador"));
            }
        } catch (SQLException ex) {
        }
        
    }
    /**
     * Modifica la cantidad de victorias de un luchador en la base de datos
     * @param luchador 
     */
    @Override
    public void modificarVictorias(LuchadorDTO luchador){
        try {
            con=CnxBD.getConexion();
            pst=con.prepareStatement("UPDATE luchador SET victorias=? WHERE id=? ");
            pst.setInt(1,luchador.getVictorias());
            pst.setInt(2,luchador.getIdLuchador());
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    
    /**
     * Consulta los datos del luchador en la base de datos a partir del id dado.
     * Además convierte el String de kimarites al String[] que usa el DTO para los mismos y así poder llamar
     * al método que hace la instanciación
     * @param idLuchador
     * @return luchador consultado
     */
    public LuchadorDTO consultarLuchador(int idLuchador){
        try {
            con = CnxBD.getConexion();
            pst=con.prepareStatement("SELECT * FROM luchador WHERE id_luchador=?");
            pst.setInt(1,idLuchador);
            rs= pst.executeQuery();
            
            if(rs.next()){
                luchador= cl.crearLuchador(rs.getInt("id_luchador"),rs.getString("nombre"),rs.getDouble("peso"), rs.getInt("peso"), 
                        rs.getString("kimarites").split(","));
            }
            
        } catch (SQLException ex) {
        }
        return luchador;
    }
}
