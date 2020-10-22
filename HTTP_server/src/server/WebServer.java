package server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe principale du serveur.
 * @author Faouz HACHIM, Zakaria NASSREDDINE
 */
public class WebServer {
	
	/**
	 * Main. Lance une socket d'écoute et récupére les demandes de connexions.
	 * Pour chaque connexion, démarre un thread ServerThread pour répondre à la requête du client.
	 * @param Server port
	 */
    public static void main(String args[]) {
        ServerSocket s = null;

        if (args.length != 1) {
            System.out.println("Usage: java WebServer <Server port>");
            System.exit(1);
        }

        System.out.println("Webserver starting up on port " + args[0]);
        System.out.println("(press ctrl-c to exit");

        try {
            // create the main server socket
            s = new ServerSocket(Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }

        System.out.println("Waiting for connection");

        try {
            while (true) {
                Socket remote = s.accept();
                
                ServerThread st = new ServerThread(remote);
                st.start();
                // remote is now the connected socket
                System.out.println("New connection, sending data.");

            }

        } catch (Exception e) {
            System.out.println("Error in WebServer: " + e);
            
            e.printStackTrace();
        }

    }

}