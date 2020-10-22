package server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un salon de discussion à partir d'un ID, 
 * un fichier pour l'historique et une liste de participants.
 * @author H. Faouz, N. Zakaria
 */
public class ChatRoom {
    private static int iD = 0;
    private int id;
    private List<Participant> participants = new ArrayList<>();
    private String historyFile;
    
    /**
     * Constructeur principal. Instancie une chatRoom. 
     * A chaque appel du constructeur, l'id de la nouvelle room est incrémenté.
     */
    ChatRoom() {
        this.id = iD++;
        try {
            this.historyFile = "../data/room"+ "_" + id + ".txt";
            File file = new File(historyFile);
            if (file.createNewFile()) { // si le fichier n'existe pas déjà on le crée
                System.out.println("The history file for room "+ id + " has been created.");
            } else {
                System.out.println("The history file for room "+ id + " already exists.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Ajoute un participant à la liste des participants de la room.
     * @param p Participant à ajouter.
     */
    public synchronized void acceptParticipant (Participant p){
        this.participants.add(p);
    }
    
    /**
     * Renvoie le numéro de la room.
     * @return int, numéro de la room.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Renvoie la liste des participants.
     * @return List<Participant>
     */
    public List<Participant> getParticipants() {
        return participants;
    }
    
    /**
     * Renvoie le chemin vers le fichier avec l'historique du chat de la room.
     * @return String, chemin vers un fichier .txt contenant l'historique.
     */
    public String getHistoryFile() {
        return historyFile;
    }

    
    
    
}