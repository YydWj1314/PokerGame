package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.ExceptionMessage;
import model.enumuration.HandRank;
import model.exception.CardNotFoundException;
import utils.HandEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Straight extends Hand {
    /**
     * Constructor that takes in card list
     *
     * @param cards the list of cards that match the pattern
     */
    public Straight(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.STRAIGHT;
    }

    @Override
    public int getHandValue() {
        return HandRank.STRAIGHT.getRankValue();
    }

    @Override
    public List<Card> getMainCards() {
        return new ArrayList<>(this.cards);
    }

    /**
     * Getting the card with the highest rank in the straight
     *
     * @return card with the highest rank in the straight
     */
    public Card getHighestCard(){
        HandEvaluator.sortHandByValue(this.cards);

        // Get the list of value: A-3-2 --> 14-3-2
        List<Integer> cardValues = cards.stream()
                .map(card -> card.getRank().getValue())
                .collect(Collectors.toList());

        // Special case: the minimum straight A-3-2, return 2 as highest card
        if (cardValues.containsAll(List.of(14, 3, 2))){
            return cards.stream()
                    .filter(card -> card.getRank().getValue() == 2)
                    .findFirst()
                    .orElseThrow(() -> new CardNotFoundException(ExceptionMessage.CARD_NOT_FOUND.getMessage()));
        }
        // Normal case
        return cards.get(0);
    }
}
