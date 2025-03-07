package model;

import model.enumuration.CardRank;
import model.enumuration.CardSuit;

import java.util.Objects;

/**
 *
 */
public class Card {
    private CardSuit cardSuit;
    private CardRank cardRank;
    private Boolean inDeck;

    /**
     * Constructor with cardSuit and cardRank
     *
     * @param cardSuit
     * @param cardRank
     */
    public Card(CardSuit cardSuit, CardRank cardRank) {
        this.cardSuit = cardSuit;
        this.cardRank = cardRank;
    }

    /**
     * Constructor with cardSuit, cardRank and inDeck
     *
     * @param cardSuit
     * @param cardRank
     * @param inDeck
     */
    public Card(CardSuit cardSuit, CardRank cardRank, Boolean inDeck) {
        this.cardSuit = cardSuit;
        this.cardRank = cardRank;
        this.inDeck = inDeck;
    }


    /**
     * Getter and Setter
     */
    public CardSuit getSuit() {
        return cardSuit;
    }

    public void setSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    public CardRank getRank() {
        return cardRank;
    }

    public void setRank(CardRank cardRank) {
        this.cardRank = cardRank;
    }

    public Boolean getInDeck() {
        return inDeck;
    }

    public void setInDeck(Boolean inDeck) {
        this.inDeck = inDeck;
    }

    @Override
    public String toString() {
        return cardSuit.toString() + cardRank.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardSuit == card.cardSuit && cardRank == card.cardRank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardSuit, cardRank);
    }
}
