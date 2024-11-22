package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import cliente.ClienteTCP;
import cliente.PrimitivaCliente;

/**
 * TODO: Complementa esta clase para que acepte conexiones TCP con clientes para
 * recibir un boleto, generar la respuesta y finalizar la sesion
 */
public class ServidorTCP {
	private BufferedReader entrada;
	private PrintWriter salida;
	private Socket socketCliente;
	private ServerSocket socketServidor;
	private String[] respuesta;
	private int[] combinacion;
	private int reintegro;
	private int complementario;
	private boolean juegoEnCurso;
	private boolean correcto;
	private boolean incorrecto;

	/**
	 * Constructor
	 */
	public ServidorTCP(int puerto) {
		juegoEnCurso = true; // Variable para terminar el juego
		try {
			socketServidor = new ServerSocket(puerto);
			System.out.println("Esperando conexión...");
			socketCliente = socketServidor.accept();
			System.out.println("Conexión aceptada: " + socketCliente);
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);
		} catch (IOException e) {
			System.out.println("No puede escuchar en el puerto: " + puerto);
			System.exit(-1);
		}

		this.respuesta = new String[9];
		this.respuesta[0] = "Boleto inválido - Números repetidos";
		this.respuesta[1] = "Boleto inválido - números incorretos (1-49)";
		this.respuesta[2] = "6 aciertos";
		this.respuesta[3] = "5 aciertos + complementario";
		this.respuesta[4] = "5 aciertos";
		this.respuesta[5] = "4 aciertos";
		this.respuesta[6] = "3 aciertos";
		this.respuesta[7] = "Reintegro";
		this.respuesta[8] = "Sin premio";
		generarCombinacion();
		imprimirCombinacion();
	}

	/**
	 * @return Debe leer la combinacion de numeros que le envia el cliente
	 */
	public String leerCombinacion() {
		try {
			while (juegoEnCurso) {
				ClienteTCP.comprobarBoleto(combinacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "El cliente tiene esta combinación: " + combinacion;

	}

	/**
	 */
	public String comprobarBoleto() {
		if (juegoEnCurso) {
			BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("");
			String respuest;
			try {
				respuest = consola.readLine();
				salida.println(respuest);

				String result = entrada.readLine();

				if (result.equals(combinacion)) {
					System.out.println(correcto);
				} else {
					System.out.println(incorrecto);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				finSesion();
			}
		}
		return null;
	}

	/**
	 * @param respuesta se debe enviar al ciente
	 */
	public void enviarRespuesta(String respuesta) {

		if (correcto) {
			System.out.println("Tu combinación es la correcta!!");
		}
		if (incorrecto) {
			System.out.println("Tu combinacion es incorrecta, suerte la próxima vez");
		}

	}

	/**
	 * Cierra el servidor
	 */
	public void finSesion() {
		try {
			salida.close();
			entrada.close();
			socketCliente.close();
			socketServidor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-> Servidor Terminado");
	}

	/**
	 * Metodo que genera una combinacion. NO MODIFICAR
	 */
	private void generarCombinacion() {
		Set<Integer> numeros = new TreeSet<Integer>();
		Random aleatorio = new Random();
		while (numeros.size() < 6) {
			numeros.add(aleatorio.nextInt(49) + 1);
		}
		int i = 0;
		this.combinacion = new int[6];
		for (Integer elto : numeros) {
			this.combinacion[i++] = elto;
		}
		this.reintegro = aleatorio.nextInt(49) + 1;
		this.complementario = aleatorio.nextInt(49) + 1;
	}

	/**
	 * Metodo que saca por consola del servidor la combinacion
	 */
	private void imprimirCombinacion() {
		System.out.print("Combinación ganadora: ");
		for (Integer elto : this.combinacion)
			System.out.print(elto + " ");
		System.out.println("");
		System.out.println("Complementario:       " + this.complementario);
		System.out.println("Reintegro:            " + this.reintegro);
	}

}
