package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * TODO: Complementa esta clase para que genere la conexi�n TCP con el servidor
 * para enviar un boleto, recibir la respuesta y finalizar la sesion
 */
public class ClienteTCP {
	 private Socket socketCliente;
	    private BufferedReader entrada;
	    private PrintWriter salida;
	    private PrimitivaCliente primitiva;	
	    private boolean juegoEnCurso;
	/**
	 * Constructor
	 */
	public ClienteTCP(String ip, int puerto) {
		 primitiva = new PrimitivaCliente();
	        juegoEnCurso = true; // Inicialmente el juego está activo.
	        try {
	            // Establecer la conexión con el servidor
	            socketCliente = new Socket(ip, puerto);
	            System.out.println("Conexión establecida con el servidor en " + ip + ":" + puerto);

	            // Inicializar los flujos de entrada y salida
	            entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
	            salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
	        } catch (IOException e) {
	            System.err.printf("No se pudo conectar al servidor en ip: %s / puerto: %d%n", ip, puerto);
	            System.exit(-1);
	        }
	    }

	/**
	 * @param combinacion que se desea enviar
	 * @return respuesta del servidor con la respuesta del boleto
	 */
	public static String comprobarBoleto(int[] combinacion) {
		String respuesta = "Sin hacer";
		return respuesta;
	}

	/**
	 * Sirve para finalizar la la conexión de Cliente y Servidor
	 */
	public void finSesion () {
		try {
            // Cerrar todos los recursos
            salida.close();
            entrada.close();
            socketCliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("-> Cliente terminado. Conexión cerrada.");
    }

	
}
