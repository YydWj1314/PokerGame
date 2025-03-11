package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class LoginFrame extends JFrame {

    private JLabel unameJLabel;
    private JTextField unameJTextField;
    private JButton jButton;

    public LoginFrame(){
        // Creating component objects
        this.unameJLabel = new JLabel("UserName");
        this.unameJTextField = new JTextField();
        this.jButton = new JButton("LoginFrame");

        // Setting attributes
        this.setSize(400, 300);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new GridLayout(2, 2));

        // Adding components to the window
        this.add(unameJLabel);
        this.add(unameJTextField);
        this.add(jButton);
        this.pack();  // auto layout

        // binding event
        MyEvent myEvent = new MyEvent();
        this.jButton.addActionListener(myEvent);
    }


    /**
     *
     */
    class MyEvent implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            // 1. Get username
            String uname = unameJTextField.getText();

            // 2. Creat socket connecting server
            try {
                Socket socket = new Socket("127.0.0.1", 8888);

                // 3. Jump to main frame
                new MainFrame(uname, socket);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
