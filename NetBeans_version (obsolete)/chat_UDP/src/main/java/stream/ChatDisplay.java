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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatDisplay extends Thread {

    private MulticastSocket socket;

    ChatDisplay(MulticastSocket s) {
        this.socket = s;
    }

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
