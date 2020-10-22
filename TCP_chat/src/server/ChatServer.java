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
    * Méthode main.
    * Lance une socket d'écoute et récupère les demandes de connexion.
    * Pour chaque connexion, lance un thread ClientThread qui gérera le participant.
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
       
       // Initialisation des 10 rooms de chat
       ChatRoom[] allRooms = new ChatRoom[10];
       for (int i=0; i<10; i++){
           allRooms[i] = new ChatRoom();
       }
       
       try {
           listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
           System.out.println("Server ready...");
           while (true) {
               Socket clientSocket = listenSocket.accept();
               System.out.println("Connexion from:" + clientSocket.getInetAddress());
               BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
               
               // Les deux premieres lignes recues sont le pseudo et le numero de room
               String nickname = socIn.readLine();
               String roomID = socIn.readLine();
               int id = Integer.parseInt(roomID);
               Participant p = new Participant(clientSocket, nickname);
               allRooms[id].acceptParticipant(p);
               ClientThread ct = new ClientThread(p, allRooms[id]);
               
               ct.start();
           }
       } catch (Exception e) {
           System.err.println("Error in ChatServer:" + e);
       }
   }
}