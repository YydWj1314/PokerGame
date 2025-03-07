package model;

import model.enumuration.ExceptionMessage;
import model.exception.EmptyDeckException;
import model.exception.InvalidCardsCountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Player {
    private String uid;
    private List<Card> hand;
    private Integer sore;
    private Hand handRank;

    /**
     * Constructor with user id
     *
     * @param uid user id of the player
     */
    public Player(String uid) {
        this.uid = uid;
        this.hand = new ArrayList<>();
        this.sore = 0;
        this.handRank = null;
    }

    public String getUid() {
        return uid;
    }

    /**
     * Getting the hand of the player
     *
     * @return the hand of the player
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Setting the player's hand
     *
     * @param cards cards to the player
     */
    public void setHand(List<Card> cards) {
        this.hand = cards;
    }


    /**
     * Setting the hand rank of the player
     *
     * @param handRank the hand rank of the player
     */
    public void setHandRank(Hand handRank) {
        this.handRank = handRank;
    }

    /**
     * Getting the hand rank of the player
     *
     * @return the hand rank of the player
     */
    public Hand getHandRank() {
        return this.handRank;
    }

    /**
     * Acquiring n cards from deck top
     *
     * @param n number of cards in the deck
     */
    public void getCardsFromDeckTop(Deck deck, int n) {
        if (deck == null || deck.countCards() == 0) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_DECK.getMessage());
        }
        this.hand.addAll(deck.deal(n));
    }


    /**
     * Selecting n cards from hand
     *
     * @param n select n cards from hand
     * @return the card list selected by player
     */
    public List<Card> selectCards(int n, Scanner scanner) {
//        Scanner scanner = new Scanner(System.in);
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
            throw new InvalidCardsCountException(ExceptionMessage.INVALID_CARDS_COUNT.getMessage());
        }
        this.hand.removeAll(cards);
        return cards;
    }

    @Override
    public String toString() {
        return "\n" + "{" +"uid:" + uid +
                ", hand:" + hand +
                ", handRank:" + handRank + "}";
    }
}
