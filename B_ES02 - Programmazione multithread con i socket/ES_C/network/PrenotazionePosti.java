package network;

public class PrenotazionePosti {
	
	private String[] prenotazione;
	
	PrenotazionePosti(int prenotazionenum ) {
		prenotazione=new String[prenotazionenum];
    	for (int i=0 ; i<prenotazionenum ; i++ ) {
    		prenotazione[i]="Posto Libero";
    	}
    }
	
	public synchronized String getPrenotazione() {
    	String pren="";
    	for (int i=0 ; i<prenotazione.length ; i++ ) {
    		pren= pren + "\n" + i + " " + prenotazione[i];
    	}
        return pren;
    }
	
	public synchronized String setPrenotazione( int n, String nome ) {
    	String s = "Rifiutato la prenotazione di: " + nome ;
    	if ( prenotazione[n].equals("Posto Libero")) {
    		 prenotazione[n]=nome;
    		 s="Acettato la prenotazione di: " + nome ;
    	} 
        return s;
    }
}
