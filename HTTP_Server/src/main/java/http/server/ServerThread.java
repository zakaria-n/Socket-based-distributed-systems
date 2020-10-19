/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

            /*
            while(true) {
                // gérer des requêtes
            }
             */
            //Gérer la requête
            HTTPRequest request = new HTTPRequest(in);
            request.readRequest();
            System.out.println(request.getMethod());
            System.out.println(request.getRequest_uri());

            switch (request.getMethod()) {
                case "GET":
                    httpGET(out, request.getRequest_uri());
                    break;
                case "POST":
                    httpPOST(out, in, request.getRequest_uri());
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
            System.err.println("Error in ServerThread: " + e);
            e.printStackTrace();
        }
    }

    private void httpGET(BufferedOutputStream out, String request_uri) {
        //Répond à une requete GET
        File file = new File(request_uri);
        BufferedReader reader = null;
        String code = null;
        String toSend = new String();
        boolean success = false;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int cur = '\0';

            while ((cur = reader.read()) != -1) {
                toSend += (char) cur;
            }
            code = "200 OK";
            success = true;
        } catch (FileNotFoundException ex) {
            System.err.println("Error in httpGET: " + ex);
            ex.printStackTrace();
            
            if(file.isFile()) {
                code = "403 Forbidden";
            } else {
                code = "404 Not Found";
            }

        } catch (IOException ex) {
            System.err.println("Error in httpGET: " + ex);
            code = "404 Not Found";
            ex.printStackTrace();
        }

        String type = "...";
        long length = 0;

        switch (code) {

            case "200 OK":
                type = getContentType(file);
                length = file.length();

                break;

            case "404 Not Found":
                type = "Content-Type: text/html\r\n";
                length = (long) notFound.length();
                toSend = notFound;
                break;
            
            case "403 Forbidden":
                type = "Content-Type: text/html\r\n";
                length = (long) forbidden.length();
                toSend = forbidden;
                break;
                
            
            default:
                break;
        }

        try {
            out.write(makeHeader(code, type, length).getBytes());
            out.write(toSend.getBytes());
            out.flush();
        } catch (IOException ex) {
            System.err.println("Error in httpGET: " + ex);
            ex.printStackTrace();
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

    private void httpPOST(BufferedOutputStream out, BufferedReader in, String request_uri) {
        try {
            File resource = new File(request_uri);
            boolean newFile = resource.createNewFile();

            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(resource, resource.exists()));

            byte[] buffer = new byte[1024];
            String lineBody = in.readLine();
            while (lineBody != null && !lineBody.equals("")) {
                System.out.println("line :" + lineBody);
                lineBody = in.readLine();
                fileOut.write(lineBody.getBytes(), 0, lineBody.getBytes().length);
                fileOut.write("\r\n".getBytes(), 0, "\r\n".getBytes().length);
            }
            fileOut.flush();
            fileOut.close();

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
