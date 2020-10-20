package http.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe représentant un thread de serveur.
 * Ce choix d'implémentation a été fait pour permettre de lancer plusieurs instances du serveur en parallèle pour pouvoir assurer la réponse à plusieurs clients qui demandent d'accéder à des ressources différentes.
 * @author Faouz Hachim, Zakaria NASSREDDINE
 * version 1.0
 */
public class ServerThread extends Thread {

    private Socket socket;
    
    /* Constructeur qui prend en paramètre une socket */
    public ServerThread(Socket s) {
        this.socket = s;
    }

    public void run() {
        try {
            // Ouverture du flux de lecture qui permettra de lire le contenu de la requête envoyée par le client 
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            // Ouverture du flux d'écriture qui permettra d'envoyer du contenu au client sous forme de bytes 
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

            // Attend indéfiniment des requêtes pour les traiter, tel un bon petit serveur !
            while(true) {
                HTTPRequest request = new HTTPRequest(in);
                request.readRequest();
                System.out.println(request.getMethod());
                System.out.println(request.getRequest_uri());
                
                /* Traite la requête en fonction de son type */
                if (request!=null) {
                    switch (request.getMethod()) {
                        case "GET":
                            httpGET(out, request.getRequest_uri());
                            break;
                        case "POST":
                            httpPOST(out, in, request.getRequest_uri());
                            break;
                        case "PUT":
                            httpPUT(out, in, request.getRequest_uri());
                            break;
                        case "HEAD":
                            httpHEAD(out, request.getRequest_uri());
                            break;
                        case "DELETE":
                            httpDELETE(out, request.getRequest_uri());
                            break;
                        default:
                            /* Cas où le serveur ne sait pas traiter le type de requête reçue */
                            try {
                                out.write(makeHeader("501 Not Implemented").getBytes());
                                out.flush();
                            } catch (Exception e) {
                                System.out.println(e);
                            }               
                    }
                } 
            }
            

        /* Tentative de prévenir le client dans le cas où tout échoue. */
        } catch (Exception e) {
            System.err.println("Error in ServerThread: " + e);
            e.printStackTrace();
        }
    }

    private void httpGET(BufferedOutputStream out, String request_uri) {
        File resource = new File(request_uri);
        try {
            boolean exists = resource.exists();
            boolean valid = resource.isFile();
            if (exists && valid){
                long length = resource.length();
                String type = getContentType(resource);
                out.write(makeHeader("200 OK",type, length).getBytes());
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(resource));
                byte[] buffer = new byte[1000];
                int nbRead;
                while((nbRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, nbRead);
                }
                in.close();
                out.flush();             
            }else{
                String type = "Content-Type: text/html\r\n";
                String code = valid ? "403 Forbidden" : "404 Not Found";
                String body = valid ? forbidden : notFound;
                long length = (long) body.length();
                out.write(makeHeader(code, type, length).getBytes());
                out.write(body.getBytes());
                out.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                out.write(makeHeader("500 Internal Server Error").getBytes());
                out.flush();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private String makeHeader(String code, String type, long length) {

        String header;

        header = "HTTP/1.1 " + code + "\r\n";
        header += type;
        header += "Content-Length: " + length + "\r\n";
        header += "Server: Mini WebServer\r\n";
        header += "\r\n";
        return header;
    }

    private String makeHeader(String code) {
        String header = "HTTP/1.1 " + code + "\r\n";
        header += "Server: Mini WebServer\r\n";
        header += "\r\n";
        return header;
    }

    private void httpPOST(BufferedOutputStream out, BufferedInputStream in, String request_uri) {
        //Répond à une requête POST
        try {
            File resource = new File(request_uri);
            boolean newFile = resource.createNewFile();
            
            if (getContentType(resource).equals("python")){
                byte[] buffer = new byte[256];
                int nbRead=0;
                while(in.available() > 0) {
                    nbRead = in.read(buffer);
                }
                String S = new String(buffer);
                S = S.substring(0,nbRead);
                System.out.println(S);
               
                Process process = Runtime.getRuntime().exec("/usr/local/bin/python3 /Users/zakaria/Documents/GitHub/Socket-based-distributed-systems/resources/adder.py "+ S + " --sum");
            }
            
            else {
                BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(resource, resource.exists()));

                byte[] buffer = new byte[256];
                while(in.available() > 0) {
                    int nbRead = in.read(buffer);
                    fileOut.write(buffer, 0, nbRead);
                }
                fileOut.flush();
                fileOut.close();
            }
            if (newFile) {
                out.write(makeHeader("201 Created").getBytes());
                out.write("\r\n".getBytes());
            } else {
                out.write(makeHeader("200 OK").getBytes());
                out.write("\r\n".getBytes());
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                out.write(makeHeader("500 Internal Server Error").getBytes());
                out.write("\r\n".getBytes());
                out.flush();
            } catch (Exception e2) {
                System.out.println(e);
            }
        }
    }

    private void httpPUT(BufferedOutputStream out, BufferedInputStream in, String request_uri) {
        //Répond à une requête PUT
        try {
            File resource = new File(request_uri);
            if (!resource.createNewFile()){
                PrintWriter pw = new PrintWriter(resource);
                pw.close();
            }
            
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(resource, resource.exists()));

            byte[] buffer = new byte[256];
            while(in.available() > 0) {
                int nbRead = in.read(buffer);
                fileOut.write(buffer, 0, nbRead);
            }
            fileOut.flush();
            fileOut.close();

            if (resource.createNewFile()) {
                out.write(makeHeader("204 No Content").getBytes());
                out.write("\r\n".getBytes());
            } else {
                out.write(makeHeader("201 Created").getBytes());
                out.write("\r\n".getBytes());
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                out.write(makeHeader("500 Internal Server Error").getBytes());
                out.write("\r\n".getBytes());
                out.flush();
            } catch (Exception e2) {
                System.out.println(e);
            }

        }
    }
    
