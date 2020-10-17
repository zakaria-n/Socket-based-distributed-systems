/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.server;

import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author faouz
 */
public class Participant {
    
    private Socket clientSocket;
    private String nickname;
    
    Participant(Socket cs, String n) {
        clientSocket = cs;
        nickname = n;
    }
    
    public Socket getClientSocket() {
        return clientSocket;
    }
    
    public String getNickname() {
        return nickname;
    }
}
    
    
