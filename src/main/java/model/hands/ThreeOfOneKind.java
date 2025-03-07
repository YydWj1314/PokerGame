package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;

import java.util.ArrayList;
import java.util.List;


public class ThreeOfOneKind extends Hand {

    /**
     * Constructor that takes in card list
     *
     * @param cards
     */
    public ThreeOfOneKind(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.THREE_OF_A_KIND;
    }

    @Override
    public int getHandValue() {
        return HandRank.THREE_OF_A_KIND.getRankValue();
    }

    @Override
    public List<Card> getMainCards() {
        return new ArrayList<>(this.cards);
    }

    /**
     * Getting the rank of three of a kind
     *
     * @return the rank of three of a kind
     */
    public CardRank getThreeOfOneKindRank() {
        return this.cards.get(0).getRank();
    }

}
