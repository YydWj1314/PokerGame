package Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import enumuration.CardRank;
import enumuration.CardSuit;
import enumuration.CommandType;
import model.Card;
import model.Deck;
import model.Player;
import model.PlayerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class GameController {
    private static final int MAX_PLAYER_NUMBER = 1;
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private List<Player> playerList = Collections.synchronizedList(new ArrayList<>());
    Map<Player, List<Card>> playedCardsMap = new HashMap<>();
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
        String remainingMessage = message.substring(commandType.length()).trim();

        // Dealing commands
        switch (commandType){
            case "JOIN" ->{
                log.info("===== SERVER: JOIN COMMAND =====");
                // Creating new player and adding to the player list
                Player newPlayer = new Player(parts[1], socket);
                this.playerList.add(newPlayer);
                log.info("Added a new player [id:{}, name:{}, socket:{}]",
                        newPlayer.getId(), newPlayer.getName(), newPlayer.getSocket());

                // Sending Login player info and welcome command to Frontend
                // command eg: WELCOME (name)daniel (id)1 (socket)127.0.0.1 (player number)1
                String welcomeCommand = CommandBuilder.buildCommand(CommandType.WELCOME,
                        newPlayer.getName(),  // parts[1]
                        Integer.toString(newPlayer.getId()),  // parts[2]
                        Integer.toString(playerList.size()),  // parts[3]
                        newPlayer.getSocket().getRemoteSocketAddress().toString()); //parts[4]

                MessageBroadcaster.broadcastMessage(playerList, welcomeCommand);

                // Game starts when players are ready
                if(playerList.size() >= MAX_PLAYER_NUMBER) {
                    // Broadcasting game start message
                    String startMessage = CommandBuilder.buildCommand(CommandType.BROADCAST, "Game Start!");
                    MessageBroadcaster.broadcastMessage(playerList,startMessage);

                    // Shuffling and Dealing cards to players
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


                    // Encapsulate DTO and Sending DTO to frontend
                    //         PlayerDTO:{id, name, hand}
                    List<PlayerDTO> playerDTOs = playerList.stream()
                            .map(player -> new PlayerDTO(player.getId(), player.getName(),
                                    player.getHand(), player.getScore()))
                            .collect(Collectors.toList());

                    // Converting PlayerDTO to json string
                    String jsonMessage = JsonUtil.toJson(playerDTOs);
                    log.info("JSON generated: {}", jsonMessage);

                    // Each player sending playerDTO json string using SocketHandler
                    for (Player player : playerList) {
                        String jsonCommand = CommandBuilder.buildCommand(CommandType.JSON, jsonMessage);
                        player.sendMessage(jsonCommand);
                    }
                    log.info("Finished sending players' info to client");
                }
            }
            case "CLIENT_PLAY" -> {
                log.info("===== CLIENT_PLAY =====");
                // CLIENT_PLAY [{"playerId":1,"rank":"JACK","suit":"HEARTS"},
                //              {"playerId":1,"rank":"QUEEN","suit":"DIAMONDS"},
                //              {"playerId":1,"rank":"TEN","suit":"DIAMONDS"}]
                JSONArray playedCardsJsonArray = JsonUtil.toArray(remainingMessage);

                List<Card> playedCards = new ArrayList<>();
                int playerId = -1;

                // Parsing Json String and encapsulating card object
                // Adding played cards to playedCards list
                for(int i = 0; i < playedCardsJsonArray.size(); i++){
                    JSONObject playedCardJson = (JSONObject) playedCardsJsonArray.get(i);
                    playerId = playedCardJson.getInteger("playerId");
                    String playedCardRank = playedCardJson.getString("rank");
                    String playedCardSuit = playedCardJson.getString("suit");

                    Card playedCard  = new Card(CardSuit.fromName(playedCardSuit),
                            CardRank.fromName(playedCardRank));

                    playedCards.add(playedCard);
                }

                // Putting {player-playedCards} entry to the map
                for (Player player : playerList) {
                    if (player.getId() == playerId) {
                        playedCardsMap.put(player, playedCards);
                        break;
                    }
                }
                System.out.println(playedCardsMap);
                System.out.println(playedCardsMap.size());
            }
        }

    }
}
