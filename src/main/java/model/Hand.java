package model;

import model.enumuration.HandRank;

import java.util.List;
import java.util.Objects;

public abstract class Hand implements Comparable<Hand> {
    protected List<Card> cards;

    /**
     * Constructor that takes in card list
     *
     * @param cards the list of cards that match the pattern
     */
    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Getting the list of all the cards in hand
     *
     * @return the list of all cards in hand
     */
    public List<Card> getCards() {
        return cards;
    }

    public abstract HandRank getHandName();

    public abstract int getHandValue();

    public abstract List<Card> getMainCards();

    @Override
    public int compareTo(Hand other) {
        return Integer.compare(this.getHandValue(), other.getHandValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Objects.equals(getCards(), hand.getCards());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCards());
    }

    @Override
    public String toString() {
        return "Hands:" + cards;
    }
}
