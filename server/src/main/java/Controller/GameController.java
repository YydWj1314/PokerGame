package Controller;

import enumuration.CommandType;
import model.Card;
import model.Deck;
import model.Player;
import model.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.JsonUtil;
import utils.MessageBuffer;
import utils.SocketHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {
    private static final int MAX_PLAYER_NUMBER = 1;
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private Socket socket;
    private List<Player> playerList = Collections.synchronizedList(new ArrayList<>());
    private Deck deck = new Deck();


    public GameController() {
        startMessageListener();
    }

    private void startMessageListener(){
        //Starting Message thread
        new Thread(() -> {
            log.info("GameController Listening for Messages...");
            while(true){
                MessageBuffer.MessageEntry entry = MessageBuffer.takeMessage();
                log.info("GC has taken message: {} from {}",
                        entry.message, entry.socket);
                if(entry != null){
                    handleMessage(entry.socket, entry.message);
                }
            }
        }).start();
    }

    private void handleMessage(Socket socket, String message) {
        String[] parts = message.split(" ");
        String commandType =  parts[0];

        // Dealing commands
        switch (commandType){
            case "JOIN" ->{
                Player newPlayer = new Player(parts[1], socket);
                playerList.add(newPlayer);
                log.info("Added a new player [id:{}, name:{}, socket:{}]",
                        newPlayer.getId(), newPlayer.getName(), newPlayer.getSocket());

                // Game starts when players are ready
                if(playerList.size() >= MAX_PLAYER_NUMBER) {
                    deck.shuffle();
                    log.info("Initialized deck and shuffled");
                    for (Player player : playerList) {
                        // Dealing cards
                        player.getCardsFromDeck(deck, 5);

                        // Sorting cards in descending order according to card rank
                        List<Card> hand = player.getHand();
                        Collections.sort(hand, Comparator
                                .comparingInt((Card c) -> c.getRank().getValue())
                                .reversed()
                        );
                    }
                    log.info("Finished dealing cards to all players: {}", playerList);


                    // Encapsulate DTO and  Sending DTO to frontend
                    //         PlayerDTO:{id, name, hand}
                    List<PlayerDTO> playerDTOs = playerList.stream()
                            .map(player -> new PlayerDTO(player.getId(), player.getName(),
                                    player.getHand(), player.getScore()))
                            .collect(Collectors.toList());

                    // Changing PlayerDTO to json string
                    String jsonMessage = JsonUtil.toJson(playerDTOs);
                    log.info("JSON generated: {}", jsonMessage);

                    // Each player sending playerDTO json string using SocketHandler
                    for (Player player : playerList) {
                        player.sendMessage(jsonMessage);
                    }
                    log.info("Finished sending players' info to clients");
                }

            }
        }

    }
}
