package controller;

import ch.qos.logback.core.net.server.Client;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import enumuration.CardRank;
import enumuration.CardSuit;
import model.Card;
import model.CardVO;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ClientMessageBuffer;
import utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private String message;
    private Player currentPlayer;
    private ClientControllerListener listener;

    public ClientController(String message, ClientControllerListener listener) {
        // get Join Cmd from frame containing name of current player
        // JOIN yyd
        this.currentPlayer = new Player();
        this.message = message;
        String[] parts = this.message.split(" ");
        String name = parts[1];
        this.currentPlayer.setName(name);

        this.listener = listener;

        startMessageListener();
    }

    private void startMessageListener() {
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
        log.info("CC starts processing message: {}", message);

        //TODO: After taking msg, handling by cases

        // Parsing json strings to array
        JSONArray playerJsonArray = JsonUtil.toArray(message);

        // Generating CardVO object
        List<Player> playerList = new ArrayList<>();
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
            if(this.currentPlayer.getName().equals(player.getName())){
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
