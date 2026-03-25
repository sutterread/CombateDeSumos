package pa.combatedesumos.Servidor.Control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;
import pa.combatedesumos.Servidor.Vista.PanelDojo;

/**
 * Control encargado de gestionar la lógica del combate en el dohyō.
 * Sincroniza los hilos de los luchadores y determina el ganador basado
 * en la ejecución de kimarites con reproducción de GIFs.
 *
 * @author Sergio, Asus
 */
public class ControlDojo {

    private SrvControlPrincipal srvControlPrincipal;
    private PanelDojo panelDojo;

    /**
     * Lista de luchadores pendientes por combatir.
     */
    private List<LuchadorDTO> pendientes;

    /**
     * Mapa de hilos activos — clave: idLuchador, valor: hilo.
     */
    private Map<Integer, HiloLuchador> hilos;
    private Random random;
    
    /**
     * Lock para sincronizar el turno en el dohyō
     */
    private final Object lockDohyo = new Object();

    /**
     * Constructor de ControlDojo.
     *
     * @param srvControlPrincipal control principal del servidor
     * @param panelDojo panel donde se visualiza el combate
     */
    public ControlDojo(SrvControlPrincipal srvControlPrincipal, PanelDojo panelDojo) {
        this.srvControlPrincipal = srvControlPrincipal;
        this.panelDojo = panelDojo;
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
     * Inicia el torneo validando que haya mínimo 6 luchadores.
     * FLUJO CORRECTO:
     * - Selecciona 2 de 6 al azar
     * - Ganador pelea con otro al azar
     * - Continúa hasta no haber pendientes
     *
     * @param luchadores lista de luchadores registrados
     */
    public void iniciarTorneo(List<LuchadorDTO> luchadores) {
        if (luchadores.size() < 6) {
            srvControlPrincipal.mostrarError("Se necesitan mínimo 6 luchadores para iniciar");
            return;
        }
        
        pendientes = new ArrayList<>(luchadores);

        // Primer combate: dos luchadores seleccionados al azar
        LuchadorDTO a = seleccionarLuchador();
        LuchadorDTO b = seleccionarLuchador();
        
        // Cambiar al panel de combate
        srvControlPrincipal.mostrarPanelCombate();
        
        ejecutarCombate(a, b);
    }

    /**
     * Selecciona un luchador al azar de los pendientes y lo remove.
     *
     * @return luchador seleccionado
     */
    private LuchadorDTO seleccionarLuchador() {
        int indice = random.nextInt(pendientes.size());
        return pendientes.remove(indice);
    }

    /**
     * Ejecuta el combate entre dos luchadores con sincronización real.
     * Cada turno: reproduce GIF → espera que termine → verifica resultado
     *
     * @param a Luchador A
     * @param b Luchador B
     */
    public void ejecutarCombate(LuchadorDTO a, LuchadorDTO b) {
        // Actualizar vista con combatientes
        panelDojo.actualizarCombatientes(a.getNombre(), b.getNombre());
        srvControlPrincipal.actualizarCombate("Combate: " + a.getNombre() + " vs " + b.getNombre());

        boolean dentroA = true;
        boolean dentroB = true;
        boolean turnoA = true;

        while (dentroA && dentroB) {
            if (turnoA) {
                // Turno del luchador A
                dentroB = ejecutarTurnoConGif(a, b);
            } else {
                // Turno del luchador B
                dentroA = ejecutarTurnoConGif(b, a);
            }
            turnoA = !turnoA;
        }

        LuchadorDTO ganador = dentroA ? a : b;
        LuchadorDTO perdedor = dentroA ? b : a;
        finalizarCombate(ganador, perdedor);
    }

    /**
     * Ejecuta un turno completo: selecciona kimarite, reproduce GIF,
     * espera que termine, y verifica si saca al oponente.
     *
     * @param atacante luchador que ataca
     * @param defensor luchador que defiende
     * @return true si el defensor se mantiene dentro, false si sale
     */
    private boolean ejecutarTurnoConGif(LuchadorDTO atacante, LuchadorDTO defensor) {
        synchronized (lockDohyo) {
            // Seleccionar kimarite aleatorio
            String[] kimarites = atacante.getKimarites();
            String kimarite = kimarites[random.nextInt(kimarites.length)];
            
            srvControlPrincipal.actualizarCombate(
                atacante.getNombre() + " usa: " + kimarite
            );

            // Crear CountDownLatch para sincronizar con el GIF
            CountDownLatch gifTerminado = new CountDownLatch(1);
            
            // Reproducir GIF y esperar que termine
            String rutaGif = "/Imgs/Gifs/Tecnicas/" + kimarite.replace(" ", "_") + ".gif";
            panelDojo.reproducirGifYEsperar(rutaGif, () -> {
                gifTerminado.countDown();
            });

            // Esperar a que el GIF termine
            try {
                gifTerminado.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Después de que el GIF terminó, ejecutar kimarite
            // 80% NO saca, 20% SÍ saca
            boolean sacaDelDohyo = random.nextInt(100) < 20;
            
            if (sacaDelDohyo) {
                srvControlPrincipal.actualizarCombate(
                    defensor.getNombre() + " es sacado del dohyō!"
                );
                return false;  // El defensor fue sacado
            } else {
                srvControlPrincipal.actualizarCombate(
                    defensor.getNombre() + " resiste en el dohyō"
                );
                return true;   // El defensor se mantiene dentro
            }
        }
    }

    /**
     * Finaliza el combate actual y prepara el siguiente si hay pendientes.
     *
     * @param ganador luchador ganador
     * @param perdedor luchador perdedor
     */
    private void finalizarCombate(LuchadorDTO ganador, LuchadorDTO perdedor) {
        // Actualizar victorias en BD
        srvControlPrincipal.sumarVictoria(ganador);

        // Consultar datos frescos de BD
        LuchadorDTO ganadorBD = srvControlPrincipal.consultarLuchador(ganador.getIdLuchador());
        LuchadorDTO perdedorBD = srvControlPrincipal.consultarLuchador(perdedor.getIdLuchador());

        // Escribir RAF y notificar a luchadores
        srvControlPrincipal.finalizarCombate(ganadorBD, perdedorBD);

        // Siguiente combate
        if (!pendientes.isEmpty()) {
            LuchadorDTO siguiente = seleccionarLuchador();
            
            // Pequeña pausa antes del próximo combate
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            ejecutarCombate(ganador, siguiente);
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