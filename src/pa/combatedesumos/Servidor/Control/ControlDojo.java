package pa.combatedesumos.Servidor.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Control encargado de gestionar la logica del combate en el dojo. Sincroniza
 * los dos hilos de los luchadores y determina el ganador.
 *
 * @author Sergio, Asus
 */
public class ControlDojo {

    private SrvControlPrincipal srvControlPrincipal;

    /**
     * Lista de luchadores pendientes por combatir.
     */
    private List<LuchadorDTO> pendientes;
    private LuchadorDTO ganadorActual;

    /**
     * Mapa de hilos activos — clave: idLuchador, valor: hilo.
     */
    private Map<Integer, HiloLuchador> hilos;
    private Random random;

    /**
     * Constructor de ControlDojo.
     *
     * @param srvControlPrincipal control principal del servidor
     */
    public ControlDojo(SrvControlPrincipal srvControlPrincipal) {
        this.srvControlPrincipal = srvControlPrincipal;
        this.pendientes = new ArrayList<>();
        this.hilos = new HashMap<>();
        this.random =  new Random();
    }
    
    /**
     * Agrega un hilo al mapa de hilos activos.
     *
     * @param idLuchador ID del luchador
     * @param hilo       hilo del luchador
     */
    public void agregarHilo(int idLuchador, HiloLuchador hilo){
        hilos.put(idLuchador, hilo);
    }
    
    public void iniciarTorneo(){
        //List<LuchadorDTO> luchadores = srvControlPrincipal.getControlLuchador().getLuchadores();
    }
    
}
