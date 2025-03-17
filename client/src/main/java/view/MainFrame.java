package view;


import controller.ClientController;
import controller.ClientControllerListener;
import model.CardVO;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;
import util.ViewUtil;


import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ClientControllerListener {
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private cardPanel cardPanel;
    private String message;
    private Socket socket;
    private ClientSendThread clientSendThread;
    private ClientReceiveThread clientReceiveThread;
    private Player currentPlayer = new Player();
    private List<CardVO> cardVOList = new ArrayList<>();

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
        cardPanel = new cardPanel();
        cardPanel.setBounds(0, 0, 1200, 700);
        this.add(cardPanel);

        // Opening ClientSendThread to send messages
        clientSendThread = new ClientSendThread(socket, message);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");

        // Opening ClientReceiveThread to receive messages
        clientReceiveThread = new ClientReceiveThread(socket);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");

        new ClientController(message, this);

    }


    @Override
    public void onPlayerListUpdated(List<Player> playerList) {

    }

    @Override
    public void onCardVOUpdated(List<CardVO> cardVOList) {
        log.info("Updating UI...");
        SwingUtilities.invokeLater(() -> {
            this.cardPanel.removeAll();
            this.cardPanel.revalidate();
            this.cardPanel.repaint();

            // Creating Timer，interval 300ms
            Timer timer = new Timer(100, null);
            final int[] i = {0};             // counter
            timer.addActionListener(e -> {
                if (i[0] < cardVOList.size()) {
                    CardVO cardVO = cardVOList.get(i[0]);
                    cardVO.setUp(true);
                    this.cardVOList.add(cardVO);

                    cardVO.setBounds(300 + 30 * i[0], 450, 150, 200);
                    this.cardPanel.add(cardVO);
                    this.cardPanel.setComponentZOrder(cardVO, 0);
                    ViewUtil.move(cardVO, 300 + 30 * i[0]++, 450);

                    this.cardPanel.revalidate();
                    this.cardPanel.repaint();
                } else {
                    ((Timer) e.getSource()).stop(); // stop Timer
                }
            });

            timer.start();
        });
    }
}
