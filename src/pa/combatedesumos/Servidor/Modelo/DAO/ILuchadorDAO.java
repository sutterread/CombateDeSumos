package pa.combatedesumos.Servidor.Modelo.DAO;

import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;
/**
 * Contrato para los métodos de inserción, consulta y modificación
 * @author Sergio Vanegas
 */
public interface ILuchadorDAO {
    /**
     * Consulta en la base de datos el luchador con el id dado
     * @param idLuchador
     * @return luchador consultado
     */
    LuchadorDTO consultarLuchador(int idLuchador);
        /**
     * Insertar un luchador a la base de datos
     * @param luchador /**
     * Insertar
     */
    void insertarLuchador(LuchadorDTO luchador);
    /**
     * Modifica la cantidad de victorias de un luchador
     * @param luchador 
     */
    void modificarVictorias(LuchadorDTO luchador);
    
}
