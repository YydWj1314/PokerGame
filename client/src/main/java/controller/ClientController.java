package controller;

import ch.qos.logback.core.net.server.Client;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import enumuration.CardRank;
import enumuration.CardSuit;
import enumuration.CommandType;
import model.Card;
import model.CardVO;
import model.PlayCardDTO;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientSendThread;
import util.ClientMessageBuffer;
import utils.CommandBuilder;
import utils.JsonUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private String message;
    private Player currentPlayer;
    private ClientControllerListener listener;
    private ClientSendThread clientSendThread;

    List<Player> playerList = new ArrayList<>();

    public ClientController(String message, ClientSendThread clientSendThread, ClientControllerListener listener) {
        // get Join Cmd from frame containing name of current player
        // JOIN yyd
        this.currentPlayer = new Player();

        this.message = message;
        String[] parts = this.message.split(" ");
        String name = parts[1];

        this.currentPlayer.setName(name);

        this.clientSendThread = clientSendThread;

        this.listener = listener;

        startMessageThread();
    }

    /**
     * Starting message thread and polling messages
     */
    private void startMessageThread() {
        // Starting Message thread and taking messages
        new Thread(() -> {
            log.info("ClientController Listening for Messages...");
            while (true) {
                ClientMessageBuffer.MessageEntry entry = ClientMessageBuffer.takeMessage();
                if (entry != null) {
                    log.info("CC has taken message: {}", entry.message);
                    // Handling messages
                    handleMessage(entry.message);
                }
            }
        }).start();
    }

    private void handleMessage(String message){
        System.out.println("======= handleMessage =======");

        log.info("CC starts processing message: {}", message);

        //TODO: After taking msg, handling by cases
        String[] parts = message.split(" ");
        String commandType =  parts[0];
        String remainingMessage = message.substring(commandType.length()).trim();
        System.out.println(remainingMessage);

        switch (commandType){

            case "WELCOME" -> {
                // eg: WELCOME (name)daniel (id)1 (player number)1
                log.info("===== WELCOME =====");
                if(listener != null){
                    String welcomeString = String.format("%s Welcome! Online player number: %s." ,
                            parts[1], parts[3]);

                    // Getting socket send by server
                    String receivedSocketAddress = parts[4];
                    // Getting current client socket
                    String localSocketAddress = clientSendThread.getSocket().getLocalSocketAddress().toString();

                    // Setting id for player
                    if (receivedSocketAddress.equals(localSocketAddress)) {
                        this.currentPlayer.setId(Integer.parseInt(parts[2]));
                        log.info("Set ID for current player: {}", this.currentPlayer.getId());
                    }

                    listener.onTextAreaUpdated(welcomeString);
                }
            }

            case "BROADCAST" -> {
                log.info("===== BROADCAST =====");
                // eg: BROADCAST "Game Start"
                if(listener != null){
                    listener.onTextAreaUpdated(remainingMessage);
                }
            }

            case "JSON" -> {
                log.info("===== JSON =====");
                // Parsing json strings to array
                JSONArray playerJsonArray = JsonUtil.toArray(remainingMessage);

                // Generating CardVO object
                for (int i = 0; i < playerJsonArray.size(); i++) {
                    JSONObject playerJson = (JSONObject) playerJsonArray.get(i);

                    int playerId = playerJson.getInteger("id");
                    String playerName = playerJson.getString("name");

                    List<Card> playerHand = new ArrayList<>();

                    JSONArray handJsonArray = playerJson.getJSONArray("hand");
                    for (int j = 0; j < handJsonArray.size(); j++) {
                        JSONObject handCard = (JSONObject) handJsonArray.get(j);
                        String handCardRank = handCard.getString("rank");
                        String handCardSuit = handCard.getString("suit");
                        playerHand.add(
                                new Card(CardSuit.valueOf(handCardSuit), CardRank.valueOf(handCardRank)));
                    }

                    playerList.add(
                            new Player(playerId, playerName, playerHand));
                }

                System.out.println("Client PlayerList: " + playerList);

                // Searching current player info and Encapsulating VO
                List<CardVO> cardVOList = new ArrayList<>();
                for (Player player : playerList) {
                    if(this.currentPlayer.getId() == player.getId()){
                        // Encapsulating Player object
                        this.currentPlayer.setAll(player);

                        List<Card> currentPlayerHand = currentPlayer.getHand();
                        log.info("Current Player Hand: {}", currentPlayerHand );

                        cardVOList = currentPlayerHand.stream()
                                .map(card -> new CardVO(card.getSuit(), card.getRank(), true))
                                .collect(Collectors.toList());
                        log.info("Mapped to CardVO: {}", cardVOList);
                        break;
                    }
                }

                // Notifying listener
                if(listener != null){
                    listener.onCardVOUpdated(cardVOList);
                }
            }
        }

    }


    /**
     * Sending card played info to backend
     * @param selectedCardVOList
     */
    public void sendPlayCommand(List<CardVO> selectedCardVOList) {
        System.out.println("======= sendPlayCommand =======");
        if (selectedCardVOList == null || selectedCardVOList.isEmpty()) {
            log.error("selectedCardVOList is empty, cannot send message.");
        }
        // Encapsulating CardVO to playCardDTO
        List<PlayCardDTO> playCardDTOs = selectedCardVOList.stream()
                .map(cardVO -> new PlayCardDTO(currentPlayer.getId(),cardVO.getSuit(),cardVO.getRank()))
                .toList();

        String jsonMessage = JsonUtil.toJson(playCardDTOs);
        log.info("JSON generated: {}", jsonMessage);

        String playCommand = CommandBuilder.buildCommand(CommandType.CLIENT_PLAY, jsonMessage);

        if (clientSendThread == null) {
            log.error("❌ clientSendThread is NULL, cannot send message!");
            return;
        }

        clientSendThread.sendMessage(playCommand);
        log.info("Send Command to server: {}", playCommand);
    }

}
