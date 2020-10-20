package client;

import java.io.*;
import java.net.*;
import javax.swing.DefaultListModel;

/**
 * Thread pour gérer les entrées de la socket de connexion
 * avec le serveur
 * @author H. Faouz, N. Zakaria
 */
public class ChatDisplay extends Thread {

    private Socket clientSocket;
    private ChatRoomUI gui = null;

    private volatile boolean exit = false;
    
    /**
     * Instancie le thread à partir de la socket de connexion
     * avec le serveur
     * @param socket Socket
     */
    public ChatDisplay(Socket socket) {
        this.clientSocket = socket;
    }
    
    /**
     * Récupère les messages reçus et les interpréte 
     * pour effectuer l'affichage qu'il faut pour l'utilisateur
     */
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (!exit) {
                String line = socIn.readLine();
                if (line != null) {
                    if(line.startsWith("UPDATE_PARTICIPANTS|")) {
                        updateParticipants(line);
                    } else if (line.startsWith("CONNECTION_TEST")) {
                        //nothing, ignore
                    } else {
                        gui.getMessageArea().append(line + "\n");
                    }
                    
                }

            }
        } catch (SocketException e) {
            System.out.println("Socket closed!");
            return;
        } catch (IOException ex) {
            System.err.println("Error in ChatDisplay:" + ex);
            ex.printStackTrace();
        }
    }
    
    private void updateParticipants(String participants) {
        int cutPos = participants.indexOf("|");
        participants = participants.substring(cutPos+1);
        DefaultListModel dlm = (DefaultListModel) gui.getParticipantsJList().getModel();
        dlm.clear();
        while( ( cutPos = participants.indexOf("|") ) != -1) {
            String nickname = participants.substring(0,cutPos);
            dlm.addElement(nickname);
            participants = participants.substring(cutPos+1);
        }
    }
    
    /**
     * Termine le thread
     */
    public void exit() {
        exit = true;
    }
    
    /**
     * Modifie l'interface avec lequel interargit le thread
     * @param crui ChatRoomUI : User interface du chat pour un participant
     */
    public void setChatRoomUI(ChatRoomUI crui) {
        this.gui = crui;
    }
    
   
}