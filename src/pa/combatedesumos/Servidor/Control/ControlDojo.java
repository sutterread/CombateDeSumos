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
        this.random = new Random();
    }

    /**
     * Agrega un hilo al mapa de hilos activos.
     *
     * @param idLuchador ID del luchador
     * @param hilo hilo del luchador
     */
    public void agregarHilo(int idLuchador, HiloLuchador hilo) {
        hilos.put(idLuchador, hilo);
    }

    /**
     * Primero valida si hay 6 luchadores para pelear Selecciona los dos
     * luchadores y ejecuta el combate entre ellos
     *
     * @param luchadores
     */
    public void iniciarTorneo(List<LuchadorDTO> luchadores) {
        if (luchadores.size() < 6) {
            srvControlPrincipal.mostrarError("Se necesitam minimo 6 luchadores para iniciar");
            return;
        }
        pendientes = new ArrayList<>(luchadores);

        LuchadorDTO a = seleccionarLuchador();
        LuchadorDTO b = seleccionarLuchador();
        ejecutarCombate(a, b);
    }

    /**
     * Selecciona un luchador dentro de la lista
     *
     * @return borra el luchador seleccionado
     */
    private LuchadorDTO seleccionarLuchador() {
        int indice = random.nextInt(pendientes.size());
        return pendientes.remove(indice);
    }

    /**
     * Ejecuta el combate entre los luchadores seleccionados ejecutando kimaries
     * y variando sus turnos
     *
     * @param Luchador a
     * @param Luchador b
     */
    public synchronized void ejecutarCombate(LuchadorDTO a, LuchadorDTO b) {
        srvControlPrincipal.actualizarCombate("Combate: " + a.getNombre() + " vs " + b.getNombre());

        boolean dentroA = true;
        boolean dentroB = true;
        boolean turnoA = true;

        while (dentroA && dentroB) {
            if (turnoA) {
                dentroB = ejecutarKimarite(a);
            } else {
                dentroA = ejecutarKimarite(b);
            }
            turnoA = !turnoA;

            try {
                wait(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        LuchadorDTO ganador = dentroA ? a : b;
        LuchadorDTO perdedor = dentroA ? b : a;
        finalizarCombate(ganador, perdedor);
    }

    /**
     * Ejecuta un kimarite aleatorio del arreglo de kimarites del luchador
     *
     * @param luchador
     * @return Probabilidad
     */
    private boolean ejecutarKimarite(LuchadorDTO luchador) {
        String[] kimarites = luchador.getKimarites();
        String kimarite = kimarites[random.nextInt(kimarites.length)];
        srvControlPrincipal.actualizarCombate(luchador.getNombre() + " usa: " + kimarite);

        //80% DE PROBABILIDAD DE NO SACAR, 20% DE SACAR
        return random.nextInt(100) >= 20;
    }

    /**
     * Delega a cp para sumar una victoria aal ganador obtiene la pk de cada
     * luchador el ganador y el perdedor delega a cp para terminar el combate
     *
     * Realiza el siguiente combate Delega a cp para mostrarelRAF final
     *
     * @param ganador
     * @param perdedor
     */
    private void finalizarCombate(LuchadorDTO ganador, LuchadorDTO perdedor) {
        // actualizar victorias
        srvControlPrincipal.sumarVictoria(ganador);

        // consultar datos frescos de BD
        LuchadorDTO ganadorBD = srvControlPrincipal.consultarLuchador(ganador.getIdLuchador());
        LuchadorDTO perdedorBD = srvControlPrincipal.consultarLuchador(perdedor.getIdLuchador());

        // escribir RAF y notificar — todo delegado a SControlPrincipal
        srvControlPrincipal.finalizarCombate(ganadorBD, perdedorBD);

        // siguiente combate
        ganadorActual = ganador;
        if (!pendientes.isEmpty()) {
            LuchadorDTO siguiente = seleccionarLuchador();
            ejecutarCombate(ganadorActual, siguiente);
        } else {
            srvControlPrincipal.mostrarArchivoFinal();
        }

    }

    /**
     * Retorna el mapa de hilos activos.
     *
     * @return mapa de hilos
     */
    public Map<Integer, HiloLuchador> getHilos() {
        return hilos;
    }
}
