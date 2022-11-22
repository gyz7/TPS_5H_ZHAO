package network;

import java.io.DataInputStream;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.net.Socket;

public class TcpClient {
	
	static Scanner scanner;

	public static void main(String[] args) throws Exception {
		try {

			String severAddress="127.0.0.1";  // localhost
			int severPort=8698;
			String clientMessage = "";
			String serverMessage = "";
			
			menu();
			
			// Create connection to server socket
			System.out.print("Client: Tentativo di connessione server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); //
			System.out.println("Connected");

			// Create streams to read/write data from socket
			DataInputStream inStream = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
			// Create streams to read data from System.in
			Scanner scanner = new Scanner(System.in);			
			
			while (!clientMessage.equals("end")) {
				
				clientMessage = prompt();

				// Send the entered number to server
				System.out.println("Client: inserisci il comando da inviare: " + clientMessage);
				outStream.writeUTF(clientMessage);
				outStream.flush();

				// Read data from socket input stream
				serverMessage = inStream.readUTF();
				System.out.println("Client: ricevuto il comando: " + serverMessage);
			}

			// Close resources
			inStream.close();
			outStream.close();
			socket.close();
			scanner.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void menu() {
		System.out.println("Help: comandi disponibili");
		System.out.println("  help");
		System.out.println("  info");
		System.out.println("  reserve <numero> <nome>");
		System.out.println("  end");

	}
	
	public static String prompt() {
		scanner = new Scanner(System.in);
		System.out.print("Inserisci il comando: ");
		String comando = scanner.nextLine();
		return comando;
	}
}
