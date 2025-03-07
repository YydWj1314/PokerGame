package model.hands;

import model.Card;
import model.enumuration.CardRank;
import model.enumuration.CardSuit;
import model.enumuration.HandRank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StraightFlushTest {
    private StraightFlush straightFlushTest1;

    @BeforeEach
    void setUp() {
        // [♥A, ♥Q, ♥K]
        List<Card> straightFlush1 = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.QUEEN),
                new Card(CardSuit.HEARTS, CardRank.KING));

        straightFlushTest1 = new StraightFlush(straightFlush1);
        System.out.println(straightFlush1);
    }

    @Test
    void getHandName() {
        Assertions.assertEquals(HandRank.STRAIGHT_FLUSH, straightFlushTest1.getHandName());
    }

    @Test
    void getHandValue() {
        Assertions.assertEquals(6, straightFlushTest1.getHandValue());
    }

    @Test
    void getMainCards() {
        List<Card> expected = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
                new Card(CardSuit.HEARTS, CardRank.QUEEN),
                new Card(CardSuit.HEARTS, CardRank.KING));
        Assertions.assertEquals(expected, straightFlushTest1.getMainCards());
    }

    @Test
    void getStraightFlushSuit() {
        Assertions.assertEquals(CardSuit.HEARTS, straightFlushTest1.getStraightFlushSuit());

    }
}