    private void httpDELETE(BufferedOutputStream out, String request_uri) {
        //Répond à une requête DELETE
        try {
            File resource = new File(request_uri);
            boolean exists = resource.exists();
            boolean removed = false;
            
            if (exists && resource.isFile()){
                removed = resource.delete();
            }

            if(removed) {
                out.write(makeHeader("204 No Content").getBytes());
                out.write("\r\n".getBytes());
            } else if (!exists) {
                out.write(makeHeader("404 Not Found").getBytes());
                out.write("\r\n".getBytes());
            } else {
                out.write(makeHeader("403 Forbidden").getBytes());
                out.write("\r\n".getBytes());
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                out.write(makeHeader("500 Internal Server Error").getBytes());
                out.write("\r\n".getBytes());
                out.flush();
            } catch (Exception e2) {
                System.out.println(e);
            }

        }
    }
    
    private void httpHEAD(BufferedOutputStream out, String request_uri) {
        try{
            File resource = new File(request_uri);
            if (resource.exists() && resource.isFile()){
                long length = resource.length();
                String type = getContentType(resource);
                out.write(makeHeader("200 OK", type, length).getBytes());
                out.write("\r\n".getBytes());
            }else{
                out.write(makeHeader("404 Not Found").getBytes());
                out.write("\r\n".getBytes());
            }
            out.flush();
        }catch (IOException e) {
            e.printStackTrace();
            try {
                 out.write(makeHeader("500 Internal Server Error").getBytes());
                 out.flush();
            } catch (Exception e2) {
                System.out.println(e);
            }
        }
        
    }
    public String getContentType(File file) {

        String fileName = file.getName();
        String type = ".txt";

        if (fileName.endsWith(".html") || fileName.endsWith(".htm") || fileName.endsWith(".txt")) {
            type = "Content-Type: text/html\r\n";
        } else if (fileName.endsWith(".mp4")) {
            type = "Content-Type: video/mp4\r\n";
        } else if (fileName.endsWith(".png")) {
            type = "Content-Type: image/png\r\n";
        } else if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpeg")) {
            type = "Content-Type: image/jpg\r\n";
        } else if (fileName.endsWith(".mp3")) {
            type = "Content-Type: audio/mp3\r\n";
        } else if (fileName.endsWith(".avi")) {
            type = "Content-Type: video/x-msvideo\r\n";
        } else if (fileName.endsWith(".css")) {
            type = "Content-Type: text/css\r\n";
        } else if (fileName.endsWith(".pdf")) {
            type = "Content-Type: application/pdf\r\n";
        } else if (fileName.endsWith(".odt")) {
            type = "Content-Type: application/vnd.oasis.opendocument.text\r\n";
        } else if (fileName.endsWith(".json")) {
            type = "Content-Type: application/json\r\n";
        } else if (fileName.endsWith(".py")) {
            type = "python";
        }

        return type;
    }
    
    
    private static final String notFound = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + "   <title>404 Not Found</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "   <h1>404 Not Found</h1>\n"
            + "   <p>The requested URL was not found on this server.</p>\n"
            + "</body>\n"
            + "</html> ";
    
    private static final String forbidden = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n"
            + "<html>\n"
            + "<head>\n"
            + "   <title>403 Forbidden</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "   <h1>403 Forbidden</h1>\n"
            + "   <p>Access is forbidden to the requested page.</p>\n"
            + "</body>\n"
            + "</html> ";
    
}
