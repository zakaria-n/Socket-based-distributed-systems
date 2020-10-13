/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream;

import java.io.*;
import java.net.*;
import java.util.List;

public class ClientThread extends Thread {

    private Socket clientSocket;
    private List<Socket> participants;

    ClientThread(Socket s, List<Socket> p) {
        this.clientSocket = s;
        this.participants = p;
    }

    /**
     * receives a request from client then sends an echo to the client
     *
     * @param clientSocket the client socket
     *
     */
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                String line = socIn.readLine();
                //socOut.println(line);
                if (line != null) {
                    broadcast("Message from " + clientSocket.getInetAddress() + ": " + line);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    public void broadcast(String message) {
        try {
            for (Socket s : participants) {
                PrintStream socOut = new PrintStream(s.getOutputStream());
                socOut.println(message);
            }
        } catch (Exception e) {
            System.err.println("Broadcast error: " + e);
        }
    }

}
