/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            /*
            while(true) {
                // gérer des requêtes
            }
             */
            //Gérer la requête
            HTTPRequest request = new HTTPRequest(in);
            request.readRequest();

            switch (request.getMethod()) {
                case "GET":
                    httpGET(out, request.getRequest_uri());
                    break;
                case "POST":
                    //
                    break;
                case "PUT":
                    //
                    break;
                case "HEAD":
                    //
                    break;
                case "DELETE":
                    //
                    break;
                default:

                    break;
            }

            //Fermer la socket
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public void httpGET(PrintWriter out, String request_uri) {
        //Répond à une requete GET
        File file = new File(request_uri);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException ex) {
            System.err.println("Error in httpGET: " + ex);
            //Envoyer une erreur 404 : file not found
            
        }
        int cur = '\0';
        String toSend = new String();
        try {
            while((cur = reader.read()) != -1 ) {
                toSend += cur;
            }
        } catch (IOException ex) {
            System.err.println("Error in httpGET: " + ex);
        }
        
        // Faire l'entête de réponde
        
        out.print(toSend);
    }

}
