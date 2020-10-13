/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream;
import java.io.*;
import java.net.*;

/**
 *
 * @author zakaria
 */
public class ChatDisplay extends Thread {
    private Socket clientSocket;
    
    ChatDisplay(Socket s) {
        this.clientSocket = s;
    }
    
    public void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String line = socIn.readLine();
                if (line != null)
                {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}
