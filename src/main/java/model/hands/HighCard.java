package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.HandRank;
import utils.HandEvaluator;

import java.util.List;

public class HighCard extends Hand {
    /**
     * Constructor that takes in card list
     *
     * @param cards the list of cards that match the pattern
     */
    public HighCard(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.HIGH_CARD;
    }

    @Override
    public int getHandValue() {
        return HandRank.HIGH_CARD.getRankValue();
    }

    /**
     * Getting the list with card with the highest rank
     *
     * @return the card with the highest rank
     */
    @Override
    public List<Card> getMainCards() {
        // Sorting cards
        HandEvaluator.sortHandByValue(this.cards);
        return List.of(this.cards.get(0));
    }

}
