/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.client;

import java.io.*;
import java.net.*;
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
                    gui.getMessageArea().append(line + "\n");
                }

            }
        } catch (Exception e) {
            System.err.println("Error in ChatDisplay:" + e);
            e.printStackTrace();
        }
    }

    public void exit() {
        exit = true;
    }
    
    public void setChatRoomUI(ChatRoomUI crui) {
        this.gui = crui;
    }
}
