/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.server;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;

public class ClientThread extends Thread {

    private String clientNickname;
    private Socket clientSocket;
    private List<Participant> participants;
    private static final String path = "../data/history.txt";
    private boolean justJoined = true;
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

            //System.out.println(loadHistory());
            socOut.print(loadHistory());
            broadcast(clientNickname + " just hopped into the server! ");
            //broadcast(loadHistory());
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
                            broadcast("From " + clientNickname + ": " + message);
                            break;
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
            //Premiere boucle pour mettre à jour l'ensemble des participants

            Iterator<Participant> participantIterator = participants.iterator();
            while (participantIterator.hasNext()) {
                Participant p = participantIterator.next();
                Socket s = p.getClientSocket();
                OutputStream output = s.getOutputStream();
                try {
                    output.write("CONNECTION_TEST\r\n".getBytes());
                    //output.flush();
                } catch (Exception e) {
                    System.out.println(p.getNickname() + " is disconnected");
                    participantIterator.remove();
                }
                
                
            }

            //Deuxieme boucle pour diffuser le message
            //Et mettre à jour la liste des participants en meme temps
            for (Participant p : participants) {
                Socket s = p.getClientSocket();
                PrintStream socOut = new PrintStream(s.getOutputStream());
                socOut.println(message);
                socOut.println("UPDATE_PARTICIPANTS|" + getParticipantsList());
            }
            saveHistory(message);

        } catch (Exception e) {
            System.err.println("Broadcast error: " + e);
        }
    }

    public synchronized String loadHistory() {
        String history = "";
        try {
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
            String line = reader.readLine();
            while (line != null) {
                history += line + "\n";
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return history;
    }

    public synchronized void saveHistory(String message) {
        try {
            File file = new File(path);
            if (file.createNewFile()) { // si le fichier n'existe pas déjà on le crée
                System.out.println("The history file has been created.");
            } else {
                System.out.println("The history file already exists.");
            }
            PrintWriter logWriter = new PrintWriter(new FileOutputStream(file, true), true);
            logWriter.append(message + "\n");
            logWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    private String getParticipantsList() {
        String list = "";
        for (Participant p : participants) {
            list += (p.getNickname() + "|");
        }
        return list;
    }
    
}
