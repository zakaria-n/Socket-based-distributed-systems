/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatClient {

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
            
            while (true) {
                String msg = stdIn.readLine();
                msg = "From " + socket.getLocalAddress() +": " + msg;
                DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.length(), groupAddr, groupPort);
                socket.send(packet);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
