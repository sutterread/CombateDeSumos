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
     * Primero valida si hay 6 luchadores para pelear. Selecciona los dos
     * luchadores y ejecuta el combate entre ellos.
     * FLUJO CORRECTO:
     * - Selecciona 2 de 6 luchadores al azar
     * - Ganador pelea con otro al azar de los 4 restantes
     * - Ganador de ese pelea con otro al azar de los 3 restantes
     * - Y así sucesivamente hasta que no haya pendientes
     *
     * @param luchadores lista de luchadores registrados
     */
    public void iniciarTorneo(List<LuchadorDTO> luchadores) {
        if (luchadores.size() < 6) {
            srvControlPrincipal.mostrarError("Se necesitan mínimo 6 luchadores para iniciar");
            return;
        }
        // Copiar lista para no modificar la original
        pendientes = new ArrayList<>(luchadores);

        // Primer combate: dos luchadores seleccionados al azar
        LuchadorDTO a = seleccionarLuchador();
        LuchadorDTO b = seleccionarLuchador();
        ejecutarCombate(a, b);
    }

    /**
     * Selecciona un luchador dentro de la lista de pendientes y lo quita
     *
     * @return luchador seleccionado al azar (removido de la lista)
     */
    private LuchadorDTO seleccionarLuchador() {
        int indice = random.nextInt(pendientes.size());
        return pendientes.remove(indice);
    }

    /**
     * Ejecuta el combate entre los luchadores seleccionados ejecutando kimarites
     * y variando sus turnos
     *
     * @param a Luchador A
     * @param b Luchador B
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
     * @param luchador luchador que ataca
     * @return true si el luchador se mantiene dentro, false si lo sacan del dohyō
     */
    private boolean ejecutarKimarite(LuchadorDTO luchador) {
        String[] kimarites = luchador.getKimarites();
        String kimarite = kimarites[random.nextInt(kimarites.length)];
        srvControlPrincipal.actualizarCombate(luchador.getNombre() + " usa: " + kimarite);

        // 80% DE PROBABILIDAD DE NO SACAR, 20% DE SACAR
        return random.nextInt(100) >= 20;
    }

    /**
     * Finaliza el combate actual. Si hay luchadores pendientes, el ganador
     * pelea contra el siguiente. Si no hay pendientes, termina el torneo.
     *
     * @param ganador ganador del combate
     * @param perdedor perdedor del combate
     */
    private void finalizarCombate(LuchadorDTO ganador, LuchadorDTO perdedor) {
        // Actualizar victorias en BD
        srvControlPrincipal.sumarVictoria(ganador);

        // Consultar datos frescos de BD
        LuchadorDTO ganadorBD = srvControlPrincipal.consultarLuchador(ganador.getIdLuchador());
        LuchadorDTO perdedorBD = srvControlPrincipal.consultarLuchador(perdedor.getIdLuchador());

        // Escribir RAF y notificar a ambos luchadores
        srvControlPrincipal.finalizarCombate(ganadorBD, perdedorBD);

        // Siguiente combate
        if (!pendientes.isEmpty()) {
            // Ganador pelea contra el siguiente de la lista
            LuchadorDTO siguiente = seleccionarLuchador();
            ejecutarCombate(ganador, siguiente);
        } else {
            // No hay más luchadores, mostrar resultados finales
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