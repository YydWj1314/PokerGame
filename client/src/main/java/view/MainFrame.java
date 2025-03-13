package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientSendThread;


import javax.swing.*;
import java.net.Socket;

public class MainFrame extends JFrame{
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private MyPanel myPanel;
    private String message;
    private Socket socket;
    private ClientSendThread clientSendThread;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MainFrame(Socket socket, String message){
        this.socket = socket;
        this.message = message;

        // Setting Attributes
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // Adding Panel
        myPanel = new MyPanel();
        myPanel.setBounds(0, 0, 1200, 700);
        this.add(myPanel);

        // Opening ClientSendThread sending messages
        clientSendThread = new ClientSendThread(socket, message);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }
}
