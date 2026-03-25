package pa.combatedesumos.Servidor.Control;

import java.util.ArrayList;
import java.util.List;
import pa.combatedesumos.Servidor.Modelo.DAO.LuchadorDAO;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Control encargado de gestionar los luchadores registrados.
 * Coordina la creación, inserción en BD y mantenimiento de la lista.
 *
 * @author Sergio Vanegas
 * @author Asus
 */
public class ControlLuchador {

    /** Referencia al control principal del servidor. */
    private SrvControlPrincipal sControlPrincipal;

    /** Lista de luchadores registrados en la sesión. */
    private List<LuchadorDTO> luchadores;

    /** DAO para operaciones de BD. */
    private LuchadorDAO ldao;

    /**
     * Constructor que recibe una inyección de dependencia y envía otra.
     *
     * @param sControlPrincipal referencia al control principal
     */
    public ControlLuchador(SrvControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
        this.luchadores = new ArrayList<>();
        this.ldao = new LuchadorDAO(this);
    }

    /**
     * Instancia un LuchadorDTO con los datos dados.
     * Usado por el DAO al consultar desde BD.
     *
     * @param idLuchador ID del luchador
     * @param nombre     nombre del luchador
     * @param peso       peso del luchador
     * @param victorias  victorias del luchador
     * @param kimarites  técnicas del luchador
     * @return LuchadorDTO instanciado
     */
    public LuchadorDTO crearLuchador(int idLuchador, String nombre, double peso, int victorias, String[] kimarites) {
        return new LuchadorDTO(idLuchador, nombre, peso, victorias, kimarites);
    }

    /**
     * Consulta un luchador en BD por su ID.
     *
     * @param idLuchador ID del luchador
     * @return LuchadorDTO con datos frescos de BD
     */
    public LuchadorDTO consultarLuchadorEnBD(int idLuchador) {
        try {
            return ldao.consultarLuchador(idLuchador);
        } catch (Exception e) {
            sControlPrincipal.mostrarError("Error al consultar luchador: " + e.getMessage());
            return null;
        }
    }

    /**
     * Inserta un nuevo luchador en BD y lo agrega a la lista de registrados.
     * Notifica al control principal si hay suficientes luchadores.
     *
     * @param nombre    nombre del luchador
     * @param peso      peso del luchador
     * @param kimarites técnicas del luchador
     */
    public void insertarLuchadorEnBD(String nombre, double peso, String[] kimarites) {
        try {
            LuchadorDTO luchador = new LuchadorDTO(nombre, peso, 0, kimarites);
            ldao.insertarLuchador(luchador);
            luchadores.add(luchador);
            if (luchadores.size() >= 6) {
                sControlPrincipal.habilitarCombate();
            }
        } catch (Exception e) {
            sControlPrincipal.mostrarError("Error al insertar luchador: " + e.getMessage());
        }
    }

    /**
     * Suma una victoria al luchador y actualiza en BD.
     *
     * @param luchador luchador ganador
     */
    public void sumarVictoria(LuchadorDTO luchador) {
        try {
            luchador.setVictorias(luchador.getVictorias() + 1);
            ldao.modificarVictorias(luchador);
        } catch (Exception e) {
            sControlPrincipal.mostrarError("Error al actualizar victorias: " + e.getMessage());
        }
    }

    /**
     * Retorna la lista de luchadores registrados en la sesión.
     *
     * @return lista de LuchadorDTO
     */
    public List<LuchadorDTO> getLuchadores() {
        return luchadores;
    }
}