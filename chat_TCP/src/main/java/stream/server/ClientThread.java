/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.server;

import java.io.*;
import java.net.*;
import java.util.List;

public class ClientThread extends Thread {
    
    private String clientNickname;
    private Socket clientSocket;
    private List<Participant> participants;

    private volatile boolean exit = false;

    ClientThread(String cn, Socket s, List<Participant> p) {
        this.clientNickname = cn;
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
            PrintStream socOut = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socOut = new PrintStream(clientSocket.getOutputStream());
            //broadcast("UPDATE_PARTICIPANTS|" + getParticipantsList());
            while (!exit) {
                String line = socIn.readLine();
                if (line != null) {
                    int toDoEnd = line.indexOf("|");
                    String toDo = null;
                    if (toDoEnd != -1) {
                        toDo = line.substring(0, toDoEnd);
                    }

                    switch (toDo) {
                        case "SEND":
                            String message = line.substring((toDoEnd + 1));
                            broadcast("PRINT|From " + clientNickname + ": " + message);
                            break;
                        /*
                        case "LEAVE":
                            socOut.println("EXIT| ");
                            participants.remove(clientSocket);
                            broadcast("UPDATE_PARTICIPANTS|" + getParticipantsList());
                            exit = true;
                            break;
                            */
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ClientThread:" + e);
        }
    }
    
    
    public synchronized void broadcast(String message) {
        try {
            for (Participant p : participants) {
                Socket s = p.getClientSocket();
                PrintStream socOut = new PrintStream(s.getOutputStream());
                socOut.println(message);
            }
        } catch (Exception e) {
            System.err.println("Broadcast error: " + e);
        }
    }
    
    /*
    private String getParticipantsList() {
        String list = "";
        for (Socket s : participants) {
            list += (s.getPort() + "|");
        }
        return list;
    }
    */

}
