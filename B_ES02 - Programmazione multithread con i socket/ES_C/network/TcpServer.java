package network;

import java.net.InetSocketAddress;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class TcpServer extends Thread {
	Socket serverClientSocket;
	int clientNo;
	PrenotazionePosti prenotaz;
	
	TcpServer(Socket inSocket, int ClientNo, PrenotazionePosti pre) {
		serverClientSocket = inSocket;
		clientNo = ClientNo;
		//super(nomeThr);
		prenotaz=pre;
	}
	
	public String array( String v[] , int x , int y ) {
		  String s="";
		  for (int i=x ; i<=y ; i++) {
			  s=s+ " " + v[i];
		  }
		  return s;
		}

	public boolean isNumeric( String number) {
		  boolean result=true;
		  try {
	        int num = Integer.parseInt(number);
	      } 
		  catch (NumberFormatException e) {
	        result = false;
	      }
		return result;
	}


	public void run() {
		try {
			// Streams to read and write the data to socket streams
			DataInputStream inStream = new DataInputStream(serverClientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(serverClientSocket.getOutputStream());

			String cMsg = "";
			String sMsg = "";

			while (!cMsg.equals("end")) {

				//Leggiamo il messaggio proveniente dal client, e lo stampiamo a schermo
				cMsg = inStream.readUTF();
				cMsg.trim();
				String clientCommando[]=cMsg.split("\\s+");
				System.out.println("Server.Thread " + clientNo + " Ricevuto il comando " + cMsg );
				switch (clientCommando[0]) {
                
				  case "informazione" :  sMsg=prenotaz.getPrenotazione();
                               break;
                          
				  case "reserve": System.out.println("Server.Thread: " + clientNo +" (reserve) numero="+  clientCommando[1] + " nome=" +  clientCommando[2] );
					             if (isNumeric(clientCommando[1]) == true) {
					            	 int num=Integer.parseInt(clientCommando[1]);
					            	 //String name=clientCommandArr[2];
					            	 String name=array(clientCommando , 2 , clientCommando.length-1 );
					            	 sMsg=prenotaz.setPrenotazione(num, name);
					             } else {
					            	 sMsg="comando reserve: Errore!";
					             }
                               break;
		            
				  case "help" :  sMsg="Help --> I comandi disponibili sono: \nhelp\ninformazione\nreserve <numero di posto> <nome di prenotazione>\nend";
				                 break;
				                 
				  case "end" :  sMsg="Server: Bye!";
	                            break;
		                		
				  default:      sMsg="comando: " + cMsg + "non valido";
		                        break;
				}
				//Echo
				//sMsg=cMsg;
				System.out.println("Server.Thread " + clientNo + " Invio comando " + sMsg );
				outStream.writeUTF(sMsg);
				outStream.flush();
			}
			
			//Prima di chiudere la connessione viene inviato un ultimo messaggio
			sMsg="Bye";
			System.out.println("Server.Thread " + clientNo + " Invio messaggio " + cMsg );
			outStream.writeUTF(sMsg);
			outStream.flush();

			//Chiusura delle risorse utilizzate
			inStream.close();
			outStream.close();
			serverClientSocket.close();

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			System.out.println("Client -" + clientNo + " exit!! ");
		}
	}
}
