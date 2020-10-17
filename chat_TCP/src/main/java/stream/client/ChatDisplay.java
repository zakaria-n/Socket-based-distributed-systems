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
    private ChatRoomUI gui;
    
    private volatile boolean exit = false;
    
    public ChatDisplay(Socket s, ChatRoomUI ui ) {
        this.clientSocket = s;
        this.gui=ui;
    }
    
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (!exit) {
                String line = socIn.readLine();
                if (line != null)
                {
                    //int toDoEnd = line.indexOf("|");
                    //String toDo = null;
                    //if(toDoEnd != -1) {
                        //toDo = line.substring(0,toDoEnd);
                    //}
                    
                    //switch(toDo) {
                        //case "PRINT" :
                            //String message = line.substring((toDoEnd+1));
                            gui.getMessageArea().append(line+"\n");
                            //break;
                        //default:
                            //break;
                    }
                    
                }
            } catch (Exception e) {
            System.err.println("Error in ChatDisplay:" + e);
        }
    }
    
    public void exit() {
        exit = true;
    }
}
