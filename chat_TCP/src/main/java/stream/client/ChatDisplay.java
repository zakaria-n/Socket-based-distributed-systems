/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.client;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import ui.components.ChatRoomUI;

/**
 *
 * @author zakaria
 */
public class ChatDisplay extends Thread {

    private Socket clientSocket;
    private ChatRoomUI gui = null;

    private volatile boolean exit = false;

    public ChatDisplay(Socket s) {
        this.clientSocket = s;
    }

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
    
    public void exit() {
        exit = true;
    }
    
    public void setChatRoomUI(ChatRoomUI crui) {
        this.gui = crui;
    }
    
   
}
