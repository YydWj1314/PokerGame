package model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Player {
    private static final Logger log = LoggerFactory.getLogger(Player.class);

    private static int ID_COUNTER = 1;

    private int id;
    private String name;
    private List<Card> hand;
    private Integer score;
    private Socket socket;
    private SocketHandler socketHandler;

    public Player() {

    }

    /**
     * Constructor with name and given socket
     *
     * @param name   name of the player
     * @param socket socket with server ip and port
     */
    public Player(String name, Socket socket) {
        this.id = ID_COUNTER++;
        this.name = name;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.socket = socket;
        this.socketHandler = new SocketHandler(socket);
    }

    public Player(int id, String name, List<Card> hand) {
        this.id = id;
        this.name = name;
        this.hand = hand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * Acquiring n cards from deck top
     *
     * @param n number of cards in the deck
     */
    public void getCardsFromDeck(Deck deck, int n) {
        if (deck == null || deck.countCards() == 0) {
            log.warn("Deck is empty");
            return;
        }
        List<Card> dealtCards = deck.deal(n);
        this.hand.addAll(dealtCards);
        log.info("Player {{}} get cards from deck: {}", this.name, dealtCards);
    }


    /**
     * Selecting n cards from hand
     *
     * @param n select n cards from hand
     * @return the card list selected by player
     */
    public List<Card> selectCards(int n, Scanner scanner) {
//        Scanner = new Scanner(System.in);
//        System.out.println("Input " + n + " indices, split by space");
        String input = scanner.nextLine();
        String[] indices = input.split(" ");

        List<Card> selectedCards = new ArrayList<>();
        for (String i : indices) {
            int index = Integer.parseInt(i);
            if (index < 0 || index >= this.hand.size()) {
                throw new IllegalArgumentException("Index out of bound");
            }
            selectedCards.add(this.hand.get(index));
        }
        return selectedCards;
    }

    /**
     * Playing cards from hand
     *
     * @param cards cards to be played by player
     * @return cards played
     */
    public List<Card> playCards(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            return null;
        }
        this.hand.removeAll(cards);
        return cards;
    }

    public void sendMessage(String message) {
        socketHandler.sendMessage(message);
    }

    public void setAll(Player other) {
        this.name = other.name;
        this.hand = new ArrayList<>(other.hand);
    }

    @Override
    public String toString() {
        return "\n" + "{" +
                "id:" + id +
                ", name:" + name +
                ", hand:" + hand +
                "}";
    }
}
