package utils;

import model.Card;
import model.Hand;
import model.enumuration.CardRank;
import model.enumuration.ExceptionMessage;
import model.enumuration.HandRank;
import model.exception.CardNotFoundException;
import model.exception.HandRankNotFoundException;
import model.hands.Pair;

import java.util.Comparator;
import java.util.List;

public class RankComparator implements Comparator<List<Card>> {
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
        Hand hand1 = HandEvaluator.getResult(o1);
        Hand hand2 = HandEvaluator.getResult(o2);

        if (hand1 == null || hand2 == null) {
            throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
        }

        Integer handValue1 = hand1.getHandValue();
        System.out.println("handValue1:" + hand1 + " " + hand1.getHandName() + " " + handValue1);
        Integer handValue2 = hand2.getHandValue();
        System.out.println("handValue2:" + hand2 + " " + hand2.getHandName() + " " + handValue2);

        // Situation 1: if rank is the same, call compareSameRank() method
        if (handValue1.equals(handValue2)) {
            return this.compareSameRank(o1, o2);
        }

        // Situation 2: if rank values are not same, return reversed order
        return handValue2.compareTo(handValue1);
    }

    /**
     * Comparing the rank with same hand rank
     *
     * @param o1
     * @param o2
     * @return
     */
    private int compareSameRank(List<Card> o1, List<Card> o2) {
        Hand hand1 = HandEvaluator.getResult(o1);
        Hand hand2 = HandEvaluator.getResult(o2);

        if (hand1 == null || hand2 == null) {
            throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
        }

        HandRank rank = hand1.getHandName();
        switch (rank) {
            case STRAIGHT_FLUSH, THREE_OF_A_KIND, FLUSH, STRAIGHT -> {
                // 1. StraightFLush, ThreeOfKind, Flush, Straight:
                //    (1)sort by value in descending order and compare the highest card,
                //    (2)if the highest card rank is same, return 0
                HandEvaluator.sortHandByValue(hand1.getCards());
                HandEvaluator.sortHandByValue(hand2.getCards());

                CardRank highestRank1 = hand1.getCards().get(0).getRank();
                CardRank highestRank2 = hand2.getCards().get(0).getRank();

                if (highestRank1 == highestRank2) {
                    return 0;
                }
                return highestRank2.compareTo(highestRank1);
            }

            case PAIR -> {
                // 2. Pair:
                //    (1)compare the rank of the pair,
                //    (2)if same, compare the rank of the rest card
                //    (3)if same, return 0;
                Pair pairHand1 = (Pair) hand1;
                Pair pairHand2 = (Pair) hand2;

                if (pairHand1.getPairRank() == pairHand2.getPairRank()) {
                    if (pairHand1.getNonPairCard() == null || pairHand2.getNonPairCard() == null) {
                        throw new CardNotFoundException(ExceptionMessage.CARD_NOT_FOUND.getMessage());
                    }
                    return pairHand2.getNonPairCard().getRank()
                            .compareTo(pairHand1.getNonPairCard().getRank());
                }

                return pairHand2.getPairRank()
                        .compareTo(pairHand1.getPairRank());
            }

            case HIGH_CARD -> {
                // 3. HighCard: sort the cards and compare cards in turn
                List<Card> cards1 = hand1.getCards();
                List<Card> cards2 = hand2.getCards();

                HandEvaluator.sortHandByValue(cards1);
                HandEvaluator.sortHandByValue(cards2);

                for (int i = 0; i < cards2.size(); i++) {
                    int diff = cards2.get(i).getRank()
                            .compareTo(cards1.get(i).getRank());
                    if (diff != 0) {
                        return diff;
                    }
                }
                return 0;
            }

            default -> throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
        }
    }
}
