package server;

import java.net.Socket;

/**
 * Représente un participant du chat.
 * @author H. Faouz, N. Zakaria
 */
public class Participant {
   
    private Socket clientSocket;
    private String nickname;
    
    /**
     * Instancie un participant à partir d'une socket
     * et d'un pseudo.
     * @param clientSocket Socket de connexion avec le client
     * @param nickname Pseudo du participant
     */
    Participant(Socket clientSocket, String nickname) {
        this.clientSocket = clientSocket;
        this.nickname = nickname;
    }
    
    /**
     * Renvoie la socket de connexion avec le client
     * @return Socket
     */
    public Socket getClientSocket() {
        return clientSocket;
    }
    
    /**
     * Renvoie le pseudo du participant
     * @return String 
     */
    public String getNickname() {
        return nickname;
    }
}
    