package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StraightFlush extends Hand {


    /**
     * Constructor that takes in card list
     *
     * @param cards
     */
    public StraightFlush(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.STRAIGHT_FLUSH;
    }

    @Override
    public int getHandValue() {
        return HandRank.STRAIGHT_FLUSH.getRankValue();
    }

    @Override
    public List<Card> getMainCards() {
        // shallow copy  --> all cards in the list will be original
        return new ArrayList<>(this.cards);

//         deep copy --> all cards will be new object
//        return this.cards.stream()
//                .map(card -> new Card(card.getSuit(), card.getRank()))
//                .collect(Collectors.toList());
    }

    /**
     * Get the suit of the straight flush
     *
     * @return the suit of straight flush
     */
    public CardSuit getStraightFlushSuit() {
        return this.cards.get(0).getSuit();
    }
}
