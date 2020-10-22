package server;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;

/**
 * Thread qui gère la connexion d'un client au serveur
 * @author H. Faouz, N. Zakaria
 *
 */
public class ClientThread extends Thread {

    private String clientNickname;
    private Socket clientSocket;
    private ChatRoom room;
    private volatile boolean exit = false;
    
    
    /**
     * Constructeur principal. Instancie le thread avec 
     * le participant correspondant au client et la room du chat.
     * @param participant Participant géré par le thread
     * @param chatRoom Room du chat que le participant a rejoint.
     */
    public ClientThread(Participant participant, ChatRoom chatRoom) {
        this.clientNickname = participant.getNickname();
        this.clientSocket = participant.getClientSocket();
        this.room = chatRoom;
    }

    /**
     * Charge l'historique du chat.
     * Diffuse à tous les participants les messages reçus via
     * la socket de connexion.
     */
    public void run() {
        try {
            BufferedReader socIn = null;
            PrintStream socOut = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socOut = new PrintStream(clientSocket.getOutputStream());

            socOut.print(loadHistory());
            
            broadcast(clientNickname + " just hopped into the server! ");
            
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
    
    /**
     * Diffuse le message à tous les participants du chat
     * (y compris le participant courant). Envoie aussi un message 
     * permettant de mettre à jour la liste des participants au niveau
     * des clients
     * @param message String contenant le message à diffuser.
     */
    private synchronized void broadcast(String message) {
        try {
        	
            //Premiere boucle pour mettre à jour l'ensemble des participants
        	String toSend = "";
            Iterator<Participant> participantIterator = room.getParticipants().iterator();
            while (participantIterator.hasNext()) {
                Participant p = participantIterator.next();
                Socket s = p.getClientSocket();
                OutputStream output = s.getOutputStream();
                try {
                    output.write("CONNECTION_TEST\r\n".getBytes());
                } catch (Exception e) {
                    System.out.println(p.getNickname() + " is disconnected");
                    toSend += p.getNickname() + " left the chat.\r\n";
                    participantIterator.remove();
                }
            }

            //Deuxieme boucle pour diffuser le message
            //Et mettre à jour la liste des participants en meme temps
            for (Participant p : room.getParticipants()) {
                Socket s = p.getClientSocket();
                PrintStream socOut = new PrintStream(s.getOutputStream());
                socOut.println(message);
                socOut.println("UPDATE_PARTICIPANTS|" + getParticipantsList());
                if (toSend.length()>0) {
                    socOut.println(toSend);
                }
            }
            
            //Sauvegarde du chat
            saveHistory(message);

        } catch (Exception e) {
            System.err.println("Broadcast error: " + e);
        }
    }
    
    /**
     * Renvoie l'historique du chat
     * @return String contenant les messages enregistrés
     */
    private synchronized String loadHistory() {
        String history = "";
        try {
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(room.getHistoryFile()))));
            String line = reader.readLine();
            while (line != null) {
                history += line + "\n";
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        return history;
    }
    
    /**
     * Ajoute le message en paramètre à l'historique du chat
     * @param message Message à rajouter à l'historique
     */
    private synchronized void saveHistory(String message) {
        try {
            File file = new File(room.getHistoryFile());
            if (file.createNewFile()) { // si le fichier n'existe pas déjà on le crée
                System.out.println("The history file has been created.");
            } else {
                System.out.println("The history file already exists.");
            }
            PrintWriter logWriter = new PrintWriter(new FileOutputStream(file, true), true);
            logWriter.append(message + "\n");
            logWriter.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Retourne la liste des participants sous la forme
     * d'une chaine de caractères interprétables par le client
     * @return String liste des participants
     */
    private String getParticipantsList() {
        String list = "";
        for (Participant p : room.getParticipants()) {
            list += (p.getNickname() + "|");
        }
        return list;
    }
    
}