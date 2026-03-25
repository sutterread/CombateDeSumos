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
 *
 * @author Sergio Vanegas
 */
public class LuchadorDAO implements ILuchadorDAO {

    private ControlLuchador cl;

    /**
     * Constructor que recibe la inyección de dependencia
     *
     * @param cl controlador de luchador utilizado para crear instancias de
     * LuchadorDTO
     */
    public LuchadorDAO(ControlLuchador cl) {
        this.cl = cl;
    }

    /**
     * Inserta un luchador en la tabla de la base de datos, convirtiendo un
     * String[] del DTO en String
     *
     * @param luchador
     */
    @Override
    public void insertarLuchador(LuchadorDTO luchador) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = CnxBD.getConexion();
            pst = con.prepareStatement("INSERT INTO luchador (nombre,peso,victorias,kimarites) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, luchador.getNombre());
            pst.setDouble(2, luchador.getPeso());
            pst.setInt(3, luchador.getVictorias());
            pst.setString(4, String.join(",", luchador.getKimarites()));
            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                luchador.setIdLuchador(rs.getInt(1));
            }
        } catch (SQLException e) {

        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pst != null) 
                try {
                pst.close();
            } catch (SQLException e) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
            }

        }

    }

    /**
     * Modifica la cantidad de victorias de un luchador en la base de datos
     *
     * @param luchador
     */
    @Override
    public void modificarVictorias(LuchadorDTO luchador) {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = CnxBD.getConexion();
            pst = con.prepareStatement("UPDATE luchador SET victorias = ? WHERE id_luchador = ? ");
            pst.setInt(1, luchador.getVictorias());
            pst.setInt(2, luchador.getIdLuchador());
            pst.executeUpdate();
        } catch (SQLException ex) {
        } finally {
            if (pst != null) try {
                pst.close();
            } catch (SQLException e) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
            }
        }
    }

    /**
     * Consulta los datos del luchador en la base de datos a partir del id dado.
     * Además convierte el String de kimarites al String[] que usa el DTO para
     * los mismos y así poder llamar al método que hace la instanciación
     *
     * @param idLuchador
     * @return luchador consultado
     */
    @Override
    public LuchadorDTO consultarLuchador(int idLuchador) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        LuchadorDTO luchador = null;
        try {
            con = CnxBD.getConexion();
            pst = con.prepareStatement("SELECT * FROM luchador WHERE id_luchador = ?");
            pst.setInt(1, idLuchador);
            rs = pst.executeQuery();

            if (rs.next()) {
                luchador = cl.crearLuchador(rs.getInt("id_luchador"), rs.getString("nombre"), rs.getDouble("peso"), rs.getInt("victorias"),
                        rs.getString("kimarites").split(","));
            }
        } catch (SQLException ex) {
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pst != null) try {
                pst.close();
            } catch (SQLException e) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException e) {
            }
        }
        return luchador;
    }
}
