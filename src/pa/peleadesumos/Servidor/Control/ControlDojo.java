package pa.peleadesumos.Servidor.Control;

import java.util.concurrent.ThreadLocalRandom;
import pa.peleadesumos.Servidor.Modelo.Luchador;

/**
 * Control encargado de gestionar la logica del combate en el dojo. Sincroniza
 * los dos hilos de los luchadores y determina el ganador.
 *
 * @author Sergio, Asus
 */
public class ControlDojo {

    private SControlPrincipal sControlPrincipal;

    private Luchador[] luchadores;
    //contador de luchadores que han llegado al dojo
    private int contadorLuchadores;
    //indice del arreglo para definir de cual de los dos luchadores es el turno
    private int turnoActual;
    private boolean combateActivo;

    private Luchador ganador;

    /**
     * Constructor de ControlDojo.
     *
     * @param sControlPrincipal control principal del servidor
     */
    public ControlDojo(SControlPrincipal sControlPrincipal) {
        this.sControlPrincipal = sControlPrincipal;
        this.luchadores = new Luchador[2];
        this.contadorLuchadores = 0;
        this.turnoActual = 0;
        this.combateActivo = false;
        this.ganador = null;
    }

    /**
     * Sube un luchador al dojo. si ya estan los dos inicia el combate. Si solo
     * hay uno, espera al segundo luchador
     *
     * @param luchador lcuhador a subir al dojito
     * @throws InterruptedException
     */
    public synchronized void subirLuchador(Luchador luchador) throws InterruptedException {
        luchadores[contadorLuchadores] = luchador;
        contadorLuchadores++;
        sControlPrincipal.notificarLlegada(luchador);
        if (contadorLuchadores < 2) {
            wait();
        } else {
            combateActivo = true;
            notifyAll();
        }
    }

    /**
     * Ejecuta el turno del luchador. Espera si no es su turno. Aplica un
     * kimarite aleatorio y verifica si saca al rival.
     *
     * @param luchador luchador que ejecuta el turno
     * @throws InterruptedException si el hilo es interrumpido
     */
    public synchronized void ejecutarTurno(Luchador luchador) throws InterruptedException {
        int miIndice = luchadores[0] == luchador ? 0 : 1; //primera vez que el operador ternario me sirve de algo
        int rivalIndice = miIndice == 0 ? 1 : 0;

        //no olvidar que entra si es true porque es un while lol
        while (combateActivo) {

            if (turnoActual == miIndice) {
                wait(ThreadLocalRandom.current().nextInt(500));

                //EJECUTAR KIMARITEEE
                String kimarite = obtenerKimariteRandom(luchador.getKimarites());
                sControlPrincipal.notificarKimarite(luchador, kimarite);

                //verificacion a ver si saco al rival
                if (obtenerResultadoRandom()) {
                    ganador = luchador;
                    combateActivo = false;
                    sControlPrincipal.notificarGanador(ganador);
                    notifyAll();
                } else {
                    turnoActual = rivalIndice;
                    notifyAll();
                    if (combateActivo) {
                        wait();
                    }
                }
            } else {
                wait();
            }

        }
    }

    /**
     * Retorna un kimarite aleatorio del arreglo del luchador.
     *
     * @param kimaritesLuchador arreglo de kimarites del luchador
     * @return kimarite seleccionado aleatoriamente
     */
    public static String obtenerKimariteRandom(String[] kimaritesLuchador) {
        int i = ThreadLocalRandom.current().nextInt(kimaritesLuchador.length);
        return kimaritesLuchador[i];
    }

    /**
     * Determina aleatoriamente si el kimarite saca al rival del dojo. Hay un
     * 20% de probabilidad de sacar al rival.
     *
     * @return true si saca al rival, false si no
     */
    public static boolean obtenerResultadoRandom() {
        return ThreadLocalRandom.current().nextInt(10) > 7;
    }

    /**
     * Retorna el ganador del combate.
     *
     * @return luchador ganador
     */
    public Luchador determinarGanador() {
        return ganador;
    }

}
