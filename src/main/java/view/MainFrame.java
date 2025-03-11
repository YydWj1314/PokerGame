package view;

import javax.swing.*;
import java.net.Socket;

public class MainFrame extends JFrame {
    // TODO change to private
    public MyPanel myPanel;
    public String uname;
    public Socket socket;

    public MainFrame(String uname, Socket socket){
        this.uname = uname;
        this.socket = socket;

        // set window attributes
        this.setSize(1200, 700);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add panel
        myPanel = new MyPanel();
        myPanel.setBounds(0, 0, 1200, 700);
        this.add(myPanel);

        //
    }
}
