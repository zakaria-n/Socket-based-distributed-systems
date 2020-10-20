package stream;

import java.net.DatagramPacket;
import java.net.MulticastSocket;

/**
 * Thread qui gère les messages reçus du client.
 * @author H. Faouz, N. Zakaria
 *
 */
public class ChatDisplay extends Thread {

    private MulticastSocket socket;
    
    /**
     * Instancie un thread à partir de la socket multicast
     * @param s
     */
    ChatDisplay(MulticastSocket multicastSocket) {
        this.socket = multicastSocket;
    }
    
    /**
     * Affiche sur le terminal les messages reçus
     */
    public void run() {
        try {
            while (true) {
                byte[] buf = new byte[1000];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                socket.receive(recv);

                String received = new String(recv.getData(), 0, recv.getLength());
                System.out.println(received);
            }
        } catch (Exception e) {
            System.err.println("Error in ChatDisplay:" + e);
        }
    }

}