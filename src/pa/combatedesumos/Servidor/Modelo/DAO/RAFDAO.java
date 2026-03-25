package pa.combatedesumos.Servidor.Modelo.DAO;

import java.io.IOException;
import java.io.RandomAccessFile;
import pa.combatedesumos.Servidor.Modelo.Conexiones.CnxRAF;
import pa.combatedesumos.Servidor.Modelo.LuchadorDTO;

/**
 * Clase encargada de gestionar la escritura y lectura de resultados
 * de combates en el archivo de acceso aleatorio.
 *
 * @author
 */
public class RAFDAO {

    /** Tamaño fijo del campo nombre en caracteres. */
    private static final int TAM_NOMBRE = 100;

    /** Tamaño fijo del campo resultado en caracteres. */
    private static final int TAM_RESULTADO = 10;

    /** Tamaño total de cada registro en bytes.
     *  id(4) + nombre(100*2) + peso(8) + victorias(4) + resultado(10*2) = 236
     */
    private static final int TAM_REGISTRO = 4 + (TAM_NOMBRE * 2) + 8 + 4 + (TAM_RESULTADO * 2);

    /**
     * Constructor privado — no se instancia.
     */
    private RAFDAO() {}

    /**
     * Escribe el resultado de un combate en el archivo de acceso aleatorio.
     * Los datos deben venir expresamente de la base de datos.
     *
     * @param luchador luchador con datos frescos de BD
     * @param resultado "GANO" o "PERDIO"
     * @throws IOException si ocurre un error al escribir
     */
    public static void escribirResultado(LuchadorDTO luchador, String resultado) throws IOException {
        RandomAccessFile raf = CnxRAF.conexion();

        // ajustar nombre al tamaño fijo
        String nombre = luchador.getNombre();
        while (nombre.length() < TAM_NOMBRE) nombre += " ";
        if (nombre.length() > TAM_NOMBRE) nombre = nombre.substring(0, TAM_NOMBRE);

        // ajustar resultado al tamaño fijo
        while (resultado.length() < TAM_RESULTADO) resultado += " ";
        if (resultado.length() > TAM_RESULTADO) resultado = resultado.substring(0, TAM_RESULTADO);

        raf.seek(raf.length());
        raf.writeInt(luchador.getIdLuchador());
        raf.writeChars(nombre);
        raf.writeDouble(luchador.getPeso());
        raf.writeInt(luchador.getVictorias());
        raf.writeChars(resultado);
    }

    /**
     * Lee todos los registros del archivo y los imprime por consola.
     *
     * @throws IOException si ocurre un error al leer
     */
    public static void leerTodos() throws IOException {
        RandomAccessFile raf = CnxRAF.conexion();
        raf.seek(0);
        int numRegistros = (int) (raf.length() / TAM_REGISTRO);

        for (int i = 0; i < numRegistros; i++) {
            int id = raf.readInt();

            StringBuilder nombre = new StringBuilder();
            for (int j = 0; j < TAM_NOMBRE; j++) {
                nombre.append(raf.readChar());
            }

            double peso = raf.readDouble();
            int victorias = raf.readInt();

            StringBuilder resultado = new StringBuilder();
            for (int j = 0; j < TAM_RESULTADO; j++) {
                resultado.append(raf.readChar());
            }

            System.out.println(
                "ID: " + id +
                " | Nombre: " + nombre.toString().trim() +
                " | Peso: " + peso +
                " | Victorias: " + victorias +
                " | Resultado: " + resultado.toString().trim()
            );
        }
    }
}
