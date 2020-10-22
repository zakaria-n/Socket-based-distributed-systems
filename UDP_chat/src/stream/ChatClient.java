package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Classe main à éxecuter pour lancer un client.
 * @author H. Faouz, N. Zakaria
 *
 */
public class ChatClient {
	
	/**
	 * Méthode main. Connecte un client sur une socket
	 * multicast. Lis l'entrée saisie par l'utilisateur et l'envoie 
	 * aux participants.
	 * @param args
	 */
    public static void main(String[] args) {

        try {

            InetAddress groupAddr = InetAddress.getByName("230.0.0.0");
            int groupPort = 4321;

            MulticastSocket socket = new MulticastSocket(groupPort);
            socket.joinGroup(groupAddr);

            ChatDisplay affichage = new ChatDisplay(socket);
            affichage.start();
            
            BufferedReader stdIn = null;
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connected. Ready to chat...");
            // Identifiant aléatoire pour l'instant
            int id = (int) (Math.random()*10000);
            while (true) {
                String msg = stdIn.readLine();
                if(msg!=null) {
                	msg = "From " + id +": " + msg;
                    DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), groupAddr, groupPort);
                    socket.send(packet);
                } else {
                	return;
                }
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}