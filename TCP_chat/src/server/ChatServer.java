package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
* Classe main du serveur à exécuter pour lancer le serveur.
* @author H. Faouz, N. Zakaria
*/
public class ChatServer {
	
   /**
    * 
    * Méthode main
    * Usage: java ChatServer <Server port> 
    * @param Server port 
    *
    */
   public static void main(String args[]) {
	   
	   
       ServerSocket listenSocket;
       if (args.length != 1) {
           System.out.println("Usage: java ChatServer <Server port>");
           System.exit(1);
       }
       
       try {
           List<Participant> participants;
           participants = new ArrayList<Participant>();
           listenSocket = new ServerSocket(Integer.parseInt(args[0])); 
           System.out.println("Server ready...");
           while (true) {
               Socket clientSocket = listenSocket.accept();
               System.out.println("Connexion from:" + clientSocket.getInetAddress());
               BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               String nickname = socIn.readLine(); //La premiere ligne recue est le pseudo du participant
               Participant participant = new Participant(clientSocket, nickname);
               participants.add(participant);
               ClientThread ct = new ClientThread(participant, participants);
               ct.start();
           }
       } catch (Exception e) {
           System.err.println("Error in ChatServer:" + e);
       }
   }
}