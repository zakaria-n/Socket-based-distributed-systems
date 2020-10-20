/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedInputStream;
import java.util.ArrayList;

/**
 * Une classe représentant une requête HTTP générique..
 * @author Faouz Hachim
 * @version 1.0
 */
public class HTTPRequest {

    private BufferedInputStream in;

    private String method;
    private String request_uri;
    private String httpVersion;
    private ArrayList<String> fields;
    private ArrayList<String> body;
    
    // Constructeur de requête: prend en paramètre le flux d'entrée.
    public HTTPRequest(BufferedInputStream input) {
        this.in = input;
    }

    // Méthode qui analyse la requête pour affecter les bonnes valeurs aux attributs.
    // Le header se termine par la séquence \r\n\r\n (CR LF CR LF)
    public void readRequest() {
        int bcur = '\0';
        int bprec = '\0';
        boolean newline = false;

        String header = new String();
        try {
            while ((bcur = in.read()) != -1 && !(newline && bprec == '\r' && bcur == '\n')) {
                if (bprec == '\r' && bcur == '\n') {
                    newline = true;
                } else if (!(bprec == '\n' && bcur == '\r')) {
                    newline = false;
                }
                bprec = bcur;
                header += (char) bcur;
            }
        } catch (Exception e) {
            System.err.println("Error in readRequest: " + e);
            e.printStackTrace();
        }

        int wordEnd = header.indexOf(" ");
        if (wordEnd != -1) {
            method = header.substring(0, wordEnd);
            header = header.substring((wordEnd + 1));
        }
        wordEnd = header.indexOf(" ");
        if (wordEnd != -1) {
            request_uri = header.substring(0, wordEnd);
            request_uri = "../" + request_uri;
            header = header.substring((wordEnd + 1));
        }
        wordEnd = header.indexOf("\r\n");
        if (wordEnd != -1) {
            httpVersion = header.substring(0, wordEnd);
            header = header.substring((wordEnd + 2));
        }
        
        //Lecture fields
        fields = new ArrayList<String>();
        while (header.length() > 4) {
            wordEnd = header.indexOf("\r\n");
            if (wordEnd != -1) {
                String field = header.substring(0, wordEnd);
                fields.add(field);
                header = header.substring((wordEnd + 2));
            }
        }
         
        // Eventuellement lire le body

    }

    public String getMethod() {
        return method;
    }

    public String getRequest_uri() {
        return request_uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public ArrayList<String> getBody() {
        return body;
    }
    
    

}
