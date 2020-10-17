/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.client;

import java.io.*;
import java.net.*;
import ui.components.*;

public class ChatClient {

    /**
     * main method accepts a connection, receives a message from client then
     * sends an echo to the client
    *
     */
    public static void main(String[] args) throws IOException {
        
        ChatRoomUI crui = null;
        ClientConnectionUI ccui = null;
        
        ChatDisplay affichage = null;
        
        Socket socket = null;
        
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;


        try {
            //Connection frame
            ccui = new ClientConnectionUI();
            ccui.setVisible(true);
            // creation socket ==> connexion
            
            socket = new Socket(args[0], Integer.parseInt(args[1]));
            socIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            socOut = new PrintStream(socket.getOutputStream());
            
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            affichage = new ChatDisplay(socket, crui);
            affichage.start();
            
           
            crui = new ChatRoomUI(socket,affichage);
            crui.setVisible(true);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + args[0]);
            System.exit(1);
        }

        String line;
        
        while (true) {
            /*
            line = stdIn.readLine();
            //line = input.getText();
            if (line.equals(".")) {
                break;
            }
            socOut.println(line);
            //System.out.println(socIn.readLine());
            */
            if(affichage.getState() == Thread.State.TERMINATED) {
                break;
            }
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        socket.close();
    }
    
    
}
