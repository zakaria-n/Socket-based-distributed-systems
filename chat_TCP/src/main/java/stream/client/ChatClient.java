/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.client;

import java.io.*;
import java.net.*;
import ui.components.ChatRoomUI;

public class ChatClient {

    /**
     * main method accepts a connection, receives a message from client then
     * sends an echo to the client
    *
     */
    public static void main(String[] args) throws IOException {
        ChatRoomUI gui = null;
        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        if (args.length != 2) {
            System.out.println("Usage: java ChatClient <EchoServer host> <EchoServer port>");
            System.exit(1);
        }

        try {
            // creation socket ==> connexion
            echoSocket = new Socket(args[0], Integer.parseInt(args[1]));
            socIn = new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            socOut = new PrintStream(echoSocket.getOutputStream());
            
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            gui = new ChatRoomUI(echoSocket);
            gui.setVisible(true);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to:" + args[0]);
            System.exit(1);
        }

        String line;
        ChatDisplay affichage = new ChatDisplay(echoSocket, gui);
        affichage.start();
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
        echoSocket.close();
    }
    
    
}
