package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	public static void main(String[] args) throws Exception {
		
		int severPort=8765;
		String clientMsg = "";
		
		try {			 
				int lettere=0;
				int vocali=0;
				int consonanti=0;
			
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sulla porta " + severPort);

			// Attesa della connessione con il client
			Socket clientSocket = serverSocket.accept();
			
			// Create input and output streams to read/write data
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	

			// Scambio di dati tra client e server
			while(!clientMsg.equals("quit")){
				//Lettura dato da stream di rete
				clientMsg = inStream.readUTF();
				
				//Invio dati su stream di rete
				//outStream.writeUTF("Echo from server : "         + clientMsg);
				//outStream.flush();
				//System.out.println("Server: invio messaggio "    + clientMsg );
				
				//Elaborazione stringa ricevuta
				clientMsg=clientMsg.toLowerCase();
				lettere=0;
				for(int i=0; i<clientMsg.length(); i++) {
					if(Character.isLetter(clientMsg.charAt(i))) {
						lettere++;
					}
				}				
				for(int i=0; i<clientMsg.length(); ++i) {
					char ca=clientMsg.charAt(i);
					if(ca=='a' || ca=='e' || ca=='i' || ca=='o' || ca=='u') {
						vocali++;
					} else 
					{
						
							consonanti++;
						
					}
				}
				
				//Invio del msg al client
				outStream.writeUTF("Server: invio messaggio "    + clientMsg + "\nLettere in totale:" + lettere + "\nNumero vocali:" + vocali + "\nNumero consonanti:" + consonanti);
				outStream.flush();
				
				//Controllo chiusura
				if(consonanti==(vocali/2)) {
					clientMsg="quit";
				}
				//outStream.writeUTF(clientMsg);
				outStream.flush();


			}

			// Close resources
			serverSocket.close();
			//clientSocket.close();
			//inStream.close();
			//outStream.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
