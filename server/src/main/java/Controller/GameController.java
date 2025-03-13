package Controller;

import enumuration.CommandType;
import model.Deck;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageBuffer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private Socket socket;
    private List<Player> playerList = new ArrayList<>();
    private Deck deck = new Deck();



    public GameController() {
        startMessageListener();
    }

    private void startMessageListener(){
        new Thread(() -> {
            log.info("GC Listening for Messages...");
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
        switch (commandType){
            case "JOIN" ->{
                Player newPlayer = new Player(parts[1], socket);
                playerList.add(newPlayer);
                log.info("Added a new player [id:{}, name:{}, socket:{}]",
                        newPlayer.getId(), newPlayer.getName(), newPlayer.getSocket());
                if(playerList.size() >= 3){
                    deck.shuffle();
                    log.info("Initialized deck and shuffled");
                    for (Player player : playerList) {
                        player.getCardsFromDeck(deck, 5);
                    }
                    log.info("Finished dealing cards to all players");
                    System.out.println(playerList);
                }
            }
        }

    }
}
