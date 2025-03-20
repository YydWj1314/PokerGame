package view;

import controller.ClientController;
import controller.ClientControllerListener;
import enumuration.CommandType;
import model.CardVO;
import model.PlayCardDTO;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;
import util.ClientJsonUtil;
import util.ClientMessageBuffer;
import utils.CommandBuilder;
import utils.JsonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainFrame extends JFrame implements ClientControllerListener {
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private CardPanel cardPanel;
    private JTextArea systemMessageArea;
    private JButton playButton;


    private String message;
    private Socket socket;
    private ClientController clientController;
    private ClientSendThread clientSendThread;
    private ClientReceiveThread clientReceiveThread;
    private Player currentPlayer = new Player();
    private List<CardVO> cardVOList = new ArrayList<>();
    private List<CardVO> selectedCardVOList = new ArrayList<>();

    /**
     * @param socket
     * @param message
     */
    public MainFrame(Socket socket, String message){
        this.socket = socket;
        this.message = message;

        // Setting window attributes
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        // add CardPanel
        cardPanel = new CardPanel();
        cardPanel.setBounds(0, 0, 1200, 700);
        cardPanel.setLayout(null);
        this.add(cardPanel);

        // Adding System message area
        systemMessageArea = new JTextArea();
        systemMessageArea.setEditable(false); // read only
        systemMessageArea.setLineWrap(true);  //wrap text
        JScrollPane scrollPane = new JScrollPane(systemMessageArea);
        scrollPane.setBounds(600, 475, 500, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardPanel.add(scrollPane);


        // Initializing `playButton`
        playButton = new JButton("Play");
        playButton.setBounds(200, 400, 100, 50);
        cardPanel.add(playButton);


        // Add event to the button
        playButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // click the button and play cards
                if(selectedCardVOList.size() < 3){
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            "Please Select 3 Cards!",
                            "Selection Error",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                if (selectedCardVOList == null ||selectedCardVOList .isEmpty()) {
                    log.error("selectedCardVOList is empty, cannot send message.");
                    return;
                }
                clientController.sendPlayCommand(selectedCardVOList);
                log.info("Finished Sending Command to Backend");

            }
        });

        // Starting ClientSendThread to send messages
        clientSendThread = new ClientSendThread(socket, message);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");

        // Initializing client controller
        clientController =  new ClientController(message, clientSendThread, this);

        // Starting ClientReceiveThread to receive messages
        clientReceiveThread = new ClientReceiveThread(socket);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");

    }

    @Override
    public void onCardVOUpdated(List<CardVO> cardVOList) {
        log.info("Updating UI...");
        SwingUtilities.invokeLater(() -> {
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

                    int xPos = 300 + 30 * i[0]++;
                    int yPos = 450;
                    cardVO.setBounds(xPos, yPos, 150, 200);

                    // Adding mouse press event
                    cardVO.addMouseListener(new CardClickListener(cardVO, xPos, yPos));

                    this.cardPanel.add(cardVO);

                    this.cardPanel.setComponentZOrder(cardVO, 0);

                    this.cardPanel.revalidate();
                    this.cardPanel.repaint();
                } else {
                    ((Timer) e.getSource()).stop(); // stop Timer
                }
            });

            timer.start();
        });
    }

    @Override
    public void onTextAreaUpdated(String message, Object... args) {
        systemMessageArea.append(message + "\n");
    }


    /**
     * Inner class of mouse click listener
     */
    private class CardClickListener extends MouseAdapter {
        private final CardVO cardVO;
        private final int xPos, yPos;
        private boolean isSelected = false;

        public CardClickListener(CardVO cardVO, int xPos, int yPos) {
            this.cardVO = cardVO;
            this.xPos = xPos;
            this.yPos = yPos;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (isSelected) {
                if (selectedCardVOList.size() < 3) {
                    cardVO.setBounds(xPos, yPos - 20, 150, 200);
                    selectedCardVOList.add(cardVO);
                    log.info("Selected Card: {}", cardVO);
                }
                else return;

            } else {
                cardVO.setBounds(xPos, yPos, 150, 200);
                selectedCardVOList.remove(cardVO);
                log.info("Removed Card: {}", cardVO);

            }
            log.info("Selected CardList: {}", selectedCardVOList);
            isSelected = !isSelected;

            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }
}