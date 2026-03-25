package pa.combatedesumos.Servidor.Modelo.DAO;

import java.io.IOException;
import java.io.RandomAccessFile;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxRAF;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Clase encargada de gestionar la escritura y lectura de resultados
 * de combates en el archivo de acceso aleatorio.
 *
 * <p>Formato de cada registro (200 bytes):
 * <ul>
 *   <li>Nombre ganador  : 25 chars × 2 bytes = 50 bytes</li>
 *   <li>Peso ganador    : float = 4 bytes</li>
 *   <li>Nombre perdedor : 25 chars × 2 bytes = 50 bytes</li>
 *   <li>Peso perdedor   : float = 4 bytes</li>
 *   <li>Resultado       : 1 char × 2 bytes = 2 bytes  (G = ganó, P = perdió)</li>
 *   <li>Padding         : 90 bytes</li>
 *   <li>Total           : 200 bytes</li>
 * </ul>
 *
 * @author Sergio Vanegas
 */
public class RAFDAO {

    /** Cantidad maxima de caracteres por nombre en el archivo. */
    private static final int CHARS_NOMBRE = 25;

    /** Tamaño total de cada registro en bytes. */
    private static final int TAM_REGISTRO = 200;

    /** Caracter que identifica al ganador en el archivo RAF. */
    private static final char RESULTADO_GANADOR = 'G';

    /**
     * Constructor privado — no se instancia.
     */
    private RAFDAO() {}

    /**
     * Escribe el resultado de un combate en el archivo de acceso aleatorio.
     * Cada combate ocupa exactamente {@value #TAM_REGISTRO} bytes.
     * Los datos deben venir expresamente de la base de datos.
     *
     * @param ganador  luchador ganador con datos frescos de BD
     * @param perdedor luchador perdedor con datos frescos de BD
     * @throws IOException si ocurre un error al escribir
     */
    public static synchronized void escribirCombate(LuchadorDTO ganador, LuchadorDTO perdedor) throws IOException {
        RandomAccessFile raf = CnxRAF.conexion();
        raf.seek(raf.length());

        // Nombre ganador — 25 chars (50 bytes)
        escribirNombre(raf, ganador.getNombre());
        // Peso ganador — float (4 bytes)
        raf.writeFloat((float) ganador.getPeso());

        // Nombre perdedor — 25 chars (50 bytes)
        escribirNombre(raf, perdedor.getNombre());
        // Peso perdedor — float (4 bytes)
        raf.writeFloat((float) perdedor.getPeso());

        // Resultado — 1 char (2 bytes): RESULTADO_GANADOR = 'G'
        raf.writeChar(RESULTADO_GANADOR);

        // Padding — 90 bytes para completar los 200 bytes del registro
        byte[] padding = new byte[90];
        raf.write(padding);
    }

    /**
     * Lee todos los registros del archivo y los imprime por consola.
     *
     * @throws IOException si ocurre un error al leer
     */
    public static void leerTodos() throws IOException {
        RandomAccessFile raf = CnxRAF.conexion();
        raf.seek(0);
        long numRegistros = raf.length() / TAM_REGISTRO;

        for (long i = 0; i < numRegistros; i++) {
            String nombreGanador  = leerNombre(raf);
            float  pesoGanador    = raf.readFloat();
            String nombrePerdedor = leerNombre(raf);
            float  pesoPerdedor   = raf.readFloat();
            char   resultado      = raf.readChar();
            // Saltar padding
            raf.skipBytes(90);

            System.out.println(
                "Combate " + (i + 1) +
                " | Ganador: "  + nombreGanador  + " (" + pesoGanador  + " kg)" +
                " | Perdedor: " + nombrePerdedor + " (" + pesoPerdedor + " kg)" +
                " | Resultado: " + resultado
            );
        }
    }

    // -------------------------------------------------------------------------
    // Metodos auxiliares privados
    // -------------------------------------------------------------------------

    /**
     * Escribe un nombre como {@value #CHARS_NOMBRE} caracteres (50 bytes).
     * Si el nombre es mas corto, se rellena con espacios.
     * Si es mas largo, se recorta.
     *
     * @param raf    archivo de acceso aleatorio
     * @param nombre nombre a escribir
     * @throws IOException si ocurre un error de E/S
     */
    private static void escribirNombre(RandomAccessFile raf, String nombre) throws IOException {
        StringBuilder sb = new StringBuilder(nombre == null ? "" : nombre);
        while (sb.length() < CHARS_NOMBRE) {
            sb.append(' ');
        }
        String ajustado = sb.substring(0, CHARS_NOMBRE);
        raf.writeChars(ajustado);
    }

    /**
     * Lee {@value #CHARS_NOMBRE} caracteres del archivo y retorna el nombre
     * sin espacios de relleno al final.
     *
     * @param raf archivo de acceso aleatorio
     * @return nombre leido y recortado
     * @throws IOException si ocurre un error de E/S
     */
    private static String leerNombre(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CHARS_NOMBRE; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
}
