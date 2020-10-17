/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stream.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zakaria
 */
public class ChatRoom {
    private static int iD = 0;
    private int id;
    private String name;
    private List<Participant> participants = new ArrayList<>();
    private String historyFile;
    private BufferedWriter writer = null;
    private BufferedReader reader = null;
    
    ChatRoom(String roomName) {
        this.id = iD++;
        this.name = roomName;
        try {
            this.historyFile = "users/zakaria/Documents/GitHub/Socket-based-distributed-systems/data" + name + "_" + id + ".txt";
            File file = new File(historyFile);
            if (file.createNewFile()) { // si le fichier n'existe pas déjà on le crée
                System.out.println("The history file for room "+ id + "has been created.");
            } else {
                System.out.println("The history file for room "+ id + "already exists.");
            }
            writer = new BufferedWriter(new FileWriter(new File(historyFile), true)); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public synchronized void acceptParticipant (Participant p){
        this.participants.add(p);
    }
    
    public synchronized void removeParticipant (Participant p){
        this.participants.remove(p);
    }
    
    public synchronized String loadHistory(){
        String history = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(historyFile)));
            String line = reader.readLine();
            while (line != null) {
                history += "\r\n" + line;
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return history;
    }
    
    
    public synchronized void saveHistory(String message){
        try {
            writer.write(message);
            writer.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getiD() {
        return iD;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public String getHistoryFile() {
        return historyFile;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }
    
    
    
}
    