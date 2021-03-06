/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.components;

import java.io.PrintStream;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import stream.client.ChatDisplay;

/**
 *
 * @author zakaria
 */
public class ChatRoomUI extends javax.swing.JFrame {
    
    /**
     * Creates new form ChatRoomUI
     */
    public ChatRoomUI(Socket cs, ChatDisplay cd) {
        this.clientSocket = cs;
        this.chatDisplay = cd;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        ChatInput = new javax.swing.JTextField();
        ChatDisplay = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MessageArea = new javax.swing.JTextArea();
        SendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        LeaveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ParticipantsJList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Saeka Messenger");

        ChatInput.setText("Write something...");
        ChatInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChatInputMouseClicked(evt);
            }
        });
        ChatInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChatInputActionPerformed(evt);
            }
        });

        MessageArea.setColumns(20);
        MessageArea.setRows(5);
        jScrollPane2.setViewportView(MessageArea);

        javax.swing.GroupLayout ChatDisplayLayout = new javax.swing.GroupLayout(ChatDisplay);
        ChatDisplay.setLayout(ChatDisplayLayout);
        ChatDisplayLayout.setHorizontalGroup(
            ChatDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatDisplayLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        ChatDisplayLayout.setVerticalGroup(
            ChatDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChatDisplayLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Participants");

        LeaveButton.setText("Leave chat");
        LeaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveButtonActionPerformed(evt);
            }
        });

        ParticipantsJList.setModel(new DefaultListModel());
        ParticipantsJList.setToolTipText("");
        jScrollPane1.setViewportView(ParticipantsJList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ChatDisplay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ChatInput))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(LeaveButton)))
                        .addGap(33, 33, 33))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ChatDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(12, 12, 12)
                        .addComponent(LeaveButton)
                        .addGap(42, 42, 42)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChatInput, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ChatInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChatInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChatInputActionPerformed

    private void ChatInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChatInputMouseClicked
        // TODO add your handling code here:
        ChatInput.setText("");
    }//GEN-LAST:event_ChatInputMouseClicked

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed

        try {
            String message = ChatInput.getText();
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            socOut.println("SEND|" + message);
            ChatInput.setText("");
        } catch (Exception e) {
            System.err.println("SendButtonActionPerformed error: " + e);
        }

    }//GEN-LAST:event_SendButtonActionPerformed

    private void LeaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveButtonActionPerformed
        // TODO add your handling code here:
        try {
            chatDisplay.exit();
            clientSocket.close();
            System.exit(0);
        } catch (Exception e) {
            System.err.println("LeaveButtonActionPerformed error: " + e);
            e.printStackTrace();
        }
    }//GEN-LAST:event_LeaveButtonActionPerformed

    /**
     * @param args the command line arguments
     */

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatDisplay;
    private javax.swing.JTextField ChatInput;
    private javax.swing.JButton LeaveButton;
    private javax.swing.JTextArea MessageArea;
    private javax.swing.JList<String> ParticipantsJList;
    private javax.swing.JButton SendButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
    private Socket clientSocket;
    private ChatDisplay chatDisplay;
    
    
    public JPanel getChatDisplay() {
        return ChatDisplay;
    }

    public JTextField getChatInput() {
        return ChatInput;
    }

    public JTextArea getMessageArea() {
        return MessageArea;
    }

    public JButton getSendButton() {
        return SendButton;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }
    
   public JList<String> getParticipantsJList() {
       return ParticipantsJList;
   }
}
