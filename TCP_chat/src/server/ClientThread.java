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
    private List<Participant> participants;
    private volatile boolean exit = false;
    
    private static final String path = "../data/history.txt";
    
    /**
     * Constructeur principal. Instancie le thread avec 
     * le participant correspondant au client et la liste des participants.
     * @param participant Participant géré par le thread
     * @param participants Liste des participants
     */
    public ClientThread(Participant participant, List<Participant> participants) {
        this.clientNickname = participant.getNickname();
        this.clientSocket = participant.getClientSocket();
        this.participants = participants;
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
     * (y compris le participant courant)
     * @param message
     */
    private synchronized void broadcast(String message) {
        try {
        	
            //Premiere boucle pour mettre à jour l'ensemble des participants

            Iterator<Participant> participantIterator = participants.iterator();
            while (participantIterator.hasNext()) {
                Participant p = participantIterator.next();
                Socket s = p.getClientSocket();
                OutputStream output = s.getOutputStream();
                try {
                    output.write("CONNECTION_TEST\r\n".getBytes());
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
    
    /**
     * Ajoute le message en paramètre à l'historique du chat
     * @param message Message à rajouter à l'historique
     */
    private synchronized void saveHistory(String message) {
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

    /**
     * Retourne la liste des participants sous la forme
     * d'une chaine de caractères interprétables par le client
     * @return String liste des participants
     */
    private String getParticipantsList() {
        String list = "";
        for (Participant p : participants) {
            list += (p.getNickname() + "|");
        }
        return list;
    }
    
}