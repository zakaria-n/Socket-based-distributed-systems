/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author faouz
 */
public class ServerThread extends Thread {

    private Socket socket;

    public ServerThread(Socket s) {
        this.socket = s;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            
            while(true) {
                // gérer des requêtes
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
    
    
}
