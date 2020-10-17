/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedInputStream;
import java.util.ArrayList;

/**
 *
 * @author faouz
 */
public class HTTPRequest {
    
    private BufferedInputStream in;
    
    private String method;
    private String request_uri;
    private String httpVersion;
    private ArrayList<String> fields;
    
    public HTTPRequest(BufferedInputStream input) {
        this.in = input;
    }
    
    public void readRequest() {
        // Lire la requete et remplir les attributs
        
    }
    
    
}
