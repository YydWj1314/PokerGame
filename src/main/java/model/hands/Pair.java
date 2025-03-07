package model.hands;

import model.Card;
import model.Hand;
import model.enumuration.CardRank;
import model.enumuration.HandRank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pair extends Hand {
    /**
     * Constructor that takes in card list
     *
     * @param cards
     */
    public Pair(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandRank getHandName() {
        return HandRank.PAIR;
    }

    @Override
    public int getHandValue() {
        return HandRank.PAIR.getRankValue();
    }

    @Override
    public List<Card> getMainCards() {
        CardRank pairRank = this.getPairRank();
        List<Card> mainCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getRank() == pairRank) {
                mainCards.add(card);
            }
        }
        return mainCards;
    }

    /**
     * Getting the rank of the pair
     *
     * @return the rank of the pair
     */
    public CardRank getPairRank() {
        // use set to count appearance
        Map<CardRank, Integer> rankCount = new HashMap<>();
        for (Card card : this.cards) {
            CardRank rank = card.getRank();
            rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
        }
        // Get the rank of card that appears 2 times
        for (Map.Entry<CardRank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 2) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Getting the non-pair card
     *
     * @return the non-pair card
     */
    public Card getNonPairCard() {
        // Get the rank of the pair
        CardRank pairRank = this.getPairRank();
        // Get the non-pair card
        for (Card card : cards) {
            if (pairRank != null && card.getRank() != pairRank) {
                return card;
            }
        }
        return null;
    }
}
