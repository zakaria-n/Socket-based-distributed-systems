package client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Fenêtre de connexion pour un participant du chat.
 * @author H. Faouz, N. Zakaria
 */
public class ClientConnectionUI extends javax.swing.JFrame {

    /**
     * Creates new form ClientConnectionUI
     */
    public ClientConnectionUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        JoinJButton = new javax.swing.JButton();
        ServerHostTextField = new javax.swing.JTextField();
        ServerPortTextField = new javax.swing.JTextField();
        NicknameTextField = new javax.swing.JTextField();
        HostJLabel = new javax.swing.JLabel();
        PortJLabel = new javax.swing.JLabel();
        NicknameJLabel = new javax.swing.JLabel();
        ErrorLabel = new javax.swing.JLabel();
        roomIDBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Saeka Messenger Connection");

        JoinJButton.setText("Join chat");
        JoinJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JoinJButtonActionPerformed(evt);
            }
        });

        ServerHostTextField.setText("localhost");
        ServerHostTextField.setToolTipText("");
        ServerHostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ServerHostTextFieldActionPerformed(evt);
            }
        });

        ServerPortTextField.setText("1234");

        NicknameTextField.setText("Nickname");

        HostJLabel.setText("Host:");

        PortJLabel.setText("Port:");

        NicknameJLabel.setText("Nickname:");

        ErrorLabel.setForeground(new java.awt.Color(250, 76, 76));
        ErrorLabel.setText("...");

        roomIDBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Room 0", "Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7", "Room 8", "Room 9", " " }));
        roomIDBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomIDBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 176, Short.MAX_VALUE)
                .addComponent(ErrorLabel)
                .addContainerGap(192, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(HostJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ServerHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(PortJLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NicknameJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ServerPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomIDBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(JoinJButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(ErrorLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ServerHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ServerPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HostJLabel)
                    .addComponent(PortJLabel))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NicknameJLabel)
                    .addComponent(roomIDBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(JoinJButton)
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>                        

    private void JoinJButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        Socket socket = null;
        PrintStream socOut = null;
        
        ChatDisplay affichage = null;
        ChatRoomUI crui = null;
        
        String host = ServerHostTextField.getText();
        String port = ServerPortTextField.getText();
        String nickname = NicknameTextField.getText();
        String roomID = (String) roomIDBox.getSelectedItem();
        roomID = roomID.substring(5);
        
        try {
            // creation socket ==> connexion
            
            socket = new Socket(host, Integer.parseInt(port));
            
            socOut = new PrintStream(socket.getOutputStream());
            
            // La premiere ligne envoyee est le nickname du client
            if(nickname.length() > 0) {
                socOut.println(nickname);
            } else {
                socOut.println(socket.getPort());
            }
            socOut.println(roomID);
            affichage = new ChatDisplay(socket);          
            crui = new ChatRoomUI(socket,affichage);
            crui.setTitle("Saeka Messenger | Room " + roomID);
            affichage.setChatRoomUI(crui);
            affichage.start();
            crui.setVisible(true);
            
            
            
            this.setVisible(false);
            
        } catch (UnknownHostException e) {
            String error = "Don't know about host:" + host;
            System.err.println(error);
            ErrorLabel.setText(error);
            
        } catch (IOException e) {
            String error = "Couldn't get I/O for "
                    + "the connection to:" + host ;
            System.err.println(error);
            ErrorLabel.setText(error);
        } catch (NumberFormatException e) {
            String error =  "Error: " + port + " is not an number";
            System.err.println(error);
            ErrorLabel.setText(error);
        }
        
 
    }                                           

    private void ServerHostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
    }                                                   

    private void roomIDBoxActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientConnectionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientConnectionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientConnectionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientConnectionUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientConnectionUI().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel ErrorLabel;
    private javax.swing.JLabel HostJLabel;
    private javax.swing.JButton JoinJButton;
    private javax.swing.JLabel NicknameJLabel;
    private javax.swing.JTextField NicknameTextField;
    private javax.swing.JLabel PortJLabel;
    private javax.swing.JTextField ServerHostTextField;
    private javax.swing.JTextField ServerPortTextField;
    private javax.swing.JComboBox<String> roomIDBox;
    // End of variables declaration                   
}