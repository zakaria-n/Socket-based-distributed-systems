/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *
 * @author faouz
 */
public class HTTPRequest {

    private BufferedReader in;

    private String method;
    private String request_uri;
    private String httpVersion;
    private ArrayList<String> fields;
    private ArrayList<String> body;

    public HTTPRequest(BufferedReader input) {
        this.in = input;
    }

    public void readRequest() {
        // Lire la requete et remplir les attributs
        // Le header se termine par la séquence \r\n\r\n (CR LF CR LF)
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
