/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author faouz
 */
public class ChatServer {
    /**
     * main method
     *
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
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String nickname = socIn.readLine();
                participants.add(new Participant(clientSocket, nickname));
                ClientThread ct = new ClientThread(nickname, clientSocket, participants);
                //ct.broadcast(ct.loadHistory());
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in ChatServer:" + e);
        }
    }
}
