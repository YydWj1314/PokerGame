package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PairTest {
    private Pair pairTest1;

    @BeforeEach
    void setUp() {
        // [♣7, ♦5, ♠5]
        List<Card> pair1 = new ArrayList<>();
        pair1.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
        pair1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
        pair1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
        System.out.println(pair1);
        this.pairTest1 = new Pair(pair1);

    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.PAIR, pairTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(2, pairTest1.getHandValue());
    }

    @Test
    void getPairRank() {
        Assertions.assertEquals(CardRank.FIVE, pairTest1.getPairRank());
    }

    @Test
    void getNonPairCard() {
        Card nonPairCard = pairTest1.getNonPairCard();
        System.out.println(nonPairCard);
        Assertions.assertEquals(new Card(CardSuit.CLUBS, CardRank.SEVEN),
               nonPairCard );
    }

    @Test
    void getMainCards() {
        List<Card> mainCards = pairTest1.getMainCards();
        List<Card> expected = Arrays.asList(
                new Card(CardSuit.DIAMONDS, CardRank.FIVE),
                new Card(CardSuit.SPADES, CardRank.FIVE)
        );
        Assertions.assertEquals(expected, mainCards);
    }
}