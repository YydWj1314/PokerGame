package model;


import enumuration.CardRank;
import enumuration.CardSuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        this.initDeck();
    }

    /**
     * Initialization: adding cards into the deck
     */
    public void initDeck() {
        // clean the collection avoiding duplicate additions
        cards.clear();
        for (CardSuit cardSuit : CardSuit.values()) {
            for (CardRank cardRank : CardRank.values()) {
                cards.add(new Card(cardSuit, cardRank, true));
            }
        }
    }

    /**
     * Getting the total number of cards in the deck
     *
     * @return cards number in the deck
     */
    public int countCards() {
        return cards.size();
    }

    /**
     * Showing the top card in the deck
     *
     * @return card on the top of the deck
     */
    public Card getTop() {
        return cards.get(0);
    }

    /**
     * Shuffle the deck randomly
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Dealing n cards from deck top
     *
     * @param n deal n cards in one time
     * @return the list of cards that are dealt
     */
    public List<Card> deal(int n) {
        if (cards.isEmpty()) {
            return null;
        }
        List<Card> dealCards = new ArrayList<>(cards.subList(0, n)); // copy the cards dealt
        cards.subList(0, n).clear();  // remove the dealt cards
        return dealCards;
    }


    @Override
    public String toString() {
        return "Deck:" + cards;
    }

}
