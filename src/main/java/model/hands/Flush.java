package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;

import java.util.ArrayList;
import java.util.List;

public class Flush extends Hand {
    /**
     * Constructor that takes in card list
     *
     * @param cards
     */
    public Flush(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.FLUSH;
    }

    @Override
    public int getHandValue() {
        return HandRank.FLUSH.getRankValue();
    }

    @Override
    public List<Card> getMainCards() {
        return new ArrayList<>(this.cards);
    }

    /**
     * Getting the suit of flush
     *
     * @return the suit of flush
     */
    public CardSuit getFlushSuit() {
        return this.cards.get(0).getSuit();
    }
}
