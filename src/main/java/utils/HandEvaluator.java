package utils;

import model.Card;
import model.Hand;
import model.enumuration.CardSuit;
import model.enumuration.ExceptionMessage;
import model.exception.EmptyDeckException;
import model.exception.InvalidCardsCountException;
import model.hands.*;

import java.util.*;
import java.util.stream.Collectors;

public class HandEvaluator {

    /**
     * Sorting cards by card value in descending order
     *
     * @param cards list of unsorted cards
     */
    public static void sortHandByValue(List<Card> cards) {
        cards.sort(new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return Integer.compare(o2.getRank().getValue(), o1.getRank().getValue());
            }
        });
    }

    /**
     * Helping judge the Poker Hand Rank
     *
     * @param handCards
     * @return
     */
    public static Hand getResult(List<Card> handCards) {
        if (handCards == null || handCards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        if (handCards.size() != 3) {
            throw new InvalidCardsCountException(ExceptionMessage.INVALID_CARDS_COUNT.getMessage());
        }

        if (HandEvaluator.isStraightFlush(handCards)) return new StraightFlush(handCards);
        if (HandEvaluator.isThreeOfOneKind(handCards)) return new ThreeOfOneKind(handCards);
        if (HandEvaluator.isFlush(handCards)) return new Flush(handCards);
        if (HandEvaluator.isStraight(handCards)) return new Straight(handCards);
        if (HandEvaluator.isPair(handCards)) return new Pair(handCards);
        if (HandEvaluator.isHighCard(handCards)) return new HighCard(handCards);
        return null;
    }


    /**
     * Judging Straight
     *
     * @param cards
     * @return
     */
    public static boolean isStraight(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        // 1.Checking the number of cards
        if (cards.size() != 3) return false;

        // 2.Mapping the list of card value and sorting in reverse order
        List<Integer> cardsValue = cards.stream()
                .map(card -> card.getRank().getValue())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
//        System.out.println("Straight Value:" + cardsValue);

        // 3.Checking special straight case: A-3-2
        List<Integer> lowAceStraight = Arrays.asList(14, 3, 2);
        if (new HashSet<>(cardsValue).containsAll(lowAceStraight)) {
            return true;
        }

        // 4.Checking normal straight case
        for (int i = 0; i < cardsValue.size() - 1; i++) {
            if (cardsValue.get(i) - cardsValue.get(i + 1) != 1)
                return false;
        }
        return true;
    }

    /**
     * Judging Flush
     *
     * @param cards
     * @return
     */
    public static boolean isFlush(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        // 1.Checking the number of cards
        if (cards.size() != 3) return false;

        // 2.Mapping to the set of card suit
        Set<CardSuit> cardSuitSet = cards.stream()
                .map(card -> card.getSuit())
                .collect(Collectors.toSet());
//        System.out.println("Flush Suit:" + cardSuitSet);

        // 3.Checking number of elements in the set: =1 -> is flush, >1 ->not flush
        if (cardSuitSet.size() > 1) {
            return false;
        }

        return true;
    }

    /**
     * Judging StraightFlush
     *
     * @param cards
     * @return
     */
    public static boolean isStraightFlush(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        // pay attention to the sequence of condition!
        if (HandEvaluator.isStraight(cards) && HandEvaluator.isFlush(cards)) {
            return true;
        }

        return false;
    }


    /**
     * Judging Three of one kind
     *
     * @param cards
     * @return
     */
    public static boolean isThreeOfOneKind(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        // 1.Checking the number of cards
        if (cards.size() != 3) return false;

        // 2.Mapping to the Set of card value
        Set<Integer> cardValueSet = cards.stream()
                .map(card -> card.getRank().getValue())
                .collect(Collectors.toSet());
        if (cardValueSet.size() > 1) {
            return false;
        }

        return true;
    }

    /**
     * Judging Pair
     *
     * @param cards
     * @return
     */
    public static boolean isPair(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }
        if (cards.size() != 3) {
            return false;
        }

        Set<Integer> cardValueSet = cards.stream()
                .map(card -> card.getRank().getValue())
                .collect(Collectors.toSet());

        if (cardValueSet.size() != 2) {
            return false;
        }
        return true;
    }

    /**
     * Judging High Card
     *
     * @param cards
     * @return
     */
    public static boolean isHighCard(List<Card> cards) {
        if (cards == null || cards.isEmpty()) {
            throw new EmptyDeckException(ExceptionMessage.EMPTY_HAND.getMessage());
        }

        if (cards.size() != 3) {
            return false;
        }

        if (!isStraight(cards)
                && !isFlush(cards)
                && !isThreeOfOneKind(cards)
                && !isPair(cards)) {
            return true;
        }
        return false;
    }
}
