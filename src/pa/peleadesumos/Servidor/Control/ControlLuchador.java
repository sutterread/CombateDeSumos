package pa.peleadesumos.Servidor.Control;

import pa.peleadesumos.Servidor.Modelo.Luchador;

/**
 *
 * @author Asus
 */
public class ControlLuchador {
    
    private SControlPrincipal sControlPrincipal;
    
    public ControlLuchador(SControlPrincipal sControlPrincipal) {
        this.sControlPrincipal=sControlPrincipal;
    }
    
    /**
     * Crea un luchador y lo manda al controlprincipal para que lo pueda delegar
     * @param nombre
     * @param peso
     * @param combatesGanados
     * @param kimarites
     */
    public void crearLuchador(String nombre, float peso, int combatesGanados, String[] kimarites) throws InterruptedException{
        Luchador luchador = new Luchador(nombre, peso, combatesGanados, kimarites);
        sControlPrincipal.subirAlDojo(luchador);
        sControlPrincipal.ejecutarTurno(luchador);
    }
    
}
